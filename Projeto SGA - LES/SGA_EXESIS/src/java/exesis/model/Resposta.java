package exesis.model;

import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

public class Resposta extends EntidadeDominio{
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "id_exercicio", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private Exercicio exercicio;
    private String dissertativa;
    private List<Alternativa> alternativas;

    
    public String getDissertativa() {
        return dissertativa;
    }

    public void setDissertativa(String dissertativa) {
        this.dissertativa = dissertativa;
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }
    
    
}
