/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.core.dao.hibernate;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.model.EntidadeDominio;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author SAMUEL
 */
public class HibernateDAO implements IDAO{
    protected boolean ctrlTransation = true;
    protected Transaction tx;
    protected Session session;
    protected Configuration cfg;
    protected SessionFactory sf;
    protected Resultado resultado;
    

    public Resultado salvar(EntidadeDominio entidade){
        return persistir(entidade);
    }
    
    public Resultado alterar(EntidadeDominio entidade){
        return persistir(entidade);
    }
    
    public Resultado excluir(EntidadeDominio entidade){
        resultado = Resultado.getResultado();
        openConection();
        try{
            session.delete(entidade);
            tx.commit();
        }catch(org.hibernate.HibernateException erro){
            resultado.setMsg(erro.getMessage());
            tx.rollback();
        }finally{
            session.close();
        }
        return resultado;
    }
    public Resultado consultar(EntidadeDominio entidade){
        resultado = Resultado.getResultado();
        openConection();
        try{
            EntidadeDominio e = (EntidadeDominio) session.get(entidade.getClass(),entidade.getId());
            ArrayList<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
            if(e != null)
                entidades.add(e);
            resultado.setEntidades(entidades);
        }catch(org.hibernate.HibernateException erro){
            resultado.setMsg(erro.getMessage());
        }finally{
            session.close();
        }
        return resultado;
    }
    
    
    protected Resultado persistir(EntidadeDominio entidade){
        resultado = Resultado.getResultado();
        openConection();
        try{
            session.saveOrUpdate(entidade);
            tx.commit();
        }catch(org.hibernate.HibernateException erro){
            resultado.setMsg(erro.getMessage());
            tx.rollback();
        }finally{
            session.close();
        }
        return resultado;
    }
    
    public Session openConection(){
        cfg = new AnnotationConfiguration();
        cfg.configure("/exesis/core/dao/hibernate/config/hibernate.cfg.xml");
        sf = cfg.buildSessionFactory();
        session = sf.openSession();
        tx = session.beginTransaction(); 
        return session;
    }    
}
