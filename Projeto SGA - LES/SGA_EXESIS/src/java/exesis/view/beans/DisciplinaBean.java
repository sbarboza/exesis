package exesis.view.beans;

import exesis.model.Disciplina;
import exesis.model.Professor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;



@ManagedBean(name="disciplinaBean")
@SessionScoped
public class DisciplinaBean implements Serializable{
    private DualListModel<Professor> professores;
    private List<Professor> profCadastrados;
    private List<Professor> profAlocados;
    private Disciplina disciplina;

    public DualListModel<Professor> getProfessores() {
        return professores;
    }

    @PostConstruct
    public void init(){
        profAlocados = new ArrayList<Professor>();
        profCadastrados = new ArrayList<Professor>();
        Professor prof = new Professor();
        prof.setNome("João");
        profCadastrados.add(prof);
        
        Professor prof1 = new Professor();
        prof1.setNome("Carlos");
        profCadastrados.add(prof1);
        
        Professor prof2 = new Professor();
        prof2.setNome("Alberto");
        profCadastrados.add(prof2);
        
        Professor prof3 = new Professor();
        prof3.setNome("Nobrega");
        profCadastrados.add(prof3);
        
        Professor prof4 = new Professor();
        prof4.setNome("Roberto");
        profCadastrados.add(prof4);
        professores = new DualListModel<>(profCadastrados, profAlocados);
    }

    public List<Professor> getProfCadastrados() {
        return profCadastrados;
    }

    public void setProfCadastrados(List<Professor> profCadastrados) {
        this.profCadastrados = profCadastrados;
    }

    public List<Professor> getProfAlocados() {
        return profAlocados;
    }

    public void setProfAlocados(List<Professor> profAlocados) {
        this.profAlocados = profAlocados;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void editar(){
        FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler navHandler = context.getApplication().getNavigationHandler();
            navHandler.handleNavigation(context, null , "/admin/instituicao/consultar/editar_disciplina.jsf");
            
    }
    
}
