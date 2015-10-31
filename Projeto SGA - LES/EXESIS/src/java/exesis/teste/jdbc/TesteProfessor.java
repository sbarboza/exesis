package exesis.teste.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.Usuario;
import java.util.Date;

public class TesteProfessor {
    public static IFachada fachada;
    public static Resultado resultado;
    public static void main(String[] args) {
        consultarUsuario();
    }
    public static void cadastrarUsuario(){
        fachada = new Fachada();
        Usuario usuario = new Usuario();
        usuario.setEmail("professor@professor.com");
        usuario.setLogin("login11");
        usuario.setPerfilAcesso(usuario.PROFESSOR);
        usuario.setSenha("pro2f");
        Professor professor = new Professor();
        professor.setDtCadastro(new Date());
        professor.setInformacoesAdicionais("teste");
        professor.setNome("administrador");
        professor.setSobrenome("sobrenome");
        professor.setDataNascimento("08/11/1995");
        professor.setSexo("M");
        professor.setTelefone("(11) 94239-4234");
        professor.setUsuario(usuario);
        
        resultado = fachada.salvar(professor);
        if(!resultado.getMsgs().isEmpty())
            System.out.println(resultado.getMsgs().toString());
    }
    
    public static void alterarUsuario(){
        fachada = new Fachada();
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@lotesgin.com");
        usuario.setLogin("teste");
        usuario.setId(1);
        usuario.setPerfilAcesso(usuario.ADMINISTRADOR);
        Professor professor = new Professor();
        professor.setDtCadastro(new Date());
        professor.setInformacoesAdicionais("teste");
        professor.setNome("administrador");
        professor.setSobrenome("sobrenome");
        professor.setDataNascimento("08/11/1995");
        professor.setSexo("M");
        professor.setTelefone("(11) 94239-4234");
        professor.setUsuario(usuario);
        
        fachada.alterar(professor);
    }
    
    public static void consultarUsuario(){
        fachada = new Fachada();
        resultado = fachada.consultar(new Professor());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Professor u = (Professor) e;
                testeProfessor(u);
            }
        }
    }
    
    public static void excluirUsuario(){
        
    }

        public static void testeProfessor(Professor a){
        System.out.println("ID PROFESSOR: " + a.getId());
//        System.out.println("NOME PROFESSOR: " + a.getNome());
//        System.out.println("SOBRENOME PROFESSOR: " + a.getSobrenome());
//        System.out.println("SEXO PROFESSOR: " + a.getSexo());
//        System.out.println("DTNASCIMENTO PROFESSOR: " + a.getDataNascimento());
//        System.out.println("DTCADASTRO PROFESSOR: " + a.getDtCadastro());
//        System.out.println("TELEFONE PROFESSOR: " + a.getTelefone());
//        System.out.println("INFO PROFESSOR: " + a.getInformacoesAdicionais());
//        TesteUsuario.testeUsuario(a.getUsuario());
        
    }
    
    public static void testeAluno(Aluno a){
        System.out.println("ID PROFESSOR: " + a.getId());
        System.out.println("NOME PROFESSOR: " + a.getNome());
        System.out.println("SOBRENOME PROFESSOR: " + a.getSobrenome());
        System.out.println("SEXO PROFESSOR: " + a.getSexo());
        System.out.println("DTNASCIMENTO PROFESSOR: " + a.getDataNascimento());
        System.out.println("DTCADASTRO PROFESSOR: " + a.getDtCadastro());
        System.out.println("TELEFONE PROFESSOR: " + a.getTelefone());
        System.out.println("INFO PROFESSOR: " + a.getInformacoesAdicionais());
        System.out.println("INFO MATRICULA: " + a.getMatricula());
        TesteUsuario.testeUsuario(a.getUsuario());
        
    }
    
    
}
