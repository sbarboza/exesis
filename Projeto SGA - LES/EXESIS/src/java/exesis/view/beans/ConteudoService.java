package exesis.view.beans;

import exesis.model.Conteudo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

 
@ManagedBean(name = "conteudoService")
@ApplicationScoped
public class ConteudoService {

    private final static String[] professores;
     
    private final static String[] disciplinas;
     
    static {
        professores = new String[10];
        professores[0] = "Alberto";
        professores[1] = "Roberto";
        professores[2] = "Cláudia";
        professores[3] = "Mariana";
        professores[4] = "Renata";
        professores[5] = "Fernanda";
        professores[6] = "Patricia";
        professores[7] = "Romario";
        professores[8] = "Jose";
        professores[9] = "Joao";
         
        disciplinas = new String[10];
        disciplinas[0] = "Matemática";
        disciplinas[1] = "Biologia";
        disciplinas[2] = "História";
        disciplinas[3] = "Geografia";
        disciplinas[4] = "Português";
        disciplinas[5] = "Física";
        disciplinas[6] = "Química";
        disciplinas[7] = "Inglês";
        disciplinas[8] = "Artes";
        disciplinas[9] = "Educação Física";
    }
     
    public List<Conteudo> createConteudos(int size) {
        List<Conteudo> list = new ArrayList<Conteudo>();
        for(int i = 0 ; i < size ; i++) {
            list.add(new Conteudo(getRandomId(), getRandomDisciplina(), getRandomProfessor(), getRandomDesc())); 
        }
        return list;
    }
     
    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
          
    private String getRandomProfessor() {
        return professores[(int) (Math.random() * 10)];
    }
    
    private String getRandomDesc() {
        int rand;
        rand = (int) (Math.random() * 10);
        if(rand % 2 == 0){
            return "Conteúdo Complementar";
        }
        return "Texto Auxiliar";
    }
     
    private String getRandomDisciplina() {
        return disciplinas[(int) (Math.random() * 10)];
    }
     
    
    public List<String> getProfessores() {
        return Arrays.asList(professores);
    }
     
    public List<String> getDisciplinas() {
        return Arrays.asList(disciplinas);
    }
}