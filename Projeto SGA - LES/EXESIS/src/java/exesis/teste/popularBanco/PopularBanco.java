package exesis.teste.popularBanco;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.core.control.IFachada;
import exesis.model.Administrador;
import exesis.model.Alternativa;
import exesis.model.Aluno;
import exesis.model.Disciplina;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.Nivel;
import exesis.model.Professor;
import exesis.model.ResponsavelAluno;
import exesis.model.Serie;
import exesis.model.Tag;
import exesis.model.Usuario;
import static exesis.teste.jdbc.TesteExercicio.fachada;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopularBanco {
    public static void main(String[] args) {
        new PopularBanco().popular(5);
    }
    
    public void popular(int quantidade){
        popularAdministrador(quantidade);
        popularAluno(quantidade);
        popularProfessor(quantidade);
        popularResponsavel(quantidade);
        popularDisciplina();
        popularSeries(quantidade);
        popularExercicios(quantidade);
    }
    
    public void popularProfessor(int quantidade){
        for(int i = 0; i < quantidade; i++){
            Professor professor = new Professor();
            Random rand = new Random();
            professor.setNome("testeNome"+ i);
            professor.setSobrenome("testeSobrenome"+i);
            professor.setDataNascimento("08/11/1995");
            professor.setInformacoesAdicionais("GERADO AUTOMATICAMENTE");
            professor.setTelefone("(11) 97334-3442");
            professor.setSexo("M");
            
            Usuario usuario = new Usuario();
            usuario.setLogin("professor"+i);
            usuario.setEmail("teste"+i+"@teste.com");
            usuario.setSenha("senha");
            
            professor.setUsuario(usuario);
            
            salvar(professor);
            
        }
    }
    
    public void popularAluno(int quantidade){
        for(int i = 0; i < quantidade; i++){
            Aluno aluno = new Aluno();
            Random rand = new Random();
            aluno.setNome("testeNome"+ i);
            aluno.setSobrenome("testeSobrenome"+i);
            aluno.setDataNascimento("08/11/1995");
            aluno.setInformacoesAdicionais("GERADO AUTOMATICAMENTE");
            aluno.setTelefone("(11) 97334-3442");
            aluno.setSexo("M");
            aluno.setMatricula("2015"+rand.nextInt(i+quantidade));
            Usuario usuario = new Usuario();
            usuario.setLogin("aluno"+i);
            usuario.setEmail("teste"+i+"@teste.com");
            usuario.setSenha("senha");
            aluno.setUsuario(usuario);
            salvar(aluno);
        }
    }
    public void popularAdministrador(int quantidade){
        for(int i = 0; i < quantidade; i++){
            Administrador admin = new Administrador();
            Random rand = new Random();
            admin.setNome("testeNome"+ i);
            admin.setSobrenome("testeSobrenome"+i);
            admin.setDataNascimento("08/11/1995");
            admin.setInformacoesAdicionais("GERADO AUTOMATICAMENTE");
            admin.setTelefone("(11) 97334-3442");
            admin.setSexo("M");
            Usuario usuario = new Usuario();
            usuario.setLogin("admin"+i);
            usuario.setEmail("teste"+i+"@teste.com");
            usuario.setSenha("senha");
            admin.setUsuario(usuario);
            
            salvar(admin);
        }
    }
    public void popularResponsavel(int quantidade){
        for(int i = 0; i < quantidade; i++){
            ResponsavelAluno responsavel = new ResponsavelAluno();
            Random rand = new Random();
            responsavel.setNome("testeNome"+ i);
            responsavel.setSobrenome("testeSobrenome"+i);
            responsavel.setDataNascimento("08/11/1995");
            responsavel.setInformacoesAdicionais("GERADO AUTOMATICAMENTE");
            responsavel.setTelefone("(11) 97334-3442");
            responsavel.setSexo("M");
            Usuario usuario = new Usuario();
            usuario.setLogin("responsavel"+i);
            usuario.setEmail("teste"+i+"@teste.com");
            usuario.setSenha("senha");
            responsavel.setUsuario(usuario);
            
            salvar(responsavel);
        }
    }
    
    private void popularDisciplina(){
        List<Disciplina> listaDisciplinas = new ArrayList<Disciplina>();
        listaDisciplinas.add(new Disciplina("Português"));
        listaDisciplinas.add(new Disciplina("Matemática"));
        listaDisciplinas.add(new Disciplina("História"));
        listaDisciplinas.add(new Disciplina("Ciências"));
        listaDisciplinas.add(new Disciplina("Geografia"));
        listaDisciplinas.add(new Disciplina("Inglês"));
        listaDisciplinas.add(new Disciplina("Artes"));
        listaDisciplinas.add(new Disciplina("Espanhol"));
        Resultado resultado = Resultado.getResultado();
        resultado.zerar();
        resultado = new Fachada().consultar(new Professor());
        for(int i = 0; i < listaDisciplinas.size(); i++){
            listaDisciplinas.get(i).setProfessores(new ArrayList<Professor>());
        }
        if(!resultado.getEntidades().isEmpty()){
            for(int i = 0; i < resultado.getEntidades().size(); i++){
                listaDisciplinas.get(i%listaDisciplinas.size()).getProfessores().add((Professor)resultado.getEntidades().get(i));
            }
        }
        for(Disciplina d: listaDisciplinas)
            salvar(d);
    }
    
    private void popularSeries(int quantidade){
        for(int i = 0; i < quantidade; i++){
            salvar(new Serie(i + "º SÉRIE"));
        }
    }
    
    private void popularExercicios(int quantidade){
        popularNiveis();
        for(int i = 0; i < quantidade; i++){
            cadastrarExercicios("Exercicio teste"+i);
        }
    }
    private void popularNiveis(){
        for(int i = 0; i < 5; i++){
            salvar(new Nivel("Dificuldade "+i));
        }
    }
    
    private void cadastrarExercicios(String exe) {
        fachada = new Fachada();
        Exercicio exercicio = new Exercicio();
        exercicio.setId(1);
        exercicio.setEnunciado(exe);
        exercicio.setTipo(exercicio.MULTIPLAESCOLHA);
        exercicio.setNivel(new Nivel(new Random().nextInt(4)+1));
        exercicio.setTags(new ArrayList<Tag>());
        exercicio.getTags().add(new Tag(exe+"1"));
        exercicio.getTags().add(new Tag("tag1"));
        exercicio.getTags().add(new Tag("todos"));
        exercicio.setAlternativas(new ArrayList<Alternativa>());
        exercicio.getAlternativas().add(new Alternativa(true, "Alternativa1"));
        exercicio.getAlternativas().add(new Alternativa(false, "Alternativa2"));
        exercicio.getAlternativas().add(new Alternativa(true, "Alternativa3"));
        fachada.salvar(exercicio);
    }

    
    private void salvar(EntidadeDominio entidade){
        Resultado resultado = Resultado.getResultado();
            IFachada fachada = new Fachada();
            resultado = fachada.salvar(entidade);
            if(!resultado.getMsgs().isEmpty()){
                for(String s: resultado.getMsgs())
                    System.out.println(s);
            }
    }
}
