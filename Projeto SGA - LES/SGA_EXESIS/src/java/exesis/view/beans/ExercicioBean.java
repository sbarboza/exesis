/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.view.beans;

import com.sun.org.apache.xpath.internal.operations.Mult;
import exesis.model.Alternativa;
import exesis.model.Exercicio;
import exesis.model.MultiplaEscolha;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author SAMUEL
 */
@ManagedBean
@ViewScoped
public class ExercicioBean extends AbstractBean{
    private List<String> keywords;
    private List<String> keywordsSelected;
    private Exercicio exercicio;
    private int quantidade;
    private MultiplaEscolha multiplaescolha;
//    private List<MultiplaEscolha> multiplaEscolha;
    
    @PostConstruct
    public void init(){
        keywords = new ArrayList<String>();
        keywords.add("equacao");
        keywords.add("numeros complexos");
        keywords.add("geometria");
        keywords.add("triangulos");
        keywords.add("equilateros");
        keywords.add("plano cartesiano");
        
        keywordsSelected = new ArrayList<String>();
        multiplaescolha = new MultiplaEscolha();
        multiplaescolha.setAlternativas(new ArrayList<Alternativa>());
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
    
    

//    public List<MultiplaEscolha> getMultiplaEscolha() {
//        return multiplaEscolha;
//    }
//
//    public void setMultiplaEscolha(List<MultiplaEscolha> multiplaEscolha) {
//        this.multiplaEscolha = multiplaEscolha;
//    }

    public MultiplaEscolha getMultiplaescolha() {
        return multiplaescolha;
    }

    public void setMultiplaescolha(MultiplaEscolha multiplaescolha) {
        this.multiplaescolha = multiplaescolha;
    }


}
