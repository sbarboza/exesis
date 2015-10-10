package exesis.view.beans;
import exesis.core.control.Fachada;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import exesis.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "alunoBean")
@RequestScoped
public class AlunoBean extends AbstractBean{
    
    private Aluno aluno;
    private Usuario usuario;
    private List<Aluno> listaAluno;
    private String busca = "";
    
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
    
    
    public void salvar(){
        fachada = new Fachada();
        renderizarCampos = Boolean.FALSE;
        aluno.setUsuario(usuario);        
        resultado = fachada.salvar(aluno);
        if(!resultado.getMsgs().isEmpty())
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Para continuar o cadastro, preencha corretamente os seguintes campos: ", resultado.getMsgs().toString()));
        else{
            if(aluno.getSexo().equals("F"))
                Mensagem(FacesMessage.SEVERITY_INFO, "Salvo com sucesso",  "Os dados da aluna " + aluno.getNome() +" foram salvos com sucesso!");
            else
                Mensagem(FacesMessage.SEVERITY_INFO, "Salvo com sucesso",  "Os dados do aluno " + aluno.getNome() +" foram salvos com sucesso!");
            reset();
        }
    }
    
    public void alterar(){
        fachada = new Fachada();
        aluno.setUsuario(usuario);
        usuario.setId(aluno.getId());
        resultado = fachada.alterar(aluno);
        if(!resultado.getMsgs().isEmpty())      
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Para continuar a atualização o cadastro, preencha corretamente os seguintes campos: ", resultado.getMsgs().toString()));
        else{
            if(aluno.getSexo().equals("F"))
                Mensagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso",  "Os dados da aluna " + aluno.getNome() +" foram atualizados com sucesso!");
            else
                Mensagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso",  "Os dados do aluno " + aluno.getNome() +" foram atualizados com sucesso!");
            reset();
        }
    }
    
    public void consultar(){
        renderizarCampos = Boolean.TRUE;
        fachada = new Fachada();
        reset();
        if(busca.trim().length() != 0){
            aluno.setNome(busca);
            aluno.setSobrenome(busca);
            usuario.setEmail(busca);
            usuario.setLogin(busca);
        }
        aluno.setUsuario(usuario);
        resultado = fachada.consultar(aluno);
        Aluno a;
        if(!resultado.getEntidades().isEmpty()){
            a = (Aluno) resultado.getEntidades().get(0);
            aluno = a;
            usuario = a.getUsuario();
            listaAluno.clear();
            for(EntidadeDominio e: resultado.getEntidades()){
                listaAluno.add((Aluno)e);
            }
            resultado.getEntidades().clear();
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Não encontrado",  "Nada encontrado com os parâmetros da busca");
            reset();
        }
    } 
    
    public void excluir(){
        fachada = new Fachada();
        reset();
        aluno.setUsuario(usuario);
        resultado = fachada.excluir(aluno);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_ERROR, "Erro ao tentar excluir",  "Não foi possível excluir o registro") ;
        }else{
             Mensagem(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", resultado.getMsgs());
        }
        reset();
    }
    
    public void reset(){ 
        renderizarCampos = Boolean.FALSE;
        int id = aluno.getId();
        limpar();
        aluno.setId(id);
        usuario.setId(id);
    }
    
    public void limpar(){
        aluno = new Aluno();
        usuario = new Usuario();
        listaAluno = new ArrayList<Aluno>();
        fachada = new Fachada();
        busca = "";
    }
}


