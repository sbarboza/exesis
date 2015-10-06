package exesis.teste.exercicio;

import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.Tag;
import java.util.ArrayList;

public class TesteListaCriadaExercicios {
    public static void main(String[] args) {
        criarListas();
    }
    
    public static void criarListas(){
        ListaCriada lista = new ListaCriada();
        lista.setNome("Avaliação de equação");
        lista.setQuantidade(15);
        lista.setCategoria("Prova Bimestral");
        lista.setTags(new ArrayList<Tag>());
        lista.getTags().add(new Tag("teste"));
        lista.getTags().add(new Tag("testando"));
        lista.getTags().add(new Tag("exercicio"));
        
        lista.setExercicios(new ArrayList<Exercicio>());
        
        
    }
}
