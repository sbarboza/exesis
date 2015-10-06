package exesis.model;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

public class ListaCriada extends EntidadeDominio{
    private String nome;
    private String categoria;
    private int quantidade;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbListasCriadasTags", joinColumns = {
        @JoinColumn(name = "lista_criada_id", referencedColumnName = "id")},   
        inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    private List<Tag> tags;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tbListasCriadasExercicios", joinColumns = {
        @JoinColumn(name = "lista_criada_id", referencedColumnName = "id")},   
        inverseJoinColumns = {@JoinColumn(name = "exercicio_id", referencedColumnName = "id")}
    )
    private List<Exercicio> exercicios;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
}
