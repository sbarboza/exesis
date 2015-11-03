package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.jdbc.ExercicioDAO;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.Nivel;
import exesis.model.Tag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GerarListaAutomatica implements IStrategy{

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        ListaCriada lista = (ListaCriada) entidade;
        List<String> tags = new ArrayList<String>();
        if(lista.getTags() != null){
            for(Tag t: lista.getTags()){
                tags.add(t.getNome());
            }
        
            resultado.zerar();
            if(!lista.getExercicios().isEmpty())
            resultado = classificarPorRelevancia(tags, lista.getExercicios());
            lista.setExercicios(new ArrayList<Exercicio>());
            for(EntidadeDominio e: resultado.getEntidades()){
                Exercicio exercicio = (Exercicio) e;
                lista.getExercicios().add(exercicio);
            }
           lista = filtroDificuldades(lista); 
            lista = adicionarContador(lista);
            resultado.zerar();
            resultado.setEntidade(lista);
        }else{
            resultado.zerar();
            resultado.setEntidade(lista);
        }
        return resultado;
    }
    private Resultado classificarPorRelevancia(List<String> tags, List<Exercicio> lista){
		Resultado resultado = Resultado.getResultado();
		boolean troca;
		Integer[][] classificar = new Integer[lista.size()][4]; // vetor de inteiros, que deve ter em cada índice, 
		//(o número de tags iguais, o número de tags diferentes, e o número do id do exercício)
		Collection<String> iguais = null, diferentes = null; // para fazer a intersecção e a diferença das tags
		int aux = 0;
		// Verificação de relevância
		for(Exercicio e: lista){
			// Atribui todas as tags do exercicio corrente nos dois objetos  
			diferentes = new HashSet<String>();
			iguais = new HashSet<String>();
			// varre a lista de tags do exercício corrente e add em cada um dos collections
			for(Tag s: e.getTags()){
				diferentes.add(s.getNome());
				iguais.add(s.getNome());
			}
			diferentes.addAll(tags); // adiciona as tags do input			
			iguais.retainAll(tags); // retira as tags que estão diferentes
			 // intersecção (deixa só os elementos comuns) (1,2,3) (2, 5) = Intersecção (2)
			diferentes.removeAll(iguais);
			// remove as tags iguais
			
			classificar[aux][0] = iguais.size();
			classificar[aux][1] = diferentes.size();
			classificar[aux][2] = e.getContador();
                        classificar[aux][3] = e.getId(); 		
			aux++;
		}
		do{
			troca=false;
			for(int i = 0; i < classificar.length - 1; i++){ 
                                // Se o próximo tem a quantidade de iguais maior que o anterior
				if(classificar[i][0] < classificar[i+1][0]|| 
                                    // Se a quantidade de iguais é a mesma, mas a quantidade de diferentes é menor que o anterior
                                    (classificar[i][0] == classificar[i+1][0] && (classificar[i][1] > classificar[i+1][1]) ||
                                        // Se o contador for maior que o do próximo
                                        classificar[i][2] > classificar[i+1][2])){ 
					for(int j = 0; j < classificar[i].length; j++){
						aux = classificar[i][j];
						classificar[i][j] = classificar[i+1][j];
						classificar[i+1][j] = aux;
						troca= true;
					}
				}
			}
		}while(troca);
		// coloca as entidades da lista que vem como parâmetro de acordo com os id que está no vetor
		for(int i = 0; i < classificar.length; i++){ // para cada item do vetor...
			for(Exercicio e: lista){ // varrer a lista ...
				if(e.getId() == classificar[i][3]) // verificando se na lista, tem um exercicio com o id do vetor
					resultado.getEntidades().add(e);
			}
		}
		return resultado;
	}
    private ListaCriada adicionarContador(ListaCriada lista){
        ExercicioDAO dao = new ExercicioDAO();
        for(Exercicio exercicio: lista.getExercicios()){
            Exercicio e = new Exercicio();
            e.setId(exercicio.getId());
            e.setEnunciado(exercicio.getEnunciado());
            e.setNivel(new Nivel(exercicio.getNivel().getId()));
            e.setContador(exercicio.getContador()+1);
            dao.alterar(e);
        }
        return lista;
    }
    
    private ListaCriada filtroDificuldades(ListaCriada lista){
        Map<Nivel, List<Exercicio>> mapaNivelExercicio = new HashMap<Nivel, List<Exercicio>>();
        for(Nivel n: lista.getListaNivel()){
            List<Exercicio> listaExercicio = new ArrayList<Exercicio>();
            for(Exercicio e: lista.getExercicios()){
                if(e.getNivel().getId() == n.getId() && listaExercicio.size() < n.getQuantidade()){
                    listaExercicio.add(e);
                }
            }
            mapaNivelExercicio.put(n, listaExercicio);
        }
        lista.setExercicios(new ArrayList<Exercicio>());
        for(List<Exercicio> listaExercicio: mapaNivelExercicio.values()){
            for(Exercicio exe: listaExercicio){
                lista.getExercicios().add(exe);
            }
        }
        if(lista.getExercicios().size() > lista.getQuantidade()) // Se a quantidade de exercicios for maior que a pedida
            lista.setExercicios(lista.getExercicios().subList(0, lista.getQuantidade())); // retire o restante que não foi pedido
        return lista;
    }
        
}
