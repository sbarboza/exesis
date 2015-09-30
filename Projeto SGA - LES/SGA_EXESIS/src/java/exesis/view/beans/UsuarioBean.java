package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.Usuario;
import exesis.teste.popularBanco.PopularBancoHibernate;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class UsuarioBean extends AbstractBean{
    private String nomeUsuario;
    private String senhaUsuario;
    private Usuario usuario;
    private PopularBancoHibernate popular;
    
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
        usuario.setLogin(nomeUsuario);
        usuario.setSenha(senhaUsuario);
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
            usuario = null;
        }
        return null;
    }
    
    public String sair(){
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("usuario", null);
        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler navHandler = context.getApplication().getNavigationHandler();
        navHandler.handleNavigation(context, null , "login");
        usuario = null;
        return null;
    }
    
    public void teste(){
        popular = new PopularBancoHibernate();
        popular.popular(10);
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    
    public PopularBancoHibernate getPopular() {
        return popular;
    }

    public void setPopular(PopularBancoHibernate popular) {
        this.popular = popular;
    }
    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }
    
}
