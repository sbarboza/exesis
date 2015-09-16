/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.core.dao.sql.impl.UsuarioDAO;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.Usuario;

/**
 *
 * @author SAMUEL
 */
public class ValidarNomeUsuario implements IStrategy{

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        Usuario usuario = new Usuario();
        Usuario usuario2 = null;
        if(entidade instanceof Professor)
            usuario2 = ((Professor)entidade).getUsuario();
        if(usuario2 != null)
            usuario.setLogin(usuario2.getLogin());
            IDAO dao = new UsuarioDAO();
            resultado.getEntidades().clear();
            resultado = dao.consultar(usuario);
            
            if(resultado.getEntidades().size() == 1)
                if(usuario2.getId() != resultado.getEntidades().get(0).getId())
                    resultado.setMsg("Nome de usuário já existe.");
        return resultado;
    }
    
}
