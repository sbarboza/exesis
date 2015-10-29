package exesis.core.aplicacao;

import exesis.model.EntidadeDominio;
import java.util.ArrayList;
import java.util.List;


public class Resultado {
    private static Resultado resultadostatico;
    private static List<String> msgs;
    private static List<EntidadeDominio> entidades;
    private Resultado(){  
    }
    public void zerar(){
        msgs = null;
        entidades = null;
        resultadostatico = null;    
    }
    
    public static Resultado getResultado(){
        if(resultadostatico == null)
            resultadostatico = new Resultado();
        return resultadostatico;
    }
    public List<String> getMsgs() {
        if(Resultado.msgs == null)
            Resultado.msgs = new ArrayList<String>();
        return Resultado.msgs;
    }
    public void setMsgs(List<String> msgs) {
        Resultado.msgs = msgs;
    }

    public void setMsg(String msg) {
        if(msgs == null)
            msgs = new ArrayList<String>();
        Resultado.msgs.add(msg);
    }
    public List<EntidadeDominio> getEntidades() {
        if(entidades == null)
            entidades = new ArrayList<EntidadeDominio>();
        return entidades;
    }

    public void setEntidades(List<EntidadeDominio> entidades) {
        Resultado.entidades = entidades;
    }
    public void setEntidade(EntidadeDominio entidade) {
        if(entidades == null)
            entidades = new ArrayList<EntidadeDominio>();
        Resultado.entidades.add(entidade);
    }
}
