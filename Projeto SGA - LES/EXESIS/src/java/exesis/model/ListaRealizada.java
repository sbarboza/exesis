package exesis.model;

import java.util.List;

public class ListaRealizada extends EntidadeDominio{
    private Aluno aluno; 
    private Avaliacao avaliacao;
    private List<Resposta> listaRespostas;
    private float notaParcial;
    private float notaFinal;

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<Resposta> getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(List<Resposta> listaRespostas) {
        this.listaRespostas = listaRespostas;
    }

    public float getNotaParcial() {
        return notaParcial;
    }

    public void setNotaParcial(float notaParcial) {
        this.notaParcial = notaParcial;
    }

    public float getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(float notaFinal) {
        this.notaFinal = notaFinal;
    }
    
    
    
}
