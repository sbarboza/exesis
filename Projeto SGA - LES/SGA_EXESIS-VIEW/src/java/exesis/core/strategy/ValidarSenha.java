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

/**
 *
 * @author SAMUEL
 */
public class ValidarSenha implements IStrategy{

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        String senha = "";
        if(entidade instanceof Usuario){
            Usuario usuario = (Usuario) entidade;
            senha = usuario.getSenha();
        }
        if(entidade instanceof Professor){
            Professor professor = (Professor) entidade;
            if(professor.getUsuario() != null)
                senha = professor.getUsuario().getSenha();
            else
                resultado.getMsgs().add("Usuário não informado!");
            if(senha.length() > 0)
                if(senha.contains(professor.getNome().trim()) || senha.trim().contains(professor.getSobrenome()))
                    resultado.getMsgs().add("A senha não pode conter o nome ou parte do nome!");
        }
        if(senha.length() == 0)
            resultado.getMsgs().add("Senha");
        else if(senha.contains(" "))
            resultado.setMsg("A senha não deve ter espaços em branco");
        else if(senha.length() < 5)
            resultado.getMsgs().add("A senha deve ter pelo menos 5 caracteres!");
        return resultado;
    }
}
