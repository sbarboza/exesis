package exesis.view.beans;

import exesis.core.control.Fachada;
import exesis.model.ListaCriada;
import exesis.model.TipoLista;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author EXESIS
 */
@ManagedBean(name = "metodoAvaliacaoBean")
@ViewScoped
public class MetodoAvaliacaoBean extends AbstractBean{
    private TipoLista tipoLista;
    private List<TipoLista> listaTipos;
    @PostConstruct
    public void init(){
        fachada = new Fachada();
        tipoLista = new TipoLista();
        atualizar();
    }
 
    public void atualizar(){
         resultado = fachada.consultar(new TipoLista());
        listaTipos = new ArrayList<TipoLista>();
        if(!resultado.getEntidades().isEmpty()){
            for(int i = 0; i < resultado.getEntidades().size(); i++)
            listaTipos.add((TipoLista) resultado.getEntidades().get(i));
        }

    }
    
    public void salvar(){
        fachada = new Fachada();
        resultado = fachada.salvar(tipoLista);
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Salvo", "");
        }
        atualizar();
    }
    
     public void apagar(int id){
        fachada = new Fachada();
        resultado = fachada.excluir(new TipoLista(id));
        if(!resultado.getMsgs().isEmpty()){
            Mensagem(FacesMessage.SEVERITY_WARN, "Erro", resultado.getMsgs());
        }else{
            Mensagem(FacesMessage.SEVERITY_INFO, "Excluído com sucesso", "");
        }
        atualizar();
    }
    public void alterartodos(int idTipo){
            TipoLista tipo = null;
            for(TipoLista t: listaTipos){
                if(t.getId() == idTipo)
                    tipo = t;
            }
            if(tipo != null){
                resultado = fachada.alterar(tipo    );
                if(!resultado.getMsgs().isEmpty()){
                    Mensagem(FacesMessage.SEVERITY_WARN, "Erro", resultado.getMsgs());
                }else{
                    Mensagem(FacesMessage.SEVERITY_INFO, "Salvo", "");
                }
            }
        atualizar();
    }
    
    
    public TipoLista getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(TipoLista tipoLista) {
        this.tipoLista = tipoLista;
    }

    public List<TipoLista> getListaTipos() {
        return listaTipos;
    }

    public void setListaTipos(List<TipoLista> listaTipos) {
        this.listaTipos = listaTipos;
    }
    
    
}
