package exesis.model;

import java.util.Date;


public class EntidadeDominio implements IEntidade{
	private int id;
	private Date dtCadastro;

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
