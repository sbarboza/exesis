package exesis.core.impl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exesis.core.aplicacao.Resultado;
import exesis.dominio.Dissertativa;
import exesis.dominio.EntidadeDominio;
import exesis.dominio.Exercicio;
import exesis.dominio.MultiplaEscolha;

public class AlternativaDAO extends AbstractJdbcDAO {

	public AlternativaDAO() {
		super("tb_alternativa", "id_alt");
		// TODO Auto-generated constructor stub
	}
	@Override
	public Resultado salvar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		Resultado resultado = null;
		PreparedStatement pst=null;
		MultiplaEscolha exercicio = (MultiplaEscolha) entidade;
		try {
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			for(String e: exercicio.getAlternativas().keySet()){
				sql.append("INSERT INTO ");
				sql.append(table);
				sql.append("(alternativa, id_exer, resposta)");
				sql.append(" VALUES('");
				sql.append(e);
				sql.append("',");
				sql.append(exercicio.getId());
				sql.append(",");
				sql.append(exercicio.getAlternativas().get(e));
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
				pst.close();
				if(ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return resultado;
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade) throws SQLException {
		openConnection();
		Resultado resultado = null;
		PreparedStatement pst=null;
		MultiplaEscolha exercicio = (MultiplaEscolha) entidade;
		if(exercicio.getId() == 0){
			resultado = new Resultado();
			resultado.setMsg("O Exercício não tem ID para fazer a alteração!");
			return resultado;
		}
		try {
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM ");
			sql.append(table);
			sql.append(" WHERE id_exer = ");
			sql.append(exercicio.getId());
			sql.append(";");
			for(String e: exercicio.getAlternativas().keySet()){
				sql.append("INSERT INTO ");
				sql.append(table);
				sql.append("(alternativa, id_exer, resposta)");
				sql.append(" VALUES('");
				sql.append(e);
				sql.append("',");
				sql.append(exercicio.getId());
				sql.append(",");
				sql.append(exercicio.getAlternativas().get(e));
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
				pst.close();
				if(ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<EntidadeDominio> preencherAlternativas(List<EntidadeDominio> lista){
		Map<String, Boolean> alternativas; 
		List<EntidadeDominio> listaAux = new ArrayList<EntidadeDominio>();
		Map<Integer, Map<String, Boolean>> mapa = new HashMap<Integer, Map<String, Boolean>>();
		PreparedStatement pst = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		sql.append(table);
		sql.append(" WHERE ");
		for(int i = 0; i < lista.size(); i++){
			sql.append(" id_exer = ");
			sql.append(lista.get(i).getId());
			if(i < lista.size()-1)
				sql.append(" or ");
		}
		sql.append(";");
		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				alternativas = new HashMap<String, Boolean>(); 
				if(mapa.containsKey(rs.getInt("id_exer"))){
					alternativas = mapa.get(rs.getInt("id_exer"));
					alternativas.put(rs.getString("alternativa"), rs.getBoolean("resposta"));
					mapa.put(rs.getInt("id_exer"),alternativas);
				}else{
					alternativas.put(rs.getString("alternativa"), rs.getBoolean("resposta"));
					mapa.put(rs.getInt("id_exer"),alternativas);
				}
			}
			for(EntidadeDominio e: lista){
				if(mapa.containsKey(e.getId())){
					MultiplaEscolha m = new MultiplaEscolha();
					m.setDt_cadastro(e.getDt_cadastro());
					m.setId(e.getId());
					m.setPergunta(((Exercicio)e).getPergunta());
					m.setPeso(((Exercicio)e).getPeso());
					m.setTags(((Exercicio)e).getTags());
					m.setAlternativas(mapa.get(e.getId()));
					listaAux.add(m);
				}else{
					Dissertativa d = new Dissertativa();
					d.setDt_cadastro(e.getDt_cadastro());
					d.setId(e.getId());
					d.setPergunta(((Exercicio)e).getPergunta());
					d.setPeso(((Exercicio)e).getPeso());
					d.setDt_cadastro(e.getDt_cadastro());
					d.setTags(((Exercicio)e).getTags());
					listaAux.add(d);
				}
			}
			for(EntidadeDominio e: listaAux){
				for(EntidadeDominio l: lista){
					if(l.getId() == e.getId()){
						lista.remove(l);
						break;
					}	
				}
				lista.add(e);
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
