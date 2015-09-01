/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exesis.core.dao.impl;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.AbstractDAOPostGreSQL;
import exesis.core.dao.IDAO;
import exesis.model.EntidadeDominio;
import exesis.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author SAMUEL
 */
public class UsuarioDAO extends AbstractDAOPostGreSQL{

    public UsuarioDAO(){
        super("tbUsuarios", "idUsuario");
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
                System.out.println(pst.toString());
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
        System.out.println("ALTERAR - USUARIO");
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
                pst = connection.prepareStatement(sql.toString());
                pst.setString(1, usuario.getLogin());
                pst.setString(2, usuario.getSenha());
                pst.setString(3, usuario.getEmail());
                pst.executeUpdate();
                System.out.println(sql.toString());
                System.out.println(pst.toString());
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
        System.out.println("PROFESSOR");
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();
        try {
                openConnection();
                sql.append("SELECT * FROM ");
                sql.append(table);                
                sql.append(" WHERE ");
                sql.append(" idUsuario = ");
                sql.append(entidade.getId());
                sql.append(";");
                System.out.println(sql.toString());
                pst = connection.prepareStatement(sql.toString());
                ResultSet rs = pst.executeQuery();
                while(rs.next()){
                    Usuario usuario = new Usuario();
                    usuario.setDtCadastro(rs.getDate("dataCadastro"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setId(rs.getInt(idTable));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setPerfilAcesso(rs.getInt("nivelAcesso"));
                    usuario.setSenha("senha");
                    resultado.getEntidades().add(usuario);
                }
                
        } catch (SQLException e) {
            e.printStackTrace();			
        }
        return resultado;
    }
    
}
