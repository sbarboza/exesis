/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Pessoa;


public class ValidarNomeUsuario implements IStrategy{

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
//        UsuarioHibernate dao = new UsuarioHibernate();
        if(entidade instanceof Pessoa){
            Pessoa p = (Pessoa) entidade;
//            resultado = dao.consultarLogin(p.getUsuario());
            if(resultado.getEntidades() != null && resultado.getEntidades().size() > 0)
                if(entidade.getId() == 0 || ((Pessoa)entidade).getUsuario().getId() != resultado.getEntidades().get(0).getId()){
                    resultado.setMsg("Usuário já cadastrado!");
                }
        }
        
        return resultado;
    }
    
}
