package exesis.aplicacao;

import java.util.ArrayList;
import java.util.List;

import exesis.entidade.EntidadeDominio;

public class Resultado {
	public static Resultado resultadoStatico;
	private static List<String> msgs;
	private List<EntidadeDominio> entidades;

	public static void setResultado(Resultado resultado){
		resultadoStatico = resultado;
	}
	public static Resultado getResultado(){
		if(resultadoStatico == null){
			resultadoStatico = new Resultado();
			msgs = new ArrayList<String>();
			}
		return resultadoStatico;
	}
	
	public static List<String> getMsg() {
		return msgs;
	}
	
	public static void setMsg(String msg) {
		Resultado.msgs.add(msg);
	}
	
	public List<EntidadeDominio> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<EntidadeDominio> entidades) {
		this.entidades = entidades;
	}
	
	
	
	
	
}
