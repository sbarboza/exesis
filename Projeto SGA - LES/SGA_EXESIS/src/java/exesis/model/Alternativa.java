package exesis.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbAlternativas")
public class Alternativa extends EntidadeDominio{
    private boolean resposta;
    private String descricao;

    public Alternativa(){}
    public Alternativa(int hash){
        this.id = hash;
    }
    public Alternativa(String descricao){
        this.descricao = descricao;
    }
    public Alternativa(boolean resposta, String descricao){
        this.descricao = descricao;
        this.resposta = resposta;
    }
    public boolean getResposta() {
        return resposta;
    }

    public void setResposta(boolean resposta) {
        this.resposta = resposta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
        
}
