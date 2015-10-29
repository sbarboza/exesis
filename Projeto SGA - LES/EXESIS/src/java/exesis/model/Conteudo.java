package exesis.model;

public class Conteudo extends EntidadeDominio{
    private String disciplina;
    private String professor;
    private String titulo;

    public Conteudo(String id, String disciplina, String professor, String titulo){
        this.id = Integer.parseInt(id);
        this.disciplina = disciplina;
        this.professor = professor;
        this.titulo = titulo;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
