package exesis.controle;

import exesis.aplicacao.Resultado;
import exesis.entidade.EntidadeDominio;

public interface IFachada {
	public Resultado salvar(EntidadeDominio entidade);
	public Resultado alterar(EntidadeDominio entidade);
	public Resultado excluir(EntidadeDominio entidade);
	public Resultado consultar(EntidadeDominio entidade);
}
