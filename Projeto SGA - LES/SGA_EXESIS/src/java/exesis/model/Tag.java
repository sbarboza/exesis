package exesis.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbTags")
public class Tag extends EntidadeDominio{
    public Tag(){}
    public Tag(String nome){
        this.nome = nome;
    }
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
//    @Override
//    public boolean equals(Object o){
//        if (this == o)
//			return true;
//        if (o == null || getClass() != o.getClass())
//		return false;
//        Tag tag = (Tag) o;
//        if (getNome() != null ? !getNome().equals(tag.getNome()): tag.getNome()!= null)
//            return false;
//	return true;
//    }
}
