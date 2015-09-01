/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exesis.core.teste;
import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.core.dao.IDAO;
import exesis.core.dao.impl.ProfessorDAO;
import exesis.model.Professor;
import exesis.model.Usuario;
import java.util.Date;

/**
 *
 * @author SAMUEL
 */
public class TesteBancoSQL {
    
    public static void main(String[] args) {
         
       testeFachadaProfessor();
    }
    public  static void testeDAOProfessor(){
        
        IDAO dao = new ProfessorDAO();
        Professor professor = new Professor();
        Usuario usuario = new Usuario();
        
        usuario.setLogin("samuel.barboza");
        usuario.setEmail("samuel.barboza@live.com");
        usuario.setSenha("senha");
        usuario.setPerfilAcesso(1);
        usuario.setDtCadastro(new Date());
        
        professor.setNome("Samuel");
        professor.setSobrenome("de Oliveira Barboza");
        professor.setCpf("43403763889");
//        professor.setDataNascimento(new Date());
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
        usuario.setSenha("senha");
        usuario.setPerfilAcesso(1);
        usuario.setDtCadastro(new Date());
        
        professor.setNome("Samuel");
        professor.setSobrenome("de Oliveira Barboza");
        professor.setCpf("43403763889");
        professor.setDataNascimento("08/11/1995");
        professor.setSexo("M");
        professor.setInformacoesAdicionais("Testando informações adicionais");
        professor.setTelefone("973652912");
        
        professor.setUsuario(usuario);
        
        Resultado resultado = fachada.salvar(professor);
        if(!resultado.getMsgs().isEmpty())
            for(String s: resultado.getMsgs())
                System.out.print(s);
        
    }
}
