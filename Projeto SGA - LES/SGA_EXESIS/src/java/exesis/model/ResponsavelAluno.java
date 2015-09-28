package exesis.model;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "tbResponsavelAluno")
@Component("responsavelAluno")
public class ResponsavelAluno extends Pessoa{
        @OneToMany(mappedBy = "responsavel")
        private Collection<Aluno> alunos;

    public Collection<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(Collection<Aluno> alunos) {
        this.alunos = alunos;
    }

}
