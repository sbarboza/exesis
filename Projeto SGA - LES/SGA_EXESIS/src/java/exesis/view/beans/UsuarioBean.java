package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class UsuarioBean extends AbstractBean{
    private Usuario usuario;
    
    public UsuarioBean(){
        usuario = new Usuario();
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String entrar(){
        fachada = new Fachada();
        resultado = fachada.consultar(usuario);
        if(resultado.getEntidades().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro ao entrar", "Usuário e/ou senha incorretos!");
            return null;
        }
        else{
            String pagina = "";
            HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("usuario", usuario);
            usuario = (Usuario) resultado.getEntidades().get(0);
            switch(usuario.getPerfilAcesso()){
                case Usuario.ADMINISTRADOR:
                    pagina = "admin";
                    break;
                case Usuario.ALUNO:
                    pagina = "aluno";
                    break;
                case Usuario.PROFESSOR:
                    pagina = "professor";
                    break;
                case Usuario.RESPONSAVEL_ALUNO:
                    pagina = "responsavel";
                    break;
            }
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler navHandler = context.getApplication().getNavigationHandler();
            navHandler.handleNavigation(context, null , pagina);
        }
        return null;
    }
    
    public String sair(){
        FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler navHandler = context.getApplication().getNavigationHandler();
            navHandler.handleNavigation(context, null , "login");
        return null;
    }
}