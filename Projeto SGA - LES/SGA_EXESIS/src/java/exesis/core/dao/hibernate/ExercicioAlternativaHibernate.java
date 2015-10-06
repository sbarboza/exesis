package exesis.core.dao.hibernate;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Exercicio")
public class ExercicioAlternativaHibernate extends HibernateDAO{
    
    @Override
    public Resultado salvar(EntidadeDominio entidade){
        return persistir(entidade);
    }
    
    @Override
    public Resultado alterar(EntidadeDominio entidade){
        return persistir(entidade);
    }
    
    @Override
    protected Resultado persistir(EntidadeDominio entidade){
        resultado = Resultado.getResultado();
        openConection();
        try{
            session.merge(entidade);
            tx.commit();
        }catch(org.hibernate.HibernateException erro){
            resultado.setMsg(erro.getMessage());
            tx.rollback();
        }finally{
            session.close();
            sf.close();
        }
        return resultado;
    }
}
