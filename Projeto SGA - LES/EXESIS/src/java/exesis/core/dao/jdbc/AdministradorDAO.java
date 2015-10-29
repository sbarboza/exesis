package exesis.core.dao.jdbc;

import exesis.core.aplicacao.Resultado;
import exesis.core.dao.IDAO;
import exesis.model.Administrador;
import exesis.model.EntidadeDominio;
import exesis.model.Pessoa;
import exesis.model.Usuario;
import exesis.teste.jdbc.TesteAdministrador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.stereotype.Component;

@Component(value = "exesis.model.Administrador")
public class AdministradorDAO extends AbstractJdbcDAO{

    public AdministradorDAO() {
        super("tbAdministradores", "id");
    }

    @Override
    public Resultado salvar(EntidadeDominio entidade) {
        // DEFINIR ATRIBUTOS A SEREM USADOS NESTE MÉTODO
        Resultado resultado = Resultado.getResultado();
        PreparedStatement pst = null;
        StringBuilder sql = null;
        IDAO dao = null;
        // RECEBE A INSTÂNCIA DA ENTIDADE FAZENDO O CAST PARA A CLASSE
        Administrador administrador = (Administrador) entidade;
        try {
                // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                openConnection();
                connection.setAutoCommit(false);
                ctrlTransaction = false;
                dao = new UsuarioDAO();
                resultado = dao.salvar(administrador.getUsuario());
                if(!resultado.getEntidades().isEmpty())
                    administrador.setUsuario((Usuario) resultado.getEntidades().get(0));
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
                pst.setString(1, administrador.getNome());
                pst.setString(2, administrador.getSobrenome());
                Timestamp time = new Timestamp(administrador.getDtCadastro().getTime());
                pst.setTimestamp(3, time);
                pst.setString(4, administrador.getDataNascimento());
                pst.setString(5, administrador.getSexo());
                pst.setString(6, administrador.getTelefone());
                pst.setString(7, administrador.getInformacoesAdicionais());
                pst.setInt(8, administrador.getUsuario().getId());
                // EXECUTA O SQL (SEM SALVAR)
                pst.executeUpdate();
                resultado.zerar();
                resultado.setEntidade(administrador);
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
        Administrador administrador = (Administrador) entidade;
        try {
                // ABRIR UMA CONEXÃO COM O BANCO DE DADOS
                openConnection();
                connection.setAutoCommit(false);
                ctrlTransaction = false;
                dao = new UsuarioDAO();
                resultado = dao.alterar(administrador.getUsuario());
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
                sql.append(administrador.getId());
                sql.append(";");
                // PREPARA O SQL PARA RETORNAR A CHAVE GERADA
                pst = connection.prepareStatement(sql.toString());
                pst.setString(1, administrador.getNome());
                pst.setString(2, administrador.getSobrenome());
                pst.setString(3, administrador.getDataNascimento());
                pst.setString(4, administrador.getSexo());
                pst.setString(5, administrador.getTelefone());
                pst.setString(6, administrador.getInformacoesAdicionais());
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
    public Resultado excluir(EntidadeDominio entidade) {
        Pessoa pessoa = (Pessoa) entidade;
        return new UsuarioDAO().excluir(pessoa.getUsuario()); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public Resultado consultar(EntidadeDominio entidade) {
            // DECLARAÇÃO DAS VARIÁVEIS
            /*
            SELECT 
            id,nome, sobrenome, sexo, telefone, informacoesadicionais, login, email, idusuario, perfilacesso 
            FROM 
            tbadministradores, 
            tbusuarios
          WHERE 
            idusuario = id AND
            id = 1;
            */
            openConnection();
            Resultado resultado = Resultado.getResultado();
            PreparedStatement pst = null;
            StringBuilder sql = new StringBuilder();
            Administrador administrador = (Administrador) entidade;
            Usuario usuario = null;
                sql.append("SELECT ");
                sql.append("tbadministradores.id ,nome, sobrenome, dtCadastro, dtNascimento, sexo, telefone, informacoesadicionais, login, email, usuario_id, perfilacesso ");
                sql.append("FROM ");                
                sql.append("tbadministradores, ");
                sql.append("tbusuarios ");
                if(administrador.getId() != 0){
                sql.append(" WHERE ");
                sql.append("tbadministradores.id");
                sql.append(" = ");
                sql.append(administrador.getId());
                sql.append(" AND ");
                sql.append(" usuario_id ");
                sql.append(" = ");
                sql.append(" tbusuarios.id");
                sql.append(";");
                }else if(administrador.getUsuario() != null && 
                        administrador.getUsuario().getLogin() != null &&
                        !administrador.getUsuario().getLogin().isEmpty()){
                sql.append(" WHERE ");
                sql.append(" login = '");
                sql.append(administrador.getUsuario().getLogin());
                sql.append("'; ");
                }
                resultado.zerar();
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
                            administrador = new Administrador();
                            administrador.setUsuario(usuario);
                            administrador.setId(rs.getInt("id"));
                            administrador.setNome(rs.getString("nome"));
                            administrador.setSobrenome(rs.getString("sobrenome"));
                            administrador.setDataNascimento(rs.getString("dtNascimento"));
                            administrador.setDtCadastro(rs.getTimestamp("dtCadastro"));
                            administrador.setTelefone(rs.getString("telefone"));
                            administrador.setSexo(rs.getString("sexo"));
                            administrador.setInformacoesAdicionais(rs.getString("informacoesAdicionais"));
                            resultado.setEntidade(administrador);
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
}
