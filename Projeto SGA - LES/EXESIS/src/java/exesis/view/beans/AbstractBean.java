
package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.IFachada;
import exesis.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class AbstractBean implements Serializable{
    protected Resultado resultado;

    protected IFachada fachada;
    protected boolean renderizarCampos;
    protected Usuario usuario;
    
    public AbstractBean() {
        renderizarCampos = Boolean.FALSE;
        verificarNivelAcesso();
    }
    
    public void verificarNivelAcesso(){
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        usuario = (Usuario) session.getAttribute("usuario");
        // verificar se o usuário pode acessar a URL solicitada
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        boolean possuiAcesso = true;
        if(usuario != null)
            switch(usuario.getPerfilAcesso()){
                case Usuario.ADMINISTRADOR:
                    if(!url.contains("admin"))
                        possuiAcesso = false;
                    break;
                case Usuario.ALUNO:
                    if(!url.contains("aluno"))
                        possuiAcesso = false;
                    break;
                case Usuario.PROFESSOR:
                    if(!url.contains("professor"))
                        possuiAcesso = false;
                    
                    break;
                case Usuario.RESPONSAVEL_ALUNO:
                    if(!url.contains("responsavel"))
                        possuiAcesso = false;
                    
                    break;
                default:
                        possuiAcesso = false;
            }
            
            if(!possuiAcesso){
                FacesContext context = FacesContext.getCurrentInstance();
                NavigationHandler navHandler = context.getApplication().getNavigationHandler();
                navHandler.handleNavigation(context, null , "login");
            }
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
