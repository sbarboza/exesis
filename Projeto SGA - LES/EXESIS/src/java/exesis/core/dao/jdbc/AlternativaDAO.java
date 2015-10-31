package exesis.core.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exesis.core.aplicacao.Resultado;
import exesis.model.Alternativa;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class AlternativaDAO extends AbstractJdbcDAO{

	public AlternativaDAO() {
		super("tbAlternativas", "id");
		// TODO Auto-generated constructor stub
	}
	@Override
	public Resultado salvar(EntidadeDominio entidade){
                // DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
                StringBuilder sql = null;
                Alternativa alternativaAux = null;
                List<Alternativa> listaAlternativas;
                // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
		Exercicio exercicio = (Exercicio) entidade;
                try {
                        // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                        openConnection();
                        // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
                        // NÃO DEIXA O SQL FAZER O COMMIT AUTOMÁTICO
                        connection.setAutoCommit(false);
                        listaAlternativas = new ArrayList<Alternativa>();
                        for(Alternativa alt: exercicio.getAlternativas()){ // PARA CADA (ALTERNATIVA) CONTIDA EM EXERCICIO
                            // CONCATENA O SQL        
                            sql = new StringBuilder();
                            sql.append("INSERT INTO ");
                            sql.append(table);
                            // VARIÁVEIS DA TABELA
                            sql.append("(descricao, resposta)");
                            sql.append(" VALUES(?, ?);");
                            // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                            // ADICIONA OS ATRIBUTOS REFERENCIANDO AS INTERROGAÇÕES DO "VALUES" DO SQL
                            pst.setString(1, alt.getDescricao());
                            pst.setBoolean(2, alt.getResposta());
                            // EXECUTA O SQL (SEM SALVAR)
                            pst.executeUpdate();
                            // PEGA A CHAVE GERADA NA GRAVAÇÃO DOS DADOS
                            ResultSet rs = pst.getGeneratedKeys();
                            // VERIFICA SE TEM RETORNO
                            if(rs != null && rs.next()){
                                    // GRAVA O IDENTIFICADOR NA CLASSE, RECUPERANDO PELA REFERÊNCIA DO NOME DA TABELA (idTable)
                                    alternativaAux = alt;
                                    alternativaAux.setId(rs.getInt(idTable));
                            }
                            // SALVAR AS ALTERAÇÕES (MAS AINDA PODE FAZER UM ROLLBACK
                            connection.commit();
                            listaAlternativas.add(alternativaAux);
                            exercicio.setAlternativas(listaAlternativas);
                        }
                        // TABELA DO JOIN
                            for(Alternativa t: exercicio.getAlternativas()){
                                sql = new StringBuilder();
                                sql.append("INSERT INTO ");
                                sql.append(" tbExerciciosAlternativas ");
                                // VARIÁVEIS DA TABELA
                                sql.append("(exercicio_id, alternativa_id)");
                                sql.append(" VALUES(?, ?);");
                                // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                                pst = connection.prepareStatement(sql.toString());
                                // ADICIONA OS ATRIBUTOS REFERENCIANDO AS INTERROGAÇÕES DO "VALUES" DO SQL
                                pst.setInt(1, exercicio.getId());
                                pst.setInt(2, t.getId());
                                // EXECUTA O SQL (SEM SALVAR)
                                pst.executeUpdate();    
                            }
                        connection.commit();
		}catch(SQLException e){
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
                                if(pst != null)
                                    pst.close();
				if(ctrlTransaction) // SE A TRANSAÇÃO TERMINOU
					connection.close(); // FECHA A CONEXÃO
			} catch (SQLException e) {
                                //SE DER ERRO, MOSTRE
				e.printStackTrace();
			}
		}
                // RETORNA O RESULTADO
		return resultado;	}

	
	@Override
	public Resultado alterar(EntidadeDominio entidade){
		// DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
		Resultado resultado;
                resultado = excluir(entidade);
                resultado = salvar(entidade);

                // RETORNA O RESULTADO
		return resultado;
	}

    @Override
    public Resultado excluir(EntidadeDominio entidade) {
        /* SQL CONSULTA EXCLUIR
            SELECT 
        tbexerciciosalternativas.alternativa_id, 
        tbexerciciosalternativas.exercicio_id
      FROM 
        tbexerciciosalternativas, 
        tbalternativas
      WHERE 
        tbexerciciosalternativas.alternativa_id = tbalternativas.id AND
        tbexerciciosalternativas.exercicio_id = 1;
        
        SQL EXCLUSÃO 
        delete from tbalternativas
        where id = 22;

        */
        openConnection();
        Resultado resultado = Resultado.getResultado();
        StringBuilder sqlConsulta = new StringBuilder();
        StringBuilder sqlExclusao = new StringBuilder();
        PreparedStatement pst=null;	
        if(entidade instanceof Exercicio){
                Exercicio exercicio = (Exercicio) entidade;
                sqlConsulta.append("SELECT ");
                sqlConsulta.append("tbexerciciosalternativas.alternativa_id, ");
                sqlConsulta.append("tbexerciciosalternativas.exercicio_id ");
                sqlConsulta.append("FROM ");
                sqlConsulta.append("tbexerciciosalternativas, ");
                sqlConsulta.append("tbalternativas ");
                sqlConsulta.append(" WHERE ");
                sqlConsulta.append(" tbexerciciosalternativas.alternativa_id = tbalternativas.id ");
                sqlConsulta.append(" AND ");
                sqlConsulta.append(" tbexerciciosalternativas.exercicio_id ");
                sqlConsulta.append(" = ");
                sqlConsulta.append(exercicio.getId());	
                sqlConsulta.append(";");                
        }else
            return super.excluir(entidade);
        try {
                
                connection.setAutoCommit(false);
                pst = connection.prepareStatement(sqlConsulta.toString());
                ResultSet rs = pst.executeQuery();
                List<Integer> alternativas = new ArrayList<Integer>();
                while(rs.next()){
                    alternativas.add(rs.getInt("alternativa_id"));
                }
                sqlExclusao.append("DELETE FROM ");
                sqlExclusao.append(table);
                sqlExclusao.append(" WHERE ");
                sqlExclusao.append(idTable);
                sqlExclusao.append(" in (");
                for(int i = 0; i < alternativas.size(); i++){
                    sqlExclusao.append(alternativas.get(i));
                    if(i < alternativas.size() - 1)
                        sqlExclusao.append(", ");
                }
                sqlExclusao.append(");");
                pst = connection.prepareStatement(sqlExclusao.toString());
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
	public Resultado consultar(EntidadeDominio entidade){
            /*
            SELECT 
            tbexercicios.id, alternativa_id, descricao, resposta
          FROM 
            tbexercicios, tbexerciciosalternativas, tbalternativas
          WHERE 
            tbexercicios.id = tbexerciciosalternativas.exercicio_id AND
            tbexerciciosalternativas.alternativa_id = tbalternativas.id AND
            tbexercicios.id = 1;
            */
            // DECLARAÇÃO DAS VARIÁVEIS
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            StringBuilder sql = new StringBuilder();
            Exercicio exercicio = (Exercicio) entidade;
                sql.append("SELECT ");
                sql.append("tbexercicios.id, alternativa_id, descricao, resposta ");
                sql.append("FROM ");                
                sql.append(" tbexercicios, tbexerciciosalternativas, tbalternativas ");
                sql.append(" WHERE ");
                sql.append("tbexercicios.id = tbexerciciosalternativas.exercicio_id AND ");
                sql.append("tbexerciciosalternativas.alternativa_id = tbalternativas.id AND ");
                sql.append(" tbexercicios.id =  ");
                sql.append(exercicio.getId());
                sql.append(";");
  		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
                        Map<Integer, Exercicio> mapaExercicios = new HashMap<Integer, Exercicio>();
			while (rs.next()) {
                            int id = rs.getInt("id");
                            if(!mapaExercicios.containsKey(id)){
                                exercicio = new Exercicio();
                                exercicio.setId(id);
                                exercicio.setAlternativas(new ArrayList<Alternativa>());
                            }else{
                                exercicio = mapaExercicios.get(id);
                            }
                            exercicio.setId(id);
                            Alternativa alternativa = new Alternativa();
                            alternativa.setId(rs.getInt("alternativa_id"));
                            alternativa.setDescricao(rs.getString("descricao"));
                            alternativa.setResposta(rs.getBoolean("resposta"));
                            exercicio.getAlternativas().add(alternativa);
                            mapaExercicios.put(id, exercicio);
                        }
                        resultado.zerar();
                        for(Exercicio e: mapaExercicios.values()){
                            resultado.setEntidade(e);
                        }
                }catch(SQLException e){
			e.printStackTrace();                    
                }
                return resultado;
	}
}