package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.Alternativa;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.ListaRealizada;
import exesis.model.Tag;
import exesis.model.TipoLista;
import exesis.model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "avaliacaoBean")
@ViewScoped
public class AvaliacaoBean extends AbstractBean{
    private ListaRealizada realizada;
    private ListaCriada listaCriada;
    private List<String> keywords;
    private List<String> palavrasChaves;
    private String nome;
    private int quantidade;
    private String tipo;
    private String turma;
    private String lista;
    private Map<String, String> listas;
    private Map<String, String> turmas;
    private Avaliacao avaliacao;
    private List<Avaliacao> avaliacoes;
    
    private List<Exercicio> exercicios;
    private List<Alternativa> alternativas;
    
    
    @PostConstruct
    public void init(){
        quantidade = 1;
        listas = new HashMap<String, String>();
        turmas = new HashMap<String, String>();
        
        listas.put("Avaliação Geometria", "Avaliação Geometria");
        listas.put("Avaliação Aritmética", "Avaliação Aritmética");
        listas.put("Lista de Treino - Equação", "Lista de Treino - Equação");
        listas.put("Prova Final", "Prova Final");
        
        turmas.put("1A - Manhã", "1A - Manhã");
        turmas.put("2C - Manhã", "2C - Manhã");
        turmas.put("4B - Tarde", "4B - Tarde");
        turmas.put("1E - Noite", "1E - Noite");
        palavrasChaves =new ArrayList<String>();
        keywords = new ArrayList<String>();
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(session.getAttribute("listaCriada") != null){
            listaCriada = (ListaCriada) session.getAttribute("listaCriada");
            exercicios = listaCriada.getExercicios();
            nome = listaCriada.getNome();
            tipo = listaCriada.getTipo().getDescricao();
        }
    }
    
    
    public void gerarLista(){
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null , "listaCriada");
        getListaExercicios();
    }

     public void getListaExercicios(){
        IFachada fachada = new Fachada();
        Exercicio exercicio = new Exercicio();
        exercicio.setTags(new ArrayList<Tag>());
        if(!palavrasChaves.isEmpty()){
            for(String k: palavrasChaves){
                exercicio.getTags().add(new Tag(k));
            }
        resultado = Resultado.getResultado();   
        resultado.zerar();
        resultado = fachada.consultar(exercicio);
        if(!resultado.getEntidades().isEmpty()){
            listaCriada = (ListaCriada) resultado.getEntidades().get(0);
            listaCriada.setNome(nome);
            listaCriada.setQuantidade(quantidade);
            listaCriada.setTipo(new TipoLista(tipo, 0));
            listaCriada.setTags(new ArrayList<Tag>());
            if(!palavrasChaves.isEmpty()){
                for(String k: palavrasChaves){
                    listaCriada.getTags().add(new Tag(k));
                }
            }
            HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("listaCriada", listaCriada);
        }
        }
        
    }
    public String reinit(){
        avaliacao = new Avaliacao();
        return null;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public Map<String, String> getListas() {
        return listas;
    }

    public void setListas(Map<String, String> listas) {
        this.listas = listas;
    }

    public Map<String, String> getTurmas() {
        return turmas;
    }

    public void setTurmas(Map<String, String> turmas) {
        this.turmas = turmas;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    /**
     * @return the exercicios
     */
    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    /**
     * @param exercicios the exercicios to set
     */
    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    /**
     * @return the alternativas
     */
    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    /**
     * @param alternativas the alternativas to set
     */
    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
        
    }    
    
    public List<Alternativa> getAlternativas(Exercicio exercicio) {
        for( Exercicio e: this.avaliacao.getExercicios()){
            if(!e.getEnunciado().equals(exercicio.getEnunciado()))
               continue;
            return e.getAlternativas();
        }
        return null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ListaRealizada getRealizada() {
        return realizada;
    }

    public void setRealizada(ListaRealizada realizada) {
        this.realizada = realizada;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywordsSelected() {
        return palavrasChaves;
    }

    public void setKeywordsSelected(List<String> keywordsSelected) {
        this.palavrasChaves = keywordsSelected;
    }
    
    public List<String> completeKeywors(String query) {
        List<String> allKeys = keywords;
        List<String> filteredKeys = new ArrayList<String>();
         
        for (int i = 0; i < allKeys.size(); i++) {
            String key = allKeys.get(i);
            if(key.toLowerCase().startsWith(query)) {
                filteredKeys.add(key);
            }
        }
        if(filteredKeys.isEmpty())
            filteredKeys.add(query);
        
        return filteredKeys;
    }

    public ListaCriada getListaCriada() {
        return listaCriada;
    }

    public void setListaCriada(ListaCriada listaCriada) {
        this.listaCriada = listaCriada;
    }

    public List<String> getPalavrasChaves() {
        return palavrasChaves;
    }

    public void setPalavrasChaves(List<String> palavrasChaves) {
        this.palavrasChaves = palavrasChaves;
    }
    
     
}
