package exesis.core.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.Nivel;
import exesis.model.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;

// INDICA PARA O SPRING A ENTIDADE QUE SERÁ PERSISTIDA
@Component(value = "exesis.model.Exercicio")
public class ExercicioDAO extends AbstractJdbcDAO{

	public ExercicioDAO() {
                // CONTRUTOR COM NOME DA TABELA E NOME DA COLUNA DE IDENTIFICAÇÃO
		super("tbExercicios", "id");
                
	}

	public Resultado salvar(EntidadeDominio entidade){
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
                StringBuilder sql = null;
                IDAO tagDao = new TagDAO();
                IDAO alternativaDao = new AlternativaDAO();
		Exercicio exercicio = (Exercicio) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
                ctrlTransaction = false; // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
		sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(table);
		sql.append("(dtCadastro, enunciado, tipo, contador, nivel_id)");
                sql.append(" VALUES(?, ?, ?, ?, ?)");
                try {
                    openConnection();
                    pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                    Timestamp time = new Timestamp(exercicio.getDtCadastro().getTime());
                    pst.setTimestamp(1, time);
                    pst.setString(2, exercicio.getEnunciado());
                    pst.setInt(3, exercicio.getTipo());                    
                    pst.setInt(4, exercicio.getContador());                    
                    pst.setInt(5, exercicio.getNivel().getId());                    
                    exercicio.setId(executarSQL(pst)); // EXECUTA O SQL E ATRIBUI O ID DA ENTIDADE
                    if(!exercicio.getTags().isEmpty()){
                        resultado = tagDao.salvar(exercicio); // SALVA AS TAGS DO EXERCÍCIO
                    }
                    if(exercicio.getTipo() == exercicio.MULTIPLAESCOLHA){
                        resultado = alternativaDao.salvar(exercicio); // SALVA AS ALTERNATIVAS DO EXERCÍCIO
                    }
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
                IDAO dao = null;
		Exercicio exercicio = (Exercicio) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
                try {
                        openConnection();
                        // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
                        ctrlTransaction = false;
                        // CONCATENA O SQL
			sql = new StringBuilder();
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
                        sql.append(" enunciado='");
                        sql.append(exercicio.getEnunciado());
                        sql.append("', contador=");
                        sql.append(exercicio.getContador());
                        sql.append(", nivel_id=");
                        sql.append(exercicio.getNivel().getId());
                        sql.append(" WHERE ");
			sql.append(idTable);
			sql.append(" = ");
			sql.append(entidade.getId());
			sql.append(";");                        
			pst = connection.prepareStatement(sql.toString()); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
			executarSQL(pst);
                        // SALVA AS TAGS DO EXERCÍCIO
                        if(exercicio.getTags() != null && !exercicio.getTags().isEmpty()){
                            dao = new TagDAO();
                            resultado = dao.alterar(exercicio);
                        }
                        // SALVA AS ALTERNATIVAS DO EXERCÍCIO
                        if(exercicio.getTipo() == exercicio.MULTIPLAESCOLHA){
                                dao = new AlternativaDAO();
                            resultado = dao.alterar(exercicio);
                        }
                        // AFIRMA QUE A CONEXÃO PODE SER FECHADA, POIS A TRANSAÇÃO TERMINOU
			ctrlTransaction=true;
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
            Exercicio exercicio = null;
            if(entidade instanceof Exercicio){
                exercicio = (Exercicio) entidade;
                if(exercicio.getTags() != null) // TEM TAGS
                    return consultarPorTags(exercicio);
                else if(exercicio.getId() != 0) // TEM ID
                    return consultarPorId(exercicio);
            }
            return consultarTodos();
        }

        private Resultado consultarPorTags(Exercicio exercicio){
            // DEFINIR AS VARIÁVEIS A SEREM USADAS
                Resultado resultado = Resultado.getResultado();
                ListaCriada listaCriada = new ListaCriada();
                listaCriada.setTags(exercicio.getTags());
                listaCriada.setExercicios(new ArrayList<Exercicio>());
                PreparedStatement pst = null;
		StringBuilder sql = null;
                try {	
			// ABRE A CONEXÃO COM O BANCO DE DADOS
			openConnection();
			sql = new StringBuilder();
                        sql.append("SELECT ");
                        sql.append("exercicio_id ");
                        sql.append("FROM ");
                        sql.append(" tbexercicios, tbexerciciostags, tbtags ");
                        sql.append(" WHERE ");
                        sql.append(" tbtags.id = tbexerciciostags.tag_id AND ");
                        sql.append(" tbtags.nome in('");
                        for(int i = 0; i < exercicio.getTags().size(); i++){
                            sql.append(exercicio.getTags().get(i).getNome());
                            if(i < exercicio.getTags().size() - 1)
                                sql.append("','");
                        }
                        sql.append("');");
                        JOptionPane.showMessageDialog(null,"E"+ sql.toString());
                        pst = connection.prepareStatement(sql.toString());
                        ResultSet rs = pst.executeQuery();
                        Map<Integer, Exercicio> mapaExercicios = new HashMap<Integer, Exercicio>();
			while (rs.next()) {
                            int id = rs.getInt("exercicio_id");
                            if(!mapaExercicios.containsKey(id)){
                                exercicio = new Exercicio();
                                exercicio.setId(id);
                            }else{
                                exercicio = mapaExercicios.get(id);
                            }
                            mapaExercicios.put(id, exercicio);
                        }
                        for(Exercicio e: mapaExercicios.values()){
                            resultado = consultar(e);
                            listaCriada.getExercicios().add((Exercicio)resultado.getEntidades().get(0));
                        }
                        resultado.zerar();
                        resultado.setEntidade(listaCriada);
                } catch (SQLException e) {
			e.printStackTrace();			
		}
		return resultado;
        }

        /**
         * Método privado que efetua a consulta pelo ID do exercício
         * @param exercicio exercício que tem o ID requirido na busca
         * @return Exercício referenciado
         */
        private Resultado consultarPorId(Exercicio exercicio){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" tbexercicios.id, dtCadastro, enunciado, tag_id, nome, tipo, contador, nivel_id ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(" inner join ");
            sql.append(" tbExerciciosTags ");
            sql.append(" on (tbExercicios.id = tbExerciciosTags.exercicio_id) ");
            sql.append(" inner join ");
            sql.append(" tbTags ");
            sql.append(" on (tbTags.id = tbExerciciosTags.tag_id) ");
            sql.append(" inner join ");
            sql.append(" tbNivel ");
            sql.append(" on (tbNivel.id = tbExerciciosTags.nivel_id) ");
            sql.append(" WHERE ");
            sql.append(" tbexercicios.id =  ");
            sql.append(exercicio.getId());
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado consultarTodos(){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" tbexercicios.id, dtCadastro, enunciado, tag_id, nome, tipo, contador, nivel_id, descricao, peso ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(" inner join ");
            sql.append(" tbExerciciosTags ");
            sql.append(" on (tbExercicios.id = tbExerciciosTags.exercicio_id) ");
            sql.append(" inner join ");
            sql.append(" tbTags ");
            sql.append(" on (tbTags.id = tbExerciciosTags.tag_id) ");
            sql.append(" inner join ");
            sql.append(" tbNivel ");
            sql.append(" on (tbNivel.id = tbExerciciosTags.nivel_id) ");
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado executarConsulta(String  sql){
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            Exercicio exercicio = null;
            IDAO dao;
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
                                    exercicio.setTags(new ArrayList<Tag>());
                                    exercicio.setTipo(rs.getInt("tipo"));
                                    exercicio.setDtCadastro(rs.getTimestamp("dtCadastro"));
                                    exercicio.setEnunciado(rs.getString("enunciado"));
                                    exercicio.setContador(rs.getInt("contador"));
                                    Nivel nivel = new Nivel();
                                    nivel.setId(rs.getInt("nivel_id"));
                                    nivel.setDescricao(rs.getString("descricao"));
                                    nivel.setPeso(rs.getFloat("peso"));
                                }else{
                                    exercicio = mapaExercicios.get(id);
                                    exercicio.setId(id);
                                }
                            Tag tag = new Tag();
                            tag.setId(rs.getInt("tag_id"));
                            tag.setNome(rs.getString("nome"));
                            exercicio.getTags().add(tag);
                            mapaExercicios.put(id, exercicio);
                        }
                        List<EntidadeDominio> exercicios = new ArrayList<EntidadeDominio>();
                        for(Exercicio e: mapaExercicios.values()){
                            exercicio = e;
                            if(exercicio.getTipo() == exercicio.MULTIPLAESCOLHA){
                                dao = new AlternativaDAO();
                                dao.consultar(exercicio);
                                for(EntidadeDominio exe: resultado.getEntidades()){
                                    if(exercicio.getId() == exe.getId())
                                        exercicio.setAlternativas(((Exercicio)exe).getAlternativas());
                                }
                            }
                            exercicios.add(exercicio);
                        }
                        resultado.zerar();
                        resultado.setEntidades(exercicios);
                }catch(SQLException e){
			e.printStackTrace();                    
                }
                return resultado;
        }
}
