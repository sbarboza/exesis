package exesis.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass
public class EntidadeDominio implements Serializable{
	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        protected int id; 
	@Temporal(TemporalType.DATE)
        protected Date dtCadastro;
        

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDtCadastro() {
        return dtCadastro = new Date();
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }        
}