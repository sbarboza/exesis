package exesis.core.factory;

import exesis.core.factory.strategies.EstrategiasAdministrador;
import exesis.core.factory.strategies.EstrategiasAluno;
import exesis.core.factory.strategies.EstrategiasProfessor;
import exesis.core.factory.strategies.EstrategiasResponsavel;
import exesis.core.strategy.IStrategy;
import exesis.model.Administrador;
import exesis.model.Aluno;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FactoryStrategy {
    public ArrayList<IStrategy> estrategias;
    public static FactoryStrategy create(String nmClasse){
        Map<String, FactoryStrategy> factories = new HashMap<>();
        factories.put(Professor.class.getCanonicalName(), new EstrategiasProfessor());
        factories.put(Aluno.class.getCanonicalName(), new EstrategiasAluno());
        factories.put(Administrador.class.getCanonicalName(), new EstrategiasAdministrador());
        factories.put(ResponsavelAluno.class.getCanonicalName(), new EstrategiasResponsavel());
        return factories.get(nmClasse);
    }
    public abstract List<IStrategy> getStrategies(String operacao);
    
}
