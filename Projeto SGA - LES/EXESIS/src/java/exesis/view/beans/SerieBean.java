
package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.Serie;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "serieBean")
@ViewScoped
public class SerieBean extends AbstractBean{
    private Serie serie;
    private List<Serie> listaSeries;
    @PostConstruct
    public void init(){
        fachada = new Fachada();
        serie = new Serie();
        atualizar();
    }
    
    public void atualizar(){
        resultado = fachada.consultar(new Serie());
        listaSeries = new ArrayList<Serie>();
        if(!resultado.getEntidades().isEmpty()){
            for(int i = 0; i < resultado.getEntidades().size(); i++)
            listaSeries.add((Serie) resultado.getEntidades().get(i));
        }

    }
    
    public void salvar(){
        fachada = new Fachada();
        resultado = fachada.salvar(serie);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Salvo", "");
        }
        atualizar();
    }
    
     public void apagar(int id){
        fachada = new Fachada();
        resultado = fachada.excluir(new Serie(id));
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", "");
        }
        atualizar();
    }
    public void alterartodos(int idSerie){
            Serie serie = null;
            for(Serie t: listaSeries){
                if(t.getId() == idSerie)
                    serie = t;
            }
            if(serie != null){
                resultado = fachada.alterar(serie);
                if(!resultado.getMsgs().isEmpty()){
                    Mensagem(FacesMessage.SEVERITY_WARN, "Erro", resultado.getMsgs());
                }else{
                    Mensagem(FacesMessage.SEVERITY_INFO, "Salvo", "");
                }
            }
        atualizar();
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public List<Serie> getListaSeries() {
        return listaSeries;
    }

    public void setListaSeries(List<Serie> listaSeries) {
        this.listaSeries = listaSeries;
    }
   
}
