package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.Alternativa;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.Nivel;
import exesis.model.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ExercicioBean extends AbstractBean{
    private List<String> keywords;
    private List<String> keywordsSelected;
    private List<Nivel> listaNivel;
    private Nivel nivel;
    private Exercicio exercicio;
    private boolean remover, isAlternativa; 
    private int quantidade;
    
    @PostConstruct
    public void init(){
        resultado = Resultado.getResultado();
        fachada = new Fachada(); 
        exercicio = new Exercicio();
        nivel = new Nivel();    
        remover = false;
        isAlternativa = false;
        keywords = new ArrayList<String>();
        resultado.zerar();
        resultado = fachada.consultar(new Tag());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                keywords.add(((Tag)e).getNome());
            }
        }
        keywordsSelected = new ArrayList<String>();
        exercicio.setAlternativas(new ArrayList<Alternativa>());
        exercicio.getAlternativas().add(new Alternativa());
        resultado = fachada.consultar(new Nivel());
        listaNivel = new ArrayList<Nivel>();
        if(!resultado.getEntidades().isEmpty()){
            for(int i = 0; i < resultado.getEntidades().size(); i++)
                listaNivel.add((Nivel) resultado.getEntidades().get(i));
        }
        
        desabilitarRemover();
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    public List<String> completeKeywors(String query) {
        List<String> allKeys = keywords;
        List<String> filteredKeys = new ArrayList<String>();
        filteredKeys.add(query);
        for (int i = 0; i < allKeys.size(); i++) {
            String key = allKeys.get(i);
            if(key.toLowerCase().startsWith(query) && filteredKeys.size() <= 10 && !filteredKeys.contains(key)) {
                filteredKeys.add(key);
            }
        }
        

        return filteredKeys;
    }

    public void novaAlternativa(){
        if(exercicio != null)
            if(exercicio.getAlternativas() != null)
                exercicio.getAlternativas().add(new Alternativa());
        desabilitarRemover();
    }
    
    public void removerAlternativa(Alternativa alternativa){
        exercicio.getAlternativas().remove(alternativa);
        desabilitarRemover();
    }
    public List<String> getKeywordsSelected() {
        return keywordsSelected;
    }

    public void setKeywordsSelected(List<String> keywordsSelected) {
        this.keywordsSelected = keywordsSelected;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public boolean isRemover() {
        return remover;
    }

    public void setRemover(boolean remover) {
        this.remover = remover;
    }

    private void desabilitarRemover(){
        if(exercicio.getAlternativas().size() > 1)
            remover = false;
        else 
            remover = true;
    }

    public boolean getIsAlternativa() {
        return isAlternativa;
    }

    public void setIsAlternativa(boolean isAlternativa) {
        this.isAlternativa = isAlternativa;
    }
    
    public void mudarTipo(){
        if(isAlternativa == true)
            exercicio.setTipo(exercicio.MULTIPLAESCOLHA);
        else
            exercicio.setTipo(exercicio.DISSERTATIVA);
    }
    
    public void salvar(){
        fachada = new Fachada();
        mudarTipo();
        for(Nivel n: listaNivel){
            if(n.getId() == nivel.getId()){
                exercicio.setNivel(n);
            }
        }
        exercicio.setTags(new ArrayList<Tag>());
        if(keywordsSelected != null && !keywordsSelected.isEmpty())
            for(String k: keywordsSelected){
                exercicio.getTags().add(new Tag(k));
            }
        
        resultado = fachada.salvar(exercicio);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro ao Salvar", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", "");
        }
        reset();
    }
    
    private void reset(){
        exercicio = new Exercicio();
        keywordsSelected = new ArrayList<String>();
        exercicio.setAlternativas(new ArrayList<Alternativa>());
        exercicio.setTags(new ArrayList<Tag>());
    }

    public List<Nivel> getListaNivel() {
        return listaNivel;
    }

    public void setListaNivel(List<Nivel> listaNivel) {
        this.listaNivel = listaNivel;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
    
    
}
