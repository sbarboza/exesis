package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.model.EntidadeDominio;
import exesis.model.Aluno;
import exesis.model.Pessoa;
import exesis.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Aluno")
public class AlunoDAO extends AbstractJdbcDAO{

    public AlunoDAO() {
        super("tbAlunos", "id");
    }
    public AlunoDAO(Connection connection) {
        super(connection, "tbAlunos", "id");
    }

    @Override
    public Resultado salvar(EntidadeDominio entidade) {
        // DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst = null;
        StringBuilder sql = null;
        IDAO dao = null;
        // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
        Aluno aluno = (Aluno) entidade;
        try {
                // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                openConnection();
                connection.setAutoCommit(false);
                ctrlTransaction = false;
                dao = new UsuarioDAO();
                resultado = dao.salvar(aluno.getUsuario());
                if(!resultado.getEntidades().isEmpty())
                    aluno.setUsuario((Usuario) resultado.getEntidades().get(0));
                // CONCATENA O SQL
                sql = new StringBuilder();
                sql.append("INSERT INTO ");
                sql.append(table);
                // VARIÁVEIS DA TABELA
                sql.append("(nome, sobrenome, dtCadastro, dtNascimento, sexo, telefone, informacoesAdicionais, usuario_id, matricula)");
                sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                pst = connection.prepareStatement(sql.toString());
                // ADICIONA OS ATRIBUTOS REFERENCIANDO AS INTERROGAÇÕES DO "VALUES" DO SQL
                pst.setString(1, aluno.getNome());
                pst.setString(2, aluno.getSobrenome());
                Timestamp time = new Timestamp(aluno.getDtCadastro().getTime());
                pst.setTimestamp(3, time);
                pst.setString(4, aluno.getDataNascimento());
                pst.setString(5, aluno.getSexo());
                pst.setString(6, aluno.getTelefone());
                pst.setString(7, aluno.getInformacoesAdicionais());
                pst.setInt(8, aluno.getUsuario().getId());
                pst.setString(9, aluno.getMatricula());
                // EXECUTA O SQL (SEM SALVAR)
                pst.executeUpdate();
                resultado.zerar();
                resultado.setEntidade(aluno);
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
        Aluno aluno = (Aluno) entidade;
        try {
                // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                openConnection();
                connection.setAutoCommit(false);
                ctrlTransaction = false;
                dao = new UsuarioDAO();
                resultado = dao.alterar(aluno.getUsuario());
                // CONCATENA O SQL
                sql = new StringBuilder();
                sql.append("UPDATE ");
                sql.append(table);
                sql.append(" SET ");
                // VARIÁVEIS DA TABELA
                sql.append(" nome = ?, sobrenome = ?, dtNascimento = ?, sexo = ?, telefone = ?, informacoesadicionais = ?, matricula = ? ");
                sql.append(" WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(aluno.getId());
                sql.append(";");
                // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                pst = connection.prepareStatement(sql.toString());
                pst.setString(1, aluno.getNome());
                pst.setString(2, aluno.getSobrenome());
                pst.setString(3, aluno.getDataNascimento());
                pst.setString(4, aluno.getSexo());
                pst.setString(5, aluno.getTelefone());
                pst.setString(6, aluno.getInformacoesAdicionais());
                pst.setString(7, aluno.getMatricula());
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
            /*
            SELECT 
            tbalunos.id, nome, sobrenome, sexo, telefone, informacoesadicionais, login, email, idusuario, perfilacesso 
            FROM 
            tbalunos, 
            tbusuarios
          WHERE 
            idusuario = id AND
            id = 1;
            */
            openConnection();
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            StringBuilder sql = new StringBuilder();
            Aluno aluno  = (Aluno) entidade;
            Usuario usuario = null;
                sql.append("SELECT ");
                sql.append("tbalunos.id ,nome, sobrenome, matricula, dtCadastro, dtNascimento, sexo, telefone, informacoesadicionais, login, email, usuario_id, perfilacesso ");
                sql.append("FROM ");                
                sql.append("tbalunos, ");
                sql.append("tbusuarios ");
                if(aluno.getId() != 0){
                sql.append(" WHERE ");
                sql.append("tbalunos.id");
                sql.append(" = ");
                sql.append(aluno.getId());
                sql.append(" AND ");
                sql.append(" usuario_id ");
                sql.append(" = ");
                sql.append(" tbusuarios.id");
                sql.append(";");
                }else if(aluno.getUsuario() != null && 
                        aluno.getUsuario().getLogin() != null &&
                        !aluno.getUsuario().getLogin().isEmpty()){
                sql.append(" WHERE ");
                sql.append(" login = '");
                sql.append(aluno.getUsuario().getLogin());
                sql.append("'; ");
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
                            aluno = new Aluno();
                            aluno.setUsuario(usuario);
                            aluno.setId(rs.getInt("id"));
                            aluno.setNome(rs.getString("nome"));
                            aluno.setSobrenome(rs.getString("sobrenome"));
                            aluno.setDataNascimento(rs.getString("dtNascimento"));
                            aluno.setDtCadastro(rs.getTimestamp("dtCadastro"));
                            aluno.setTelefone(rs.getString("telefone"));
                            aluno.setSexo(rs.getString("sexo"));
                            aluno.setInformacoesAdicionais(rs.getString("informacoesAdicionais"));
                            aluno.setMatricula(rs.getString("matricula"));
                            resultado.setEntidade(aluno);
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

