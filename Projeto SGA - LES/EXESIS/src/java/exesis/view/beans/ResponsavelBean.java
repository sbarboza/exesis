package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.ResponsavelAluno;
import exesis.model.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "responsavelBean")
@ViewScoped
public class ResponsavelBean extends AbstractBean{
    private ResponsavelAluno responsavel;
    
    public ResponsavelBean(){
        responsavel = new ResponsavelAluno();
        usuario = new Usuario();
    }

    public ResponsavelAluno getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelAluno responsavel) {
        this.responsavel = responsavel;
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void salvar(){
        fachada = new Fachada();
        responsavel.setUsuario(usuario);
        resultado = fachada.salvar(responsavel);
        if(!resultado.getMsgs().isEmpty())
            Mensagem(FacesMessage.SEVERITY_WARN, "Preencha os campos corretamente!", "Campos: ".concat(resultado.getMsgs().toString()));
        else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Operação realizada!", "O administrador " + responsavel.getNome() + " " + responsavel.getSobrenome() + " foi salvo com sucesso!");
            limpar();
        }
    }
    
    public void buscar(){
        consultar();
        renderizarCampos = Boolean.FALSE;
    }
    
    public void consultar(){
        fachada = new Fachada();
        responsavel.setUsuario(usuario);
        resultado = fachada.consultar(responsavel);
        if(!resultado.getEntidades().isEmpty() && responsavel.getId() != 0){
            renderizarCampos = Boolean.TRUE;
            responsavel = (ResponsavelAluno) resultado.getEntidades().get(0);
            usuario = responsavel.getUsuario();
        }else{
            renderizarCampos = Boolean.FALSE;
            Mensagem(FacesMessage.SEVERITY_WARN, "Registro não encontrado! ", "");
        }
        resultado.zerar();
    }

        public void excluir(){
            renderizarCampos = Boolean.FALSE;
            fachada = new Fachada();
            responsavel.setUsuario(usuario);
            resultado = fachada.excluir(responsavel);
            if(resultado.getMsgs().isEmpty()){
               Mensagem(FacesMessage.SEVERITY_INFO, "Operação realizada. ",  "Responsável excluído com sucesso!");
               limpar();
            }else{
                Mensagem(FacesMessage.SEVERITY_WARN, "Operação não realizada", "Não foi encontrado nenhum registro!");
            }
        }     
   
        public void alterar(){
            renderizarCampos = Boolean.FALSE;
            fachada = new Fachada();
            responsavel.setUsuario(usuario);
            resultado = fachada.alterar(responsavel);
            if(!resultado.getMsgs().isEmpty()){
                 Mensagem(FacesMessage.SEVERITY_WARN, "Preencha os campos corretamente!", "");
                 Mensagem(FacesMessage.SEVERITY_WARN, "Campos pendentes: ", resultado.getMsgs());
            }else{
                Mensagem(FacesMessage.SEVERITY_INFO, "Operação realizada. ", "");
                limpar();
            }
    }
    
        public void limpar() {
            responsavel = new ResponsavelAluno();
            usuario = new Usuario();
            renderizarCampos = false;
        }
        
}
