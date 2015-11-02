package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.model.EntidadeDominio;
import exesis.model.Pessoa;
import exesis.model.Professor;
import exesis.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Professor")
public class ProfessorDAO extends AbstractJdbcDAO{

    public ProfessorDAO() {
        super("tbProfessores", "id");
    }

    @Override
    public Resultado salvar(EntidadeDominio entidade) {
        // DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst = null;
        StringBuilder sql = null;
        IDAO dao = null;
        // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
        Professor professor = (Professor) entidade;
        try {
                // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                openConnection();
                connection.setAutoCommit(false);
                ctrlTransaction = false;
                dao = new UsuarioDAO();
                resultado = dao.salvar(professor.getUsuario());
                if(!resultado.getEntidades().isEmpty())
                    professor.setUsuario((Usuario) resultado.getEntidades().get(0));
                // CONCATENA O SQL
                sql = new StringBuilder();
                sql.append("INSERT INTO ");
                sql.append(table);
                // VARIÁVEIS DA TABELA
                sql.append("(nome, sobrenome, dtCadastro, dtNascimento, sexo, telefone, informacoesAdicionais, usuario_id)");
                sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
                // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                pst = connection.prepareStatement(sql.toString());
                // ADICIONA OS ATRIBUTOS REFERENCIANDO AS INTERROGAÇÕES DO "VALUES" DO SQL
                pst.setString(1, professor.getNome());
                pst.setString(2, professor.getSobrenome());
                Timestamp time = new Timestamp(professor.getDtCadastro().getTime());
                pst.setTimestamp(3, time);
                pst.setString(4, professor.getDataNascimento());
                pst.setString(5, professor.getSexo());
                pst.setString(6, professor.getTelefone());
                pst.setString(7, professor.getInformacoesAdicionais());
                pst.setInt(8, professor.getUsuario().getId());
                // EXECUTA O SQL (SEM SALVAR)
                pst.executeUpdate();
                resultado.zerar();
                resultado.setEntidade(professor);
                // SALVAR AS ALTERAÇÕES (MAS AINDA PODE FAZER UM ROLLBACK
                connection.commit();
                // AFIRMA QUE A CONEXÃO PODE SER FECHADA, POIS A TRANSAÇÃO TERMINOU
                ctrlTransaction=true;
                return resultado;
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
                        pst.close();
                        if(ctrlTransaction) // SE A TRANSAÇÃO TERMINOU
                                connection.close(); // FECHA A CONEXÃO
                } catch (SQLException e) {
                        //SE DER ERRO, MOSTRE
                        e.printStackTrace();
                }
        }
        // RETORNA O RESULTADO
        return resultado;
    }

    @Override
    public Resultado alterar(EntidadeDominio entidade) {
        // DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst = null;
        StringBuilder sql = null;
        IDAO dao = null;
        // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
        Professor professor = (Professor) entidade;
        try {
                // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                openConnection();
                connection.setAutoCommit(false);
                ctrlTransaction = false;
                dao = new UsuarioDAO();
                resultado = dao.alterar(professor.getUsuario());
                // CONCATENA O SQL
                sql = new StringBuilder();
                sql.append("UPDATE ");
                sql.append(table);
                sql.append(" SET ");
                // VARIÁVEIS DA TABELA
                sql.append(" nome = ?, sobrenome = ?, dtNascimento = ?, sexo = ?, telefone = ?, informacoesadicionais = ? ");
                sql.append(" WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(professor.getId());
                sql.append(";");
                // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                pst = connection.prepareStatement(sql.toString());
                pst.setString(1, professor.getNome());
                pst.setString(2, professor.getSobrenome());
                pst.setString(3, professor.getDataNascimento());
                pst.setString(4, professor.getSexo());
                pst.setString(5, professor.getTelefone());
                pst.setString(6, professor.getInformacoesAdicionais());
                // EXECUTA O SQL (SEM SALVAR)
                pst.executeUpdate();
                // SALVAR AS ALTERAÇÕES (MAS AINDA PODE FAZER UM ROLLBACK
                connection.commit();
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
                        pst.close();
                        if(ctrlTransaction) // SE A TRANSAÇÃO TERMINOU
                                connection.close(); // FECHA A CONEXÃO
                } catch (SQLException e) {
                        //SE DER ERRO, MOSTRE
                        e.printStackTrace();
                }
        }
        // RETORNA O RESULTADO
        return resultado;
    }

    @Override
    public Resultado consultar(EntidadeDominio entidade) {
            // DECLARAÇÃO DAS VARIÁVEIS
            openConnection();
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            StringBuilder sql = new StringBuilder();
            Professor professor  = (Professor) entidade;
            Usuario usuario = null;
                sql.append("SELECT ");
                sql.append("tbprofessores.id, nome, sobrenome, dtCadastro, dtNascimento, sexo, telefone, informacoesadicionais, login, email, usuario_id, perfilacesso ");
                sql.append("FROM ");                
                sql.append("tbprofessores, ");
                sql.append("tbusuarios ");
                sql.append(" WHERE ");
                sql.append(" usuario_id ");
                sql.append(" = ");
                sql.append(" tbusuarios.id");
                if(professor.getId() != 0){
                    sql.append(" AND ");
                    sql.append("tbprofessores.id");
                    sql.append(" = ");
                    sql.append(professor.getId());
                    sql.append(";");
                }else if(professor.getUsuario() != null && 
                        professor.getUsuario().getLogin() != null &&
                        !professor.getUsuario().getLogin().isEmpty()){      
                    sql.append(" login = '");
                    sql.append(professor.getUsuario().getLogin());
                    sql.append("'; ");
                }else{
                    sql.append("; ");
                }
  		try {
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
                        resultado.zerar();
                        while (rs.next()) {
                            usuario = new Usuario();
                            usuario.setId(rs.getInt("usuario_id"));
                            usuario.setEmail(rs.getString("email"));
                            usuario.setLogin(rs.getString("login"));
                            usuario.setPerfilAcesso(rs.getInt("perfilAcesso"));
                            professor = new Professor();
                            professor.setUsuario(usuario);
                            professor.setId(rs.getInt("id"));
                            professor.setNome(rs.getString("nome"));
                            professor.setSobrenome(rs.getString("sobrenome"));
                            professor.setDataNascimento(rs.getString("dtNascimento"));
                            professor.setDtCadastro(rs.getTimestamp("dtCadastro"));
                            professor.setTelefone(rs.getString("telefone"));
                            professor.setSexo(rs.getString("sexo"));
                            professor.setInformacoesAdicionais(rs.getString("informacoesAdicionais"));
                            resultado.setEntidade(professor);
                        }
                } catch (SQLException e) {
                 // MOSTRE O ERRO
                e.printStackTrace();			
                }finally{// FAÇA INDEPENDENTE DO QUE ACONTECER
                    try {// TENTE
                        // FECHAR O PREPARESATEMENT
                        
                        pst.close();
                        if(ctrlTransaction) // SE A TRANSAÇÃO TERMINOU
                                connection.close(); // FECHA A CONEXÃO
                    } catch (SQLException e) {
                            //SE DER ERRO, MOSTRE
                            e.printStackTrace();
                    }
                }
                return resultado;
    }
    
    
    @Override
    public Resultado excluir(EntidadeDominio entidade) {
        Pessoa pessoa = (Pessoa) entidade;
        return new UsuarioDAO().excluir(pessoa.getUsuario()); //To change body of generated methods, choose Tools | Templates.
    }
}

