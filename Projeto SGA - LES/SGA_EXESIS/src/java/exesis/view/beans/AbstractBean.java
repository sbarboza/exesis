
package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.IFachada;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class AbstractBean {
    protected Resultado resultado;
    protected AnnotationConfigApplicationContext context;
    protected IFachada fachada;
    protected boolean renderizarCampos;
    public AbstractBean() {
        context = new AnnotationConfigApplicationContext("exesis");
        renderizarCampos = Boolean.FALSE;
    }
    
    protected void Mensagem(FacesMessage.Severity tipo, String title, String body){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, title, body));
    }
    
    protected void Mensagem(FacesMessage.Severity tipo, String title, List<String> lista){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(tipo, title, ""));
        for(String msg: lista)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, "", msg));
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public void setContext(AnnotationConfigApplicationContext context) {
        this.context = context;
    }

    public IFachada getFachada() {
        return fachada;
    }

    public void setFachada(IFachada fachada) {
        this.fachada = fachada;
    }

    public boolean isRenderizarCampos() {
        return renderizarCampos;
    }

    public void setRenderizarCampos(boolean renderizarCampos) {
        this.renderizarCampos = renderizarCampos;
    }
    
}
