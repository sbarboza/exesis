/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.Tag;
import exesis.model.TipoLista;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author EXESIS
 */
@Component(value = "exesis.model.ListaCriada")
public class ListaCriadaDAO extends AbstractJdbcDAO{

    public ListaCriadaDAO() {
        super("tbListasCriadas", "id");
    }

    	public Resultado salvar(EntidadeDominio entidade){
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
                StringBuilder sql = null;
                ListaCriada listaCriada = (ListaCriada) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
		sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(table);
		sql.append("(nome, dtcadastro, tipolista_id)");
                sql.append(" VALUES(?, ?, ?);");
                try {
                    openConnection();
                    ctrlTransaction = false;
                    pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                    pst.setString(1, listaCriada.getNome());
                    pst.setTimestamp(2, new Timestamp(listaCriada.getDtCadastro().getTime()));
                    pst.setInt(3, listaCriada.getTipo().getId());                    
                    listaCriada.setId(executarSQL(pst)); // EXECUTA O SQL E ATRIBUI O ID DA ENTIDADE
                    sql = new StringBuilder();
                    for(Exercicio e: listaCriada.getExercicios()){
                    sql.append("INSERT INTO ");
                    sql.append("tbListasCriadasExercicios ");
                    sql.append("(exercicio_id, listaCriada_id, peso) ");
                    sql.append(" values(");
                    sql.append(e.getId());
                    sql.append(",");
                    sql.append(listaCriada.getId());
                    sql.append(",");
                    sql.append(e.getPeso());
                    sql.append("); ");
                    }
                    executarSQLSemRetorno(sql.toString());
                    
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
                ListaCriada listaCriada = (ListaCriada) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
                try {
                        openConnection();
                        // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
			sql = new StringBuilder();
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET nome=?, tipoLista_id=? WHERE ");
			sql.append(idTable);
			sql.append(" = ");
			sql.append(entidade.getId());
			sql.append(";");                        
			pst = connection.prepareStatement(sql.toString()); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
			pst.setString(1, listaCriada.getNome());
			pst.setInt(2, listaCriada.getTipo().getId());
                        executarSQL(pst);
			return resultado;
		} catch (SQLException e) {
			e.printStackTrace(); // MOSTRE O ERRO
		}finally{
			try {// TENTE
				if(ctrlTransaction) // SE A TRANSAÇÃO TERMINOU
					connection.close(); // FECHA A CONEXÃO
			} catch (SQLException e) {
				e.printStackTrace(); //SE DER ERRO, MOSTRE
			}
		}
		return resultado;
        }

	@Override
	public Resultado consultar(EntidadeDominio entidade){
            // DECLARAÇÃO DAS VARIÁVEIS
            ListaCriada listaCriada = null;
            if(entidade instanceof Exercicio){
                listaCriada = (ListaCriada) entidade;
                if(listaCriada.getId() != 0) // TEM ID
                    return consultarPorId(listaCriada);
            }
            return consultarTodos();
        }

        /**
         * Método privado que efetua a consulta pelo ID da lista criada
         * @param exercicio Lista criada que tem o ID requirido na busca
         * @return Lista Criada referenciada
         */
        private Resultado consultarPorId(ListaCriada listaCriada){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" descricao, tbtiposlistas.peso, tipolista_id, nome, dtcadastro, tblistascriadas.id, tblistascriadasexercicios.peso, tblistascriadasexercicios.exercicio_id ");
            sql.append(" FROM ");
            sql.append(" tbListasCriadas, tbListasCriadasExercicios, tbTiposListas ");
            sql.append(" WHERE ");
            sql.append(" tblistascriadas.tipolista_id = tbtiposlistas.id AND ");
            sql.append(" tblistascriadas.id = tblistascriadasexercicios.listacriada_id AND ");
            sql.append(" tbListasCriadas.id =  ");
            sql.append(listaCriada.getId());
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado consultarTodos(){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" descricao, tbtiposlistas.peso as pesolista, tipolista_id, nome, dtcadastro, tblistascriadas.id, tblistascriadasexercicios.peso as pesoexercicio, tblistascriadasexercicios.exercicio_id ");
            sql.append(" FROM ");
            sql.append(" tbListasCriadas, tbListasCriadasExercicios, tbTiposListas ");
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado executarConsulta(String  sql){
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            ListaCriada listaCriada = null;
            IDAO dao;
            try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
                        Map<Integer, ListaCriada> mapaListas = new HashMap<Integer, ListaCriada>();
			while (rs.next()) {
                            int id = rs.getInt("id");
                                if(!mapaListas.containsKey(id)){
                                    listaCriada = new ListaCriada();
                                    listaCriada.setId(id);
                                    listaCriada.setExercicios(new ArrayList<Exercicio>());
                                    listaCriada.setTipo(new TipoLista(rs.getInt("tipolista_id"), rs.getString("descricao"), rs.getFloat("pesoexercicio")));
                                    listaCriada.setDtCadastro(rs.getDate("dtCadatro"));
                                    listaCriada.setNome(rs.getString("nome"));
                                }else{
                                    listaCriada = mapaListas.get(id);
                                }
                            
                            listaCriada.getExercicios().add(new Exercicio(rs.getInt("exercicio_id")));
                            mapaListas.put(id, listaCriada);
                        }
                        dao = new ExercicioDAO();
                        List<Exercicio> exercicios = new ArrayList<Exercicio>();
                        for(int i = 0; i < mapaListas.size(); i++){
                            for(Exercicio e: mapaListas.get(i).getExercicios()){
                                resultado = dao.consultar(e);
                                exercicios.add((Exercicio) resultado.getEntidades().get(0));
                            }
                            mapaListas.get(i).setExercicios(exercicios);
                        }
                        resultado.zerar();
                        for(ListaCriada lista: mapaListas.values()){
                        resultado.setEntidade(lista);
                        }
                }catch(SQLException e){
			e.printStackTrace();                    
                }
                return resultado;
        }
}
