package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.Disciplina;
import exesis.model.DisciplinaProfessor;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.Serie;
import exesis.model.Turma;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "turmaBean")
@ViewScoped
public class  TurmaBean extends AbstractBean{
    private List<Disciplina> disciplinas;
    private List<Professor> professores;
    
    private List<DisciplinaProfessor> lista;
    private Professor professorSelecionado;
    private Disciplina disciplinaSelecionada;
    private Turma turma;
    private DisciplinaProfessor disciplinaProfessor;
    private String serie;
    private String serieSelecionada;
    private String disciplina;
    private String professor;
    Map<String, Map<String, String>> dados;
    Map<String, String> mapaProfessores;
    Map<String, String> mapaDisciplinas;
    Map<String, Serie> mapSeries;
    @PostConstruct
    public void init(){ 
        resultado = Resultado.getResultado();
        fachada = new Fachada();
        mapSeries = new HashMap<String, Serie>();
        resultado.zerar();
        resultado = fachada.consultar(new Serie());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                mapSeries.put(((Serie)e).getNome(), (Serie)e);
            }
        }
        lista = new ArrayList<DisciplinaProfessor>();
        turma = new Turma();
        disciplinaProfessor = new DisciplinaProfessor();
        dados = new HashMap<String, Map<String, String>>();
        
        resultado.zerar();
        resultado = fachada.consultar(new Disciplina());
        disciplinas = new ArrayList<Disciplina>();
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                disciplinas.add((Disciplina)e);
            }
        }
        if(!disciplinas.isEmpty()){
            mapaDisciplinas = new HashMap<String, String>();
            for(Disciplina d: disciplinas){
                mapaProfessores = new HashMap<String, String>();
                if(d.getProfessores() != null && !d.getProfessores().isEmpty()){
                    for(Professor p: d.getProfessores()){
                        mapaProfessores.put(p.getNome(), p.getNome());
                    }
                }
                dados.put(d.getNome(),mapaProfessores);        
                mapaDisciplinas.put(d.getNome(), d.getNome());                
            }
        }
        
    }
    
    public List<String> completeText(String procura){
        List<String> lista = new ArrayList<String>();
        for(Serie s: mapSeries.values()){
            if(s.getNome().toUpperCase().contains(procura.toUpperCase()))
                lista.add(s.getNome());
        }
        return lista;
    }
    
    public void onChange(){
       if(mapaDisciplinas !=null && !mapaDisciplinas.isEmpty()){
            mapaProfessores = dados.get(disciplinaProfessor.getDisciplina());
        }
        else
            mapaProfessores = new HashMap<String, String>();
    }
    
    public void createNew() {
        if(disciplinas.contains(disciplinaSelecionada)) {
            Mensagem(FacesMessage.SEVERITY_WARN, "Dublicated", "This disciplinas has already been added");
        } 
        else {
            disciplinaProfessor = new DisciplinaProfessor();
            disciplinaProfessor.setDisciplina(disciplina);
            disciplinaProfessor.setProfessor(professor);
            lista.add(disciplinaProfessor);
        }
    }
    public void salvar(){
        for(Serie s: mapSeries.values()){
            System.out.println(s.getNome() +"=="+serie);
            if(s.getNome().equals(serie)){
                turma.setSerie(s);
            }
        }
        turma.setMapaDisciplinaProfessor(new HashMap<Disciplina, Professor>());
        for(DisciplinaProfessor discProf: lista){
            for(Disciplina d: disciplinas){
                if(d.getNome().equals(discProf.getDisciplina())){
                    for(Professor p: d.getProfessores()){
                        if(p.getNome().equals(discProf.getProfessor()))
                            turma.getMapaDisciplinaProfessor().put(d, p);
                    }
                }
            }
        }
        resultado = fachada.salvar(turma);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_INFO, "Erro ao salvar", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Salvo", "");
        }
        
    }
     
    public String reinit() {
        disciplinaProfessor = new DisciplinaProfessor();  
        return null;
    }

    public Map<String, Serie> getMapSeries() {
        return mapSeries;
    }

    public void setMapSeries(Map<String, Serie> mapSeries) {
        this.mapSeries = mapSeries;
    }
    


    
    
    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
    
    public Professor getProfessorSelecionado() {
        return professorSelecionado;
    }

    public void setProfessorSelecionado(Professor professorSelecionado) {
        this.professorSelecionado = professorSelecionado;
    }

    public Disciplina getDisciplinaSelecionada() {
        return disciplinaSelecionada;
    }

    public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
        this.disciplinaSelecionada = disciplinaSelecionada;
    }

    public String getSerieSelecionada() {
        return serieSelecionada;
    }

    public void setSerieSelecionada(String serieSelecionada) {
        this.serieSelecionada = serieSelecionada;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Map<String, Map<String, String>> getDados() {
        return dados;
    }

    public void setDados(Map<String, Map<String, String>> dados) {
        this.dados = dados;
    }

    public Map<String, String> getMapaProfessores() {
        return mapaProfessores;
    }

    public void setMapaProfessores(Map<String, String> mapaProfessores) {
        this.mapaProfessores = mapaProfessores;
    }

    public Map<String, String> getMapaDisciplinas() {
        return mapaDisciplinas;
    }

    public void setMapaDisciplinas(Map<String, String> mapaDisciplinas) {
        this.mapaDisciplinas = mapaDisciplinas;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public List<DisciplinaProfessor> getLista() {
        return lista;
    }

    public void setLista(List<DisciplinaProfessor> lista) {
        this.lista = lista;
    }

    public DisciplinaProfessor getDisciplinaProfessor() {
        return disciplinaProfessor;
    }

    public void setDisciplinaProfessor(DisciplinaProfessor disciplinaProfessor) {
        this.disciplinaProfessor = disciplinaProfessor;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
    
}
