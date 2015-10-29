 package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.Alternativa;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.ListaRealizada;

/**
 * Estratégia que calcula a nota referênte a uma lista realizada
 * @author EXESIS
 */
public class CalcularNotaListaRealizada implements IStrategy{

    /**
     * Executa o calculo das notas de cada exercício da lista realizada
     * @param entidade Lista Realizada que precisa ser corrigida (somente os exercícios de múltipla escolha)
     * @return Lista Realizada com as notas calculadas
     */
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        ListaRealizada listaRealizada = (ListaRealizada) entidade;
        for(Exercicio criados: listaRealizada.getListaCriada().getExercicios()){
            if(criados.getTipo() == Exercicio.MULTIPLAESCOLHA){
                for(int i = 0; i < listaRealizada.getListaRespostas().size(); i++){
                    if(listaRealizada.getListaRespostas().get(i).getExercicio().getId() == criados.getId()){
                        double certos = 0;
                        double nota = 0;
                        for(Alternativa alt: criados.getAlternativas()){
                            for(Alternativa altRealizada: listaRealizada.getListaRespostas().get(i).getAlternativas()){
                                if(altRealizada.getId() == alt.getId() && altRealizada.getResposta() == true && alt.getResposta() == true){
                                    certos++;
                                }
                            }
                        }
                        nota = (certos/criados.getAlternativas().size()) * criados.getPeso();
                        listaRealizada.getListaRespostas().get(i).setNota(nota);
                    }
                }
            }
        }
        resultado.zerar();
        resultado.setEntidade(listaRealizada);
        return  resultado;
    }

}
