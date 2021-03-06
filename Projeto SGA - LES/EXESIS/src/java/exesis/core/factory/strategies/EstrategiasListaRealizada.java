package exesis.core.factory.strategies;

import exesis.core.factory.FactoryStrategy;
import exesis.core.strategy.CalcularNotaListaRealizada;
import exesis.core.strategy.ComplementarDtCadastro;
import exesis.core.strategy.IStrategy;
import exesis.core.strategy.ValidarExercicio;
import java.util.ArrayList;
import java.util.List;

public class EstrategiasListaRealizada extends FactoryStrategy{

    @Override
    public List<IStrategy> getStrategies(String operacao) {
        estrategias = new ArrayList<IStrategy>();
        switch(operacao){
            case "SALVAR":
            case "ALTERAR":
                estrategias.add(new CalcularNotaListaRealizada());
                break;
            case "EXCLUIR":
                break;
            case "CONSULTAR":
                break;
        }
        return estrategias;
    }

}
