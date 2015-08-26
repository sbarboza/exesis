package exesis.strategy;

import exesis.aplicacao.Resultado;
import exesis.entidade.Aluno;
import exesis.entidade.EntidadeDominio;

public class ValidarNullAluno implements IStrategy{

	public Resultado processar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
		Aluno aluno = (Aluno) entidade;
		if(aluno.getNome() == null || aluno.getNome().isEmpty()) 
			resultado.setMsg("Favor preencher o nome!");
		if(aluno.getMatricula() == null || aluno.getMatricula().isEmpty())
			resultado.setMsg("Favor preencher a matricula!");
		ValidarNullCredencial valNullCred = new ValidarNullCredencial();
			resultado = valNullCred.processar(aluno.getCredencial());
		return resultado;
	}
	
}
