package exesis.model;

import java.util.List;

public class Resposta extends EntidadeDominio{
    private Exercicio exercicio;
    private String dissertativa;
    private List<Alternativa> alternativas;
    private double nota;

    
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    
}
