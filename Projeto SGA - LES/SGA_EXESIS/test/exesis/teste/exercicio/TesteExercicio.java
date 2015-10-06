package exesis.teste.exercicio;

import exesis.core.control.Fachada;
import exesis.model.Alternativa;
import exesis.model.Dissertativo;
import exesis.model.Exercicio;
import exesis.model.MultiplaEscolha;
import exesis.model.Tag;
import java.util.ArrayList;

public class TesteExercicio {
    public static void main(String[] args) {
        cadastrarExercicio();
    }
    
    public static void cadastrarExercicio(){
        Exercicio exercicio = new Dissertativo();
        exercicio.setEnunciado("novo testando o enunciado?");
        exercicio.setTags(new ArrayList<Tag>());
        exercicio.getTags().add(new Tag("Exercício"));
        exercicio.getTags().add(new Tag("teste"));
        exercicio.getTags().add(new Tag("testando"));
        Fachada f = new Fachada();
        f.salvar(exercicio);
    }
    
    public static void cadastrarExercicioAlternativas(){
        MultiplaEscolha exercicio = new MultiplaEscolha();
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
        f.salvar(exercicio);
    }
    
}
