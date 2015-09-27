package exesis.view.beans;

import exesis.model.Disciplina;
import exesis.model.DisciplinaProfessor;
import exesis.model.Professor;
import exesis.model.Turma;
import java.io.Serializable;
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
public class  TurmaBean extends AbstractBean implements Serializable{
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
    Map<String, String> mapSeries;
    @PostConstruct
    public void init(){ 
        mapSeries = new HashMap<String, String>();
        mapSeries.put("1ª - FUNDAMENTAL", "1ª - FUNDAMENTAL");
        mapSeries.put("2ª - FUNDAMENTAL", "2ª - FUNDAMENTAL");
        mapSeries.put("3ª - FUNDAMENTAL", "3ª - FUNDAMENTAL");
        mapSeries.put("4ª - FUNDAMENTAL", "4ª - FUNDAMENTAL");
        mapSeries.put("5ª - FUNDAMENTAL", "5ª - FUNDAMENTAL");
        mapSeries.put("6ª - FUNDAMENTAL", "6ª - FUNDAMENTAL");
        mapSeries.put("7ª - FUNDAMENTAL", "7ª - FUNDAMENTAL");
        mapSeries.put("8ª - FUNDAMENTAL", "8ª - FUNDAMENTAL");
        mapSeries.put("1ª - MÉDIO", "1ª - MÉDIO");
        mapSeries.put("2ª - MÉDIO", "2ª - MÉDIO");
        mapSeries.put("3ª - MÉDIO", "3ª - MÉDIO");
        
        
        lista = new ArrayList<DisciplinaProfessor>();
        
        disciplinaProfessor = new DisciplinaProfessor();
        dados = new HashMap<String, Map<String, String>>();
        
        mapaDisciplinas = new HashMap<String, String>();
        mapaDisciplinas.put("MATEMATICA","MATEMATICA");
        mapaDisciplinas.put("PORTUGUES","PORTUGUES");
        mapaDisciplinas.put("BIOLOGIA","BIOLOGIA");
        mapaDisciplinas.put("HISTORIA","HISTORIA");
        mapaDisciplinas.put("GEOGRAFIA","GEOGRAFIA");
        
        mapaProfessores = new HashMap<String, String>();
        mapaProfessores.put("MARCOS","MARCOS");
        mapaProfessores.put("EDMILSON", "EDMILSON");
        dados.put("MATEMATICA",mapaProfessores);
        
        mapaProfessores = new HashMap<String, String>();
        mapaProfessores.put("MARCOS","MARCOS");
        mapaProfessores.put("ALMIR", "ALMIR");
        dados.put("BIOLOGIA",mapaProfessores);
        
        mapaProfessores = new HashMap<String, String>();
        mapaProfessores.put("MARIANGELA","MARIANGELA");
        mapaProfessores.put("ALMIR", "ALMIR");
        dados.put("HISTORIA",mapaProfessores);
        
        mapaProfessores = new HashMap<String, String>();
        mapaProfessores.put("MARCOS","MARCOS");
        mapaProfessores.put("MARIANGELA", "MARIANGELA");
        dados.put("GEOGRAFIA",mapaProfessores);
        
        mapaProfessores = new HashMap<String, String>();
        mapaProfessores.put("MARIANGELA","MARIANGELA");
        mapaProfessores.put("ALMIR", "ALMIR");
        dados.put("PORTUGUES",mapaProfessores);
        
        disciplinas = new ArrayList<Disciplina>();
        disciplinas.add(new Disciplina("Matemática"));
        disciplinas.add(new Disciplina("Português"));
        disciplinas.add(new Disciplina("Biologia"));
        disciplinas.add(new Disciplina("História"));
        disciplinas.add(new Disciplina("Geografia"));
        
        
    }
    
    public List<String> completeText(String procura){
        List<String> lista = new ArrayList<String>();
        for(String s: mapSeries.values()){
            if(s.toUpperCase().contains(procura.toUpperCase()))
                lista.add(s);
        }
        return lista;
    }
    
    public void onChange(){
       if(mapaDisciplinas !=null && !mapaDisciplinas.equals(""))
            mapaProfessores = dados.get(disciplinaProfessor.getDisciplina());
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
     
    public String reinit() {
        disciplinaProfessor = new DisciplinaProfessor();  
        return null;
    }
    
    public void atualizarLista(){
        
    }

    public Map<String, String> getSeries() {
        return mapSeries;
    }

    public void setSeries(Map<String, String> series) {
        this.mapSeries = series;
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
