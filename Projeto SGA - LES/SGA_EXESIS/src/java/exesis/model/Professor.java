package exesis.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "tbProfessores")
@Component("professor")
public class Professor extends Pessoa{
        public Professor(){}
        public Professor(String nome){
            this.nome = nome;
        }
}
    