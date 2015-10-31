/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.teste.popularBanco;

import exesis.teste.jdbc.TesteExercicio;

public class PopularBancoExercicios {

    
    public static void main(String[] args) {
        for(int i = 0; i < 20; i++){
            TesteExercicio.cadastrarExercicios("Exercicio Teste"+i);
        }
    }
    
}
