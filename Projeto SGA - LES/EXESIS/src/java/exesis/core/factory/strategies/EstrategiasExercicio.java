package exesis.core.factory.strategies;

import exesis.core.factory.FactoryStrategy;
import exesis.core.strategy.ComplementarDtCadastro;
import exesis.core.strategy.IStrategy;
import exesis.core.strategy.ValidarExercicio;
import exesis.core.strategy.ValidarPalavrasChave;
import java.util.ArrayList;
import java.util.List;

public class EstrategiasExercicio extends FactoryStrategy{

    @Override
    public List<IStrategy> getStrategies(String operacao) {
        estrategias = new ArrayList<IStrategy>();
        switch(operacao){
            case "SALVAR":
                estrategias.add(new ComplementarDtCadastro());
            case "ALTERAR":
                estrategias.add(new ValidarExercicio());
                break;
            case "EXCLUIR":
                break;
            case "CONSULTAR":
                break;
        }
        return estrategias;
    }
    
}
