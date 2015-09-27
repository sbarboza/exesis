package exesis.model;

import java.util.ArrayList;
import java.util.List;

public class MultiplaEscolha extends Exercicio{
    public  MultiplaEscolha(){
        alternativas = new ArrayList<Alternativa>();
    }
    private List<Alternativa> alternativas;

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

}
