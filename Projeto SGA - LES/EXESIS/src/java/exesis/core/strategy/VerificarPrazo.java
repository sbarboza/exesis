package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.Avaliacao;
import exesis.model.EntidadeDominio;
import java.util.Date;

public class VerificarPrazo implements IStrategy{

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        if(entidade instanceof Avaliacao){
            Avaliacao avaliacao = (Avaliacao) entidade;
            if(avaliacao.getPrazo() != null){
                if(avaliacao.getPrazo().before(new Date(System.currentTimeMillis())))
                    resultado.setMsg("Favor definir prazo a partir desta data!");
            }else{
                resultado.setMsg("Favor incluir o prazo!");
            }
                
        }
        return resultado;
    }

}
