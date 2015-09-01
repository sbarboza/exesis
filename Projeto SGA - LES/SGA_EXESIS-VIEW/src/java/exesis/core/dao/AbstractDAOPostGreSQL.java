package exesis.core.dao;

import exesis.core.aplicacao.Resultado;
import exesis.core.util.Conexao;
import exesis.model.EntidadeDominio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public abstract class AbstractDAOPostGreSQL implements IDAO{
	protected Connection connection;
	protected String table;
	protected String idTable;
	protected boolean ctrlTransaction=true;
	
	public AbstractDAOPostGreSQL(Connection connection, String table, String idTable){
		this.table = table;
		this.idTable = idTable;
		 this.connection = connection;
	}
	
	protected AbstractDAOPostGreSQL(String table, String idTable){
		this.table = table;
		this.idTable = idTable;
	}
	public Resultado excluir(EntidadeDominio entidade) {		
		openConnection();
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ");
		sql.append(table);
		sql.append(" WHERE ");
		sql.append(idTable);
		sql.append(" = ");
		sql.append(entidade.getId());	
		sql.append(";");
                System.out.println(sql.toString());
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());
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
		}
		return resultado;
	}		
	
	
	protected void openConnection(){
		try {
			
			if(connection == null || connection.isClosed()){
				connection = Conexao.getConnection();				
                                System.out.println("CONEXÃO OK!");
                        }
		} catch (ClassNotFoundException e) {
			System.out.println("CONEXÃO FAILED - ClassNotFoundException!");
                        e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("CONEXÃO FAILED - SQLException!");
                        e.printStackTrace();
		}
	}
	
}
