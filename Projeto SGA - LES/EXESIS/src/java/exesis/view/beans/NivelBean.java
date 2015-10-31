package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.Nivel;
import exesis.model.TipoLista;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "nivelBean")
@ViewScoped
public class NivelBean extends AbstractBean{
    private Nivel nivel;
    private List<Nivel> listaNivel;
    
    @PostConstruct
    public void init(){
        fachada = new Fachada();
        nivel = new Nivel();
        atualizar();
    }
 
    public void atualizar(){
        resultado = fachada.consultar(new Nivel());
        listaNivel = new ArrayList<Nivel>();
        if(!resultado.getEntidades().isEmpty()){
            for(int i = 0; i < resultado.getEntidades().size(); i++)
                listaNivel.add((Nivel) resultado.getEntidades().get(i));
        }

    }
    
    public void salvar(){
        fachada = new Fachada();
        resultado = fachada.salvar(nivel);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Salvo", "");
        }
        atualizar();
    }
    
     public void apagar(int id){
        fachada = new Fachada();
        resultado = fachada.excluir(new Nivel(id));
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", "");
        }
        atualizar();
    }
    public void alterartodos(int idTipo){
            Nivel nivel = null;
            for(Nivel n: listaNivel){
                if(n.getId() == idTipo)
                    nivel = n;
            }
            if(nivel != null){
                resultado = fachada.alterar(nivel);
                if(!resultado.getMsgs().isEmpty()){
                    Mensagem(FacesMessage.SEVERITY_WARN, "Erro", resultado.getMsgs());
                }else{
                    Mensagem(FacesMessage.SEVERITY_INFO, "Salvo", "");
                }
            }
        atualizar();
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public List<Nivel> getListaNivel() {
        return listaNivel;
    }

    public void setListaNivel(List<Nivel> listaNivel) {
        this.listaNivel = listaNivel;
    }
    
    
    
    
}
