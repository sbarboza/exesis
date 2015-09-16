/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.core.strategy;
import exesis.core.aplicacao.Resultado;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;

/**
 *
 * @author Vinicius Oliveira
 */
public class ValidarNullAluno implements IStrategy {

    @Override
    public Resultado processar(EntidadeDominio entidade) {
        Aluno aluno = (Aluno) entidade;
        Resultado resultado = Resultado.getResultado();
        
        if(aluno.getNome().trim().isEmpty() || aluno.getNome() == null)
            resultado.setMsg("Favor preencher o campo NOME!");
        if(aluno.getSobrenome().trim().isEmpty() || aluno.getSobrenome() == null)
            resultado.setMsg("Favor preencher o campo SOBRENOME!");
        if(aluno.getDataNascimento().toString().isEmpty() || aluno.getDataNascimento().toString() == null)
            resultado.setMsg("Favor preencher o campo DATA DE NASCIMENTO!");
        if(aluno.getSexo().isEmpty() || aluno.getSexo() == null)
            resultado.setMsg("Favor preencher o campo SEXO!");
        if(aluno.getTelefone().isEmpty() || aluno.getTelefone() == null)
            resultado.setMsg("Favor peencher o campo TELEFONE!");
        if(aluno.getMatricula().isEmpty() || aluno.getMatricula() == null)
            resultado.setMsg("Favor preencher o campo MATRÍCULA!");
        ValidarNullUsuario valNullUser = new ValidarNullUsuario();
        //resultado.setMsg(valNullUser.processar(aluno.getUsuario()).toString()); // isso nao existe, pq o que ele devolve é um resultado
        resultado = valNullUser.processar(aluno.getUsuario());
        return resultado;
    }
    
}
