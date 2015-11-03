package exesis.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ListaCriada extends EntidadeDominio{
    private String nome;
    private TipoLista tipo;
    private Date prazo;
    private int quantidade;
    private List<Tag> tags;
    private List<Exercicio> exercicios;
    private List<Nivel> listaNivel;
    public ListaCriada(){}
    public ListaCriada(int id){
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    public TipoLista getTipo() {
        return tipo;
    }

    public void setTipo(TipoLista tipo) {
        this.tipo = tipo;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public List<Nivel> getListaNivel() {
        return listaNivel;
    }

    public void setListaNivel(List<Nivel> listaNivel) {
        this.listaNivel = listaNivel;
    }

    
    
}
