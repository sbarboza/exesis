/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.teste.usuario;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.core.dao.IDAO;
import exesis.core.dao.hibernate.UsuarioHibernate;
import exesis.model.Usuario;

/**
 *
 * @author SAMUEL
 */
public class testeLogin {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testeProfessor();
        
    }
    
    public static void testeProfessor(){
        inserirTestes();
        IFachada fachada = new Fachada();
        Resultado resultado = Resultado.getResultado();
        Usuario usuario = new Usuario();
        usuario.setLogin("samuel.barboza");
        usuario.setSenha("senha1");
        
        resultado = fachada.consultar(usuario);
        if(resultado.getEntidades().isEmpty()){
            System.out.println("Erro no login");
        }else{
            usuario = (Usuario) resultado.getEntidades().get(0);
            System.out.println("Username: "+usuario.getLogin());
            System.out.println("Password: "+usuario.getSenha());
        }     
    }
    public static void inserirTestes(){
        IDAO dao = new UsuarioHibernate();
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");
        usuario.setSenha("admin");
        usuario.setPerfilAcesso(Usuario.ADMINISTRADOR);
        dao.salvar(usuario);
        Usuario usuario2 = new Usuario();
        usuario2.setLogin("aluno");
        usuario2.setSenha("aluno");
        usuario2.setPerfilAcesso(Usuario.ALUNO);
        dao.salvar(usuario2);
        Usuario usuario3 = new Usuario();
        usuario3.setLogin("professor");
        usuario3.setSenha("professor");
        usuario3.setPerfilAcesso(Usuario.PROFESSOR);
        dao.salvar(usuario3);
        Usuario usuario4 = new Usuario();
        usuario4.setLogin("responsavel");
        usuario4.setSenha("responsavel");
        usuario4.setPerfilAcesso(Usuario.RESPONSAVEL_ALUNO);
        dao.salvar(usuario4);
    }
}
