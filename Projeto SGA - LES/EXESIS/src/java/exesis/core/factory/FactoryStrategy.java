package exesis.core.factory;

import exesis.core.factory.strategies.EstrategiasAdministrador;
import exesis.core.factory.strategies.EstrategiasAluno;
import exesis.core.factory.strategies.EstrategiasAvaliacao;
import exesis.core.factory.strategies.EstrategiasExercicio;
import exesis.core.factory.strategies.EstrategiasListaCriada;
import exesis.core.factory.strategies.EstrategiasListaRealizada;
import exesis.core.factory.strategies.EstrategiasProfessor;
import exesis.core.factory.strategies.EstrategiasResponsavel;
import exesis.core.strategy.IStrategy;
import exesis.model.Administrador;
import exesis.model.Alternativa;
import exesis.model.Aluno;
import exesis.model.Avaliacao;
import exesis.model.Dissertativo;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.ListaRealizada;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import exesis.model.Tag;
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
        factories.put(Exercicio.class.getCanonicalName(), new EstrategiasExercicio());
        factories.put(Dissertativo.class.getCanonicalName(), new EstrategiasExercicio());
        factories.put(Alternativa.class.getCanonicalName(), new EstrategiasExercicio());
        factories.put(Tag.class.getCanonicalName(), new EstrategiasExercicio());
        factories.put(ListaCriada.class.getCanonicalName(), new EstrategiasListaCriada());
        factories.put(Avaliacao.class.getCanonicalName(), new EstrategiasAvaliacao());
        factories.put(ListaRealizada.class.getCanonicalName(), new EstrategiasListaRealizada());
        return factories.get(nmClasse);
    }
    public abstract List<IStrategy> getStrategies(String operacao);
    
}
