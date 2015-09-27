/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.model;

import java.util.Map;

/**
 *
 * @author Vinicius Oliveira
 */
public class Turma extends EntidadeDominio{

    private String serie;
    private int periodo;
    private String fase;         // FUNDAMENTAL/ MEDIO
    private String prefixo;      // A, B, C.......N
    private Map<Disciplina, Professor> mapaDisciplinaProfessor;

    public Map<Disciplina, Professor> getMapaDisciplinaProfessor() {
        return mapaDisciplinaProfessor;
    }

    public void setMapaDisciplinaProfessor(Map<Disciplina, Professor> mapaDisciplinaProfessor) {
        this.mapaDisciplinaProfessor = mapaDisciplinaProfessor;
    }
    
    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getFase() {
        return fase;
    }

    public void setFase() {
        if(Integer.parseInt(serie) < 8)
            this.fase = "Fundamental";
        else
            this.fase = "Ensino Médio";
    }

    public String getPrefixo() {
        return prefixo;
    }
    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }


    
    
    

}
