package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;

public interface IStrategy {
	public Resultado processar(EntidadeDominio entidade);
}
