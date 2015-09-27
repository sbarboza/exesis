/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.Administrador;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import exesis.model.Usuario;

public class DefinirNivelAcesso implements IStrategy{

    
    public Resultado processar(EntidadeDominio entidade) {
        if(entidade instanceof Professor)
            ((Professor)entidade).getUsuario().setPerfilAcesso(Usuario.PROFESSOR);
        if(entidade instanceof Administrador)
            ((Administrador)entidade).getUsuario().setPerfilAcesso(Usuario.ADMINISTRADOR);
        if(entidade instanceof Aluno)
            ((Aluno)entidade).getUsuario().setPerfilAcesso(Usuario.ALUNO);
        if(entidade instanceof ResponsavelAluno)
            ((ResponsavelAluno)entidade).getUsuario().setPerfilAcesso(Usuario.RESPONSAVEL_ALUNO);
        return Resultado.getResultado();
    }
        
}
