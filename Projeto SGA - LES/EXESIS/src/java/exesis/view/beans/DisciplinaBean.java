package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.Disciplina;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;



@ManagedBean(name="disciplinaBean")
@ViewScoped
public class DisciplinaBean extends AbstractBean{
    private DualListModel<String> professores;
    private Map<String, Professor> mapaProfessores;
    private List<String> profCadastrados;
    private List<String> profAlocados;
    private Disciplina disciplina;
    private List<Disciplina> listaDisciplinas;

    @PostConstruct
    public void init(){
        disciplina = new Disciplina();
        mapaProfessores = new HashMap<String, Professor>();
        profAlocados = new ArrayList<String>();
        profCadastrados = new ArrayList<String>();
        listaDisciplinas = new ArrayList<Disciplina>();
        resultado = Resultado.getResultado();
        resultado.zerar();
        fachada = new Fachada();
        resultado = fachada.consultar(new Professor());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                mapaProfessores.put(((Professor)e).getNome(), (Professor)e);
                profCadastrados.add(((Professor)e).getNome());
            }
        }
        professores = new DualListModel<>(profCadastrados, profAlocados);
        resultado.zerar();
        resultado = fachada.consultar(new Disciplina());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                listaDisciplinas.add(((Disciplina)e));
            }
        }    
    }

    public void redirecionar(){
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler navHandler = context.getApplication().getNavigationHandler();
            navHandler.handleNavigation(context, null , "/admin/instituicao/consultar/editar_disciplina.xhtml");
    }
    
    public void editar(Disciplina disciplina){
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler navHandler = context.getApplication().getNavigationHandler();
            navHandler.handleNavigation(context, null , "/admin/instituicao/consultar/editar_disciplina.jsf");
            consultar(disciplina);
    }
    
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }


    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }
    public void consultar(Disciplina disciplina){
        this.disciplina = disciplina;
        resultado = fachada.consultar(disciplina);
        if(!resultado.getEntidades().isEmpty()) {
            System.out.println("1");
            Disciplina d = (Disciplina) resultado.getEntidades().get(0);
            profAlocados = new ArrayList<String>();
            for(Professor p: d.getProfessores()){  
                profAlocados.add(((Professor)p).getNome());
                System.out.println("2");
            }
        }
        profCadastrados.removeAll(profAlocados);
        professores = new DualListModel<>(profCadastrados, profAlocados);
    }
    
    public void salvar(){
        disciplina.setProfessores(new ArrayList<Professor>());
        if(!professores.getTarget().isEmpty()){
            for(String p: professores.getTarget()){
                disciplina.getProfessores().add(mapaProfessores.get(p));
            }
        }
        resultado = fachada.salvar(disciplina);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_INFO, "Erro ao salvar", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Salva com sucesso", "A disciplina "+disciplina.getNome()+" foi salva com sucesso!" );
        }
            
    }

        public void alterar(){
        disciplina.setProfessores(new ArrayList<Professor>());
        if(!professores.getTarget().isEmpty()){
            for(String p: professores.getTarget()){
                disciplina.getProfessores().add(mapaProfessores.get(p));
            }
        }
        resultado = fachada.alterar(disciplina);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_INFO, "Erro ao alterar", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso", "A disciplina "+disciplina.getNome()+" foi alterada com sucesso!" );
        }
            
    }

    public DualListModel<String> getProfessores() {
        return professores;
    }

    public void setProfessores(DualListModel<String> professores) {
        this.professores = professores;
    }

    public Map<String, Professor> getMapaProfessores() {
        return mapaProfessores;
    }

    public void setMapaProfessores(Map<String, Professor> mapaProfessores) {
        this.mapaProfessores = mapaProfessores;
    }

    public List<String> getProfCadastrados() {
        return profCadastrados;
    }

    public void setProfCadastrados(List<String> profCadastrados) {
        this.profCadastrados = profCadastrados;
    }

    public List<String> getProfAlocados() {
        return profAlocados;
    }

    public void setProfAlocados(List<String> profAlocados) {
        this.profAlocados = profAlocados;
    }
}
