package exesis.core.control;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;

public interface IFachada {
        public Resultado salvar(EntidadeDominio entidade);
        public Resultado alterar(EntidadeDominio entidade);
        public Resultado consultar(EntidadeDominio entidade);
        public Resultado excluir(EntidadeDominio entidade);
        
}
