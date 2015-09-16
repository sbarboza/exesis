package exesis.view.beans;
import exesis.core.control.Fachada;
import exesis.model.Aluno;
import exesis.model.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "alunoBean")
@RequestScoped
public class AlunoBean extends AbstractBean{
    
    private Aluno aluno;
    private Usuario usuario;
    
    public AlunoBean(){    
        aluno = new Aluno();
        usuario = new Usuario();
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public boolean isRenderizarCampos() {
        return renderizarCampos;
    }

    public void setRenderizarCampos(boolean renderizarCampos) {
        this.renderizarCampos = renderizarCampos;
    }
    
    public void salvar(){
        fachada = new Fachada();
        aluno.setUsuario(usuario);
        resultado = fachada.salvar(aluno);
        if(!resultado.getMsgs().isEmpty())
            Mensagem(FacesMessage.SEVERITY_WARN, "Preencha os campos corretamente!", "Campos: ".concat(resultado.getMsgs().toString()));
        else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Operação realizada!", "O aluno " + aluno.getNome() + " " + aluno.getSobrenome() + " foi salvo com sucesso!");
            aluno = new Aluno();
            usuario = new Usuario();
        }
    }
    
    public void buscar(){
        consultar();
        renderizarCampos = Boolean.FALSE;
    }
    
    public void consultar(){
        fachada = new Fachada();
        aluno.setUsuario(usuario);
        resultado = fachada.consultar(aluno);
        if(!resultado.getEntidades().isEmpty()){
            renderizarCampos = Boolean.TRUE;
            aluno = (Aluno) resultado.getEntidades().get(0);
            usuario = aluno.getUsuario();
            resultado.getEntidades().clear();  
        }else{
            renderizarCampos = Boolean.FALSE;
            Mensagem(FacesMessage.SEVERITY_WARN, "Registro não encontrado! ", "");
        }
        
    }

        public void excluir(){
            renderizarCampos = Boolean.FALSE;
            fachada = new Fachada();
            aluno.setUsuario(usuario);
            resultado = fachada.excluir(aluno);
            if(!resultado.getMsgs().isEmpty()){
               Mensagem(FacesMessage.SEVERITY_INFO, "Operação realizada. ",  "Aluno excluído com sucesso!");
               limpar();
            }else{
                Mensagem(FacesMessage.SEVERITY_WARN, "Operação não realizada", "Não foi encontrado nenhum registro!");
            }
        }     
   
        public void alterar(){
            renderizarCampos = Boolean.FALSE;
            fachada = new Fachada();
            aluno.setUsuario(usuario);
            resultado = fachada.alterar(aluno);
            if(!resultado.getMsgs().isEmpty()){
                 Mensagem(FacesMessage.SEVERITY_WARN, "Preencha os campos corretamente!", "");
                 Mensagem(FacesMessage.SEVERITY_WARN, "Campos pendentes: ", resultado.getMsgs());
            }else{
                Mensagem(FacesMessage.SEVERITY_INFO, "Operação realizada. ", "");
                limpar();
            }
    }
    
        public void limpar() {
            aluno = new Aluno();
            usuario = new Usuario();
        }
}


