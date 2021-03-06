/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.teste.professor;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.core.dao.IDAO;
import exesis.core.dao.hibernate.ProfessorHibernate;
import exesis.model.Professor;
import exesis.model.Usuario;
import java.util.Date;

/**
 *
 * @author SAMUEL
 */
public class TesteHibernate {
     public static void main(String[] args) {
         
       testeFachadaProfessor();
    }
    public  static void testeDAOProfessor(){
        
        IDAO dao = new ProfessorHibernate();
        Professor professor = new Professor();
        Usuario usuario = new Usuario();
        
        usuario.setLogin("samuel.barboza");
        usuario.setEmail("samuel.barboza@live.com");
        usuario.setSenha("senha");
        usuario.setPerfilAcesso(1);
        usuario.setDtCadastro(new Date());
        professor.setDataNascimento("08/11/1995");
        professor.setNome("Samuel");
        professor.setSobrenome("de Oliveira Barboza");
        professor.setSexo("M");
        professor.setInformacoesAdicionais("Testando informações adicionais");
        professor.setTelefone("973652912");
        
        professor.setUsuario(usuario);
        
        Resultado resultado = dao.salvar(professor);
        if(!resultado.getMsgs().isEmpty())
            for(String s: resultado.getMsgs())
                System.out.print(s);
            
    }
     public  static void testeFachadaProfessor(){
        
        IFachada fachada = new Fachada();
        Professor professor = new Professor();
        Usuario usuario = new Usuario();
        
        usuario.setLogin("samuel.barboza");
        usuario.setEmail("samuel.barboza@live.com");
        usuario.setPerfilAcesso(1);
        usuario.setDtCadastro(new Date());
        usuario.setSenha("senha");
        professor.setNome("Samuel");
        professor.setSobrenome("de Oliveira Barboza");
        professor.setDataNascimento("08/11/1995");
        professor.setSexo("M");
        professor.setInformacoesAdicionais("Testando informações adicionais");
        professor.setTelefone("973652912");
        
        professor.setUsuario(usuario);
        
        Resultado resultado = null;
        for(int i = 0; i < 3; i++){
            resultado = fachada.salvar(professor);
        }
        if(!resultado.getMsgs().isEmpty())
            for(String s: resultado.getMsgs())
                System.out.print(s);
        
    }
    
}
