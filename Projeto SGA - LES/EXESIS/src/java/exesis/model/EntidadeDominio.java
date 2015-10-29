package exesis.model;

import java.io.Serializable;
import java.util.Date;

public class EntidadeDominio implements Serializable{
    protected int id; 
    protected Date dtCadastro;
    
    public EntidadeDominio(){
        dtCadastro = new Date();
    }


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
