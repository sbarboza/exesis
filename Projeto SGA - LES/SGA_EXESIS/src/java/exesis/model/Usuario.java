
package exesis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "tbUsuarios")
@Component("usuario")
public class Usuario extends EntidadeDominio{
    public static final int ADMINISTRADOR = 2;
    public static final int PROFESSOR = 3;
    public static final int RESPONSAVEL_ALUNO = 4;
    public static final int ALUNO = 5;
    
   
    private String email;
    @Column(unique = true)
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
