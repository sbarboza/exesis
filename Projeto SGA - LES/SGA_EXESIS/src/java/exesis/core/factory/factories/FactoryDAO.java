/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.core.factory.factories;

import exesis.core.dao.IDAO;
import exesis.core.dao.hibernate.AlunoHibernate;
import exesis.core.dao.hibernate.ProfessorHibernate;
import exesis.core.dao.hibernate.ResponsavelHibernate;
import exesis.model.Aluno;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SAMUEL
 */
public class FactoryDAO {
    public static IDAO create(String nmClasse){
        Map<String, IDAO> daos = new HashMap<>();
        daos.put(Professor.class.getCanonicalName(), new ProfessorHibernate());
        daos.put(Aluno.class.getCanonicalName(), new AlunoHibernate());
        daos.put(ResponsavelAluno.class.getCanonicalName(),new ResponsavelHibernate());
        return daos.get(nmClasse);
    }
}
