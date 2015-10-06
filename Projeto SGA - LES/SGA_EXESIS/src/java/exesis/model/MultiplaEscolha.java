package exesis.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbExerciciosMultiplaEscolha")
public class MultiplaEscolha extends Exercicio{
    public  MultiplaEscolha(){
        alternativas = new ArrayList<Alternativa>();
    }
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbMulplicaEscolhaAlternativa", joinColumns = {
        @JoinColumn(name = "id_multiplaEscolha", referencedColumnName = "id")},   
        inverseJoinColumns = {@JoinColumn(name = "id_alternativa", referencedColumnName = "id")}
    )

    private List<Alternativa> alternativas;
    
    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

}
