package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Exercicio;
import exesis.model.Tag;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;



@Component(value = "exesis.model.Tag")
public class TagDAO extends AbstractJdbcDAO{


	public TagDAO() {
		super("tbTags", "id");
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade){
		// DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
		Resultado resultado = Resultado.getResultado();
		PreparedStatement pst=null;
                StringBuilder sql = null;
                Tag tagAux = null;
                List<Tag> listaTags;
                // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
		Exercicio exercicio;
                if(entidade instanceof Exercicio)
                    exercicio = (Exercicio) entidade;
                else
                    exercicio = new Exercicio();
                try {
                        // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                        openConnection();
                        ctrlTransaction = false;
                        // FALSE - PARA NÃO FECHAR A CONEXÃO ANTES QUE SE TENHA SALVO TUDO
                        listaTags = new ArrayList<Tag>();
                        // NÃO DEIXA O SQL FAZER O COMMIT AUTOMÁTICO
                        for(Tag tag: exercicio.getTags()){ // PARA CADA TAG CONTIDA EM EXERCICIO
                            // CONCATENA O SQL
                            resultado = consultar(tag); // VERIFICAR SE JÁ EXISTE NO BANCO
                            if(resultado.getEntidades().isEmpty()){  // NÃO EXISTE
                                    sql = new StringBuilder();
                                    sql.append("INSERT INTO ");
                                    sql.append(table);
                                    // VARIÁVEIS DA TABELA
                                    sql.append("(nome)");
                                    sql.append(" VALUES(?);");
                              
                                // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                                pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                                // ADICIONA OS ATRIBUTOS REFERENCIANDO AS INTERROGAÇÕES DO "VALUES" DO SQL
                                pst.setString(1, tag.getNome().toLowerCase());
                                // EXECUTA O SQL (SEM SALVAR)
                                tagAux = tag;
                                tagAux.setId(executarSQL(pst));
                            }else{ // EXISTE
                                tagAux = (Tag) resultado.getEntidades().get(0);
                                resultado.zerar();
                            }
                            listaTags.add(tagAux);
                            exercicio.setTags(listaTags);    
                        }
                        // TABELA DO JOIN
                        for(Tag t: exercicio.getTags()){
                            sql = new StringBuilder();
                            sql.append("INSERT INTO ");
                            sql.append(" tbExerciciosTags ");
                            // VARIÁVEIS DA TABELA
                            sql.append("(exercicio_id, tag_id)");
                            sql.append(" VALUES(?, ?);");
                            // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                            pst = connection.prepareStatement(sql.toString());
                            // ADICIONA OS ATRIBUTOS REFERENCIANDO AS INTERROGAÇÕES DO "VALUES" DO SQL
                            pst.setInt(1, exercicio.getId());
                            pst.setInt(2, t.getId());
                            // EXECUTA O SQL (SEM SALVAR)
                            executarSQL(pst);
                        }
		}catch(SQLException e){
                        // MOSTRE O ERRO
			e.printStackTrace();			
		}
                // RETORNA O RESULTADO
		return resultado;
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade){
		// DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
                ctrlTransaction = false;
		Resultado resultado;
                resultado = excluir(entidade);
                resultado = salvar(entidade);
                ctrlTransaction = false;
                // RETORNA O RESULTADO
		return resultado;
	}
	

	@Override
	public Resultado consultar(EntidadeDominio entidade){
		// DEFINIR AS VARIÁVEIS A SEREM USADAS
                Resultado resultado = Resultado.getResultado();
                PreparedStatement pst = null;
		StringBuilder sql = null;
                Tag tag = null;
                Exercicio exercicio = null;
		if(entidade instanceof Tag)
                    tag = (Tag) entidade;   
                if(entidade instanceof Exercicio)
                    exercicio = (Exercicio) entidade;
                try {	
			// ABRE A CONEXÃO COM O BANCO DE DADOS
			openConnection();
			sql = new StringBuilder();
                        if(tag != null){ // VERIFICA SE O ATRIBUTO ESTÁ NULO
                            sql.append("SELECT * FROM ");
                            sql.append(table);
                            if(tag.getNome() != null){
                                sql.append(" WHERE nome = '");
                                sql.append(tag.getNome().toLowerCase());
                                sql.append("'");
                            }
                        }else if(exercicio != null && exercicio.getId() != 0){ // VERIFICA SE O ATRIBUTO ESTÁ NULO
                                sql.append("SELECT * FROM ");
                                sql.append("tbExerciciosTags, ");
                                sql.append("tbtags ");
                                sql.append("WHERE ");
                                sql.append("tbexerciciostags.tag_id = tbtags.id AND ");
                                sql.append("tbexerciciostags.exercicio_id = ");
                                sql.append(exercicio.getId());
                        }
                        sql.append(";");
                        pst = connection.prepareStatement(sql.toString());
                        ResultSet rs = pst.executeQuery();
			while (rs.next()) {
                            tag = new Tag();
                            // ATRIBUI O VALOR DAS COLUNAS REFERENCIADAS
                            tag.setNome(rs.getString("nome"));
                            tag.setId(rs.getInt("id"));
                            // ADICIONA PARA O RESULTADO O RETORNO
                            resultado.setEntidade(tag);
                        }
                } catch (SQLException e) {
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
				if(ctrlTransaction) // SE A TRANSAÇÃO TERMINOU
					connection.close(); // FECHA A CONEXÃO
			} catch (SQLException e) {
                                //SE DER ERRO, MOSTRE
				e.printStackTrace();
			}
		}
		return resultado;
	}
        
        public Resultado excluir(EntidadeDominio entidade){
                openConnection();
                StringBuilder sql = new StringBuilder();
                Resultado resultado = Resultado.getResultado(); 
                PreparedStatement pst=null;	
                if(entidade instanceof Tag){
                        sql.append("DELETE FROM ");
                        sql.append(" tbExerciciosTags");
                        sql.append(" WHERE ");
                        sql.append(" tag_id ");
                        sql.append(" = ");
                        sql.append(entidade.getId());	
                        sql.append(";");
                }else if(entidade instanceof Exercicio){
                        Exercicio exercicio = (Exercicio) entidade;
                        sql.append("DELETE FROM ");
                        sql.append(" tbExerciciosTags");
                        sql.append(" WHERE ");
                        sql.append(" exercicio_id ");
                        sql.append(" = ");
                        sql.append(exercicio.getId());	
                        sql.append(";");
                }else
                    return super.excluir(entidade);
		try {
			connection.setAutoCommit(false);
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
                                if(pst != null)
				pst.close();
				if(ctrlTransaction)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultado;
        }
}