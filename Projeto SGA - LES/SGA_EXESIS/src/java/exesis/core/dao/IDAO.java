package exesis.core.dao;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;

public interface IDAO {
		public Resultado salvar(EntidadeDominio entidade); 
		public Resultado alterar(EntidadeDominio entidade);
		public Resultado excluir(EntidadeDominio entidade);
		public Resultado consultar(EntidadeDominio entidade);
}
