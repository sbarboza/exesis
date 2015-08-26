package exesis.entidade;

public class Professor extends EntidadeDominio{
	private String nome;
	private String turma;
	private Credencial credencial;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTurma() {
		return turma;
	}
	public void setTurma(String turma) {
		this.turma = turma;
	}
	public Credencial getCredencial() {
		return credencial;
	}
	public void setCredencial(Credencial credencial) {
		this.credencial = credencial;
	}
	
}
