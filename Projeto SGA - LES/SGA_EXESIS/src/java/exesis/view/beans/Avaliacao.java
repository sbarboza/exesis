package exesis.view.beans;

import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import java.util.Date;
import java.util.List;

public class Avaliacao extends EntidadeDominio{
    private String nome;
    private Date prazo;
    private String turma;
    private List<String> tags;
    private List<Exercicio> exercicios;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
    
    
}
