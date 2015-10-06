package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.ConfiguracaoAdministrador;
import exesis.model.NotificacaoAdministrador;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "configuracaoBean")
@ViewScoped
public class ConfiguracaoBean extends AbstractBean{
    private ConfiguracaoAdministrador configAdmin;
    private NotificacaoAdministrador notificaAdmin;
    
    @PostConstruct
    public void init(){
        configAdmin = new ConfiguracaoAdministrador();
        notificaAdmin = new NotificacaoAdministrador();
    }
                
    public ConfiguracaoAdministrador getConfigAdmin() {
        return configAdmin;
    }

    public void setConfigAdmin(ConfiguracaoAdministrador configAdmin) {
        this.configAdmin = configAdmin;
    }

    public NotificacaoAdministrador getNotificaAdmin() {
        return notificaAdmin;
    }

    public void setNotificaAdmin(NotificacaoAdministrador notificaAdmin) {
        this.notificaAdmin = notificaAdmin;
        configAdmin = this.notificaAdmin;
    }
    
    public void salvar(){
        IFachada fachada = new Fachada();
        fachada.salvar(notificaAdmin);
        
    }
}
