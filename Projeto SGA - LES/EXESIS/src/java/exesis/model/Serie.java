package exesis.model;

/**
 *
 * @author EXESIS
 */
public class Serie extends EntidadeDominio{
    private String nome;
    
    public Serie(){}
    public Serie(int id){
        this.id = id;
    }
    public Serie(String nome){
        this.nome = nome;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
