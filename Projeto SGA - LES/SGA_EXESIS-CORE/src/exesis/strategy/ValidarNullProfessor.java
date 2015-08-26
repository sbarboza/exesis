package exesis.strategy;

import exesis.aplicacao.Resultado;
import exesis.entidade.Aluno;
import exesis.entidade.EntidadeDominio;
import exesis.entidade.Professor;

public class ValidarNullProfessor implements IStrategy {

	public Resultado processar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
		Professor professor = (Professor) entidade;
		if(professor.getNome() == null || professor.getNome().isEmpty()) 
			resultado.setMsg("Favor preencher o nome!");
		if(professor.getTurma() == null || professor.getTurma().isEmpty())
			resultado.setMsg("Favor preencher a turma!");
		ValidarNullCredencial valNullCred = new ValidarNullCredencial();
			resultado = valNullCred.processar(professor.getCredencial());
		return resultado;

	}
	
}
