package exesis.core.factory.strategies;

import exesis.core.factory.FactoryStrategy;
import exesis.core.strategy.ComplementarDtCadastro;
import exesis.core.strategy.IStrategy;
import exesis.core.strategy.GerarListaAutomatica;
import java.util.ArrayList;
import java.util.List;

public class EstrategiasListaCriada extends FactoryStrategy{

    @Override
    public List<IStrategy> getStrategies(String operacao) {
        estrategias = new ArrayList<IStrategy>();
        switch(operacao){
            case "SALVAR":
                break;
            case "ALTERAR":
                break;
            case "EXCLUIR":
                break;
            case "CONSULTAR":
                estrategias.add(new GerarListaAutomatica());
                break;
        }
        return estrategias;
    }
    
}
