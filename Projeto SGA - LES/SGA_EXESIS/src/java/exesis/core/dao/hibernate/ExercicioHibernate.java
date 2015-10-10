package exesis.core.dao.hibernate;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Exercicio")
public class ExercicioHibernate extends HibernateDAO{
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
        String sql = "";
        openConection();
        try{
            Exercicio exercicio = (Exercicio) entidade;
            resultado = this.consultar(entidade);
            List<Tag> tags = new ArrayList<Tag>();
            List<Tag> tagsAuxiliar = new ArrayList<Tag>();
            for(Tag t: exercicio.getTags()){
                for(EntidadeDominio e: resultado.getEntidades()){
                    Tag tag = (Tag) e;
                    JOptionPane.showMessageDialog(null, tag.getNome() + " = "+ t.getNome());
                    if(tag.getNome().trim().equals(t.getNome())){
                        t.setId(tag.getId());                    
                    }
                    tags.add(t);    
            }
            if(!resultado.getEntidades().isEmpty())
                exercicio.setTags(tags);
            for(Tag tag: exercicio.getTags()){
                if(tag.getId() == 0){
                    sql = "INSERT INTO tgtags(id, nome) values("+
                            
                            t.getId()+","+
                            t.getNome()+")";  
                }else{
                    
                }
                session.createSQLQuery(sql).executeUpdate();
            }
            
        }
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
    
    
     public Resultado consultar(EntidadeDominio entidade){
            Exercicio exercicio = (Exercicio) entidade;
             if(exercicio.getTags() != null && !exercicio.getTags().isEmpty()){
           
            String[] lista = new String[exercicio.getTags().size()];
            resultado = Resultado.getResultado();
            openConection();
            try{
                int count = 0;
                for(Tag t: exercicio.getTags()){
                    lista[count++] = t.getNome();
                }
                
                List<Object[]> entidades = session.createSQLQuery("select id, nome from tbtags where nome in (:tag)")
                        .setParameterList("tag", lista).list();
                
                for(int i = 0; i < entidades.size(); i++){
                    Integer id = (Integer) entidades.get(i)[0];
                    String nome = (String) entidades.get(i)[1];
                    resultado.setEntidade(new Tag(id, nome));
                }
               
            }catch(org.hibernate.HibernateException erro){
                resultado.setMsg(erro.getMessage());
            }finally{
                session.close();
            }
            return resultado;
        }else{
                 
            return super.consultar(entidade);
        }
        
    }

    private void list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
