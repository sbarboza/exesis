package exesis.core.factory.strategies;

import exesis.core.factory.FactoryStrategy;
import exesis.core.strategy.DefinirNivelAcesso;
import exesis.core.strategy.IStrategy;
import exesis.core.strategy.ValidarData;
import exesis.core.strategy.ValidarEmail;
import exesis.core.strategy.ValidarNullResponsavel;
import exesis.core.strategy.ValidarSenha;
import java.util.ArrayList;
import java.util.List;

public class EstrategiasResponsavel extends FactoryStrategy{

    @Override
    public List<IStrategy> getStrategies(String operacao) {
                estrategias = new ArrayList<IStrategy>();
        switch(operacao.toUpperCase()){
            case "SALVAR":
                estrategias.add(new DefinirNivelAcesso());
                estrategias.add(new ValidarSenha());
            case "ALTERAR":
                estrategias.add(new ValidarNullResponsavel());
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
