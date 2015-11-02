package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.Avaliacao;
import exesis.model.Disciplina;
import exesis.model.EntidadeDominio;
import exesis.model.ListaCriada;
import exesis.model.Turma;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "disponibilizarAvaliacaoBean")
@ViewScoped
public class DisponibilizarAvaliacaoBean extends AbstractBean{
    private Map<String, ListaCriada> maplistas;
    private Map<Integer, ListaCriada> mapidlistas;
    private List<String> listasCriadas;
    private ListaCriada listaCriada;
    private String lista;
    
    private Map<String, Turma> mapturmas;
    private Map<Integer, Turma> mapidturmas;
    private List<String> listaTurmas;
    private String turma;
    
    private Map<String, Disciplina> mapDisciplinas;
    private Map<Integer, Disciplina> mapidDisciplinas;
    private List<String> listaDisciplinas;
    private String disciplina;
    
    private List<Avaliacao> listaAvaliacoes;
    private Avaliacao avaliacao;
    
    
    
    
    @PostConstruct
    public void init(){
        resultado = Resultado.getResultado();
        fachada = new Fachada();
        avaliacao = new Avaliacao();
        listaAvaliacoes = new ArrayList<Avaliacao>();
        buscarListas();
        buscarTurmas();
        buscarDisciplinas();
        atualizar();
    }

    private void buscarListas(){
        listasCriadas = new ArrayList<String>();
        maplistas = new HashMap<String, ListaCriada>();
        mapidlistas = new HashMap<Integer, ListaCriada>();
        resultado.zerar();
        resultado = fachada.consultar(new ListaCriada());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                ListaCriada listaCriada = (ListaCriada)e;
                listasCriadas.add(listaCriada.getNome());
                maplistas.put(listaCriada.getNome(), listaCriada);
                mapidlistas.put(listaCriada.getId(), listaCriada);
            }
        }
    }
    private void buscarTurmas(){
        listaTurmas = new ArrayList<String>();
        mapturmas = new HashMap<String, Turma>();
        mapidturmas = new HashMap<Integer, Turma>();
        resultado.zerar();
        resultado = fachada.consultar(new Turma());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Turma turma = (Turma)e;
                listaTurmas.add(turma.getDescricao());
                mapturmas.put(turma.getDescricao(), turma);
                mapidturmas.put(turma.getId(), turma);
            }
        }
    }
    private void buscarDisciplinas(){
        listaDisciplinas = new ArrayList<String>();
        mapDisciplinas = new HashMap<String, Disciplina>();
        mapidDisciplinas = new HashMap<Integer, Disciplina>();
        resultado.zerar();
        resultado = fachada.consultar(new Disciplina());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Disciplina disciplina = (Disciplina)e;
                listaDisciplinas.add(disciplina.getNome());
                mapDisciplinas.put(disciplina.getNome(), disciplina);
                mapidDisciplinas.put(disciplina.getId(), disciplina);
            }
        }
    }
    
    public void salvar(){
        avaliacao.setListaCriada(maplistas.get(lista));
        avaliacao.setTurma(mapturmas.get(turma));
        avaliacao.setDisciplina(mapDisciplinas.get(disciplina));
        avaliacao.setProfessor(getProfessorSession());
        if(avaliacao.getPrazo().before(new Date(System.currentTimeMillis())))
            avaliacao.setAtivo(false);
        else
            avaliacao.setAtivo(true);
        resultado = fachada.salvar(avaliacao);
        if(resultado.getMsgs().isEmpty()){
            atualizar();
            avaliacao = new Avaliacao();
        }else{
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro ao salvar", resultado.getMsgs());
        }
    }
    
    public void remover(int id){
        resultado = fachada.excluir(new Avaliacao(id));
        if(resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_INFO, "Avaliação removida", "");
        }else{
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro ao remover a avaliação", resultado.getMsgs());
        }
        atualizar();
    }
    
    private void atualizar(){
        resultado = fachada.consultar(new Avaliacao());
        listaAvaliacoes = new ArrayList<Avaliacao>();
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Avaliacao a = (Avaliacao)e;
                a.setTurma(mapidturmas.get(a.getTurma().getId()));
                a.setListaCriada(mapidlistas.get(a.getListaCriada().getId()));
                a.setDisciplina(mapidDisciplinas.get(a.getDisciplina().getId()));
                listaAvaliacoes.add(a);
            }
        }
    }

    public ListaCriada getListaCriada() {
        return listaCriada;
    }

    public void setListaCriada(ListaCriada listaCriada) {
        this.listaCriada = listaCriada;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<Avaliacao> getListaAvaliacoes() {
        return listaAvaliacoes;
    }

    public void setListaAvaliacoes(List<Avaliacao> listaAvaliacoes) {
        this.listaAvaliacoes = listaAvaliacoes;
    }

    public Map<String, Turma> getMapturmas() {
        return mapturmas;
    }

    public void setMapturmas(Map<String, Turma> mapturmas) {
        this.mapturmas = mapturmas;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public Map<String, ListaCriada> getMaplistas() {
        return maplistas;
    }

    public void setMaplistas(Map<String, ListaCriada> maplistas) {
        this.maplistas = maplistas;
    }

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public List<String> getListasCriadas() {
        return listasCriadas;
    }

    public void setListasCriadas(List<String> listasCriadas) {
        this.listasCriadas = listasCriadas;
    }

    public List<String> getListaTurmas() {
        return listaTurmas;
    }

    public void setListaTurmas(List<String> listaTurmas) {
        this.listaTurmas = listaTurmas;
    }

    public Map<String, Disciplina> getMapDisciplinas() {
        return mapDisciplinas;
    }

    public void setMapDisciplinas(Map<String, Disciplina> mapDisciplinas) {
        this.mapDisciplinas = mapDisciplinas;
    }

    public List<String> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public void setListaDisciplinas(List<String> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Map<Integer, ListaCriada> getMapidlistas() {
        return mapidlistas;
    }

    public void setMapidlistas(Map<Integer, ListaCriada> mapidlistas) {
        this.mapidlistas = mapidlistas;
    }

    public Map<Integer, Turma> getMapidturmas() {
        return mapidturmas;
    }

    public void setMapidturmas(Map<Integer, Turma> mapidturmas) {
        this.mapidturmas = mapidturmas;
    }

    public Map<Integer, Disciplina> getMapidDisciplinas() {
        return mapidDisciplinas;
    }

    public void setMapidDisciplinas(Map<Integer, Disciplina> mapidDisciplinas) {
        this.mapidDisciplinas = mapidDisciplinas;
    }
    
}
