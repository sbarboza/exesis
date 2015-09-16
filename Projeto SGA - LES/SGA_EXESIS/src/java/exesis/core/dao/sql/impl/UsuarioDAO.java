package exesis.core.dao.sql.impl;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.sql.AbstractDAOPostGreSQL;
import exesis.model.EntidadeDominio;
import exesis.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO extends AbstractDAOPostGreSQL{

    public UsuarioDAO(){
        super("tbUsuarios", "id");
    }
    
    public Resultado salvar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        openConnection();
        PreparedStatement pst=null;
        Usuario usuario = (Usuario) entidade;   
        try {
                connection.setAutoCommit(false);
                StringBuilder sql = new StringBuilder();
                sql.append("INSERT INTO ");
                sql.append(table);
                sql.append("(email, login, senha, nivelAcesso, dataCadastro)");
                sql.append(" VALUES(?,?,md5(?),?,?);");
                pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, usuario.getEmail());
                pst.setString(2, usuario.getLogin());
                pst.setString(3, usuario.getSenha());
                pst.setInt(4, usuario.getPerfilAcesso());
                java.sql.Timestamp data = new java.sql.Timestamp(usuario.getDtCadastro().getTime());
                pst.setTimestamp(5, data);
                pst.executeUpdate();
                connection.commit();
                ResultSet rs = pst.getGeneratedKeys();
                if(rs != null && rs.next())
                    entidade.setId(rs.getInt(idTable));
        } catch (SQLException e) {
                try {
                        connection.rollback();
                } catch (SQLException e1) {
                        e1.printStackTrace();
                }
                e.printStackTrace();			
        }finally{
                try {
                        pst.close();
                        if(ctrlTransaction)
                                connection.close();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
        resultado.getEntidades().add(entidade);
        return resultado;
    }

    @Override
    public Resultado alterar(EntidadeDominio entidade){
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst=null;
        StringBuilder sql= new StringBuilder();
        Usuario usuario = (Usuario) entidade;
        try {
                openConnection();
                sql.append("UPDATE ");
                sql.append(table);
                sql.append(" SET login=?, email=? WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(usuario.getId());
                sql.append(";");
                System.out.println(sql.toString());
                pst = connection.prepareStatement(sql.toString());
                pst.setString(1, usuario.getLogin());
                pst.setString(2, usuario.getEmail());
                pst.executeUpdate();
                connection.commit();
        } catch (SQLException e) {
                try {
                        connection.rollback();
                } catch (SQLException e1) {
                        e1.printStackTrace();
                }
                e.printStackTrace();			
        }finally{
                try {
                        pst.close();
                        if(ctrlTransaction)
                                connection.close();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
        return resultado;
    }

    @Override
    public Resultado consultar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        Usuario usuario = (Usuario) entidade;
        PreparedStatement pst = null;
        boolean where = false;
        StringBuilder sql = new StringBuilder();
        try {
                openConnection();
                sql.append("SELECT * FROM ");
                sql.append(table);
                if(usuario.getId() >= 0){
                    sql.append(where? " OR " : " WHERE ");
                    sql.append(idTable);
                    sql.append(" = ");
                    sql.append(usuario.getId());
                    where =true;
                }
                if(usuario.getEmail() != null && usuario.getEmail().trim().length() != 0){
                    sql.append(where? " OR " : " WHERE ");
                    sql.append(" email like '%");
                    sql.append(usuario.getEmail());
                    sql.append("%'");
                    where = true;
                }
                if(usuario.getLogin() != null && usuario.getLogin().trim().length() != 0){
                    sql.append(where? " OR " : " WHERE ");
                    sql.append(" login = '");
                    sql.append(usuario.getLogin());
                    sql.append("'");
                    
                }
                sql.append(";");
                pst = connection.prepareStatement(sql.toString());
                ResultSet rs = pst.executeQuery();
                while(rs.next()){
                    usuario = new Usuario();
                    usuario.setDtCadastro(rs.getDate("dataCadastro"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setId(rs.getInt(idTable));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setPerfilAcesso(rs.getInt("nivelAcesso"));
                    usuario.setSenha("senha");
                    resultado.getEntidades().add(usuario);
                }
                System.out.println(pst.toString());
                System.out.println(sql.toString());
                
        } catch (SQLException e) {
            e.printStackTrace();			
        }
        return resultado;
    }
    
}
