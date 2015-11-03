package exesis.model;


public class Aluno extends Pessoa{
    private String matricula;
    private ResponsavelAluno responsavel;
    private Turma turma;
    public Aluno(){}
    public Aluno(String nome){
        this.nome = nome;
    }
    public Aluno(int id){
        this.id = id;
    }
    
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

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
        
    
}
