package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.model.Alternativa;
import exesis.model.Aluno;
import exesis.model.Avaliacao;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.ListaRealizada;
import exesis.model.Resposta;
import java.sql.Connection;
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

@Component(value = "exesis.model.ListaRealizada")
public class ListaRealizadaDAO extends AbstractJdbcDAO{

        public ListaRealizadaDAO() {
                // CONTRUTOR COM NOME DA TABELA E NOME DA COLUNA DE IDENTIFICAÇÃO
		super("tbListasRealizadas", "id");
        }
        public ListaRealizadaDAO(Connection connection) {
                // CONTRUTOR COM NOME DA TABELA E NOME DA COLUNA DE IDENTIFICAÇÃO
		super(connection, "tbListasRealizadas", "id");
        }
	public Resultado salvar(EntidadeDominio entidade){
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
                StringBuilder sql = null;
         	ListaRealizada listaRealizada = (ListaRealizada) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
                ctrlTransaction = false; // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
		sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(table);
		sql.append("(dtRealizacao, aluno_id, avaliacao_id)");
                sql.append(" VALUES(?, ?, ?)");
                try {
                    openConnection();
                    pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                    Timestamp time = new Timestamp(listaRealizada.getDtCadastro().getTime());
                    pst.setTimestamp(1, time);
                    pst.setInt(2, listaRealizada.getAluno().getId());
                    pst.setInt(3, listaRealizada.getAvaliacao().getId());                    
                    listaRealizada.setId(executarSQL(pst)); // EXECUTA O SQL E ATRIBUI O ID DA ENTIDADE
                    if(listaRealizada.getListaRespostas() != null){
                        for(Resposta r: listaRealizada.getListaRespostas()){
                            if(r.getExercicio().getTipo() == Exercicio.MULTIPLAESCOLHA){
                                for(Alternativa alt: r.getAlternativas()){
                                    sql = new StringBuilder();
                                    sql.append("INSERT INTO ");
                                    sql.append("tbRespostasAlternativas ");
                                    sql.append("(listaRealizada_id, exercicio_id, alternativa_id, resposta) ");
                                    sql.append(" VALUES(?, ?, ?, ?); ");
                                    pst = connection.prepareStatement(sql.toString());
                                    pst.setInt(1, listaRealizada.getId());
                                    pst.setInt(2, r.getExercicio().getId());
                                    pst.setInt(3, alt.getId());
                                    pst.setBoolean(4, alt.getResposta());       
                                    System.out.println(pst.toString());
                                    executarSQL(pst);
                                }
                            }else if(r.getExercicio().getTipo() == Exercicio.DISSERTATIVA){
                                    sql = new StringBuilder();
                                    sql.append("INSERT INTO ");
                                    sql.append("tbRespostasDissertativas ");
                                    sql.append("(listaRealizada_id, exercicio_id, resposta) ");
                                    sql.append(" VALUES(?, ?, ?); ");
                                    pst = connection.prepareStatement(sql.toString());
                                    pst.setInt(1, listaRealizada.getId());
                                    pst.setInt(2, r.getExercicio().getId());
                                    pst.setString(3, r.getDissertativa());
                                    executarSQL(pst);
                            }
                            sql = new StringBuilder();
                            sql.append("INSERT INTO ");
                            sql.append("tbListasRealizadasNotas ");
                            sql.append("(listaRealizada_id, exercicio_id, correcao, nota) ");
                            sql.append(" VALUES(?, ?, ?,?); ");
                            pst = connection.prepareStatement(sql.toString());
                            pst.setInt(1, listaRealizada.getId());
                            pst.setInt(2, r.getExercicio().getId());
                            pst.setDouble(3, r.getCorrecao());
                            pst.setDouble(4, r.getNota());
                            executarSQL(pst);
                        }
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
		return Resultado.getResultado();
        }

	@Override
	public Resultado consultar(EntidadeDominio entidade){
            // DECLARAÇÃO DAS VARIÁVEIS
            ListaRealizada listaRealizada = null;
            if(entidade instanceof ListaRealizada){
                listaRealizada = (ListaRealizada) entidade;
                if(listaRealizada.getAluno() != null && listaRealizada.getAluno().getId() != 0) // TEM TAGS
                    return consultarPorAluno(listaRealizada.getAluno());
                else if(listaRealizada.getId() != 0) // TEM ID
                    return consultarPorId(listaRealizada);
            }
            return consultarTodos();
        }

        private Resultado consultarPorAluno(Aluno aluno){
            // DEFINIR AS VARIÁVEIS A SEREM USADAS
                Resultado resultado = Resultado.getResultado();
                PreparedStatement pst = null;
		StringBuilder sql = null;
                ListaRealizada listaRealizada;
                try {	
			// ABRE A CONEXÃO COM O BANCO DE DADOS
			openConnection();
			sql = new StringBuilder();
                        sql.append("SELECT ");
                        sql.append("tbListasRealizadas.id, dtRealizacao, aluno_id, avaliacao_id ");
                        sql.append("FROM ");
                        sql.append(table);
                        sql.append(" WHERE ");
                        sql.append(" aluno_id = ");
                        sql.append(aluno.getId());
                        sql.append(";");
                        pst = connection.prepareStatement(sql.toString());
                        ResultSet rs = pst.executeQuery();
                        Map<Integer, ListaRealizada> mapaListaRealizadas = new HashMap<Integer, ListaRealizada>();
			while (rs.next()) {
                            int id = rs.getInt("exercicio_id");
                            if(!mapaListaRealizadas.containsKey(id)){
                                listaRealizada = new ListaRealizada();
                                listaRealizada.setId(id);
                                listaRealizada.setDtCadastro(rs.getTimestamp("dtRealizacao"));
                                resultado = new AlunoDAO(connection).consultar(new Aluno(rs.getInt("aluno_id")));
                                listaRealizada.setAluno((Aluno) resultado.getEntidades().get(0));
                                resultado = new AvaliacaoDAO(connection).consultar(new Avaliacao(rs.getInt("aluno_id")));
                                listaRealizada.setAvaliacao((Avaliacao) resultado.getEntidades().get(0));
                            }else{
                                listaRealizada = mapaListaRealizadas.get(id);
                            }
                            mapaListaRealizadas.put(id, listaRealizada);
                        }
                        for(int i = 0; i < mapaListaRealizadas.values().size(); i++){
                            ListaRealizada lista = mapaListaRealizadas.get(i);
                            List<Integer> alternativa = new ArrayList<Integer>();
                            List<Integer> dissertativa = new ArrayList<Integer>();
                            
                            sql = new StringBuilder();
                            sql.append("SELECT listaRealizada_id, exercicio_id, correcao, nota ");
                            sql.append("FROM ");
                            sql.append("tbListasRealizadasNotas ");
                            sql.append("WHERE ");
                            sql.append("listaRealizada_id = ");
                            sql.append(lista.getId());
                            sql.append("AND exercicio_id in (");
                            for(int j = 0; j < lista.getAvaliacao().getListaCriada().getExercicios().size(); j++){
                                Exercicio exe = lista.getAvaliacao().getListaCriada().getExercicios().get(j);
                                if(exe.getTipo() == exe.MULTIPLAESCOLHA)
                                    alternativa.add(exe.getId());
                                else if(exe.getTipo() == exe.DISSERTATIVA)
                                    dissertativa.add(exe.getId());
                                sql.append(exe.getId());
                                if(j < lista.getAvaliacao().getListaCriada().getExercicios().size() -1)
                                    sql.append(",");
                            }
                            sql.append(");");
                            pst = connection.prepareStatement(sql.toString());
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                Resposta resposta = new Resposta();
                                resposta.setNota(rs.getDouble("nota"));
                                resposta.setCorrecao(rs.getDouble("correcao"));
                                resultado = new ExercicioDAO(connection).consultar(new Exercicio(rs.getInt("exercicio_id")));
                                resposta.setExercicio((Exercicio) resultado.getEntidades().get(0));
                                mapaListaRealizadas.get(i).getListaRespostas().add(resposta);
                            }
                            
                            sql = new StringBuilder();
                            sql.append("SELECT listaRealizada_id, exercicio_id, alternativa_id, resposta ");
                            sql.append("FROM ");
                            sql.append("tbRespostasAlternativas ");
                            sql.append("WHERE ");
                            sql.append("listaRealizada_id = ");
                            sql.append(lista.getId());
                            sql.append("AND exercicio_id in (");
                            for(int j = 0; j < alternativa.size(); j++){
                                sql.append(alternativa.get(j));
                                if(j < alternativa.size() -1)
                                    sql.append(",");
                            }
                            sql.append(");");
                            pst = connection.prepareStatement(sql.toString());
                            rs = pst.executeQuery();
                            while (rs.next()) {
                            
                            }
                        }
                        
                        
                        resultado.zerar();
                        for(ListaRealizada e: mapaListaRealizadas.values()){
                            resultado.setEntidade(e);    
                        }
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
        private Resultado consultarPorId(ListaRealizada exercicio){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" tbexercicios.id, dtCadastro, enunciado, tag_id, nome, tipo, contador, nivel_id, descricao, peso ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(" inner join ");
            sql.append(" tbListaRealizadasTags ");
            sql.append(" on (tbListaRealizadas.id = tbListaRealizadasTags.exercicio_id) ");
            sql.append(" inner join ");
            sql.append(" tbTags ");
            sql.append(" on (tbTags.id = tbListaRealizadasTags.tag_id) ");
            sql.append(" inner join ");
            sql.append(" tbNivel ");
            sql.append(" on (tbNivel.id = tbListaRealizadas.nivel_id) ");
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
            sql.append(" tbListaRealizadasTags ");
            sql.append(" on (tbListaRealizadas.id = tbListaRealizadasTags.exercicio_id) ");
            sql.append(" inner join ");
            sql.append(" tbTags ");
            sql.append(" on (tbTags.id = tbListaRealizadasTags.tag_id) ");
            sql.append(" full outer join ");
            sql.append(" tbNivel ");
            sql.append(" on (tbNivel.id = tbListaRealizadas.nivel_id) ");
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado executarConsulta(String  sql){
            Resultado resultado = Resultado.getResultado();
//            PreparedStatement pst = null;
//            ListaRealizada exercicio = null;
//            IDAO dao;
//            try {
//			openConnection();
//			pst = connection.prepareStatement(sql.toString());
//			ResultSet rs = pst.executeQuery();
//                        Map<Integer, ListaRealizada> mapaListaRealizadas = new HashMap<Integer, ListaRealizada>();
//			while (rs.next()) {
//                            int id = rs.getInt("id");
//                                if(!mapaListaRealizadas.containsKey(id)){
//                                    exercicio = new ListaRealizada();
////                                    exercicio.setId(id);
////                                    exercicio.setTags(new ArrayList<Tag>());
////                                    exercicio.setTipo(rs.getInt("tipo"));
////                                    exercicio.setDtCadastro(rs.getTimestamp("dtCadastro"));
////                                    exercicio.setEnunciado(rs.getString("enunciado"));
////                                    exercicio.setContador(rs.getInt("contador"));
////                                    Nivel nivel = new Nivel();
////                                    nivel.setId(rs.getInt("nivel_id"));
////                                    nivel.setDescricao(rs.getString("descricao"));
////                                    nivel.setPeso(rs.getFloat("peso"));
////                                    exercicio.setNivel(nivel);
//                                }else{
//                                    exercicio = mapaListaRealizadas.get(id);
//                                    exercicio.setId(id);
//                                }
//                            Tag tag = new Tag();
//                            tag.setId(rs.getInt("tag_id"));
//                            tag.setNome(rs.getString("nome"));
//                            exercicio.getTags().add(tag);
//                            mapaListaRealizadas.put(id, exercicio);
//                        }
//                        List<EntidadeDominio> exercicios = new ArrayList<EntidadeDominio>();
//                        for(ListaRealizada e: mapaListaRealizadas.values()){
//                            exercicio = e;
//                            if(exercicio.getTipo() == exercicio.MULTIPLAESCOLHA){
//                                dao = new AlternativaDAO(connection);
//                                dao.consultar(exercicio);
//                                for(EntidadeDominio exe: resultado.getEntidades()){
//                                    if(exercicio.getId() == exe.getId())
//                                        exercicio.setAlternativas(((ListaRealizada)exe).getAlternativas());
//                                }
//                            }
//                            exercicios.add(exercicio);
//                        }
//                        resultado.zerar();
//                        resultado.setEntidades(exercicios);
//                }catch(SQLException e){
//			e.printStackTrace();                    
//                }
                return resultado;
        }
}
