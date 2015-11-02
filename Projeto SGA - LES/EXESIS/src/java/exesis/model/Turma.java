package exesis.model;

import java.util.Map;

public class Turma extends EntidadeDominio{
    public final static int MANHA = 1;
    public final static int TARDE = 2;
    public final static int NOITE = 3;
    private String descricao;
    private Serie serie;
    private int periodo;
    private String prefixo;      
    private Map<Disciplina, Professor> mapaDisciplinaProfessor;
    
    public Turma(){}
    public Turma(int id){
        this.id = id;
    }
    
    
    
    public Map<Disciplina, Professor> getMapaDisciplinaProfessor() {
        return mapaDisciplinaProfessor;
    }

    public void setMapaDisciplinaProfessor(Map<Disciplina, Professor> mapaDisciplinaProfessor) {
        this.mapaDisciplinaProfessor = mapaDisciplinaProfessor;
    }
    
    public int getPeriodo() {
        return periodo;
    }
    
    public String getPeriodoString() {
        String nome = "";
        switch(periodo){
            case MANHA:
                nome = "Manhã";
                break;
            case TARDE:
                nome = "Tarde";
                break;
            case NOITE:
                nome = "Noite";
                break;
        }
        return nome;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }


    public String getPrefixo() {
        return prefixo;
    }
    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }
    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public String getDescricao() {
        descricao = "";
        if(serie != null && !serie.getNome().trim().isEmpty())
            descricao += serie.getNome() + " - ";
        descricao += prefixo + " - ";
        descricao += getPeriodoString();
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
