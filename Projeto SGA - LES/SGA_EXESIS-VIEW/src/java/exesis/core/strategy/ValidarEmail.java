/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.Usuario;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author SAMUEL
 */
public class ValidarEmail implements IStrategy{

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        Usuario usuario = new Usuario();
        String email;
        if(entidade instanceof Professor){
            Professor professor = (Professor) entidade;
            if(professor.getUsuario() != null)
                usuario = professor.getUsuario();
        }else if(entidade instanceof Usuario){
            usuario = (Usuario) entidade;
        }
        email = usuario.getEmail();
        
        
        if ((email == null) || (email.trim().length() == 0))
            return resultado;
        String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            resultado.setMsg("E-mail inválido");
        return resultado;
    }   
    
    
      
}
