package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.Alternativa;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.ListaRealizada;
import exesis.model.Nivel;
import exesis.model.Tag;
import exesis.model.TipoLista;
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

@ManagedBean(name = "avaliacaoBean")
@ViewScoped
public class AvaliacaoBean extends AbstractBean{
    private ListaRealizada realizada;
    private ListaCriada listaCriada;
    private List<String> keywords;
    private List<String> palavrasChaves;
    private String nome;
    private TipoLista tipo;
    private List<TipoLista> listatipo;
    private Map<Integer,TipoLista> mapatipo;
    private String turma;
    private String lista;
    private List<Exercicio> exercicios;
    private List<Alternativa> alternativas;
    
    private List<Nivel> listaNivel;
    private int quantidade;
    private int idNivel;
    private int disponivel;
    
    @PostConstruct
    public void init(){
        resultado = Resultado.getResultado();
        fachada = new Fachada();
        quantidade = 10;
        disponivel = 10;
        tipo = new TipoLista();

        
        resultado = fachada.consultar(new Nivel());
        listaNivel = new ArrayList<Nivel>();
        if(!resultado.getEntidades().isEmpty()){
            for(int i = 0; i < resultado.getEntidades().size(); i++)
                listaNivel.add((Nivel) resultado.getEntidades().get(i));
        }
        
        palavrasChaves =new ArrayList<String>();
        keywords = new ArrayList<String>();
        resultado.zerar();
        resultado = fachada.consultar(new Tag());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                keywords.add(((Tag)e).getNome());
            }
        }
        resultado.zerar();
        listatipo = new ArrayList<TipoLista>();
        mapatipo = new HashMap<Integer, TipoLista>();
        resultado = fachada.consultar(new TipoLista());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                listatipo.add(((TipoLista)e));
                mapatipo.put(((TipoLista)e).getId(), (TipoLista)e);
            }
        }
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(session.getAttribute("listaCriada") != null){
            listaCriada = (ListaCriada) session.getAttribute("listaCriada");
            nome = listaCriada.getNome();
            tipo = listaCriada.getTipo();
        }
    }
    
    
    public void gerarLista(){
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null , "listaCriada");
        getListaExercicios();
    }

     public void getListaExercicios(){
        fachada = new Fachada();
        listaCriada = new ListaCriada();
        listaCriada.setTags(new ArrayList<Tag>());
        listaCriada.setQuantidade(quantidade);
        if(!palavrasChaves.isEmpty()){
            for(String k: palavrasChaves){
                listaCriada.getTags().add(new Tag(k));
            }
        resultado = Resultado.getResultado();   
        resultado.zerar();
        resultado = fachada.consultar(listaCriada);
        if(!resultado.getEntidades().isEmpty()){
            listaCriada = (ListaCriada) resultado.getEntidades().get(0);
            listaCriada.setNome(nome);
            listaCriada.setQuantidade(quantidade);
            listaCriada.setTipo(mapatipo.get(tipo.getId()));
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
    public void salvar(){
        resultado.zerar();
        resultado = fachada.salvar(listaCriada);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro ao salvar", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", "");
        }
    } 
     
    public String reinit(){
        return null;
    }
    
    
    public List<String> completeKeywors(String query) {
        List<String> allKeys = keywords;
        List<String> filteredKeys = new ArrayList<String>();
         
        for (int i = 0; i < allKeys.size(); i++) {
            String key = allKeys.get(i);
            if(key.toLowerCase().startsWith(query) && !filteredKeys.contains(key)) {
                filteredKeys.add(key);
            }
        }
        if(filteredKeys.isEmpty())  
            filteredKeys.add(query);
        
        return filteredKeys;
    }

    public void calcDisponivel(){
        disponivel = quantidade;
    }
    
    public ListaRealizada getRealizada() {
        return realizada;
    }

    public void setRealizada(ListaRealizada realizada) {
        this.realizada = realizada;
    }

    public ListaCriada getListaCriada() {
        return listaCriada;
    }

    public void setListaCriada(ListaCriada listaCriada) {
        this.listaCriada = listaCriada;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getPalavrasChaves() {
        return palavrasChaves;
    }

    public void setPalavrasChaves(List<String> palavrasChaves) {
        this.palavrasChaves = palavrasChaves;
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

    public TipoLista getTipo() {
        return tipo;
    }

    public void setTipo(TipoLista tipo) {
        this.tipo = tipo;
    }

    public List<TipoLista> getListatipo() {
        return listatipo;
    }

    public void setListatipo(List<TipoLista> listatipo) {
        this.listatipo = listatipo;
    }

    public Map<Integer, TipoLista> getMapatipo() {
        return mapatipo;
    }

    public void setMapatipo(Map<Integer, TipoLista> mapatipo) {
        this.mapatipo = mapatipo;
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

    public List<Nivel> getListaNivel() {
        return listaNivel;
    }

    public void setListaNivel(List<Nivel> listaNivel) {
        this.listaNivel = listaNivel;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public int getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(int disponivel) {
        this.disponivel = disponivel;
    }
    
    
    
}
