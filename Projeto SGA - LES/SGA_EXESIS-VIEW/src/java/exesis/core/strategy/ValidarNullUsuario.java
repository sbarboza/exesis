package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Usuario;


public class ValidarNullUsuario implements IStrategy {

	public Resultado processar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
		Usuario usuario = (Usuario) entidade;
		if(usuario.getLogin() == null || usuario.getLogin().isEmpty())
			resultado.setMsg("Favor preencher o nome de usuário!");	
		if(usuario.getEmail() == null || usuario.getEmail().isEmpty())
			resultado.setMsg("Favor preencher o e-mail!");		
		return resultado;
	}

}
