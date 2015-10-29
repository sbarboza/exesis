package exesis.model;

/**
 *
 * @author EXESIS
 */
public class TipoLista extends EntidadeDominio{
    private String descricao;
    private float peso;
    public TipoLista(){}
    public TipoLista(String descricao, float peso){
        this.descricao = descricao;
        this.peso = peso;
    }
    public TipoLista(int id){
    this.id = id;
    }
    
    public TipoLista(int id, String descricao, float peso){
        this.id = id;
        this.descricao = descricao;
        this.peso = peso;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
    
}
