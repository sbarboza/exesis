
package exesis.teste.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.Disciplina;
import exesis.model.EntidadeDominio;

public class TesteDisciplina {

    public static void main(String[] args) {
        consultar();
    }
    public static void consultar() {
        Fachada fachada = new Fachada();
        Resultado resultado;
        resultado = fachada.consultar(new Disciplina());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                System.out.println(((Disciplina)e).getNome());
            }
        }
    }
    
}
