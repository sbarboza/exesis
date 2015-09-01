/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exesis.model;

/**
 *
 * @author SAMUEL
 */
public class Usuario extends EntidadeDominio{
    private String email;
    private String login;
    private String senha;
    private int perfilAcesso;

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the perfilAcesso
     */
    public int getPerfilAcesso() {
        return perfilAcesso;
    }

    /**
     * @param perfilAcesso the perfilAcesso to set
     */
    public void setPerfilAcesso(int perfilAcesso) {
        this.perfilAcesso = perfilAcesso;
    }
    
}
