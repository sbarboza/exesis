/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.core.factory;

import exesis.core.factory.strategies.StrategiesAluno;
import exesis.core.factory.strategies.StrategiesProfessor;
import exesis.core.strategy.IStrategy;
import exesis.model.Aluno;
import exesis.model.Professor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SAMUEL
 */
public abstract class FactoryStrategy {
    public ArrayList<IStrategy> estrategias;
    public static FactoryStrategy create(String nmClasse){
        Map<String, FactoryStrategy> factories = new HashMap<>();
        factories.put(Professor.class.getCanonicalName(), new StrategiesProfessor());
        factories.put(Aluno.class.getCanonicalName(), new StrategiesAluno());
        return factories.get(nmClasse);
    }
    public abstract List<IStrategy> getStrategies(String operacao);
    
}
