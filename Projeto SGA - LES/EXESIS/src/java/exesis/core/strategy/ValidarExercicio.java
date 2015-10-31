
package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;

public class ValidarExercicio implements IStrategy{

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        if(entidade instanceof Exercicio){
            Exercicio exercicio = (Exercicio) entidade;
            if(exercicio.getEnunciado() == null || exercicio.getEnunciado().trim().isEmpty())
                resultado.setMsg("Preencher o Enunciado");
            if(exercicio.getTipo() == 0)
                resultado.setMsg("Informar tipo de exercício");
            if(exercicio.getNivel() == null)
                resultado.setMsg("Informar nível do exercício");
            resultado = new ValidarPalavrasChave().processar(entidade);
        }
        return resultado;
    }

}
