package exesis.model;

import java.util.ArrayList;
import java.util.List;
    public class Exercicio extends EntidadeDominio{
    public static final int MULTIPLAESCOLHA = 1;
    public static final int DISSERTATIVA = 2;
    private String enunciado;
    private int tipo;
    private double peso;
    private int contador;
    protected List<Tag> tags;
    private List<Alternativa> alternativas;
    private Nivel nivel;
    
    public Exercicio(){
        
    }
    public Exercicio(int id){
        this.id = id;
    }
    public Exercicio(String enunciado, int tipo, double peso, List<Tag> tags, List<Alternativa> alternativas){
        this.enunciado = enunciado;
        this.tipo = tipo;
        this.peso = peso;
        this.tags = tags;
        this.alternativas = alternativas;
    }
    
    public List<Alternativa> getAlternativas() {
        if(alternativas == null)
            alternativas = new ArrayList<Alternativa>();
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


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
    
}
