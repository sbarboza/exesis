package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.model.Aluno;
import exesis.model.Avaliacao;
import exesis.model.Disciplina;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.ListaCriada;
import exesis.model.Turma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Avaliacao")
public class AvaliacaoDAO extends AbstractJdbcDAO{

    public AvaliacaoDAO() {
        super("tbavaliacoes", "id");
    }
    public AvaliacaoDAO(Connection connection) {
        super(connection,"tbavaliacoes", "id");
    }

    public Resultado salvar(EntidadeDominio entidade){
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst=null;
        StringBuilder sql = null;
        Avaliacao avaliacao = (Avaliacao) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
        sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(table);
        sql.append("(dtcadastro, turma_id, listaCriada_id, disciplina_id, prazo, ativo)");
        sql.append(" VALUES(?, ?, ?, ?, ?, ?)");
        try {
                    openConnection();
                    pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS); // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                    Timestamp cadastro = new Timestamp(avaliacao.getDtCadastro().getTime());
                    pst.setTimestamp(1, cadastro);
                    pst.setInt(2, avaliacao.getTurma().getId());
                    pst.setInt(3, avaliacao.getListaCriada().getId());                    
                    pst.setInt(4, avaliacao.getDisciplina().getId());                    
                    pst.setTimestamp(5, new Timestamp(avaliacao.getPrazo().getTime()));                    
                    pst.setBoolean(6, avaliacao.isAtivo());                    
                    executarSQLSemRetorno(pst);
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
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
            Avaliacao avaliacao = (Avaliacao) entidade; // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
            openConnection();
            sql = new StringBuilder();
            sql.append("UPDATE ");
            sql.append(table);
            sql.append(" SET ");
            sql.append(" turma_id=");
            sql.append(avaliacao.getTurma().getId());
            sql.append(", listaCriada_id=");
            sql.append(avaliacao.getListaCriada().getId());
            sql.append(", disciplina_id=");
            sql.append(avaliacao.getDisciplina().getId());
            sql.append(", prazo='");
            Timestamp prazo = new Timestamp(avaliacao.getPrazo().getTime());
            sql.append(prazo);
            sql.append("' WHERE ");
            sql.append(idTable);
            sql.append(" = ");
            sql.append(entidade.getId());
            sql.append(";");
            executarSQLSemRetorno(sql.toString());

        return resultado;
        }

	@Override
	public Resultado consultar(EntidadeDominio entidade){
            // DECLARAÇÃO DAS VARIÁVEIS
            Avaliacao avaliacao = null;
            if(entidade instanceof Exercicio){
                avaliacao = (Avaliacao) entidade;
                if(avaliacao.getTurma() != null && avaliacao.getTurma().getId() != 0) // TEM TURMA
                    return consultarPorTurma(avaliacao.getTurma());
                if(avaliacao.getId() != 0) // TEM ID
                    return consultarPorId(avaliacao);
            }
            return consultarTodos();
        }

        private Resultado consultarPorId(Avaliacao avaliacao){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, dtcadastro, turma_id, listaCriada_id, disciplina_id, prazo, ativo ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(" WHERE ");
            sql.append(" id =  ");
            sql.append(avaliacao.getId());
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        private Resultado consultarPorTurma(Turma turma){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, dtcadastro, turma_id, listaCriada_id, disciplina_id, prazo, ativo ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(" WHERE ");
            sql.append(" ativo = true AND ");
            sql.append(" turma_id =  ");
            sql.append(turma.getId());
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        private Resultado consultarPorAluno(Aluno aluno){
            Resultado resultado = new AlunoDAO().consultar(aluno);
            if(!resultado.getEntidades().isEmpty())
                aluno = (Aluno) resultado.getEntidades().get(0);
            if(aluno.getTurma() != null){
                resultado.zerar();
                resultado = consultarPorTurma(aluno.getTurma());
            }
            // Buscar listas realizadas
            // filtrar pelo ID do aluno
            // verificar os ids de listas criadas retornados e remover do retorno da consulta
            // retornar resultado
            
            return resultado;
        }
        
        private Resultado consultarTodos(){
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, dtcadastro, turma_id, listaCriada_id, disciplina_id, prazo, ativo ");
            sql.append(" FROM ");
            sql.append(table);
            sql.append(";");
            return executarConsulta(sql.toString());
        }
        
        private Resultado executarConsulta(String  sql){
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            Avaliacao avaliacao;
            try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
                        Map<Integer, Avaliacao> mapaAvaliacoes = new HashMap<Integer, Avaliacao>();
			while (rs.next()) {
                            int id = rs.getInt("id");
                                if(!mapaAvaliacoes.containsKey(id)){
                                    avaliacao = new Avaliacao();
                                    avaliacao.setId(id);
                                    avaliacao.setDtCadastro(rs.getTimestamp("dtCadastro"));
                                    avaliacao.setAtivo(rs.getBoolean("ativo"));
                                    avaliacao.setPrazo(rs.getTimestamp("prazo"));
                                    avaliacao.setTurma(new Turma(rs.getInt("turma_id")));
                                    avaliacao.setListaCriada(new ListaCriada(rs.getInt("listacriada_id")));
                                    avaliacao.setDisciplina(new Disciplina(rs.getInt("disciplina_id")));
                                    mapaAvaliacoes.put(id, avaliacao);
                                }
                        }
                        resultado.zerar();
                        for(Avaliacao av: mapaAvaliacoes.values()){
                            resultado.setEntidade(av);
                        }
                }catch(SQLException e){
			e.printStackTrace();                    
                }
                return resultado;
        }
}
