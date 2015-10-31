
package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.EntidadeDominio;
import exesis.model.ListaCriada;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "disponibilizarAvaliacaoBean")
@ViewScoped
public class DisponibilizarAvaliacaoBean extends AbstractBean{
    private List<ListaCriada> listasCriadas;
    @PostConstruct
    public void init(){
        resultado = Resultado.getResultado();
        resultado.zerar();
        fachada = new Fachada();
        resultado = fachada.consultar(new ListaCriada());
        listasCriadas = new ArrayList<ListaCriada>();
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                listasCriadas.add((ListaCriada)e);
            }
        }
    }

    public List<ListaCriada> getListasCriadas() {
        return listasCriadas;
    }

    public void setListasCriadas(List<ListaCriada> listasCriadas) {
        this.listasCriadas = listasCriadas;
    }
    
    
}
