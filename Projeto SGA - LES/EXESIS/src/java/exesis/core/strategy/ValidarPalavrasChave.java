
package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;

public class ValidarPalavrasChave implements IStrategy{

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        if(entidade instanceof Exercicio){
            Exercicio exercicio = (Exercicio) entidade;
            if(exercicio.getTags() == null || exercicio.getTags().isEmpty())
                resultado.setMsg("Favor inserir pelo menos uma palavra-chave!");
        }
        return resultado;
    }
}
