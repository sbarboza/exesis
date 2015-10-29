
package exesis.teste.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.TipoLista;
import java.util.ArrayList;

/**
 *
 * @author EXESIS
 */
public class TesteListaCriada {
    public static IFachada fachada = new Fachada();
    public static Resultado resultado = Resultado.getResultado();
    public static void main(String[] args) {
        testeCadastrar();
    }
    
    public static void testeCadastrar() {
        ListaCriada listaCriada = new ListaCriada();
        listaCriada.setNome("Teste1");
        listaCriada.setTipo(new TipoLista(1));
        listaCriada.setExercicios(new ArrayList<Exercicio>());
        listaCriada.getExercicios().add(new Exercicio(1));
        listaCriada.getExercicios().add(new Exercicio(2));
        resultado = fachada.salvar(listaCriada);
        testar(resultado);
    }
    
    public static void testar(Resultado resultado) {
        if(!resultado.getMsgs().isEmpty()){
            System.out.println(resultado.getMsgs().toString());
        }
    }
}
