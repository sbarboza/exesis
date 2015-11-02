
package exesis.model;

public class Nivel extends EntidadeDominio{
    private String descricao;
    private float peso;
    
    public Nivel(){}
    public Nivel(int id){
    this.id = id;
    }
    public Nivel(String descricao){
    this.descricao = descricao;
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
