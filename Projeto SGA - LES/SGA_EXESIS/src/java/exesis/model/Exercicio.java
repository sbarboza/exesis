package exesis.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbExercicio")
public class Exercicio extends EntidadeDominio{
    protected String enunciado;
    protected double peso;
    protected double nota;
    
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "tbExerciciosTags", 
        // Exericio - this
        joinColumns = {@JoinColumn(name = "exercicio_id", referencedColumnName = "id")},   
        // Tag - List<Tag>
        inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")} 
    )
    protected List<Tag> tags;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbExercicioAlternativa", 
        // Exercício
        joinColumns = {@JoinColumn(name = "exercicio_id", referencedColumnName = "id")},   
        // Alternativa
        inverseJoinColumns = {@JoinColumn(name = "alternativa_id", referencedColumnName = "id")}
    )
    private List<Alternativa> alternativas;
        
    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    
    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
