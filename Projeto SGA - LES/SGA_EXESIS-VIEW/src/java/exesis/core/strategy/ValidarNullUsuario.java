package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Usuario;


public class ValidarNullUsuario implements IStrategy {

	public Resultado processar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
		Usuario usuario = (Usuario) entidade;
		if(usuario.getLogin() == null || usuario.getLogin().trim().length() == 0)
			resultado.setMsg("Nome de usuário");	
		if(usuario.getEmail() == null || usuario.getEmail().trim().length() == 0)
			resultado.setMsg("E-mail");		
		return resultado;
	}

}
