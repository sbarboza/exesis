package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.Alternativa;
import exesis.model.Exercicio;
import exesis.model.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.swing.JOptionPane;

@ManagedBean
@ViewScoped
public class ExercicioBean extends AbstractBean{
    private List<String> keywords;
    private List<String> keywordsSelected;
    private Exercicio exercicio;
    private boolean remover, isAlternativa;
    private int quantidade;
    
    @PostConstruct
    public void init(){
        exercicio = new Exercicio();
        remover = false;
        isAlternativa = false;
        keywords = new ArrayList<String>();
        keywordsSelected = new ArrayList<String>();
        exercicio.setAlternativas(new ArrayList<Alternativa>());
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

    public boolean isIsAlternativa() {
        
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
        exercicio.setTags(new ArrayList<Tag>());
        if(!keywordsSelected.isEmpty())
            for(String k: keywordsSelected){
                exercicio.getTags().add(new Tag(k));
                        JOptionPane.showMessageDialog(null, k);
            }
        
        resultado = fachada.salvar(exercicio);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro ao Salvar", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_WARN, "Salvo com sucesso", "");
        }
        reset();
    }
    
    private void reset(){
        exercicio = new Exercicio();
        keywordsSelected = new ArrayList<String>();
        exercicio.setAlternativas(new ArrayList<Alternativa>());
        exercicio.setTags(new ArrayList<Tag>());
    }
}
