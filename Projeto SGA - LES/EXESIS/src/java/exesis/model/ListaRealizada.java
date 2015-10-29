package exesis.model;

import java.util.List;

public class ListaRealizada extends EntidadeDominio{
    private Aluno aluno; 
    private ListaCriada listaCriada;
    private List<Resposta> listaRespostas;

    public ListaCriada getListaCriada() {
        return listaCriada;
    }

    public void setListaCriada(ListaCriada listaCriada) {
        this.listaCriada = listaCriada;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<Resposta> getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(List<Resposta> listaRespostas) {
        this.listaRespostas = listaRespostas;
    }

    
}
