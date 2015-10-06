package exesis.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public class Exercicio extends EntidadeDominio{
    protected String enunciado;
    protected double peso;
    protected double nota;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbExerciciosTags", 
        joinColumns = {@JoinColumn(name = "exercicio_id", referencedColumnName = "id")},   
        inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    protected List<Tag> tags;
    
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
