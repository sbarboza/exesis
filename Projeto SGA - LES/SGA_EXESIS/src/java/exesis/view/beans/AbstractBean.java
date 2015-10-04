
package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.IFachada;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class AbstractBean implements Serializable{
    protected Resultado resultado;

    protected IFachada fachada;
    protected boolean renderizarCampos;
    public AbstractBean() {
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
