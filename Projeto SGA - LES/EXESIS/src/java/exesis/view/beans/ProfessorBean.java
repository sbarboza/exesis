package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name = "professorBean")
@ViewScoped
public class ProfessorBean extends AbstractBean{
    private Professor professor;
    private Usuario usuario;
    private List<Professor> listaProfessor;
    private String busca = "";
    
    public ProfessorBean(){    
        professor = new Professor();
        usuario = new Usuario();
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }
    
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Professor> getListaProfessor() {
        return listaProfessor;
    }

    public void setListaProfessor(List<Professor> listaProfessor) {
        this.listaProfessor = listaProfessor;
    }
    
    
    public void salvar(){
        fachada = new Fachada();
        renderizarCampos = Boolean.FALSE;
        professor.setUsuario(usuario);        
        resultado = fachada.salvar(professor);
        if(!resultado.getMsgs().isEmpty())
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Para continuar o cadastro, preencha corretamente os seguintes campos: ", resultado.getMsgs().toString()));
        else{
            if(professor.getSexo().equals("F"))
                Mensagem(FacesMessage.SEVERITY_INFO, "Salvo com sucesso",  "Os dados da professora " + professor.getNome() +" foram salvos com sucesso!");
            else
                Mensagem(FacesMessage.SEVERITY_INFO, "Salvo com sucesso",  "Os dados do professor " + professor.getNome() +" foram salvos com sucesso!");
            reset();
        }
    }
    
    public void alterar(){
        fachada = new Fachada();
        professor.setUsuario(usuario);
        resultado = fachada.alterar(professor);
        if(!resultado.getMsgs().isEmpty())      
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Para continuar a atualização o cadastro, preencha corretamente os seguintes campos: ", resultado.getMsgs().toString()));
        else{
            if(professor.getSexo().equals("F"))
                Mensagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso",  "Os dados da professora " + professor.getNome() +" foram atualizados com sucesso!");
            else
                Mensagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso",  "Os dados do professor " + professor.getNome() +" foram atualizados com sucesso!");
            reset();
        }
    }
    
    public void consultar(){
        renderizarCampos = Boolean.TRUE;
        fachada = new Fachada();
        reset();
        if(busca.trim().length() != 0){
            professor.setNome(busca);
            professor.setSobrenome(busca);
            usuario.setEmail(busca);
            usuario.setLogin(busca);
        }
        professor.setUsuario(usuario);
        resultado = fachada.consultar(professor);
        Professor p;
        if(!resultado.getEntidades().isEmpty() && professor.getId() != 0){
            p = (Professor) resultado.getEntidades().get(0);
            professor = p;
            usuario = p.getUsuario();
            listaProfessor.clear();
            for(EntidadeDominio e: resultado.getEntidades()){
                listaProfessor.add((Professor)e);
            }
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Não encontrado",  "Nada encontrado com os parâmetros da busca");
            reset();
        }
        resultado.zerar();
    } 
    
    public void excluir(){
        fachada = new Fachada();
        reset();
        professor.setUsuario(usuario);
        resultado = fachada.excluir(professor);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_ERROR, "Erro ao tentar excluir",  "Não foi possível excluir o registro") ;
        }else{
             Mensagem(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", resultado.getMsgs());
        }
        reset();
    }
    
    public void reset(){ 
        renderizarCampos = Boolean.FALSE;
        int id = professor.getId();
        limpar();
        professor.setId(id);
        usuario.setId(id);
    }
    
    public void limpar(){
        professor = new Professor();
        usuario = new Usuario();
        listaProfessor = new ArrayList<Professor>();
        fachada = new Fachada();
        busca = "";
    }
    
}
