package exesis.view.beans;

import exesis.core.aplicacao.Resultado;
import exesis.core.control.Fachada;
import exesis.model.EntidadeDominio;
import exesis.model.ListaCriada;
import exesis.model.Turma;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "disponibilizarListasBean")
@ViewScoped
public class DisponibilizarListasBean extends AbstractBean{
    private List<Turma> turmas;
    private List<ListaCriada> listasCriadas;
    
    @PostConstruct
    public void init(){
        resultado = Resultado.getResultado();
        fachada = new Fachada();
        // Pegar as listas criadas 
        listasCriadas = new ArrayList<ListaCriada>();
        resultado.zerar();
        resultado = fachada.consultar(new ListaCriada());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                listasCriadas.add((ListaCriada) e);
            }
        }
        
        // pegar as turmas 
        turmas = new ArrayList<Turma>();
        resultado.zerar();
        resultado = fachada.consultar(new Turma());
        if(!resultado.getEntidades().isEmpty()){
            for(EntidadeDominio e: resultado.getEntidades()){
                turmas.add((Turma)e);
            }
        }
        
    }
    
    
}
