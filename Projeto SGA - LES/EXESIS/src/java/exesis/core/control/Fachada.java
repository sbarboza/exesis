package exesis.core.control;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.core.factory.FactoryStrategy;
import exesis.core.strategy.IStrategy;
import exesis.model.EntidadeDominio;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class Fachada implements IFachada {
	private final AnnotationConfigApplicationContext context;
        public Fachada(){
            context = new AnnotationConfigApplicationContext("exesis");
        }

        @Override
	public Resultado salvar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
                resultado.zerar();
                String nmClasse = entidade.getClass().getName();
		resultado = executarRegras(entidade, "SALVAR");
		if(resultado.getMsgs().isEmpty()){
			IDAO dao = (IDAO) context.getBean(nmClasse);
                        resultado = dao.salvar(entidade);
		}
		return resultado;
	}
        @Override
	public Resultado alterar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
                resultado.zerar();
                String nmClasse = entidade.getClass().getCanonicalName();
                resultado = executarRegras(entidade, "ALTERAR");
                if(resultado.getMsgs().isEmpty()){
                    IDAO dao = (IDAO) context.getBean(nmClasse);
                    resultado = dao.alterar(entidade);
                }
		return resultado;
	}
    @Override
	public Resultado excluir(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
                resultado.zerar();
                String nmClasse = entidade.getClass().getCanonicalName();
                resultado = executarRegras(entidade, "EXCLUIR");
                if(resultado.getMsgs().isEmpty()){  
                    IDAO dao = (IDAO) context.getBean(nmClasse);
                    resultado = dao.excluir(entidade);
                }
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();

		String nmClasse = entidade.getClass().getName();
		resultado = executarRegras(entidade, "CONSULTAR");  
		if(resultado.getMsgs().isEmpty()){
			IDAO dao = (IDAO) context.getBean(nmClasse);
                        resultado = dao.consultar(entidade);
                        if(!resultado.getEntidades().isEmpty()){
                            entidade = resultado.getEntidades().get(0);
                            resultado = executarRegras(entidade, "CONSULTAR");
                        }
		}
		return resultado;
	}
	
	private Resultado executarRegras(EntidadeDominio entidade, String operacao){
		Resultado resultado = Resultado.getResultado();
		String nmClasse = entidade.getClass().getCanonicalName();	
                FactoryStrategy estrategias = FactoryStrategy.create(nmClasse);
                if(estrategias != null){
                    List<IStrategy> regras = estrategias.getStrategies(operacao);
                    if(regras != null){
                            for(IStrategy s: regras){			
                                    resultado = s.processar(entidade);		
                            }	
                    }		
                }
            return resultado;
	}

}
