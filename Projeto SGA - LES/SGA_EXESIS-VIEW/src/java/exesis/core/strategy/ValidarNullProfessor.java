package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;


public class ValidarNullProfessor implements IStrategy {

	public Resultado processar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
		Professor professor = (Professor) entidade;
		if(professor.getNome() == null || professor.getNome().trim().length() == 0) 
			resultado.setMsg("Nome");
                if(professor.getSobrenome() == null || professor.getSobrenome().trim().length() == 0) 
			resultado.setMsg("Sobrenome");
		if(professor.getTelefone() == null || professor.getTelefone().trim().length() == 0)
			resultado.setMsg("Telefone");
		ValidarNullUsuario valNullCred = new ValidarNullUsuario();
			resultado = valNullCred.processar(professor.getUsuario());
		return resultado;

	}
	
}
