
package exesis.model;

public class Usuario extends EntidadeDominio{
    private String email;
    private String login;
    private String senha;
    private int perfilAcesso;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPerfilAcesso() {
        return perfilAcesso;
    }

    public void setPerfilAcesso(int perfilAcesso) {
        this.perfilAcesso = perfilAcesso;
    }
    
}
