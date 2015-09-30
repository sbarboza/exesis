package exesis.teste.popularBanco;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.Administrador;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import exesis.model.Usuario;
import java.util.Random;

public class PopularBancoHibernate {
    public void popular(int quantidade){
        popularAdministrador(quantidade);
        popularAluno(quantidade);
        popularProfessor(quantidade);
        popularResponsavel(quantidade);
    }
    
    public void popularProfessor(int quantidade){
        for(int i = 0; i < quantidade; i++){
            Professor professor = new Professor();
            Random rand = new Random();
            professor.setNome("testeNome"+ rand.nextInt(i+quantidade));
            professor.setSobrenome("testeSobrenome"+ rand.nextInt(i+quantidade));
            professor.setDataNascimento("08/11/1995");
            professor.setInformacoesAdicionais("GERADO AUTOMATICAMENTE");
            professor.setTelefone("(11) 97334-3442");
            professor.setSexo("M");
            
            Usuario usuario = new Usuario();
            usuario.setLogin("teste"+rand.nextInt(i+quantidade));
            usuario.setEmail("teste"+i+"@teste.com");
            usuario.setSenha("senha");
            
            professor.setUsuario(usuario);
            
            salvar(professor);
            
        }
    }
    
    public void popularAluno(int quantidade){
        for(int i = 0; i < quantidade; i++){
            Aluno aluno = new Aluno();
            Random rand = new Random();
            aluno.setNome("testeNome"+ rand.nextInt(i+quantidade));
            aluno.setSobrenome("testeSobrenome"+ rand.nextInt(i+quantidade));
            aluno.setDataNascimento("08/11/1995");
            aluno.setInformacoesAdicionais("GERADO AUTOMATICAMENTE");
            aluno.setTelefone("(11) 97334-3442");
            aluno.setSexo("M");
            aluno.setMatricula("2015"+rand.nextInt(i+quantidade));
            Usuario usuario = new Usuario();
            usuario.setLogin("teste"+rand.nextInt(i+quantidade));
            usuario.setEmail("teste"+i+"@teste.com");
            usuario.setSenha("senha");
            aluno.setUsuario(usuario);
            salvar(aluno);
        }
    }
    public void popularAdministrador(int quantidade){
        for(int i = 0; i < quantidade; i++){
            Administrador admin = new Administrador();
            Random rand = new Random();
            admin.setNome("testeNome"+ rand.nextInt(i+quantidade));
            admin.setSobrenome("testeSobrenome"+ rand.nextInt(i+quantidade));
            admin.setDataNascimento("08/11/1995");
            admin.setInformacoesAdicionais("GERADO AUTOMATICAMENTE");
            admin.setTelefone("(11) 97334-3442");
            admin.setSexo("M");
            Usuario usuario = new Usuario();
            usuario.setLogin("teste"+rand.nextInt(i+quantidade));
            usuario.setEmail("teste"+i+"@teste.com");
            usuario.setSenha("senha");
            admin.setUsuario(usuario);
            
            salvar(admin);
        }
    }
    public void popularResponsavel(int quantidade){
        for(int i = 0; i < quantidade; i++){
            ResponsavelAluno responsavel = new ResponsavelAluno();
            Random rand = new Random();
            responsavel.setNome("testeNome"+ rand.nextInt(i+quantidade));
            responsavel.setSobrenome("testeSobrenome"+ rand.nextInt(i+quantidade));
            responsavel.setDataNascimento("08/11/1995");
            responsavel.setInformacoesAdicionais("GERADO AUTOMATICAMENTE");
            responsavel.setTelefone("(11) 97334-3442");
            responsavel.setSexo("M");
            Usuario usuario = new Usuario();
            usuario.setLogin("teste"+rand.nextInt(i+quantidade));
            usuario.setEmail("teste"+i+"@teste.com");
            usuario.setSenha("senha");
            responsavel.setUsuario(usuario);
            
            salvar(responsavel);
        }
    }
    
    private void salvar(EntidadeDominio entidade){
        Resultado resultado = Resultado.getResultado();
            IFachada fachada = new Fachada();
            resultado = fachada.salvar(entidade);
            if(!resultado.getMsgs().isEmpty()){
                for(String s: resultado.getMsgs())
                    System.out.println(s);
            }
    }
}
