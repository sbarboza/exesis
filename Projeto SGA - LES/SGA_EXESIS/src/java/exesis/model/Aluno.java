/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.model;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "tbAlunos")
@Component("aluno")
public class Aluno extends Pessoa{
    private String matricula;
    @ManyToOne
    @JoinColumn(name = "idResponsavel")
    private ResponsavelAluno responsavel;

    public ResponsavelAluno getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelAluno responsavel) {
        this.responsavel = responsavel;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
        
}
