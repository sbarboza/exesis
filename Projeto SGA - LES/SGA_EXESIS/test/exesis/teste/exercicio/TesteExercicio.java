package exesis.teste.exercicio;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.Alternativa;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TesteExercicio {
    public static void main(String[] args) {
        cadastrarExercicioAlternativas();
    }
    
    public static void cadastrarExercicio(){
        Exercicio exercicio = new Exercicio();
        exercicio.setEnunciado("novo testando o enunciado?");
        exercicio.setTags(new ArrayList<Tag>());
        exercicio.getTags().add(new Tag("Exercício"));
        exercicio.getTags().add(new Tag("teste"));
        exercicio.getTags().add(new Tag("testando"));
        Fachada f = new Fachada();
        f.salvar(exercicio);
    }
    
    public static void cadastrarExercicioAlternativas(){
        Resultado resultado = Resultado.getResultado();
        Exercicio exercicio = new Exercicio();
        exercicio.setEnunciado("aaaaa testando o enunciado?");
        exercicio.setTags(new ArrayList<Tag>());
        exercicio.getTags().add(new Tag("Exercício"));
        exercicio.getTags().add(new Tag("teste"));
        exercicio.getTags().add(new Tag("testei"));
        exercicio.setAlternativas(new ArrayList<Alternativa>());
        exercicio.getAlternativas().add(new Alternativa(true,"alternativaA"));
        exercicio.getAlternativas().add(new Alternativa(false,"alternativaB"));
        exercicio.getAlternativas().add(new Alternativa(false,"alternativaC"));
        exercicio.getAlternativas().add(new Alternativa(true,"alternativaD"));
        Fachada f = new Fachada();
        resultado =  f.consultar(exercicio);
        
        List<Tag> tags = new ArrayList<Tag>();
        for(Tag t: exercicio.getTags()){
            for(EntidadeDominio e: resultado.getEntidades()){
                Tag tag = (Tag) e;
                JOptionPane.showMessageDialog(null, tag.getNome() + " = "+ t.getNome());
                if(tag.getNome().trim().equals(t.getNome())){
                    t.setId(tag.getId());                    
                }
                tags.add(t);
            }
        }
        for(Tag tag: exercicio.getTags()){
        JOptionPane.showMessageDialog(null, tag.getNome());
        }
        if(!resultado.getEntidades().isEmpty())
            
            exercicio.setTags(tags);
        f.salvar(exercicio);
    }
}
