package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import java.util.Date;

public class ComplementarDtCadastro implements IStrategy {

	@Override
	public Resultado processar(EntidadeDominio entidade) {		
		Resultado resultado = Resultado.getResultado();
		
		if(entidade !=null){
			Date data = new Date();		
			entidade.setDtCadastro(data);
		}else{
			resultado.setMsg("Entidade: "+entidade.getClass().getSimpleName()+" nula!\n");
		}
		return resultado;

	}

}
