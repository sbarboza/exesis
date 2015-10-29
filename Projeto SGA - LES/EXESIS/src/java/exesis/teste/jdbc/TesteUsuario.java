package exesis.teste.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.Usuario;

public class TesteUsuario {
    public static IFachada fachada;
    public static Resultado resultado;
    public static void main(String[] args) {
        consultarUsuario();
    }
    public static void cadastrarUsuario(){
        fachada = new Fachada();
        Usuario usuario = new Usuario();
        usuario.setEmail("login@login.com");
        usuario.setLogin("login");
        usuario.setPerfilAcesso(usuario.ADMINISTRADOR);
        usuario.setSenha("senha");
        fachada.salvar(usuario);
    }
    
    public static void alterarUsuario(){
        fachada = new Fachada();
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setSenha("logon@logon.com");
        fachada.alterar(usuario);
    }
    
    public static void consultarUsuario(){
        fachada = new Fachada();
        Aluno usuario = new Aluno();
        usuario.setId(1);
        resultado = fachada.consultar(usuario);
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Aluno u = (Aluno) e;
                TesteProfessor.testeAluno(u);
            }
        }
    }
    
    public static void excluirUsuario(){
    }
    
    public static void testeUsuario(Usuario u){
        System.out.println("ID USUARIO: " + u.getId());
        System.out.println("LOGIN USUARIO: " + u.getLogin());
        System.out.println("EMAIL USUARIO: " + u.getEmail());
        System.out.println("PERFIL USUARIO: " + u.getPerfilAcesso());
    }
    
}
