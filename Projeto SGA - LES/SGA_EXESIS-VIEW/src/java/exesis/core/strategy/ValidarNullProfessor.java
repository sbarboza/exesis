package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;


public class ValidarNullProfessor implements IStrategy {

	public Resultado processar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
		Professor professor = (Professor) entidade;
		if(professor.getNome() == null || professor.getNome().isEmpty()) 
			resultado.setMsg("Favor preencher o nome!");
                if(professor.getSobrenome() == null || professor.getSobrenome().isEmpty()) 
			resultado.setMsg("Favor preencher o sobrenome!");
		if(professor.getTelefone() == null || professor.getTelefone().isEmpty())
			resultado.setMsg("Favor preencher o telefone!");
		ValidarNullUsuario valNullCred = new ValidarNullUsuario();
			resultado = valNullCred.processar(professor.getUsuario());
		return resultado;

	}
	
}
