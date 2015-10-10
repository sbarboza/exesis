
package exesis.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbDisciplinas")
public class Disciplina extends EntidadeDominio{
    private String nome;
    
    @OneToMany
    @JoinTable(name= "tbDisciplinasProfessores", 
            joinColumns = {@JoinColumn(name = "disciplina_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "professor_id", referencedColumnName = "id")})
    private List<Professor> professores;
    
    public Disciplina(){}
    public Disciplina(String nome){
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }
    
    
}
