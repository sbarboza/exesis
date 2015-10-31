package exesis.teste.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.Alternativa;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.Tag;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TesteExercicio {
    
    public static IFachada fachada;    
    public static void main(String[] args) {
        cadastrarExercicios("TESTE ENUNCIADO");
        alterarExercicios();
        consultarExercicios();
    }
    
    public static void cadastrarExercicios(String exe) {
        fachada = new Fachada();
        Exercicio exercicio = new Exercicio();
        exercicio.setId(1);
        exercicio.setEnunciado(exe);
        exercicio.setTipo(exercicio.MULTIPLAESCOLHA);
        exercicio.setTags(new ArrayList<Tag>());
        exercicio.getTags().add(new Tag(exe+"1"));
        exercicio.getTags().add(new Tag("tag1"));
        exercicio.getTags().add(new Tag("todos"));
        exercicio.setAlternativas(new ArrayList<Alternativa>());
        exercicio.getAlternativas().add(new Alternativa(true, "Alternativa1"));
        exercicio.getAlternativas().add(new Alternativa(false, "Alternativa2"));
        exercicio.getAlternativas().add(new Alternativa(true, "Alternativa3"));
        fachada.salvar(exercicio);
    }
    
    public static void alterarExercicios() {
        fachada = new Fachada();
        Exercicio exercicio = new Exercicio();
        exercicio.setId(1);
        exercicio.setEnunciado("TESTE ENUNCIADO");
        exercicio.setTipo(exercicio.MULTIPLAESCOLHA);
        exercicio.setTags(new ArrayList<Tag>());
        exercicio.getTags().add(new Tag("TESTE"));
        exercicio.getTags().add(new Tag("TAG"));
        exercicio.getTags().add(new Tag("EXERCICIO"));
        exercicio.setAlternativas(new ArrayList<Alternativa>());
        exercicio.getAlternativas().add(new Alternativa(true, "fasdfiojasoidfjoasjdf"));
        exercicio.getAlternativas().add(new Alternativa(false, "AQUELES"));
        exercicio.getAlternativas().add(new Alternativa(true, "EXERCICIO"));
        
        fachada.alterar(exercicio);
    }
    
    public static void consultarExercicios() {
        fachada = new Fachada();
        Resultado resultado;
        Exercicio exercicio = new Exercicio();
        resultado = fachada.consultar(exercicio);
        for(EntidadeDominio e: resultado.getEntidades()){
            Exercicio t = (Exercicio) e;
            testeExercicio(t);
        }
    }
    
    
    
    public static void testeTag(Tag tag){
        System.out.println("Nome TAG: " + tag.getNome());
        System.out.println("ID TAG: " + tag.getId());
    }
    
    public static void testeExercicio(Exercicio exercicio){
        System.out.println("ENUNCIADO EXERCICIO: " + exercicio.getEnunciado());
        System.out.println("ID EXE: " + exercicio.getId());
        for(Tag t: exercicio.getTags())
            testeTag(t);
        if(exercicio.getTipo() == exercicio.MULTIPLAESCOLHA)
        for(Alternativa alt: exercicio.getAlternativas())
            testeAlternativas(alt);
    }
    
     public static void testeAlternativas(Alternativa alt){
        System.out.println("ID ALTERNATIVA: " + alt.getId());
        System.out.println("DESCRICAO ALTERNATIVA: " + alt.getDescricao());
        System.out.println("RESPOSTA ALTERNATIVA: " + alt.getResposta());
    }
}
