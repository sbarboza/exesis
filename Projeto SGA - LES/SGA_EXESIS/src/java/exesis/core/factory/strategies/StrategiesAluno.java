/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.core.factory.strategies;

import exesis.core.factory.FactoryStrategy;
import exesis.core.strategy.DefinirNivelAcesso;
import exesis.core.strategy.IStrategy;
import exesis.core.strategy.ValidarData;
import exesis.core.strategy.ValidarEmail;
import exesis.core.strategy.ValidarNullAluno;
import exesis.core.strategy.ValidarSenha;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SAMUEL
 */
public class StrategiesAluno extends FactoryStrategy{

    public List<IStrategy> getStrategies(String operacao) {
        estrategias = new ArrayList<IStrategy>();
        switch(operacao.toUpperCase()){
            case "SALVAR":
                estrategias.add(new DefinirNivelAcesso());
                estrategias.add(new ValidarSenha());
            case "ALTERAR":
                estrategias.add(new ValidarNullAluno());
                estrategias.add(new ValidarData());
                estrategias.add(new ValidarEmail());
                break;
            case "EXCLUIR":
                break;
            case "CONSULTAR":
        }
        return estrategias;
    }

}
