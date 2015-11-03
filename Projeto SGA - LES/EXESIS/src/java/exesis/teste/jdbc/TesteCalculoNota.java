package exesis.teste.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.strategy.CalcularNotaListaRealizada;

import exesis.model.Alternativa;
import exesis.model.Aluno;
import exesis.model.Avaliacao;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.ListaRealizada;
import exesis.model.Resposta;
import java.util.ArrayList;
import java.util.List;

public class TesteCalculoNota {
    private static Exercicio exercicio;
    private static List<Exercicio> listaExercicios;
    private static Alternativa alternativa;
    private static List<Alternativa> listaAlternativas;
    private static ListaRealizada listaRealizada;
    private static Resposta resposta;
    private static List<Resposta> listaRespostas;
    private static ListaCriada listaCriada;
    public static void main(String[] args) {
        Resultado resultado = Resultado.getResultado();
        // Criar as alternativas
        criarAlternativas(1);
        // Criar os exercícios e colocar as alternativas
        criarExercicios();
        // Criar as respostas
        criarAlternativas(1);
        criarRespostas();
        // Criar a lista realizada
        criarListaRealizada();
        
        CalcularNotaListaRealizada estrategia = new CalcularNotaListaRealizada();
        resultado = estrategia.processar(listaRealizada);
        listaRealizada = (ListaRealizada) resultado.getEntidades().get(0);
        mostrarNotasListaRealizada();
    }   
    
    public static void criarExercicios() {
        listaExercicios = new ArrayList<Exercicio>();
        for(int i = 0; i < 5; i++){
            exercicio = new Exercicio();
            exercicio.setId(i);
            exercicio.setEnunciado("TESTE"+i);
            exercicio.setPeso(2);
            exercicio.setTipo(Exercicio.MULTIPLAESCOLHA);
            exercicio.setAlternativas(listaAlternativas);
            listaExercicios.add(exercicio);
        }
        listaCriada = new ListaCriada();
        listaCriada.setExercicios(listaExercicios);
        
    }
    
    private static void criarAlternativas(int tipo){
        listaAlternativas = new ArrayList<Alternativa>();
        for(int i = 0; i < 4; i++){
            if(i%2 == tipo)
                alternativa = new Alternativa(Boolean.TRUE, "Alternativa"+i);
            else if(tipo == 1)
                alternativa = new Alternativa(Boolean.TRUE, "Alternativa"+i);    
            else
                alternativa = new Alternativa(Boolean.FALSE, "Alternativa"+i);    
            alternativa.setId(i);
            listaAlternativas.add(alternativa);
            TesteExercicio.testeAlternativas(alternativa);
        }
    }
    
    public static void criarRespostas(){
        listaRespostas = new ArrayList<Resposta>();
        for(Exercicio exer: listaExercicios){
            resposta = new Resposta();
            resposta.setExercicio(exer);
            resposta.setAlternativas(listaAlternativas);
            listaRespostas.add(resposta);
        }
    }
    
    
    public static void criarListaRealizada() {
        listaRealizada = new ListaRealizada();
        listaRealizada.setAluno(new Aluno("Aluno1"));
        listaRealizada.setListaRespostas(listaRespostas);
        listaRealizada.setAvaliacao(new Avaliacao());
        listaRealizada.getAvaliacao().setListaCriada(listaCriada);
    }
    
    public static void mostrarNotasListaRealizada() {
        for(Resposta resp: listaRealizada.getListaRespostas()){
            if(resp.getExercicio().getTipo() == Exercicio.MULTIPLAESCOLHA)
                System.out.println("Exercício de Múltipla escolha");
            else
                System.out.println("Exercício Dissertativo");
            
            System.out.println("Nota do exercício: " + resp.getNota());
        }
    }
    
}
