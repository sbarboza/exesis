package exesis.teste.responsavel;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.core.dao.IDAO;
import exesis.core.dao.hibernate.HibernateDAO;
import exesis.model.Aluno;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import exesis.model.Usuario;
import java.util.ArrayList;
import java.util.Date;

public class TesteHibernate {
     public static void main(String[] args) {
         
       testeDAOProfessor();
    }
    public  static void testeDAOProfessor(){
        
        IDAO dao = new HibernateDAO();
        ResponsavelAluno pai = new ResponsavelAluno();
        Usuario usuario = new Usuario();
        Aluno aluno = new Aluno();
        Aluno aluno2 = new Aluno();
        
        usuario.setLogin("samuel.barboza");
        usuario.setEmail("samuel.barboza@live.com");
        usuario.setSenha("senha");
        usuario.setPerfilAcesso(Usuario.RESPONSAVEL_ALUNO);
        usuario.setDtCadastro(new Date());
        pai.setDataNascimento("08/11/1995");
        pai.setNome("Samuel PAI");
        pai.setSobrenome("de Oliveira Barboza");
        pai.setSexo("M");
        pai.setInformacoesAdicionais("Testando informações adicionais");
        pai.setTelefone("973652912");
        pai.setUsuario(usuario);
        
        aluno.setNome("Aluno1");
        aluno2.setNome("Aluno2");
        pai.setAlunos(new ArrayList<Aluno>());
        pai.getAlunos().add(aluno);
        pai.getAlunos().add(aluno2);
       
        Resultado resultado = dao.salvar(pai);
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
