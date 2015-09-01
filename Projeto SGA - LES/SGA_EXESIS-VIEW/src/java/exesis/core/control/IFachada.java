/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exesis.core.control;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;

/**
 *
 * @author SAMUEL
 */
public interface IFachada {
        public Resultado salvar(EntidadeDominio entidade);
        public Resultado alterar(EntidadeDominio entidade);
        public Resultado consultar(EntidadeDominio entidade);
        public Resultado excluir(EntidadeDominio entidade);
        
}
