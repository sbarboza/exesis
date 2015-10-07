package exesis.model;

import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

public class Lista  {
    private String id;
    private String data;
    private String professor;
    private String disciplina;
    private String descricao;
    private boolean teste = true;
    
    @OneToMany
    @JoinTable(name = "tbListasCriadasExercicios", 
            joinColumns = {@JoinColumn(name = "listaCriada_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "exercicio_id", referencedColumnName = "id")})
    private List<Exercicio> exercicios;
    
    public Lista(){}
    public Lista(String id, String desc, String data, String prof, String discipl){
        this.id = id;
        this.descricao = desc;
        this.data = data;
        this.professor = prof;
        this.disciplina = discipl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isTeste() {
        return teste;
    }

    public void setTeste(boolean teste) {
        this.teste = teste;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}