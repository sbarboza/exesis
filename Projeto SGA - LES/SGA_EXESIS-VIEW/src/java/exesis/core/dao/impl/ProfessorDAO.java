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
import exesis.model.Professor;
import exesis.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author SAMUEL
 */
public class ProfessorDAO extends AbstractDAOPostGreSQL{

    public ProfessorDAO() {
        super("tbProfessores","idProfessor");
    }

    
    public Resultado salvar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        openConnection();
        PreparedStatement pst=null;
        Professor professor = (Professor) entidade;
        Usuario usuario = professor.getUsuario();
        try {
                connection.setAutoCommit(false);
                ctrlTransaction = false;
                IDAO dao = new UsuarioDAO();
                resultado = dao.salvar(usuario);
                StringBuilder sql = new StringBuilder();
                sql.append("INSERT INTO "); 
                sql.append(table);
                sql.append("(idProfessor, nome, sobrenome, sexo, dataNascimento, telefone, informacoesAdicionais)");
                sql.append(" VALUES(?,?,?,?,?,?,?);");
                pst = connection.prepareStatement(sql.toString());
                usuario = (Usuario) resultado.getEntidades().get(0);
                resultado.getEntidades().clear();
                pst.setInt(1, usuario.getId());
                pst.setString(2, professor.getNome());
                pst.setString(3, professor.getSobrenome());
                pst.setString(4,professor.getSexo());
                pst.setString(5, professor.getDataNascimento());
                pst.setString(6, professor.getTelefone());
                pst.setString(7, professor.getInformacoesAdicionais());
                pst.executeUpdate();
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
    public Resultado alterar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        System.out.println("ALTERAR - PROFESSOR");
        PreparedStatement pst=null;
        StringBuilder sql= new StringBuilder();
        Professor professor = (Professor) entidade;
        IDAO dao = new UsuarioDAO();
        
        try {
                openConnection();
                ctrlTransaction = false;
                resultado = dao.alterar(professor.getUsuario());
                sql.append("UPDATE ");
                sql.append(table);
                sql.append(" SET nome=?, sobrenome=?, sexo=?, dataNascimento=?, telefone=?, informacoesAdicionais=? WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(professor.getUsuario().getId());
                sql.append(";");
                System.out.println(sql.toString());
                pst = connection.prepareStatement(sql.toString());
                pst.setString(1, professor.getNome());
                pst.setString(2, professor.getSobrenome());
                pst.setString(3, professor.getSexo());
                pst.setString(4, professor.getDataNascimento());
                pst.setString(5, professor.getTelefone());
                pst.setString(6, professor.getInformacoesAdicionais());
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
        return resultado;
    }
    
    public Resultado excluir(EntidadeDominio entidade){
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst=null;		
        StringBuilder sql = new StringBuilder();
        Professor professor = (Professor) entidade;
        IDAO dao = new UsuarioDAO();
        try {
                openConnection();
                ctrlTransaction = false;
                resultado = dao.excluir(professor.getUsuario());
                sql.append("DELETE FROM ");
                sql.append(table);
                sql.append(" WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(professor.getId());	
                sql.append(";");
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
                resultado.setMsg("Usuário "+professor.getNome()+" excluido com sucesso!");
        }

        return resultado;
    } 
    
    @Override
    public Resultado consultar(EntidadeDominio entidade) {
        System.out.println("PROFESSOR");
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();
        Professor professor = (Professor) entidade;
        IDAO dao = new UsuarioDAO();
        try {
                resultado = dao.consultar(professor.getUsuario());
                if(!resultado.getEntidades().isEmpty()){
                    professor.setUsuario((Usuario) resultado.getEntidades().get(0));
                    System.out.println("USUARIO");
                }
                else
                    professor.setUsuario(new Usuario());
                openConnection();
                sql.append("SELECT * FROM ");
                sql.append(table);                
                sql.append(" WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(professor.getUsuario().getId());
                sql.append(";");
                pst = connection.prepareStatement(sql.toString());
                ResultSet rs = pst.executeQuery();
                resultado.getEntidades().clear();
                while(rs.next()){
                    professor.setDataNascimento(rs.getString("dataNascimento"));
                    professor.setId(rs.getInt(idTable));
                    professor.setInformacoesAdicionais(rs.getString("informacoesAdicionais"));
                    professor.setNome(rs.getString("nome"));
                    professor.setSexo(rs.getString("sexo"));
                    professor.setSobrenome(rs.getString("sobrenome"));
                    professor.setTelefone(rs.getString("telefone"));
                    resultado.getEntidades().add(professor);
                }
        } catch (SQLException e) {
            e.printStackTrace();			
        }
        return resultado;
    }
}
