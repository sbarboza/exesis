package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.MultiplaEscolha;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TagDAO extends AbstractJdbcDAO{


	public TagDAO() {
		super("tb_tag", "id_tag");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade){
		openConnection();
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
		Exercicio exercicio = (Exercicio) entidade;
		if(exercicio.getId() == 0)
		{
			resultado.setMsg("Exercicio não cont�m ID para salvar no banco de dados");
			return resultado;
		}
		try {
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			for(String tag: exercicio.getTags()){
				sql.append("INSERT INTO ");
				sql.append(table);
				sql.append("(nome, id_exer) ");
				sql.append(" VALUES('");
				sql.append(tag);
				sql.append("',");
				sql.append(exercicio.getId());
				sql.append(");");
			}			
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
				if(pst != null)
					pst.close();
				if(ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return null;
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade){
		// TODO Auto-generated method stub
		openConnection();
		Resultado resultado = null;
		PreparedStatement pst=null;
		Exercicio exercicio = (Exercicio) entidade;
		if(exercicio.getId() == 0){
			resultado = new Resultado();
			resultado.setMsg("Exerc�cio n�o cont�m ID para salvar no banco de dados");
			return resultado;
		}
		try {
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM ");
			sql.append(table);
			sql.append(" WHERE id_exer = ");
			sql.append(entidade.getId());
			sql.append(";");
			for(String tag: exercicio.getTags()){
				sql.append("INSERT INTO ");
				sql.append(table);
				sql.append("(nome, id_exer) ");
				sql.append(" VALUES('");
				sql.append(tag);
				sql.append("',");
				sql.append(exercicio.getId());
				sql.append(");");
			}			
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
				if(pst != null)
					pst.close();
				if(ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return null;
	}
	

	@Override
	public Resultado consultar(EntidadeDominio entidade)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<EntidadeDominio> consultarIdExercicio(EntidadeDominio entidade){
		List<EntidadeDominio> lista = null;
		Exercicio exercicio = (Exercicio) entidade;
		Map<Integer, String> mapa;
		PreparedStatement pst = null;
		StringBuilder sql = new StringBuilder();
		if(exercicio.getTags() == null )
			return null;
		sql.append("SELECT * FROM ");
		sql.append(table);
		if(exercicio.getTags().size() > 0 && !exercicio.getTags().get(0).equals(""))
		sql.append(" WHERE ");
		for(int i = 0;!exercicio.getTags().get(0).trim().equals("") && i < exercicio.getTags().size(); i++){
			sql.append(" nome like '%");
			sql.append(exercicio.getTags().get(i));
			sql.append("%'");
			if(i >= 0 && i < exercicio.getTags().size()-1)
				sql.append(" or ");
		}
		sql.append(";");
		
		try {
			lista = new ArrayList<EntidadeDominio>();
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			mapa = new HashMap<Integer, String>();
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				exercicio = new MultiplaEscolha();
				exercicio.setTags(new ArrayList<String>());
				/* Estrutura de MAP usada para evitar redund�ncia*/
				int id = rs.getInt("id_exer");
				if(!mapa.containsKey(id)){
					mapa.put(id, rs.getString("nome"));
				}else{
					String tags = mapa.get(id);
					tags += "," + rs.getString("nome"); 
					mapa.put(id, tags);
				}
				exercicio.setId(id);
				String[] nomes = mapa.get(id).split(",");
				for(String n: nomes){
					exercicio.getTags().add(n);
				}				
				
				for(int i=0; i < lista.size(); i++){
					if(exercicio.getId() == lista.get(i).getId())
						lista.remove(lista.get(i));
				}
				lista.add(exercicio);
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			
		}finally{
			try {
				if(pst != null)
					pst.close();
				if(ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}
	public List<EntidadeDominio> consultarTagPorID(List<EntidadeDominio> lista){
		ArrayList<String> tags;
		Map<Integer, ArrayList<String>> mapa;
		PreparedStatement pst = null;
		StringBuilder sql = new StringBuilder();
		if(lista == null || lista.isEmpty())
			return lista;
		sql.append("SELECT * FROM ");
		sql.append(table);
		sql.append(" WHERE ");
		for(int i = 0; i < lista.size(); i++){
			sql.append(" id_exer = ");
			sql.append(lista.get(i).getId());
			if(i < lista.size() - 1)
				sql.append(" or ");
		}
		sql.append(";");
		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			mapa = new HashMap<Integer, ArrayList<String>>();
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				/* Estrutura de MAP usada para evitar redund�ncia*/
				int id = rs.getInt("id_exer");
				if(!mapa.containsKey(id)){
					tags = new ArrayList<String>();
					tags.add(rs.getString("nome"));
					mapa.put(id, tags);
				}else{
					tags = mapa.get(id);
					tags.add(rs.getString("nome")); 
					mapa.put(id, tags);
				}
			}
			for(EntidadeDominio e: lista){
				((Exercicio)e).setTags(mapa.get(e.getId()));
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			
		}finally{
			try {
				if(pst != null)
					pst.close();
				if(ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;
		
	}
	
}
