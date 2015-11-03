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
    private List<Nivel> listaNivelSelecionado;
    private Map<Integer, Nivel> mapNivel;
    private int quantidade;
    private Nivel nivelSelecionado;
    private int disponivel;
    private int peso;
    
    @PostConstruct
    public void init(){
        resultado = Resultado.getResultado();
        fachada = new Fachada();
        quantidade = 0;
        peso = 1;
        tipo = new TipoLista();
        nivelSelecionado = new Nivel();
        mapNivel = new HashMap<Integer, Nivel>();
        listaNivelSelecionado = new ArrayList<Nivel>();
        resultado = fachada.consultar(new Nivel());
        listaNivel = new ArrayList<Nivel>();
        if(!resultado.getEntidades().isEmpty()){
            for(int i = 0; i < resultado.getEntidades().size(); i++){
                Nivel nivel = (Nivel) resultado.getEntidades().get(i);
                listaNivel.add(nivel);
                mapNivel.put(nivel.getId(), nivel);
                System.out.println(mapNivel.size());
            }
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
        
        listaCriada = new ListaCriada();
        listaCriada.setListaNivel(new ArrayList<Nivel>());
        HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(session.getAttribute("listaCriada") != null){
            listaCriada = (ListaCriada) session.getAttribute("listaCriada");
            nome = listaCriada.getNome();
            tipo = listaCriada.getTipo();
        }
        listaCriada.setListaNivel(new ArrayList<Nivel>());
    }
    
    
    public void gerarLista(){
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null , "listaCriada");
        getListaExercicios();
    }

     public void getListaExercicios(){
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
    
    public void adicionar(){
        Nivel nivel = mapNivel.get(nivelSelecionado.getId());
        if(listaCriada.getListaNivel().contains(nivel)){
            Mensagem(FacesMessage.SEVERITY_WARN, "Já foi adicionado esse nivel", "");
        }else{
            listaCriada.getListaNivel().add(nivel);
            nivel.setQuantidade(peso);
            atualizarQtde();
        }
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

    public void apagar(Nivel nivel){
        atualizarQtde();
        listaCriada.getListaNivel().remove(nivel);
    }
    
    private void atualizarQtde(){
        quantidade = 0;
        for(Nivel n: listaCriada.getListaNivel()){
            quantidade += n.getQuantidade();
        }
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

    public int getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(int disponivel) {
        this.disponivel = disponivel;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public List<Nivel> getListaNivelSelecionado() {
        return listaNivelSelecionado;
    }

    public void setListaNivelSelecionado(List<Nivel> listaNivelSelecionado) {
        this.listaNivelSelecionado = listaNivelSelecionado;
    }

    public Map<Integer, Nivel> getMapNivel() {
        return mapNivel;
    }

    public void setMapNivel(Map<Integer, Nivel> mapNivel) {
        this.mapNivel = mapNivel;
    }

    public Nivel getNivelSelecionado() {
        return nivelSelecionado;
    }

    public void setNivelSelecionado(Nivel nivelSelecionado) {
        this.nivelSelecionado = nivelSelecionado;
    }
    
     
    
}
