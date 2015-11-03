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
        float ponto = 0;
        for(Exercicio criados: listaRealizada.getAvaliacao().getListaCriada().getExercicios()){
            ponto += criados.getNivel().getPeso();
        }
        ponto = 10 / ponto;
        for(Exercicio criados: listaRealizada.getAvaliacao().getListaCriada().getExercicios()){
            if(criados.getTipo() == Exercicio.MULTIPLAESCOLHA){
                for(int i = 0; i < listaRealizada.getListaRespostas().size(); i++){
                    if(listaRealizada.getListaRespostas().get(i).getExercicio().getId() == criados.getId()){
                        double certos = 0;
                        double errados = 0;
                        double verdadeiros = 0;
                        double nota = 0;
                        for(Alternativa alt: criados.getAlternativas()){
                            for(Alternativa altRealizada: listaRealizada.getListaRespostas().get(i).getAlternativas()){
                                if(altRealizada.getId() == alt.getId() && alt.getResposta() == true)
                                    verdadeiros++;
                                if(altRealizada.getId() == alt.getId() && altRealizada.getResposta() == true && alt.getResposta() == true){
                                    certos++;
                                }else if(altRealizada.getId() == alt.getId() && altRealizada.getResposta() == true && alt.getResposta() == false){
                                    errados++;
                                }
                            }
                        }
                        if(verdadeiros != 0)
                            nota = (certos-errados)/verdadeiros;
                        listaRealizada.getListaRespostas().get(i).setCorrecao(nota < 0? 0 : nota);
                        nota = nota * ponto * listaRealizada.getListaRespostas().get(i).getExercicio().getNivel().getPeso();
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
