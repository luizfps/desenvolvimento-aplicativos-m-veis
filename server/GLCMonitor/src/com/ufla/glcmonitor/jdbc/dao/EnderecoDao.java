package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufla.glcmonitor.jdbc.modelo.Endereco;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

/**
 * A classe EnderecoDao representa objetos de acesso à dados para manipular
 * endereços. Fornece métodos de adição, busca, e alteração de endereços.
 * 
 * @author glcmonitor
 *
 */
public class EnderecoDao {

	/**
	 * Adiciona um endereço de um usuário no banco de dados.
	 * 
	 * @param endereco
	 *            endereço adicionado.
	 * @param loginUsuario
	 *            login do usuário relacionado ao endereço.
	 * @throws SQLException
	 *             exceção relacionada a problemas em adicionar endereço no BD.
	 */
	protected void adiciona(Endereco endereco, String loginUsuario) throws SQLException {
		String sql = "insert into endereco " + "(logradouro,numero,complemento,bairro,"
				+ "cidade,estado,cep,usuario_login)" + " values (?,?,?,?,?,?,?,?)";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, endereco.getLogradouro());
			Util.setIntegerPreparedStatement(stmt, 2, endereco.getNumero());
			stmt.setString(3, endereco.getComplemento());
			stmt.setString(4, endereco.getBairro());
			stmt.setString(5, endereco.getCidade());
			stmt.setString(6, endereco.getEstado());
			Util.setLongPreparedStatement(stmt, 7, endereco.getCep());
			stmt.setString(8, loginUsuario);
			stmt.execute();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao.getMensagemDeExcecao(e.getMessage()));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	/**
	 * Busca o endereço de um usuário no banco de dados.
	 * 
	 * @param usuarioLogin
	 *            login do usuário relacionado ao endereço.
	 * @return endereço do usuário.
	 * @throws SQLException
	 *             exceção relacionada a problemas em buscar endereço no BD.
	 */
	protected Endereco busca(String usuarioLogin) throws SQLException {
		String sql = "select * from endereco " + "where usuario_login=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuarioLogin);
			ResultSet rs = stmt.executeQuery();
			Endereco endereco = null;
			if (rs.next()) {
				endereco = new Endereco();
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCep(Util.getResultSetValueLong(rs, "cep"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setComplemento(rs.getString("complemento"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setNumero(Util.getResultSetValueInteger(rs, "numero"));
			}
			rs.close();
			return endereco;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao.getMensagemDeExcecao(e.getMessage()));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	/**
	 * Altera o endereço de um usuário no banco de dados.
	 * 
	 * @param endereco
	 *            novo endereço.
	 * @param loginUsuario
	 *            login do usuário relacionado ao endereço.
	 * @throws SQLException
	 *             exceção relacionada a problemas em alterar endereço no BD.
	 */
	protected void altera(Endereco endereco, String loginUsuario) throws SQLException {
		String sql = "update endereco set bairro=?, cep=?, cidade=?,"
				+ "complemento=?, estado=?, logradouro=?, numero=? where usuario_login=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, endereco.getBairro());
			Util.setLongPreparedStatement(stmt, 2, endereco.getCep());
			stmt.setString(3, endereco.getCidade());
			stmt.setString(4, endereco.getComplemento());
			stmt.setString(5, endereco.getEstado());
			stmt.setString(6, endereco.getLogradouro());
			Util.setIntegerPreparedStatement(stmt, 7, endereco.getNumero());
			stmt.setString(8, loginUsuario);
			stmt.execute();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao.getMensagemDeExcecao(e.getMessage()));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	protected void remove(String loginUsuario) throws SQLException {
		String sql = "delete from endereco " + "where usuario_login=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, loginUsuario);
			stmt.execute();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao.getMensagemDeExcecao(e.getMessage()));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

}
