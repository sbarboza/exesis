package exesis.model;

import java.util.ArrayList;
import java.util.List;

public class Resposta extends EntidadeDominio{
    private Exercicio exercicio;
    private String dissertativa;
    private List<Alternativa> alternativas;
    private double correcao;
    private double nota;
    
    public Resposta(){}
    public Resposta(double correcao, double nota){
        this.correcao = correcao;
        this.nota = nota;
    }
    public Resposta(Exercicio exercicio, List<Alternativa> alternativas){
        this.exercicio = exercicio;
        List<Alternativa> lista = new ArrayList<Alternativa>();
        for(int i = 0; i < alternativas.size(); i++){
            lista.add(new Alternativa(alternativas.get(i).getId(), false, alternativas.get(i).getDescricao()));
        }
        this.alternativas = lista;
    }
    
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

    public double getCorrecao() {
        return correcao;
    }

    public void setCorrecao(double correcao) {
        this.correcao = correcao;
    }
    
    
    
}
