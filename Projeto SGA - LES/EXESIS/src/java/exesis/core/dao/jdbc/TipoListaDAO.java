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
@Component(value = "exesis.model.TipoLista")
public class TipoListaDAO extends AbstractJdbcDAO{

    public TipoListaDAO() {
        super("tbTiposListas", "id");
    }

    
    	public Resultado salvar(EntidadeDominio entidade){
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
                StringBuilder sql = null;
                TipoLista tipoLista = (TipoLista) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
                ctrlTransaction = false; // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
                
		sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(table);
		sql.append("(descricao, peso)");
                sql.append(" VALUES(?, ?);");
                try {
                    openConnection();
                    pst = connection.prepareStatement(sql.toString()); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                    pst.setString(1, tipoLista.getDescricao());
                    pst.setFloat(2, tipoLista.getPeso());
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
		TipoLista tipoLista = (TipoLista) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
                try {
                        openConnection();
			sql = new StringBuilder();
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET descricao=?, peso=? WHERE ");
			sql.append(idTable);
			sql.append(" = ");
			sql.append(entidade.getId());
			sql.append(";");                        
			pst = connection.prepareStatement(sql.toString()); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
			pst.setString(1, tipoLista.getDescricao());
			pst.setFloat(2, tipoLista.getPeso());
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
            TipoLista tipoLista = null;
            if(entidade instanceof TipoLista){
                tipoLista = (TipoLista) entidade;
                if(tipoLista.getId() != 0) // TEM ID
                    return consultarPorId(tipoLista);
            }
            return consultarTodos();
        }

        /**
         * Método privado que efetua a consulta pelo ID do Tipo de Lista
         * @param tiptoLista tipo da lista que tem o ID requirido na busca
         * @return Tipo da Lista referenciado
         */
        private Resultado consultarPorId(TipoLista tiptoLista){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, descricao, peso ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(" WHERE ");
            sql.append(" id =  ");
            sql.append(tiptoLista.getId());
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado consultarTodos(){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, descricao, peso ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado executarConsulta(String  sql){
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            TipoLista tipoLista = null;
            IDAO dao;
            try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
                        Map<Integer, Exercicio> mapaExercicios = new HashMap<Integer, Exercicio>();
                        resultado.zerar();
			while (rs.next()) {
                            tipoLista = new TipoLista();
                            tipoLista.setId(rs.getInt(idTable));
                            tipoLista.setDescricao(rs.getString("descricao"));
                            tipoLista.setPeso(rs.getFloat("peso"));
                            resultado.setEntidade(tipoLista);
                        }
                }catch(SQLException e){
			e.printStackTrace();                    
                }
                return resultado;
        }

}
