package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.model.Disciplina;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.Professor;
import exesis.model.Tag;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Disciplina")
public class DisciplinaDAO extends AbstractJdbcDAO{

    public DisciplinaDAO() {
        super("tbDisciplina", "id");
    }

    public Resultado salvar(EntidadeDominio entidade){
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
                StringBuilder sql = null;
		Disciplina disciplina = (Disciplina) entidade;
		sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(table);
		sql.append("(nome, dtcadastro)");
                sql.append(" VALUES(?, ?)");
                try {
                    ctrlTransaction = false; // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
                    openConnection();
                    pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                    Timestamp time = new Timestamp(disciplina.getDtCadastro().getTime());
                    pst.setString(1, disciplina.getNome());
                    pst.setTimestamp(2, time);
                    disciplina.setId(executarSQL(pst)); // EXECUTA O SQL E ATRIBUI O ID DA ENTIDADE
                    // Salvar professores da Disciplina
                    if(disciplina.getProfessores() != null){
                        sql = new StringBuilder();
                        for(Professor p: disciplina.getProfessores()){
                            sql.append("INSERT INTO ");
                            sql.append("tbdisciplinasprofessores ");
                            sql.append("(disciplina_id, professor_id) ");
                            sql.append(" values(");
                            sql.append(disciplina.getId());
                            sql.append(", ");
                            sql.append(p.getId());
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
		Disciplina disciplina = (Disciplina) entidade;
                    openConnection();
                    // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
                    ctrlTransaction = false;
                    // CONCATENA O SQL
                    sql = new StringBuilder();
                    sql.append("UPDATE ");
                    sql.append(table);
                    sql.append(" SET ");
                    sql.append(" nome='");
                    sql.append(disciplina.getNome());
                    sql.append("' ");
                    sql.append(" WHERE ");
                    sql.append(idTable);
                    sql.append(" = ");
                    sql.append(entidade.getId());
                    sql.append(";");                        
                    executarSQLSemRetorno(sql.toString());
                    
                    sql = new StringBuilder();
                    sql.append("DELETE ");
                    sql.append("tbDisciplinasProfessores  ");
                    sql.append(" WHERE ");
                    sql.append(" disciplina_id = ");
                    sql.append(disciplina.getId());
                    sql.append(";");
                    executarSQLSemRetorno(sql.toString());
                    
                    if(disciplina.getProfessores() != null){
                        sql = new StringBuilder();
                        for(Professor p: disciplina.getProfessores()){
                            sql.append("INSERT INTO ");
                            sql.append("tbDisciplinasProfessores ");
                            sql.append("disciplina_id, professor_id ");
                            sql.append(" values(");
                            sql.append(disciplina.getId());
                            sql.append(", ");
                            sql.append(p.getId());
                            sql.append(");");
                        }
                        executarSQLSemRetorno(sql.toString());
                    }
                    
                    ctrlTransaction=true;
                    try{ 
                            if(ctrlTransaction)
                                    connection.close(); // FECHA A CONEXÃO
                    } catch (SQLException e) {
                            e.printStackTrace(); //SE DER ERRO, MOSTRE
                    }
		return resultado;
        }

	@Override
	public Resultado consultar(EntidadeDominio entidade){
            // DECLARAÇÃO DAS VARIÁVEIS
            Disciplina disciplina = null;
            if(entidade instanceof Disciplina){
                disciplina = (Disciplina) entidade;
                if(disciplina.getId() != 0) // TEM ID
                    return consultarPorId(disciplina);
            }
            return consultarTodos();
        }

        private Resultado consultarPorId(Disciplina disciplina){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, nome, dtcadastro, professor_id");
            sql.append(" FROM ");
            sql.append("tbDisciplina, tbDisciplinasProfessores");
            sql.append(" WHERE ");
            sql.append(" tbDisciplina.id = tbDisciplinasProfessores.disciplina_id AND ");
            sql.append(" tbDisciplina.id = ");
            sql.append(disciplina.getId());
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado consultarTodos(){
             StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, nome, dtcadastro, professor_id");
            sql.append(" FROM ");
            sql.append(" tbDisciplina left outer join tbDisciplinasProfessores ");
            sql.append(" on (tbDisciplina.id = tbDisciplinasProfessores.disciplina_id);");
            return executarConsulta(sql.toString());
        }
        
        private Resultado executarConsulta(String  sql){
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            Disciplina disciplina = null;
            ProfessorDAO dao = new ProfessorDAO();
            try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
                        Map<Integer, Disciplina> mapaDisciplinas = new HashMap<Integer, Disciplina>();
			while (rs.next()) {
                            int id = rs.getInt("id");
                                if(!mapaDisciplinas.containsKey(id)){
                                    disciplina = new Disciplina();
                                    disciplina.setId(id);
                                    disciplina.setNome(rs.getString("nome"));
                                    disciplina.setProfessores(new ArrayList<Professor>());
                                }else{
                                    disciplina = mapaDisciplinas.get(id);
                                }
                            Professor professor = new Professor();
                            professor.setId(rs.getInt("professor_id"));
                            resultado = dao.consultar(professor);
                            if(!resultado.getEntidades().isEmpty())
                                professor = (Professor) resultado.getEntidades().get(0);
                            disciplina.getProfessores().add(professor);
                            mapaDisciplinas.put(id, disciplina);
                            System.out.println("1");
                        }
                        resultado.zerar();
                        for(Disciplina d: mapaDisciplinas.values()){
                            resultado.setEntidade(d);
                        }
                }catch(SQLException e){
			e.printStackTrace();                    
                }
                return resultado;
        }
}
