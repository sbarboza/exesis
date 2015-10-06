package exesis.model;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbAlunos")
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
