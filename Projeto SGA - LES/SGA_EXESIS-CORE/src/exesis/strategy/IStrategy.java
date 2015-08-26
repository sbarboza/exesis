package exesis.strategy;

import exesis.aplicacao.Resultado;
import exesis.entidade.EntidadeDominio;

public interface IStrategy {
	public Resultado processar(EntidadeDominio entidade);
}
