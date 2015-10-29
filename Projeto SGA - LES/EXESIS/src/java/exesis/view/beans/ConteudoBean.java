package exesis.view.beans;
 
import exesis.model.Conteudo;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name="conteudoBean")
@ViewScoped
public class ConteudoBean implements Serializable {
     

    private List<Conteudo> conteudo1;
    private List<Conteudo> conteudo2;
    private List<Conteudo> conteudo3;
    private List<Conteudo> conteudo4;
    private List<Conteudo> conteudo5;
    private List<Conteudo> conteudo6;
    private Conteudo selectedConteudo;
    private List<Conteudo> selectedConteudos;
    
    @ManagedProperty("#{conteudoService}")
    private ConteudoService service;
     
    @PostConstruct
    public void init() {
        conteudo1 = service.createConteudos(10);
        conteudo2 = service.createConteudos(10);
        conteudo3 = service.createConteudos(10);
        conteudo4 = service.createConteudos(10);
        conteudo5 = service.createConteudos(10);
        conteudo6 = service.createConteudos(10);
    }
 
    public List<Conteudo> getConteudos1() {
        return conteudo1;
    }
 
    public List<Conteudo> getConteudos2() {
        return conteudo2;
    }
 
    public List<Conteudo> getConteudos3() {
        return conteudo3;
    }
 
    public List<Conteudo> getConteudos4() {
        return conteudo4;
    }
 
    public List<Conteudo> getConteudos5() {
        return conteudo5;
    }
 
    public List<Conteudo> getConteudos6() {
        return conteudo6;
    }
     
    public void setService(ConteudoService service) {
        this.service = service;
    }
 
    public Conteudo getSelectedConteudo() {
        return selectedConteudo;
    }
 
    public void setSelectedConteudo(Conteudo selectedConteudo) {
        this.selectedConteudo = selectedConteudo;
    }
 
    public List<Conteudo> getSelectedConteudos() {
        return selectedConteudos;
    }
 
    public void setSelectedConteudos(List<Conteudo> selectedConteudos) {
        this.selectedConteudos = selectedConteudos;
    }
     
        private StreamedContent file;
     
    public ConteudoBean() {        
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/demo/images/optimus.jpg");
        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
    }
 
    public StreamedContent getFile() {
        return file;
    }
    
}