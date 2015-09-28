package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.Administrador;
import exesis.model.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "administradorBean")
@ViewScoped
public class AdministradorBean extends AbstractBean{
        
    private Administrador administrador;
    private Usuario usuario;
    
    public AdministradorBean(){
        administrador = new Administrador();
        usuario = new Usuario();
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void salvar(){
        fachada = new Fachada();
        administrador.setUsuario(usuario);
        resultado = fachada.salvar(administrador);
        if(!resultado.getMsgs().isEmpty())
            Mensagem(FacesMessage.SEVERITY_WARN, "Preencha os campos corretamente!", "Campos: ".concat(resultado.getMsgs().toString()));
        else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Operação realizada!", "O administrador " + administrador.getNome() + " " + administrador.getSobrenome() + " foi salvo com sucesso!");
            limpar();
        }
    }
    
    public void buscar(){
        consultar();
        renderizarCampos = Boolean.FALSE;
    }
    
    public void consultar(){
        fachada = new Fachada();
        administrador.setUsuario(usuario);
        resultado = fachada.consultar(administrador);
        if(!resultado.getEntidades().isEmpty()){
            renderizarCampos = Boolean.TRUE;
            administrador = (Administrador) resultado.getEntidades().get(0);
            usuario = administrador.getUsuario();
            resultado.getEntidades().clear();  
        }else{
            renderizarCampos = Boolean.FALSE;
            Mensagem(FacesMessage.SEVERITY_WARN, "Registro não encontrado! ", "");
        }
        
    }

        public void excluir(){
            renderizarCampos = Boolean.FALSE;
            fachada = new Fachada();
            administrador.setUsuario(usuario);
            resultado = fachada.excluir(administrador);
            if(resultado.getMsgs().isEmpty()){
               Mensagem(FacesMessage.SEVERITY_INFO, "Operação realizada. ",  "Administrador excluído com sucesso!");
               limpar();
            }else{
                Mensagem(FacesMessage.SEVERITY_WARN, "Operação não realizada", "Não foi encontrado nenhum registro!");
            }
        }     
   
        public void alterar(){
            renderizarCampos = Boolean.FALSE;
            fachada = new Fachada();
            administrador.setUsuario(usuario);
            resultado = fachada.alterar(administrador);
            if(!resultado.getMsgs().isEmpty()){
                 Mensagem(FacesMessage.SEVERITY_WARN, "Preencha os campos corretamente!", "");
                 Mensagem(FacesMessage.SEVERITY_WARN, "Campos pendentes: ", resultado.getMsgs());
            }else{
                Mensagem(FacesMessage.SEVERITY_INFO, "Operação realizada. ", "");
                limpar();
            }
    }
    
        public void limpar() {
            administrador = new Administrador();
            usuario = new Usuario();
            renderizarCampos = false;
        }

}
