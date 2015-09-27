
package exesis.teste.aluno;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.core.dao.IDAO;
import exesis.core.dao.hibernate.AlunoHibernate;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import exesis.model.Usuario;
import java.util.Date;



public class TesteAluno {
    
    public static void main(String[] args) {
         
       testeFachadaAlunoConsultar();
    }
    
    // teste DAO - Funcionando
    public  static void testeDAOAluno(){
        
        IDAO dao = new AlunoHibernate();
        Aluno aluno = new Aluno();
        Usuario usuario = new Usuario();
        
        usuario.setLogin("vinicius.oliveira");
        usuario.setEmail("vinicius.oliveira40@fatec.sp.gov.br");
        usuario.setSenha("senha");
        usuario.setPerfilAcesso(5);
        usuario.setDtCadastro(new Date());
        
        aluno.setNome("Vinicius Fellipe");
        aluno.setSobrenome("Melo de Oliveira");
        aluno.setMatricula("184048131132");
        aluno.setDataNascimento("31/08/1995");
        aluno.setSexo("M");
        aluno.setTelefone("47479898");
        aluno.setUsuario(usuario);
        
        dao.salvar(aluno);
        System.out.print("Funcionou");
    }
    
     public  static void testeFachadaAluno(){
        
        IFachada fachada = new Fachada();
                Aluno aluno = new Aluno();
        Usuario usuario = new Usuario();
        
        usuario.setLogin("LALALALALA");
        usuario.setEmail("LALALALALA.fachada@LALALALALA.sp.gov.br");
        usuario.setSenha("LALALALALA");
        usuario.setPerfilAcesso(5);
        usuario.setDtCadastro(new Date());
        
        aluno.setNome("LALALALALA");
        aluno.setSobrenome("LALALALALA");
        aluno.setMatricula("LALALALALA");
        aluno.setDataNascimento("31/08/1995");
        aluno.setSexo("M");
        aluno.setTelefone("47479898");
        
        aluno.setUsuario(usuario);
               
        fachada.salvar(aluno);
        System.out.print("Funcionou");
    }

    public  static void testeFachadaAlunoConsultar(){
        
        IFachada fachada = new Fachada();
        Aluno aluno = new Aluno();
        Usuario usuario = new Usuario();
        
        usuario.setId(17);
        aluno.setUsuario(usuario);
        Resultado resultado = fachada.consultar(aluno);
        
        for(EntidadeDominio e: resultado.getEntidades()){
            if(e instanceof Aluno){
               aluno = (Aluno) e;
               System.out.println("Nome:" + aluno.getNome());
               System.out.println("Sobrenome:" + aluno.getSobrenome());
               System.out.println("Sexo:" + aluno.getSexo());
               System.out.println("Nome:" + aluno.getTelefone());
               System.out.println("Nome:" + aluno.getMatricula());
               System.out.println("Nome:" + aluno.getDataNascimento());
               System.out.println("Nome:" + aluno.getInformacoesAdicionais());
               System.out.println("Nome:" + aluno.getUsuario().getEmail());
               System.out.println("Nome:" + aluno.getUsuario().getLogin());
               System.out.println("Nome:" + aluno.getUsuario().getId());
            }
        }
        System.out.print("Fachada CONSULTAR funcionou!");
    }
     
     public static void testeDAOConsultar(){
        IDAO dao = new AlunoHibernate();
        Aluno aluno = new Aluno();
        Usuario usuario = new Usuario();
        
        usuario.setId(22);
        aluno.setUsuario(usuario);
        Resultado resultado = dao.consultar(aluno);
        for(EntidadeDominio e: resultado.getEntidades()){
            if(e instanceof Aluno){
               aluno = (Aluno) e;
               System.out.println("Nome:" + aluno.getNome());
               System.out.println("Sobrenome:" + aluno.getSobrenome());
               System.out.println("Sexo:" + aluno.getSexo());
               System.out.println("Nome:" + aluno.getTelefone());
               System.out.println("Nome:" + aluno.getMatricula());
               System.out.println("Nome:" + aluno.getDataNascimento());
               System.out.println("Nome:" + aluno.getInformacoesAdicionais());
               System.out.println("Nome:" + aluno.getUsuario().getEmail());
               System.out.println("Nome:" + aluno.getUsuario().getLogin());
               System.out.println("Nome:" + aluno.getUsuario().getId());
            }
        }
        System.out.println("Consultou");
     }
}
