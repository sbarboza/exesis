package exesis.model;

import java.util.Date;

public class Avaliacao extends EntidadeDominio{
    private Disciplina disciplina;
    private Professor professor;
    private Date prazo;
    private ListaCriada listaCriada;
    private Turma turma;
    private boolean ativo;
    
    public Avaliacao(){}
    public Avaliacao(int id){
        this.id = id;
    }
    
    public Avaliacao(Turma turma){
        this.turma = turma;
    }
    
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public ListaCriada getListaCriada() {
        return listaCriada;
    }

    public void setListaCriada(ListaCriada listaCriada) {
        this.listaCriada = listaCriada;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    
}
