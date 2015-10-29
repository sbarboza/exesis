package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.model.EntidadeDominio;
import exesis.model.Pessoa;
import exesis.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Usuario")
public class UsuarioDAO extends AbstractJdbcDAO{

    public UsuarioDAO() {
        super("tbUsuarios", "id");
    }

    @Override
    public Resultado salvar(EntidadeDominio entidade) {
        // DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst = null;
        StringBuilder sql = null;
        // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
        Usuario usuario = (Usuario) entidade;
        try {
                // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                openConnection();
                connection.setAutoCommit(false);
                // CONCATENA O SQL
                sql = new StringBuilder();
                sql.append("INSERT INTO ");
                sql.append(table);
                // VARIÁVEIS DA TABELA
                sql.append("(email, login, senha, perfilAcesso)");
                sql.append(" VALUES(?, ?, md5(?), ?)");
                // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                // ADICIONA OS ATRIBUTOS REFERENCIANDO AS INTERROGAÇÕES DO "VALUES" DO SQL
                pst.setString(1, usuario.getEmail());
                pst.setString(2, usuario.getLogin());
                pst.setString(3, usuario.getSenha());
                pst.setInt(4, usuario.getPerfilAcesso());
                // EXECUTA O SQL (SEM SALVAR)
                pst.executeUpdate();
                // PEGA A CHAVE GERADA NA GRAVAÇÃO DOS DADOS
                ResultSet rs = pst.getGeneratedKeys();
                // VERIFICA SE TEM RETORNO
                if(rs != null && rs.next()){
                        // GRAVA O IDENTIFICADOR NA CLASSE, RECUPERANDO PELA REFERÊNCIA DO NOME DA TABELA (idTable)
                        usuario.setId(rs.getInt(idTable));
                }
                resultado.zerar();
                resultado.setEntidade(usuario);
                // SALVAR AS ALTERAÇÕES (MAS AINDA PODE FAZER UM ROLLBACK
                connection.commit();
                // AFIRMA QUE A CONEXÃO PODE SER FECHADA, POIS A TRANSAÇÃO TERMINOU
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
        // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
        Usuario usuario = (Usuario) entidade;
        try {
                // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                openConnection();
                connection.setAutoCommit(false);
                // CONCATENA O SQL
                sql = new StringBuilder();
                sql.append("UPDATE ");
                sql.append(table);
                sql.append(" SET ");
                // VARIÁVEIS DA TABELA
                if(usuario.getSenha() != null && !usuario.getSenha().isEmpty()){
                sql.append(" senha = md5('");
                sql.append(usuario.getSenha());
                sql.append("') ");
                }else{
                sql.append(" email = '"); 
                sql.append(usuario.getEmail());
                sql.append("', login = '");
                sql.append(usuario.getLogin());
                sql.append("' ");
                }
                sql.append(" WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(usuario.getId());
                sql.append(";");
                // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                pst = connection.prepareStatement(sql.toString());
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
            Usuario usuario = (Usuario) entidade;
                sql.append("SELECT ");
                sql.append("id, email, login, perfilAcesso ");
                sql.append("FROM ");                
                sql.append(table);
                if(usuario.getId() != 0){
                sql.append(" WHERE ");
                sql.append(idTable);
                sql.append(" = ");
                sql.append(usuario.getId());
                sql.append(";");
                }else if(usuario.getSenha() != null && usuario.getLogin() != null  &&
                        !usuario.getSenha().isEmpty() && !usuario.getLogin().isEmpty()){
                sql.append(" WHERE ");
                sql.append(" login = '");
                sql.append(usuario.getLogin());
                sql.append("' AND ");
                sql.append(" senha = md5('");
                sql.append(usuario.getSenha());
                sql.append("'); ");                
                }else if((usuario.getSenha() == null || usuario.getSenha().isEmpty()) && 
                        (usuario.getLogin() != null  && !usuario.getLogin().isEmpty())){
                sql.append(" WHERE ");
                sql.append(" login = '");
                sql.append(usuario.getLogin());
                sql.append("'; ");                
                }else{
                sql.append(";");    
                }
  		try {
			pst = connection.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
                        resultado.zerar();
                        while (rs.next()) {
                            usuario = new Usuario();
                            usuario.setId(rs.getInt("id"));
                            usuario.setEmail(rs.getString("email"));
                            usuario.setLogin(rs.getString("login"));
                            usuario.setPerfilAcesso(rs.getInt("perfilAcesso"));
                            resultado.setEntidade(usuario);
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
                return resultado;
	}
    }
    

}