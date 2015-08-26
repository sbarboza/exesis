package exesis.entidade;

public class Aluno extends EntidadeDominio{
	private String nome;
	private String matricula;
	private Credencial credencial;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Credencial getCredencial() {
		return credencial;
	}
	public void setCredencial(Credencial credencial) {
		this.credencial = credencial;
	}
	
	
}
