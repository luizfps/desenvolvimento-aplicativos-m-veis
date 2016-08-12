package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.RegistroDeTemperatura;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class RegistroDeTemperaturaDao {
	
	private Connection connection;

	public RegistroDeTemperaturaDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(RegistroDeTemperatura registroDeTemperatura,
			Long sensorCodigo) throws SQLException {
		String sql = "insert into registroDeTemperatura "
				+ "(temperatura, momento, sensor_codigo) "
				+ " values (?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			Util.setFloatPreparedStatement(stmt, 1, registroDeTemperatura
					.getTemperatura());
			Util.setDatePreparedStatement(stmt, 2, registroDeTemperatura.getMomento());
			stmt.setLong(3, sensorCodigo);
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	public List<RegistroDeTemperatura> busca(Long sensorCodigo) throws SQLException {
		try {
			List<RegistroDeTemperatura> registroDeTemperaturas = new ArrayList<>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from registroDeTemperatura "
							+ "where sensor_codigo=?");
			stmt.setLong(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// criando o objeto registroDeTemperatura
				RegistroDeTemperatura registroDeTemperatura = new RegistroDeTemperatura();
				registroDeTemperatura.setTemperatura(Util
						.getResultSetValueFloat(rs, "temperatura"));
				registroDeTemperatura.setMomento(rs.getDate("momento"));
				// adicionando o objeto à lista
				registroDeTemperaturas.add(registroDeTemperatura);
			}
			rs.close();
			stmt.close();
			return registroDeTemperaturas;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	public RegistroDeTemperatura getUltimoRegistroDeTemperatura(Long sensorCodigo) 
			throws SQLException {
		try {
			RegistroDeTemperatura registroDeTemperatura = null;
			PreparedStatement stmt = this.connection
					.prepareStatement("select from registroDeTemperatura "
							+ "where sensor_codigo=?");
			stmt.setLong(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				// criando o objeto registroDeTemperatura
				registroDeTemperatura = new RegistroDeTemperatura();
				registroDeTemperatura.setTemperatura(Util
						.getResultSetValueFloat(rs, "temperatura"));
				registroDeTemperatura.setMomento(rs.getDate("momento"));
			}
			rs.close();
			stmt.close();
			return registroDeTemperatura;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	public void remove(Long sensorCodigo) throws SQLException {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("delete from registroDeTemperatura "
							+ "where sensor_codigo=?");
			stmt.setLong(1, sensorCodigo);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}

}
