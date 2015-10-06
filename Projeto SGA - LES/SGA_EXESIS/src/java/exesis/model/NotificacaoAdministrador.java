package exesis.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbConfiguracaoNotificacaoAdministrador")
public class NotificacaoAdministrador extends ConfiguracaoAdministrador{
    private boolean fechamentoNotasTurma;
    private boolean fechamentoNotasDisciplina;
    public NotificacaoAdministrador(){
        this.fechamentoNotasTurma = true;
        this.fechamentoNotasDisciplina = true;
    }
    
    public boolean isFechamentoNotasTurma() {
        return fechamentoNotasTurma;
    }

    public void setFechamentoNotasTurma(boolean fechamentoNotasTurma) {
        this.fechamentoNotasTurma = fechamentoNotasTurma;
    }

    public boolean isFechamentoNotasDisciplina() {
        return fechamentoNotasDisciplina;
    }

    public void setFechamentoNotasDisciplina(boolean fechamentoNotasDisciplina) {
        this.fechamentoNotasDisciplina = fechamentoNotasDisciplina;
    }
    
    
    
}
