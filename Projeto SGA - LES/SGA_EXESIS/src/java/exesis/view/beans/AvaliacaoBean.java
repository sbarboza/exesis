package exesis.view.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AvaliacaoBean extends AbstractBean{
    private String nome;
    private int quantidade;
    private String tipo;
    private String turma;
    private String lista;
    private Map<String, String> listas;
    private Map<String, String> turmas;
    private Avaliacao avaliacao;
    private List<Avaliacao> avaliacoes;
    @PostConstruct
    public void init(){
        quantidade = 1;
        listas = new HashMap<String, String>();
        turmas = new HashMap<String, String>();
        
        listas.put("Avaliação Geometria", "Avaliação Geometria");
        listas.put("Avaliação Aritmética", "Avaliação Aritmética");
        listas.put("Lista de Treino - Equação", "Lista de Treino - Equação");
        listas.put("Prova Final", "Prova Final");
        
        turmas.put("1A - Manhã", "1A - Manhã");
        turmas.put("2C - Manhã", "2C - Manhã");
        turmas.put("4B - Tarde", "4B - Tarde");
        turmas.put("1E - Noite", "1E - Noite");
        
        avaliacao = new Avaliacao();
        
        avaliacoes = new ArrayList<Avaliacao>();
    }
    
    public String reinit(){
        avaliacao = new Avaliacao();
        return null;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public Map<String, String> getListas() {
        return listas;
    }

    public void setListas(Map<String, String> listas) {
        this.listas = listas;
    }

    public Map<String, String> getTurmas() {
        return turmas;
    }

    public void setTurmas(Map<String, String> turmas) {
        this.turmas = turmas;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
    
}
