
package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.core.dao.util.Conexao;
import exesis.model.EntidadeDominio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class AbstractJdbcDAO implements IDAO{

	protected Connection connection;
	protected String table;
	protected String idTable;
	protected boolean ctrlTransaction=true;
        private int count = 0;
	
	public AbstractJdbcDAO( Connection connection, String table, String idTable){
		this.table = table;
		this.idTable = idTable;
		this.connection = connection;
	}
	
	protected AbstractJdbcDAO(String table, String idTable){
		this.table = table;
		this.idTable = idTable;
	}
	@Override
	public Resultado excluir(EntidadeDominio entidade) {		
		openConnection();
		Resultado resultado = null;
		PreparedStatement pst=null;		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idTable);
		sb.append("=");
		sb.append(entidade.getId());	
		sb.append(";");
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sb.toString());
			pst.executeUpdate();
			connection.commit();
		
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			
		}finally{
			try {
				pst.close();
				if(ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			resultado = Resultado.getResultado();
		}
		return resultado;
	}	
        
	protected void openConnection(){
            try {
                       
			if(connection == null || connection.isClosed()){
				connection = Conexao.getConnection();				
                                count++;
                            System.out.println("CONEXÃO COM BANCO DE DADOS EFETUADA COM SUCESSO: "+count);
                        }
		} catch (ClassNotFoundException e) {
			System.out.println("CONEXÃO COM BANCO DE DADOS NÃO EFETUADA!");
                        e.printStackTrace();
		} catch (SQLException e) {
                        System.out.println("CONEXÃO COM BANCO DE DADOS NÃO EFETUADA!");
			e.printStackTrace();
		}
	}
        
        /**
         * Este método executa uma atualização no banco de dados
         * @param sql SQL que deve ser executado para atualizar o banco de dados
         * @return retorna a chave gerada a partir da execução do SQL
         */
        protected int executarSQL(String sql){
            PreparedStatement pst = null;
            try {  
                openConnection();
                pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return executarSQL(pst);
        }
        
         /**
         * Este método executa uma atualização no banco de dados
         * @param sql SQL que deve ser executado para atualizar o banco de dados
         * @return retorna a chave gerada a partir da execução do SQL
         */
        protected void executarSQLSemRetorno(String sql){
            PreparedStatement pst = null;
            try {  
                openConnection();
                pst = connection.prepareStatement(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            executarSQLSemRetorno(pst);
        }
        /**
         * Este método executa uma atualização no banco de dados
         * @param pst PreparedStatement - para execução do sql
         * @return retorna a chave gerada a partir do SQL
         */
        protected int executarSQL(PreparedStatement pst){
                ResultSet rs = null;
                try {
                        // NÃO DEIXA O COMMIT SER EXECUTADO AUTOMATICAMENTE
                        pst.getConnection().setAutoCommit(false);
                        // EXECUTA ATUALIZAÇÃO DO BANCO DE DADOS
                        pst.executeUpdate();
			// SALVAR AS ALTERAÇÕES (MAS AINDA PODE FAZER UM ROLLBACK)
			connection.commit();
                        rs = pst.getGeneratedKeys();
                        if(rs != null && rs.next()){
                            return rs.getInt("id");
                        }
		} catch (SQLException e) {
                        // SE DER ERRO
			try { // TENTE RECUPERAR O ESTADO ANTERIOR DO BANCO DE DADOS
				connection.rollback();
			} catch (SQLException e1) {
                             // SE DER ERRO, MOSTRE O ERRO
				e1.printStackTrace();
			}
                        // MOSTRE O ERRO
			e.printStackTrace();			
		}finally{
                        // FAÇA INDEPENDENTE DO QUE ACONTECER
			try {// TENTE
                                // FECHE O PREPARESATEMENT
				pst.close();
				if(ctrlTransaction) // SE A TRANSAÇÃO TERMINOU
					connection.close(); // FECHA A CONEXÃO
			} catch (SQLException e) {
                                //SE DER ERRO, MOSTRE
				e.printStackTrace();
			}
		}
                return 0;
        }
        
        /**
         * Este método executa uma atualização no banco de dados
         * @param pst PreparedStatement - para execução do sql
         * @return retorna a chave gerada a partir do SQL
         */
        protected void executarSQLSemRetorno(PreparedStatement pst){
                ResultSet rs = null;
                try {
                        // NÃO DEIXA O COMMIT SER EXECUTADO AUTOMATICAMENTE
                        pst.getConnection().setAutoCommit(false);
                        // EXECUTA ATUALIZAÇÃO DO BANCO DE DADOS
                        pst.executeUpdate();
			// SALVAR AS ALTERAÇÕES (MAS AINDA PODE FAZER UM ROLLBACK)
			connection.commit();
		} catch (SQLException e) {
                        // SE DER ERRO
			try { // TENTE RECUPERAR O ESTADO ANTERIOR DO BANCO DE DADOS
				connection.rollback();
			} catch (SQLException e1) {
                             // SE DER ERRO, MOSTRE O ERRO
				e1.printStackTrace();
			}
                        // MOSTRE O ERRO
			e.printStackTrace();			
		}finally{
                        // FAÇA INDEPENDENTE DO QUE ACONTECER
			try {// TENTE
                                // FECHE O PREPARESATEMENT
				pst.close();
				if(ctrlTransaction) // SE A TRANSAÇÃO TERMINOU
					connection.close(); // FECHA A CONEXÃO
			} catch (SQLException e) {
                                //SE DER ERRO, MOSTRE
				e.printStackTrace();
			}
		}
        }
}
