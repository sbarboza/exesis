/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.view.beans;

import exesis.model.Lista;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Vinicius Oliveira
 */
@ManagedBean(name = "listaService")
@ApplicationScoped
public class ListaService {
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
     
    public List<Lista> createListas(int size) {
        List<Lista> list = new ArrayList<Lista>();
        for(int i = 0 ; i < size ; i++) {
            list.add(new Lista(getRandomId(), getRandomDesc(), getNowDate(), getRandomProfessor(), getRandomDisciplina())); 
        }
        return list;
    }
     
    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
     
    private String getNowDate() {
        //return dbl.toString();
         LocalDateTime ldt = LocalDateTime.now();
         return ldt.toString();
         
         
    }
     
    private String getRandomProfessor() {
        return professores[(int) (Math.random() * 10)];
    }
    
    private String getRandomDesc() {
        int rand;
        rand = (int) (Math.random() * 10);
        if(rand % 2 == 0){
            return "Lista de Exercícios";
        }
        return "Avaliação Bimestral";
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
