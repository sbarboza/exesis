package exesis.core.dao.sql.impl;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.core.dao.sql.AbstractDAOPostGreSQL;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import exesis.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class AlunoDAO extends AbstractDAOPostGreSQL{
   
    public AlunoDAO() {
        super("tbAlunos","idAluno");
    }
    
    @Override
    public Resultado salvar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        openConnection();
        PreparedStatement pst=null;
        Aluno aluno = (Aluno) entidade;
        Usuario usuario = aluno.getUsuario();
        try {
                connection.setAutoCommit(false);
                ctrlTransaction = false;
                IDAO dao = new UsuarioDAO();
                resultado = dao.salvar(usuario);
                StringBuilder sql = new StringBuilder();
                sql.append("INSERT INTO ");
                sql.append(table);
                sql.append("(idAluno, nome, sobrenome, sexo, dataNascimento, matricula,telefone, informacoesadicionais)");
                sql.append(" VALUES(?,?,?,?,?,?,?,?);");
                pst = connection.prepareStatement(sql.toString());
                usuario = (Usuario) resultado.getEntidades().get(0);
                resultado.getEntidades().clear();
                pst.setInt(1, usuario.getId());
                pst.setString(2, aluno.getNome());
                pst.setString(3, aluno.getSobrenome());
                pst.setString(4,aluno.getSexo());
                pst.setString(5, aluno.getDataNascimento());
                pst.setString(6, aluno.getMatricula());
                pst.setString(7, aluno.getTelefone());
                pst.setString(8, aluno.getInformacoesAdicionais());
                pst.executeUpdate();
                System.out.println(pst.toString());
                ctrlTransaction = true;
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
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();
        Aluno aluno = (Aluno) entidade;
        IDAO dao = new UsuarioDAO();
        try {
                resultado = dao.consultar(aluno.getUsuario());
                if(!resultado.getEntidades().isEmpty()){
                    aluno.setUsuario((Usuario) resultado.getEntidades().get(0));
                    System.out.println("USUARIO");
                }
                else
                    aluno.setUsuario(new Usuario());
                openConnection();
                sql.append("SELECT * FROM ");
                sql.append(table);                
                sql.append(" WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(aluno.getUsuario().getId());
                sql.append(";");
                pst = connection.prepareStatement(sql.toString());
                ResultSet rs = pst.executeQuery();
                resultado.getEntidades().clear();
                while(rs.next()){
                    aluno.setDataNascimento(rs.getString("dataNascimento"));
                    aluno.setId(rs.getInt(idTable));
                    aluno.setInformacoesAdicionais(rs.getString("observacoes"));
                    aluno.setNome(rs.getString("nome"));
                    aluno.setSexo(rs.getString("sexo"));
                    aluno.setSobrenome(rs.getString("sobrenome"));
                    aluno.setTelefone(rs.getString("telefone"));
                    aluno.setMatricula(rs.getString("matricula"));
                    resultado.getEntidades().add(aluno);
                }
        } catch (SQLException e) {
            e.printStackTrace();			
        }
        return resultado;
    }
    
    @Override
    public Resultado excluir(EntidadeDominio entidade){
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst=null;		
        StringBuilder sql = new StringBuilder();
        Aluno aluno = (Aluno) entidade;
        IDAO dao = new UsuarioDAO();
        try {
                openConnection();
                ctrlTransaction = false;
                resultado = dao.excluir(aluno.getUsuario());
                sql.append("DELETE FROM ");
                sql.append(table);
                sql.append(" WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(aluno.getUsuario().getId());	
                sql.append(";");
                System.out.println(sql.toString());
                connection.setAutoCommit(false);
                pst = connection.prepareStatement(sql.toString());
                pst.executeUpdate();
                ctrlTransaction = true;
                connection.commit();
                
        } catch (SQLException e) {
                resultado.getMsgs().add(e.getMessage());
                try {
                        connection.rollback();
                } catch (SQLException e1) {
                        resultado.getMsgs().add(e1.getMessage());
                }
        }finally{
                try {
                        pst.close();
                        if(ctrlTransaction)
                                connection.close();
                } catch (SQLException e) {
                        resultado.getMsgs().add(e.getMessage());
                }
                resultado.setMsg("Registro excluido com sucesso!");
        }

        return resultado;
    } 
    
    @Override
    public Resultado alterar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        System.out.println("ALTERAR - PROFESSOR");
        PreparedStatement pst=null;
        StringBuilder sql= new StringBuilder();
        Aluno aluno = (Aluno) entidade;
        IDAO dao = new UsuarioDAO();
        
        try {
                openConnection();
                ctrlTransaction = false;
                resultado = dao.alterar(aluno.getUsuario());
                sql.append("UPDATE ");
                sql.append(table);
                sql.append(" SET nome=?, sobrenome=?, sexo=?, matricula=?, telefone=?, observacoes=?, dataNascimento=? WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(aluno.getUsuario().getId());
                sql.append(";");
                System.out.println(sql.toString());
                pst = connection.prepareStatement(sql.toString());
                pst.setString(1, aluno.getNome());
                pst.setString(2, aluno.getSobrenome());
                pst.setString(3, aluno.getSexo());
                pst.setString(4, aluno.getMatricula());
                pst.setString(5, aluno.getTelefone());
                pst.setString(6, aluno.getInformacoesAdicionais());
                pst.setString(7, aluno.getDataNascimento());
                pst.executeUpdate();
                System.out.println(sql.toString());
                ctrlTransaction = true;
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
        return resultado;    }
    
}
