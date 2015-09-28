package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.Administrador;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import exesis.model.Usuario;

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
            if(senha != null && senha.length() > 0)
                if(senha.contains(professor.getNome().trim()) || senha.trim().contains(professor.getSobrenome()))
                    resultado.getMsgs().add("A senha não pode conter o nome ou parte do nome!");
        }
        if(entidade instanceof Aluno){
            Aluno aluno = (Aluno) entidade;
            if(aluno.getUsuario() != null)
                senha = aluno.getUsuario().getSenha();
            else
                resultado.getMsgs().add("Usuário não informado!");
            if(senha.length() > 0)
                if(senha.contains(aluno.getNome().trim()) || senha.trim().contains(aluno.getSobrenome()))
                    resultado.getMsgs().add("A senha não pode conter o nome ou parte do nome!");
        }
        if(entidade instanceof Administrador){
            Administrador administrador = (Administrador) entidade;
            if(administrador.getUsuario() != null)
                senha = administrador.getUsuario().getSenha();
            else
                resultado.getMsgs().add("Usuário não informado!");
            if(senha.length() > 0)
                if(senha.contains(administrador.getNome().trim()) || senha.trim().contains(administrador.getSobrenome()))
                    resultado.getMsgs().add("A senha não pode conter o nome ou parte do nome!");
        }
        
        if(entidade instanceof ResponsavelAluno){
            ResponsavelAluno responsavel = (ResponsavelAluno) entidade;
            if(responsavel.getUsuario() != null)
                senha = responsavel.getUsuario().getSenha();
            else
                resultado.getMsgs().add("Usuário não informado!");
            if(senha.length() > 0)
                if(senha.contains(responsavel.getNome().trim()) || senha.trim().contains(responsavel.getSobrenome()))
                    resultado.getMsgs().add("A senha não pode conter o nome ou parte do nome!");
        }
        
        if(senha == null)
            resultado.getMsgs().add("Senha");
        else if(senha.contains(" "))
            resultado.setMsg("A senha não deve ter espaços em branco");
        else if(senha.length() < 5)
            resultado.getMsgs().add("A senha deve ter pelo menos 5 caracteres!");
        return resultado;
    }
}
