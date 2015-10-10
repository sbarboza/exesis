package exesis.core.dao.jdbc;


import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import exesis.core.dao.jdbc.TagDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.jdbc.AbstractJdbcDAO;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.MultiplaEscolha;


public class ExercicioDAO extends AbstractJdbcDAO {

	public ExercicioDAO() {
		super("tbExercicio", "exercicio_id");
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade){
		openConnection();
		Resultado resultado = null;
		PreparedStatement pst=null;
		Exercicio exercicio = (Exercicio) entidade;
		try {
			ctrlTransaction = false;
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ");
			sql.append(table);
			sql.append("(enunciado, peso, nota, )");
			sql.append(" VALUES(?, ?)");
			pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			Timestamp time = new Timestamp(exercicio.getDt_cadastro().getTime());
			pst.setTimestamp(1, time);
			pst.setString(2, exercicio.getPergunta());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if(rs != null && rs.next())
				exercicio.setId(rs.getInt(1));
			connection.commit();
			resultado = tagDao.salvar(entidade);
			if(exercicio instanceof MultiplaEscolha){
				AlternativaDAO alternativaDAO = new AlternativaDAO();
				resultado = alternativaDAO.salvar(entidade);
			}	
			ctrlTransaction=true;
			
			return resultado;
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
	public Resultado alterar(EntidadeDominio entidade){
		openConnection();
		Resultado resultado = null;
		PreparedStatement pst=null;
		Exercicio exercicio = (Exercicio) entidade;
		TagDAO tagDao = new TagDAO();
		try {
			ctrlTransaction = false;
			connection.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET dt_cadastro=?, pergunta=? WHERE ");
			sql.append(idTable);
			sql.append(" = ");
			sql.append(entidade.getId());
			sql.append(";");
			pst = connection.prepareStatement(sql.toString());
			Timestamp time = new Timestamp(exercicio.getDt_cadastro().getTime());
			pst.setTimestamp(1, time);
			pst.setString(2, exercicio.getPergunta());
			pst.executeUpdate();
			connection.commit();
			resultado = tagDao.alterar(entidade);
			if(exercicio instanceof MultiplaEscolha){
				AlternativaDAO alternativaDAO = new AlternativaDAO();
				resultado = alternativaDAO.alterar(entidade);
			}	
			ctrlTransaction=true;
			resultado = new Resultado();
			resultado.setMsg("Exerc�cio alterado com sucesso!");
			return resultado;
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
	public Resultado consultar(EntidadeDominio entidade){
		TagDAO tDao = new TagDAO();
		AlternativaDAO aDao = new AlternativaDAO();
		List<EntidadeDominio> lista = null;
		Resultado resultado;
		Exercicio exercicio = (Exercicio) entidade;
		PreparedStatement pst = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		sql.append(table);
		if(exercicio.getTags() != null || exercicio.getPergunta() != null || exercicio.getId() != 0)
			sql.append(" WHERE ");
		if(exercicio.getTags() != null){
			lista = tDao.consultarIdExercicio(entidade);
			for(int i = 0;lista != null && i < lista.size(); i++){
				sql.append(" id_exer = ");
				sql.append(lista.get(i).getId());
				if(i < lista.size()-1)
					sql.append(" or ");
			}
		}
		if(exercicio.getPergunta() != null){
			if(exercicio.getTags() != null)
				sql.append(" or ");
			sql.append(" pergunta like '%");
		sql.append(exercicio.getPergunta());
			sql.append("%' ");
			if(exercicio.getId() != 0)
				sql.append(" or ");
			
		}
		if(exercicio.getId() != 0){
			sql.append(" id_exer = "+exercicio.getId());
		}		
		sql.append(";");
		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			lista = new ArrayList<EntidadeDominio>();
			while (rs.next()) {
				Exercicio e = new Dissertativa();
				e.setDt_cadastro(rs.getTimestamp("dt_cadastro"));
				e.setId(rs.getInt(idTable));
				e.setPergunta(rs.getString("pergunta"));	
				if(exercicio != null)
				e.setPeso(exercicio.getPeso());
				lista.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resultado = new Resultado();
		
		resultado.setEntidades(lista); // joga para resultado os exerc�cios da lista
		resultado.setEntidades(aDao.preencherAlternativas(resultado.getEntidades())); // adiciona as alternativas nas entidades
		resultado.setEntidades(tDao.consultarTagPorID(lista)); // coloca as tags na lista
		return resultado;
	}
	
	
	public Resultado consultarPorTags(EntidadeDominio entidade){
		TagDAO dao = new TagDAO();
		Resultado resultado;
		List<EntidadeDominio> lista  = dao.consultarIdExercicio(entidade);
		Map<Integer, String> mapa = new HashMap<Integer, String>();
		PreparedStatement pst = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		if(lista != null  && lista.size() > 0)
		sql.append(" WHERE ");
		for(int i = 0; i < lista.size(); i++){
			sql.append("id_exer = ");
			sql.append(lista.get(i).getId());
			if(i >= 0 && i < lista.size()-1)
				sql.append(" or ");
		}
		sql.append(";");
		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				mapa.put(rs.getInt(idTable),rs.getString("pergunta"));
			}
			for(EntidadeDominio e: lista){
				((Exercicio)e).setPergunta(mapa.get(e.getId()));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resultado = new Resultado();
		if(lista.isEmpty()){
			resultado.setMsg("N�o encontrado nenhuma TAG informada");
		}else
			resultado.setEntidades(lista);
		
		return resultado;
	}
	
}
