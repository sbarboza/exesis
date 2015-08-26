package exesis.controle;

import java.util.List;
import java.util.Map;

import exesis.aplicacao.Resultado;
import exesis.dao.IDAO;
import exesis.entidade.EntidadeDominio;
import exesis.strategy.IStrategy;

public class Fachada implements IFachada {
	private Map<String, IDAO> daos;
	private Map<String, Map<String, List<IStrategy>>> rns;
	
	public Fachada(){
		daos = HashMap<String, IDAO>();
		
		// Listas de estrat�gias
		List<IStrategy> salvarAluno = new ArrayList<IStrategy>();
		salvarAluno.add(new ValidarNullAluno());
		
		List<IStrategy> salvarProfessor = new ArrayList<IStrategy>();
		salvarProfessor.add(new ValidarNullProfessor());
		
		// Mapas de entidades
		Map<String, List<IStrategy>> rnsSalvar = new HashMap<String, List<IStrategy>>();
		rnsSalvar.put(Aluno.class.getCanonicalName(), salvarAluno);
		rnsSalvar.put(Professor.class.getCanonicalName(), salvarProfessor);
		
		
		// Mapas de opera��es
		rns.put("SALVAR", rnsSalvar);
		
		
		
		
	}
	
	public Resultado salvar(EntidadeDominio entidade) {
		Resultado resultado = Resultado.getResultado();
		String nmClasse = entidade.getClass().getCanonicalName();
		resultado = executarRegras(entidade, "SALVAR");
		if(resultado.getMsg().isEmpty()){
			IDAO dao = daos.get(nmClasse);
		}
		
		
		return resultado;
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
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
