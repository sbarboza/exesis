package exesis.core.dao.hibernate;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component("exesis.model.Usuario")
public class UsuarioHibernate extends HibernateDAO{
    @Override
    public Resultado consultar(EntidadeDominio entidade) {
        resultado = Resultado.getResultado();
            Usuario usuario = (Usuario) entidade;
            openConection();
            usuario = (Usuario) session.createCriteria(Usuario.class)

                                // AQUI DIGO QUE DEVE TER O LOGIN IGUAL AO PASSADO POR PARAMETRO 
                                .add(Restrictions.eq("login", usuario.getLogin())) 
                                // AQUI DIGO QUE DEVE TER O SENHA IGUAL AO PASSADO POR PARAMETRO
                                .add(Restrictions.eq("senha", usuario.getSenha()))
                                // AQUI SETO PARA ME RETORNAR APENAS 1 RESULTADO
                                // AFINAL LOGIN E SENHA É UNICO NO SISTEMA
                                .uniqueResult();
            resultado.setEntidades(new ArrayList<EntidadeDominio>());
            if(usuario != null)
            resultado.getEntidades().add(usuario);
              return resultado;
        }
    public Resultado consultarLogin(EntidadeDominio entidade) {
        resultado = Resultado.getResultado();
            Usuario usuario = (Usuario) entidade;
            openConection();
            List<EntidadeDominio> lista = (List<EntidadeDominio>) session.createCriteria(Usuario.class)

                                // AQUI DIGO QUE DEVE TER O LOGIN IGUAL AO PASSADO POR PARAMETRO 
                                .add(Restrictions.eq("login", usuario.getLogin())) 
                                // AQUI DIGO QUE DEVE TER O SENHA IGUAL AO PASSADO POR PARAMETRO
                                .list();
            
            if(lista != null)
                resultado.setEntidades(lista);
              return resultado;
        }
   
}
