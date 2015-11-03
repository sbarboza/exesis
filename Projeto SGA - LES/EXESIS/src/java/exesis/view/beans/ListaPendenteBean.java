package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.Aluno;
import exesis.model.Avaliacao;
import exesis.model.Disciplina;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.Lista;
import exesis.model.ListaCriada;
import exesis.model.ListaRealizada;
import exesis.model.Professor;
import exesis.model.Resposta;
import exesis.model.Turma;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name="listaPendenteBean")
@ViewScoped
public class ListaPendenteBean extends AbstractBean{

    private Integer progress;
    private boolean exibir = false;
    
    private List<Avaliacao> listaAvaliacao;
    private Avaliacao selectedLista;
    
    private Map<Integer, Disciplina> mapDisciplina;
    private Map<Integer, Professor> mapProfessor;
    private Map<Integer, ListaCriada> mapLista;
    
    private ListaRealizada listaRealizada;
     
    @PostConstruct
    public void init() {
        fachada = new Fachada();
        resultado = Resultado.getResultado();
        
        mapDisciplina = new HashMap<Integer, Disciplina>();
        resultado = fachada.consultar(new Disciplina());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Disciplina d = (Disciplina) e;
                mapDisciplina.put(d.getId(), d);
            }
        }
        
        mapProfessor = new HashMap<Integer, Professor>();
        resultado = fachada.consultar(new Professor());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Professor p = (Professor) e;
                mapProfessor.put(p.getId(), p);
            }
        }
        
        mapLista = new HashMap<Integer, ListaCriada>();
        resultado = fachada.consultar(new ListaCriada());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                ListaCriada l = (ListaCriada) e;
                mapLista.put(l.getId(), l);
            }
        }
        
        listaAvaliacao = new ArrayList<Avaliacao>();
        resultado = fachada.consultar(new Avaliacao(new Turma(1)));
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Avaliacao avaliacao = (Avaliacao)e;
                avaliacao.setDisciplina(mapDisciplina.get(avaliacao.getDisciplina().getId()));
                if(avaliacao.getProfessor() != null && avaliacao.getProfessor().getId() != 0 ){
                     avaliacao.setProfessor(mapProfessor.get(avaliacao.getProfessor().getId()));
                }
                avaliacao.setListaCriada(mapLista.get(avaliacao.getListaCriada().getId()));
                listaAvaliacao.add(avaliacao);
            }
        }
        
         HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(session.getAttribute("listaRealizada") != null){
            listaRealizada = (ListaRealizada) session.getAttribute("listaRealizada");
        }
        
    }
 
 
    public void realizarAvaliacao(){
        listaRealizada = new ListaRealizada();
        listaRealizada.setAluno(new Aluno(1));
        listaRealizada.setAvaliacao(selectedLista);
        listaRealizada.setListaRespostas(new ArrayList<Resposta>());
        List<Exercicio> listaExercicio = listaRealizada.getAvaliacao().getListaCriada().getExercicios();
        for(Exercicio exe: listaExercicio){
            listaRealizada.getListaRespostas().add(new Resposta(exe, exe.getAlternativas()));
        }
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("listaRealizada", listaRealizada);
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null , "realizarLista");
    }
    
    public void enviar(ListaRealizada listaRealizada){
        resultado = fachada.salvar(listaRealizada);
        if(resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_INFO, "Enviar com sucesso", "");
        }else{
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro ao enviar", resultado.getMsgs());
        }
    }
    
    //PROGRESS BAR
    public Integer getProgress() {
        if(progress == null) {
            progress = 0;
        }
        else {
            progress = progress + (int)(Math.random() * 35);
             
            if(progress > 100)
                progress = 100;
        }
         
        return progress;
    }
 
    public void setProgress(Integer progress) {
        this.progress = progress;
    }
     
    public void onComplete() {
        setExibir(true);
    }
     
    public void cancel() {
        progress = null;
    }
    
    public void setExibir(){
        if(isExibir())
            setExibir(false);
        else
            setExibir(true);
    }

    /**
     * @return the exibir
     */
    public boolean isExibir() {
        return exibir;
    }

    /**
     * @param exibir the exibir to set
     */
    public void setExibir(boolean exibir) {
        this.exibir = exibir;
    }

    public List<Avaliacao> getListaAvaliacao() {
        return listaAvaliacao;
    }

    public void setListaAvaliacao(List<Avaliacao> listaAvaliacao) {
        this.listaAvaliacao = listaAvaliacao;
    }

    public Map<Integer, Disciplina> getMapDisciplina() {
        return mapDisciplina;
    }

    public void setMapDisciplina(Map<Integer, Disciplina> mapDisciplina) {
        this.mapDisciplina = mapDisciplina;
    }

    public Map<Integer, Professor> getMapProfessor() {
        return mapProfessor;
    }

    public void setMapProfessor(Map<Integer, Professor> mapProfessor) {
        this.mapProfessor = mapProfessor;
    }

    public Map<Integer, ListaCriada> getMapLista() {
        return mapLista;
    }

    public void setMapLista(Map<Integer, ListaCriada> mapLista) {
        this.mapLista = mapLista;
    }

    public ListaRealizada getListaRealizada() {
        return listaRealizada;
    }

    public void setListaRealizada(ListaRealizada listaRealizada) {
        this.listaRealizada = listaRealizada;
    }

    public Avaliacao getSelectedLista() {
        return selectedLista;
    }

    public void setSelectedLista(Avaliacao selectedLista) {
        this.selectedLista = selectedLista;
    }
    
    
}



