package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.Administrador;
import exesis.model.EntidadeDominio;
import exesis.model.ResponsavelAluno;

public class ValidarNullResponsavel implements IStrategy{
    
    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Resultado resultado = Resultado.getResultado();
        ResponsavelAluno responsavel = (ResponsavelAluno) entidade;
        if(responsavel.getNome() == null || responsavel.getNome().trim().length() == 0) 
                resultado.setMsg("Nome");
        if(responsavel.getSobrenome() == null || responsavel.getSobrenome().trim().length() == 0) 
                resultado.setMsg("Sobrenome");
        if(responsavel.getTelefone() == null || responsavel.getTelefone().trim().length() == 0)
                resultado.setMsg("Telefone");
        ValidarNullUsuario valNullCred = new ValidarNullUsuario();
                resultado = valNullCred.processar(responsavel.getUsuario());
        return resultado;
    }
}
