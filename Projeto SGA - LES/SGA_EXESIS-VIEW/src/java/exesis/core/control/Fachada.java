package exesis.core.control;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.core.dao.impl.ProfessorDAO;
import exesis.core.strategy.IStrategy;
import exesis.core.strategy.ValidarData;
import exesis.core.strategy.ValidarEmail;
import exesis.core.strategy.ValidarNomeUsuario;
import exesis.core.strategy.ValidarNullProfessor;
import exesis.core.strategy.ValidarSenha;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fachada implements IFachada {
	private Map<String, IDAO> daos;
	private Map<String, Map<String, List<IStrategy>>> rns;
	
	public Fachada(){

                // Listas de estratégias
		List<IStrategy> salvarProfessor = new ArrayList<IStrategy>();
		salvarProfessor.add(new ValidarNullProfessor());
                salvarProfessor.add(new ValidarSenha());
                salvarProfessor.add(new ValidarData()); 
                salvarProfessor.add(new ValidarEmail());
                salvarProfessor.add(new ValidarNomeUsuario());
                
		
                List<IStrategy> alterarProfessor = new ArrayList<IStrategy>();
		alterarProfessor.add(new ValidarNullProfessor());
                alterarProfessor.add(new ValidarData());
                alterarProfessor.add(new ValidarEmail());
		alterarProfessor.add(new ValidarNomeUsuario());
                
		// Mapas de entidades
		Map<String, List<IStrategy>> rnsSalvar = new HashMap<String, List<IStrategy>>();
		rnsSalvar.put(Professor.class.getCanonicalName(), salvarProfessor);
		
                Map<String, List<IStrategy>> rnsAlterar = new HashMap<String, List<IStrategy>>();
		rnsAlterar.put(Professor.class.getCanonicalName(), alterarProfessor);
		
                
		// Mapas de operações
                rns = new HashMap<String, Map<String, List<IStrategy>>>();
		rns.put("SALVAR", rnsSalvar);
                rns.put("ALTERAR", rnsAlterar);
                // Lista de DAO's - Classes para persistência
                daos = new HashMap<String, IDAO>();
                daos.put(Professor.class.getCanonicalName(), new ProfessorDAO());

	}

        @Override
	public Resultado salvar(EntidadeDominio entidade) {
		Resultado resultado = new Resultado();
		String nmClasse = entidade.getClass().getCanonicalName();
		resultado = executarRegras(entidade, "SALVAR");
		if(resultado.getMsgs().isEmpty()){
			IDAO dao = daos.get(nmClasse);
                        resultado = dao.salvar(entidade);
		}
		return resultado;
	}
        @Override
	public Resultado alterar(EntidadeDominio entidade) {
		Resultado resultado = new Resultado();
                String nmClasse = entidade.getClass().getCanonicalName();
                resultado = executarRegras(entidade, "ALTERAR");
                if(resultado.getMsgs().isEmpty()){
                    IDAO dao = daos.get(nmClasse);
                    resultado = dao.alterar(entidade);
                }
		return resultado;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		Resultado resultado = new Resultado();
                String nmClasse = entidade.getClass().getCanonicalName();
                resultado = executarRegras(entidade, "EXCLUIR");
                if(resultado.getMsgs().isEmpty()){
                    IDAO dao = daos.get(nmClasse);
                    resultado = dao.excluir(entidade);
                }
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		Resultado resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();
		resultado = executarRegras(entidade, "CONSULTAR");
		if(resultado.getMsgs().isEmpty()){
			IDAO dao = daos.get(nmClasse);
                        resultado = dao.consultar(entidade);
		}
		return resultado;
	}
	
	private Resultado executarRegras(EntidadeDominio entidade, String operacao){
		Resultado resultado = Resultado.getResultado();
		String nmClasse = entidade.getClass().getCanonicalName();		
		Map<String, List<IStrategy>> regrasOperacao;
		regrasOperacao = rns.get(operacao);
		if(regrasOperacao != null){
			List<IStrategy> regras = regrasOperacao.get(nmClasse);
			if(regras != null){
				for(IStrategy s: regras){			
					resultado = s.processar(entidade);		
				}	
			}			
		}
		return resultado;
	}

}
