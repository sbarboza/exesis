package exesis.view.beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.Professor;
import exesis.model.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


/**
 *
 * @author SAMUEL
 */
@ManagedBean(name = "professorBean")
@RequestScoped
public class ProfessorBean {
    private Professor professor;
    private Usuario usuario;
    Resultado resultado;    
    IFachada fachada;
    
    public ProfessorBean(){    
        professor = new Professor();
        usuario = new Usuario();
        fachada = new Fachada();
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
       
    public void salvar(){
        professor.setUsuario(usuario);        
        System.out.println("Professor Nome:"+ professor.getNome());
        resultado = fachada.salvar(professor);
        if(!resultado.getMsgs().isEmpty())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Preencha os campos corretamente!", resultado.getMsgs().toString()));
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Salvo com sucesso",  "O professor " + professor.getNome() +" foi salvo com sucesso!") );
            reset();
        }
    }
    
    public void alterar(){
        professor.setUsuario(usuario);
        resultado = fachada.alterar(professor);
        if(!resultado.getMsgs().isEmpty())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Preencha os campos corretamente!", resultado.getMsgs().toString()));
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Alterado com sucesso",  "O professor " + professor.getNome() +" foi atualizado com sucesso!") );

        }
    }
    
    public void consultar(){
        professor.setUsuario(usuario);
        resultado = fachada.consultar(professor);
        Professor p;
        if(!resultado.getEntidades().isEmpty()){
            p = (Professor) resultado.getEntidades().get(0);
            professor = p;
            usuario = p.getUsuario();
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Não encontrado",  "Nada encontrado com os parâmetros da busca") );
            reset();
        }
    } 
    
    public void excluir(){
        professor.setUsuario(usuario);
        professor.setId(usuario.getId());
        resultado = fachada.excluir(professor);
        if(resultado.getMsgs().isEmpty()){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao tentar excluir",  "Não foi possível excluir o registro") );
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            for(String msg: resultado.getMsgs()){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
            }
        }
        reset();
    }
    
    public void reset(){ 
        professor = new Professor();
        usuario = new Usuario();
    }
}
