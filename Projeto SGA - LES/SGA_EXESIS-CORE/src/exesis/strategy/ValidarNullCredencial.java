package exesis.strategy;

import exesis.aplicacao.Resultado;
import exesis.entidade.Credencial;
import exesis.entidade.EntidadeDominio;

public class ValidarNullCredencial implements IStrategy {

	public Resultado processar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
		Credencial credencial = (Credencial) entidade;
		if(credencial.getUsuario() == null || credencial.getUsuario().isEmpty())
			resultado.setMsg("Favor preencher o nome de usuário!");
		if(credencial.getSenha() == null || credencial.getSenha().isEmpty())
			resultado.setMsg("Favor preencher a senha!");		
		if(credencial.getEmail() == null || credencial.getEmail().isEmpty())
			resultado.setMsg("Favor preencher o e-mail!");		
		
		return resultado;
	}

}
