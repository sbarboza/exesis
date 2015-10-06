package exesis.model;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "tbExerciciosDissertativo")
public class Dissertativo extends Exercicio{
    private String resposta;

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
    
}
