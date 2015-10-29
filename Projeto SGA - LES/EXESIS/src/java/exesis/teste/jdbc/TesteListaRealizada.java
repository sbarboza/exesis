package exesis.teste.jdbc;

import exesis.model.Alternativa;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.ListaRealizada;
import java.util.ArrayList;
import java.util.Date;

public class TesteListaRealizada {

    public static void main(String[] args) {
        cadastrarRealizada();
    }
    public static void cadastrarRealizada(){
        ListaRealizada realizada = new ListaRealizada();
        ListaCriada criada = new ListaCriada();
        criada.setExercicios(new ArrayList<Exercicio>());
        Exercicio exercicio;
        for(int i = 0; i < 10; i++){
            exercicio = new Exercicio();
            exercicio.setEnunciado("LALALALAA"+i+1);
            exercicio.setDtCadastro(new Date());
            exercicio.setPeso(1);
            exercicio.setTipo(exercicio.MULTIPLAESCOLHA);
            exercicio.setAlternativas(new ArrayList<Alternativa>());
            for(int j = 0; j < 5; j++){
                exercicio.getAlternativas().add(new Alternativa(true, "teste"+j));
            }
            criada.getExercicios().add(exercicio);
        }
    }
}
