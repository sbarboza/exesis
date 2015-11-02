/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.core.strategy;


import exesis.core.aplicacao.Resultado;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import java.util.Calendar;

/**
 *
 * @author  
 */
public class ValidarDtNascimento implements IStrategy {

    private int verificarQtdeDias(int mes){
        int qtdeDias;
        switch(mes){
            case 1:
                qtdeDias = 31;
                break;
            case 2:
                qtdeDias = 28;
                break;
            case 3:
                qtdeDias = 31;
                break;
            case 4:
                qtdeDias = 30;
                break;
            case 5:
                qtdeDias = 31;
                break;
            case 6:
                qtdeDias = 30;
                break;
            case 7:
                qtdeDias = 31;
                break;
            case 8:
                qtdeDias = 31;
                break;
            case 9:
                qtdeDias = 30;
                break;
            case 10:
                qtdeDias = 31;
                break;
            case 11:
                qtdeDias = 30;
                break;
            case 12:
                qtdeDias = 31;
                break;
            default:
                qtdeDias = 0;
        }
        return qtdeDias;
    }
    
    @Override
    public Resultado processar(EntidadeDominio entidade) {
        String data;
        String strSplit[];
        String msg = "Data de nascimento inválida!";
        Resultado resultado = Resultado.getResultado();
        int dia, mes, ano, sysYear;

        sysYear = (Integer) Calendar.getInstance().get(Calendar.YEAR);
        if(entidade instanceof Aluno)
             data = ((Aluno) entidade).getDataNascimento();
        else{
                resultado.setMsg(msg);
                return resultado;
        }
        
        // comprimento
        if(data.length() != 10){
            resultado.setMsg(msg);
            return resultado;
        }
    
        strSplit = data.split("/");
        dia = Integer.parseInt(strSplit[0]);
        mes = Integer.parseInt(strSplit[1]);
        ano = Integer.parseInt(strSplit[2]);
        
        // ano
        if((ano < 1900) || (ano > sysYear)){
            resultado.setMsg(msg);
            return resultado;
        }
        // mês
        if((mes > 12) || (mes < 1)){
            resultado.setMsg(msg);
            return resultado;
        }
        // dia
        if((dia < 1) || (dia > verificarQtdeDias(mes))){
            resultado.setMsg(msg);
            return resultado;
        }
        return resultado;
    }
}
