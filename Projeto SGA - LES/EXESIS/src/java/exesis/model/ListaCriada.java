package exesis.model;


import java.util.List;

public class ListaCriada extends EntidadeDominio{
    private String nome;
    private TipoLista tipo;
    private int quantidade;
    private List<Tag> tags;
    private List<Exercicio> exercicios;

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
    
    
}
