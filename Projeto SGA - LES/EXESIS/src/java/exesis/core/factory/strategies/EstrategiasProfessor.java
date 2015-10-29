package exesis.core.factory.strategies;

import exesis.core.factory.FactoryStrategy;
import exesis.core.strategy.ComplementarDtCadastro;
import exesis.core.strategy.DefinirNivelAcesso;
import exesis.core.strategy.IStrategy;
import exesis.core.strategy.ValidarData;
import exesis.core.strategy.ValidarEmail;
import exesis.core.strategy.ValidarNomeUsuario;
import exesis.core.strategy.ValidarNullProfessor;
import exesis.core.strategy.ValidarSenha;
import java.util.ArrayList;
import java.util.List;

public class EstrategiasProfessor extends FactoryStrategy{
      public List<IStrategy> getStrategies(String operacao) {
        estrategias = new ArrayList<IStrategy>();
        switch(operacao.toUpperCase()){
            case "SALVAR":
                estrategias.add(new ComplementarDtCadastro());
                estrategias.add(new DefinirNivelAcesso());
                estrategias.add(new ValidarSenha());
            case "ALTERAR":
                estrategias.add(new ValidarNullProfessor());
                estrategias.add(new ValidarData());
                estrategias.add(new ValidarEmail());
                estrategias.add(new ValidarNomeUsuario());
                break;
            case "EXCLUIR":
                break;
            case "CONSULTAR":
        }
        return estrategias;
    }
}
