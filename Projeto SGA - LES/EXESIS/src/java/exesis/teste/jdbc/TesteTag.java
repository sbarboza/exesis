package exesis.teste.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.Tag;
import java.util.ArrayList;

public class TesteTag {
    
    public static void main(String[] args) {
        retornarTags();
    }
    
    public static void retornarTags() {
        Fachada fachada = new Fachada();
        Resultado resultado = Resultado.getResultado();
        Exercicio exercicio = new Exercicio();
        exercicio.setTags(new ArrayList<Tag>());
        exercicio.getTags().add(new Tag("comida"));
        resultado =  fachada.consultar(exercicio);
        ListaCriada lista = (ListaCriada) resultado.getEntidades().get(0);
        for(Exercicio e: lista.getExercicios()){
            TesteExercicio.testeExercicio(e);
        }
            
        
    }
}
