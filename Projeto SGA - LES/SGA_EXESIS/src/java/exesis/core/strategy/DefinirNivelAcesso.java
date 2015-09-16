/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.Usuario;

/**
 *
 * @author SAMUEL
 */
public class DefinirNivelAcesso implements IStrategy{

    
    public Resultado processar(EntidadeDominio entidade) {
        if(entidade instanceof Professor)
            ((Professor)entidade).getUsuario().setPerfilAcesso(Usuario.PROFESSOR);
        
        return Resultado.getResultado();
    }
        
}
