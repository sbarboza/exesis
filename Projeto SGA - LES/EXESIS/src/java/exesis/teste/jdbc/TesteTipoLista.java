package exesis.teste.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.TipoLista;

/**
 *
 * @author EXESIS
 */
public class TesteTipoLista {
    public static Fachada fachada = new Fachada();
    public static Resultado resultado = Resultado.getResultado();
    
    public static void main(String[] args) {
//        testeCadastrarLista();
        testeAlterar();
    }
    
    public static void testeCadastrarLista() {
        fachada.salvar(new TipoLista("Prova semestral", 5.0f));
        fachada.salvar(new TipoLista("Lista de treino", 1.0f));
        fachada.salvar(new TipoLista("Lista de conteudo", 2.0f));
        fachada.salvar(new TipoLista("Prova bimestral", 2.0f));
    }
    
    public static void testeAlterar() {
        fachada.alterar(new TipoLista(3, "dfasdfasdf", 1));
    }
}
