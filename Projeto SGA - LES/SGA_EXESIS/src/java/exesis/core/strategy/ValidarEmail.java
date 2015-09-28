package exesis.core.strategy;

import exesis.core.aplicacao.Resultado;
import exesis.model.Administrador;
import exesis.model.Aluno;
import exesis.model.EntidadeDominio;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import exesis.model.Usuario;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        }else if(entidade instanceof Aluno){
            Aluno aluno = (Aluno) entidade;
            if(aluno.getUsuario() != null)
                usuario = aluno.getUsuario();
        }else if(entidade instanceof Aluno){
            ResponsavelAluno responsavel = (ResponsavelAluno) entidade;
            if(responsavel.getUsuario() != null)
                usuario = responsavel.getUsuario();
        }else if(entidade instanceof Administrador){
            Administrador administrador = (Administrador) entidade;
            if(administrador.getUsuario() != null)
                usuario = administrador.getUsuario();
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
