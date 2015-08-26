package exesis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exesis.aplicacao.Resultado;
import exesis.entidade.EntidadeDominio;
import exesis.util.Conexao;

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
		Resultado resultado = null;
		PreparedStatement pst=null;		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idTable);
		sb.append(" = ");
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
			resultado = new Resultado();
			resultado.setMsg("Registro excluido com sucesso!");
		}
		return resultado;
	}		
	
	
	protected void openConnection(){
		try {
			
			if(connection == null || connection.isClosed())
				connection = Conexao.getConnection();				
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
