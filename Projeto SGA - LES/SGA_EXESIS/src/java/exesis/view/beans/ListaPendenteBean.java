package exesis.view.beans;

import exesis.model.Lista;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

@ManagedBean(name="listaPendenteBean")
@ViewScoped
public class ListaPendenteBean extends AbstractBean{

    private Integer progress;
    private boolean exibir = false;
    
    private List<Lista> lista1;
    private List<Lista> lista2;
    private List<Lista> lista3;
    private List<Lista> lista4;
    private List<Lista> lista5;
    private List<Lista> lista6;
    private Lista selectedLista;
    private List<Lista> selectedListas;
    
    @ManagedProperty("#{listaService}")
    private ListaService service;
     
    @PostConstruct
    public void init() {
        lista1 = service.createListas(10);
        lista2 = service.createListas(10);
        lista3 = service.createListas(10);
        lista4 = service.createListas(10);
        lista5 = service.createListas(10);
        lista6 = service.createListas(10);
    }
 
    public List<Lista> getListas1() {
        return lista1;
    }
 
    public List<Lista> getListas2() {
        return lista2;
    }
 
    public List<Lista> getListas3() {
        return lista3;
    }
 
    public List<Lista> getListas4() {
        return lista4;
    }
 
    public List<Lista> getListas5() {
        return lista5;
    }
 
    public List<Lista> getListas6() {
        return lista6;
    }
     
    public void setService(ListaService service) {
        this.service = service;
    }
 
    public Lista getSelectedLista() {
        return selectedLista;
    }
 
    public void setSelectedLista(Lista selectedLista) {
        this.selectedLista = selectedLista;
    }
 
    public List<Lista> getSelectedListas() {
        return selectedListas;
    }
 
    public void setSelectedListas(List<Lista> selectedListas) {
        this.selectedListas = selectedListas;
    }
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Lista Selected", ((Lista) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Lista Unselected", ((Lista) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    //PROGRESS BAR
    public Integer getProgress() {
        if(progress == null) {
            progress = 0;
        }
        else {
            progress = progress + (int)(Math.random() * 35);
             
            if(progress > 100)
                progress = 100;
        }
         
        return progress;
    }
 
    public void setProgress(Integer progress) {
        this.progress = progress;
    }
     
    public void onComplete() {
        setExibir(true);
    }
     
    public void cancel() {
        progress = null;
    }
    
    public void setExibir(){
        if(isExibir())
            setExibir(false);
        else
            setExibir(true);
    }

    /**
     * @return the exibir
     */
    public boolean isExibir() {
        return exibir;
    }

    /**
     * @param exibir the exibir to set
     */
    public void setExibir(boolean exibir) {
        this.exibir = exibir;
    }
}



