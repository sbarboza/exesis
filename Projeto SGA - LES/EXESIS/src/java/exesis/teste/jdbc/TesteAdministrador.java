package exesis.teste.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.Administrador;
import exesis.model.EntidadeDominio;
import exesis.model.Usuario;
import java.util.Date;

public class TesteAdministrador {
    public static IFachada fachada;
    public static Resultado resultado;
    public static void main(String[] args) {
        cadastrarUsuario();
        alterarUsuario();
        consultarUsuario();
        excluirUsuario();
    }
    public static void cadastrarUsuario(){
        fachada = new Fachada();
        Usuario usuario = new Usuario();
        usuario.setEmail("login@login.com");
        usuario.setLogin("login");
        usuario.setPerfilAcesso(usuario.ADMINISTRADOR);
        usuario.setSenha("senha");
        Administrador admin = new Administrador();
        admin.setDtCadastro(new Date());
        admin.setInformacoesAdicionais("teste");
        admin.setNome("administrador");
        admin.setSobrenome("sobrenome");
        admin.setDataNascimento("08/11/1995");
        admin.setSexo("M");
        admin.setTelefone("(11) 94239-4234");
        admin.setUsuario(usuario);
        
        fachada.salvar(admin);
    }
    
    public static void alterarUsuario(){
        fachada = new Fachada();
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@lotesgin.com");
        usuario.setLogin("teste");
        usuario.setId(1);
        usuario.setPerfilAcesso(usuario.ADMINISTRADOR);
        Administrador admin = new Administrador();
        admin.setDtCadastro(new Date());
        admin.setId(1);
        admin.setInformacoesAdicionais("teste");
        admin.setNome("teste");
        admin.setSobrenome("teste");
        admin.setDataNascimento("08/01/1995");
        admin.setSexo("M");
        admin.setTelefone("(11) 94239-1111");
        admin.setUsuario(usuario);
        
        fachada.alterar(admin);
    }
    
    public static void consultarUsuario(){
        fachada = new Fachada();
        Usuario usuario = new Usuario();
        Administrador admin = new Administrador();
        usuario.setLogin("login");
        admin.setUsuario(usuario);
        resultado = fachada.consultar(admin);
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Administrador u = (Administrador) e;
                testeAdmin(u);
            }
        }
    }
    
    public static void excluirUsuario(){
    }
    
    
    public static void testeAdmin(Administrador a){
        System.out.println("ID ADMIN: " + a.getId());
        System.out.println("NOME ADMIN: " + a.getNome());
        System.out.println("SOBRENOME ADMIN: " + a.getSobrenome());
        System.out.println("SEXO ADMIN: " + a.getSexo());
        System.out.println("DTNASCIMENTO ADMIN: " + a.getDataNascimento());
        System.out.println("DTCADASTRO ADMIN: " + a.getDtCadastro());
        System.out.println("TELEFONE ADMIN: " + a.getTelefone());
        System.out.println("INFO ADMIN: " + a.getInformacoesAdicionais());
        TesteUsuario.testeUsuario(a.getUsuario());
        
    }
    
    
}
