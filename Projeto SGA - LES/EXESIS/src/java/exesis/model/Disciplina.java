
package exesis.model;

import java.util.List;

public class Disciplina extends EntidadeDominio{
    private String nome;
    
    private List<Professor> professores;
    
    public Disciplina(){}
    public Disciplina(String nome){
        this.nome = nome;
    }
    public Disciplina(int id){
        this.id = id;
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
