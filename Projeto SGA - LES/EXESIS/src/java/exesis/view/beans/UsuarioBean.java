package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.Administrador;
import exesis.model.Aluno;
import exesis.model.Pessoa;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import exesis.model.Usuario;
import exesis.teste.popularBanco.PopularBanco;
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
    private PopularBanco popular;
    private Pessoa pessoa;
    
    public UsuarioBean(){     
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String entrar(){
        usuario = new Usuario();
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
            usuario = (Usuario) resultado.getEntidades().get(0);
            session.setAttribute("usuario", usuario);
            switch(usuario.getPerfilAcesso()){
                case Usuario.ADMINISTRADOR:
                    pagina = "admin";
                    pessoa = new Administrador();
                    break;
                case Usuario.ALUNO:
                    pagina = "aluno";
                    pessoa = new Aluno();
                    break;
                case Usuario.PROFESSOR:
                    pagina = "professor";
                    pessoa = new Professor();
                    break;
                case Usuario.RESPONSAVEL_ALUNO:
                    pagina = "responsavel";
                    pessoa = new ResponsavelAluno();
                    break;
                default:
                    pagina = "login";
            }
            resultado =  fachada.consultar(pessoa);
            if(resultado.getEntidades().get(0) != null)
                pessoa = (Pessoa) resultado.getEntidades().get(0);
            session.setAttribute("pessoa", pessoa);
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
        popular = new PopularBanco();
        popular.popular(10);
        Mensagem(FacesMessage.SEVERITY_INFO, "Populado Banco", "Realizado o cadastro dos testes no banco");
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

    
    public PopularBanco getPopular() {
        return popular;
    }

    public void setPopular(PopularBanco popular) {
        this.popular = popular;
    }
    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }
    
}
