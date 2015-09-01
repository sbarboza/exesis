/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;

/**
 *
 * @author SAMUEL
 */
public class ValidarData implements IStrategy{

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        String data;
        if(entidade instanceof Professor){
            Professor professor = (Professor) entidade;
            data = professor.getDataNascimento();
            if(data.length() != 10)
                resultado.getMsgs().add("Data inválida!");
        }
        
        
        
        return resultado;
    }
    
    
}
