
package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.model.Disciplina;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.Professor;
import exesis.model.Serie;
import exesis.model.Turma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Turma")
public class TurmaDAO extends AbstractJdbcDAO{

    public TurmaDAO() {
        super("tbTurmas", "id");
    }
    public TurmaDAO(Connection connection) {
        super(connection, "tbTurmas", "id");
    }

	public Resultado salvar(EntidadeDominio entidade){
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
                StringBuilder sql = null;
                Turma turma = (Turma) entidade;
                ctrlTransaction = false; // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
		sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(table);
		sql.append("(serie_id, prefixo, periodo)");
                sql.append(" VALUES(?, ?, ?)");
                try {
                    openConnection();
                    pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                    pst.setInt(1, turma.getSerie().getId());
                    pst.setString(2, turma.getPrefixo());                    
                    pst.setInt(3, turma.getPeriodo());                    
                    turma.setId(executarSQL(pst)); // EXECUTA O SQL E ATRIBUI O ID DA ENTIDADE
                    if(!turma.getMapaDisciplinaProfessor().isEmpty()){
                        sql = new StringBuilder();
                        for(Disciplina d :turma.getMapaDisciplinaProfessor().keySet()){
                            sql.append("INSERT INTO ");
                            sql.append("tbDisciplinasProfessores ");
                            sql.append(" (turma_id, disciplina_id, professor_id) ");
                            sql.append(" values (");
                            sql.append(turma.getId());
                            sql.append(", ");
                            sql.append(d.getId());
                            sql.append(", ");
                            sql.append(turma.getMapaDisciplinaProfessor().get(d).getId());
                            sql.append(");");
                        }
                        executarSQLSemRetorno(sql.toString());
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
		Turma turma = (Turma) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
                try {
                        openConnection();
                        // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
                        ctrlTransaction = false;
                        // CONCATENA O SQL
			sql = new StringBuilder();
			sql.append("UPDATE ");
			sql.append(table);
			sql.append(" SET ");
                        sql.append("serie_id = ");
                        sql.append(turma.getSerie().getId());
                        sql.append(", prefixo='");
                        sql.append(turma.getPrefixo());
                        sql.append("', periodo=");
                        sql.append(turma.getPeriodo());
                        sql.append(" WHERE ");
			sql.append(idTable);
			sql.append(" = ");
			sql.append(entidade.getId());
			sql.append(";");                        
			pst = connection.prepareStatement(sql.toString()); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
			executarSQL(pst);
                        
                        sql = new StringBuilder();
                        sql.append("DELETE ");
                        sql.append("FROM ");
                        sql.append("tbDisciplinasProfessores ");
                        sql.append("WHERE ");
                        sql.append("turma_id = ");
                        sql.append(turma.getId());
                        
                        if(!turma.getMapaDisciplinaProfessor().isEmpty()){
                        sql = new StringBuilder();
                            for(Disciplina d :turma.getMapaDisciplinaProfessor().keySet()){
                                sql.append("INSERT INTO ");
                                sql.append("tbDisciplinasProfessores ");
                                sql.append("(turma_id, disciplina_id, professor_id) ");
                                sql.append("(");
                                sql.append(turma.getId());
                                sql.append(", ");
                                sql.append(d.getId());
                                sql.append(", ");
                                sql.append(turma.getMapaDisciplinaProfessor().get(d).getId());
                                sql.append(");");
                            }
                            executarSQLSemRetorno(sql.toString());
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
            Turma turma  = null;
            if(entidade instanceof Exercicio){
                turma = (Turma) entidade;
                if(turma.getId() != 0) // TEM ID
                    return consultarPorId(turma);
            }
            return consultarTodos();
        }

        private Resultado consultarPorId(Turma turma){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, serie_id, prefixo, periodo, turma_id, professor_id, disciplina_id, serie ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(" left outer join ");
            sql.append(" tbDisciplinasProfessores ");
            sql.append(" on (tbTurmas.id = tbDisciplinasProfessores.turma_id) ");
            sql.append(" inner join ");
            sql.append(" tbSeries ");
            sql.append(" on (tbTurmas.serie_id = tbSeries.id) ");
            sql.append(" WHERE ");
            sql.append(" tbTurmas.id =  ");
            sql.append(turma.getId());
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado consultarTodos(){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" tbTurmas.id, serie_id, prefixo, periodo, turma_id, professor_id, disciplina_id, serie ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(" left outer join ");
            sql.append(" tbDisciplinasProfessores ");
            sql.append(" on (tbTurmas.id = tbDisciplinasProfessores.turma_id) ");
            sql.append(" inner join ");
            sql.append(" tbSeries ");
            sql.append(" on (tbTurmas.serie_id = tbSeries.id) ");
            sql.append(";");
            System.out.println(sql.toString());
            return executarConsulta(sql.toString());
        }
        
        private Resultado executarConsulta(String  sql){
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            Turma turma = null;
            IDAO dao = null;
            try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
                        Map<Integer, Turma> mapaTurmas = new HashMap<Integer, Turma>();
			while (rs.next()) {
                            int id = rs.getInt("id");
                                if(!mapaTurmas.containsKey(id)){
                                    turma = new Turma();
                                    turma.setId(id);
                                    turma.setPeriodo(rs.getInt("periodo"));
                                    turma.setPrefixo(rs.getString("prefixo"));
                                    Serie serie = new Serie();
                                    serie.setId(rs.getInt("serie_id"));
                                    serie.setNome(rs.getString("serie"));
                                    turma.setSerie(serie);
                                    turma.setMapaDisciplinaProfessor(new HashMap<Disciplina, Professor>());
                                }else{
                                    turma = mapaTurmas.get(id);
                                }
                            dao = new DisciplinaDAO();
                            resultado.zerar();
                            resultado = dao.consultar(new Disciplina(rs.getInt("disciplina_id")));
                            Disciplina disciplina = (Disciplina) resultado.getEntidades().get(0);
                            dao = new ProfessorDAO();
                            resultado.zerar();
                            resultado = dao.consultar(new Professor(rs.getInt("professor_id")));
                            Professor professor = (Professor) resultado.getEntidades().get(0);
                            turma.getMapaDisciplinaProfessor().put(disciplina, professor);
                            mapaTurmas.put(id, turma);
                        }
                        resultado.zerar();
                        for(Turma t: mapaTurmas.values())
                            resultado.setEntidade(t);
                }catch(SQLException e){
			e.printStackTrace();                    
                }
                return resultado;
        }

}
