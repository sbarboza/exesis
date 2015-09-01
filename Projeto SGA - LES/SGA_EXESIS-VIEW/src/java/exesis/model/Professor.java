/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exesis.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import sun.util.resources.cldr.pt.CalendarData_pt_BR;

/**
 *
 * @author SAMUEL
 */
public class Professor extends EntidadeDominio{
        private String nome;
        private String sobrenome;
        private String sexo;
        private String dataNascimento;
        private String telefone;
        private String cpf;
        private Usuario usuario;
        private String informacoesAdicionais;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
//    public void setDataNascimento(String data) {
//        SimpleDateFormat fmt = new SimpleDateFormat(data);
//        this.dataNascimento = fmt.getCalendar().getTime();
//        
//    }
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the informacoesAdicionais
     */
    public String getInformacoesAdicionais() {
        return informacoesAdicionais;
    }

    /**
     * @param informacoesAdicionais the informacoesAdicionais to set
     */
    public void setInformacoesAdicionais(String informacoesAdicionais) {
        this.informacoesAdicionais = informacoesAdicionais;
    }

    /**
     * @return the dataNascimento
     */
    public String getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
        
}
