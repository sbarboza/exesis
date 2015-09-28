package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.Administrador;
import exesis.model.EntidadeDominio;

public class ValidarNullAdministrador implements IStrategy {

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
		      Administrador administrador = (Administrador) entidade;
		if(administrador.getNome() == null || administrador.getNome().trim().length() == 0) 
			resultado.setMsg("Nome");
                if(administrador.getSobrenome() == null || administrador.getSobrenome().trim().length() == 0) 
			resultado.setMsg("Sobrenome");
		if(administrador.getTelefone() == null || administrador.getTelefone().trim().length() == 0)
			resultado.setMsg("Telefone");
		ValidarNullUsuario valNullCred = new ValidarNullUsuario();
			resultado = valNullCred.processar(administrador.getUsuario());
		return resultado;
    }

    
}
