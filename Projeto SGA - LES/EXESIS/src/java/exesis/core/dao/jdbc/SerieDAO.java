
package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Nivel;
import exesis.model.Serie;
import exesis.model.TipoLista;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Serie")
public class SerieDAO extends AbstractJdbcDAO{

    public SerieDAO() {
        super("tbSeries", "id");
    }
    	public Resultado salvar(EntidadeDominio entidade){
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
                StringBuilder sql = null;
                Serie serie = (Serie) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
                ctrlTransaction = false; // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
                
		sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(table);
		sql.append("(serie)");
                sql.append(" VALUES(?);");
                try {
                    openConnection();
                    pst = connection.prepareStatement(sql.toString()); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                    pst.setString(1, serie.getNome());
                    executarSQL(pst); // EXECUTA O SQL
                    ctrlTransaction=true; // AFIRMA QUE A CONEXÃO PODE SER FECHADA, POIS A TRANSAÇÃO TERMINOU
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }finally{// FAÇA INDEPENDENTE DO QUE ACONTECER
			try {// TENTE
				if(ctrlTransaction) // SE A TRANSAÇÃO TERMINOU
					connection.close(); // FECHA A CONEXÃO
			} catch (SQLException e) {
				e.printStackTrace(); //SE DER ERRO, MOSTRE
			}
		}
                // RETORNA O RESULTADO
		return resultado;
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade){
                // DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst= null;
                StringBuilder sql = null;
		Serie serie = (Serie) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
                try {
                        openConnection();
			sql = new StringBuilder();
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET serie=? WHERE ");
			sql.append(idTable);
			sql.append(" = ");
			sql.append(entidade.getId());
			sql.append(";");                        
			pst = connection.prepareStatement(sql.toString()); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
			pst.setString(1, serie.getNome());
                        executarSQL(pst);
			return resultado;
		} catch (SQLException e) {
			e.printStackTrace(); // MOSTRE O ERRO
		}
		return resultado;
        }

	@Override
	public Resultado consultar(EntidadeDominio entidade){
            // DECLARAÇÃO DAS VARIÁVEIS
            Serie serie = null;
            if(entidade instanceof TipoLista){
                serie = (Serie) entidade;
                if(serie.getId() != 0) // TEM ID
                    return consultarPorId(serie);
            }
            return consultarTodos();
        }

        private Resultado consultarPorId(Serie serie){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, serie ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(" WHERE ");
            sql.append(" id =  ");
            sql.append(serie.getId());
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado consultarTodos(){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, serie ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado executarConsulta(String  sql){
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            Serie serie = null;
            try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
                        resultado.zerar();
			while (rs.next()) {
                            serie = new Serie();
                            serie.setId(rs.getInt(idTable));
                            serie.setNome(rs.getString("serie"));
                            resultado.setEntidade(serie);
                        }
                }catch(SQLException e){
			e.printStackTrace();                    
                }
                return resultado;
        }

}
