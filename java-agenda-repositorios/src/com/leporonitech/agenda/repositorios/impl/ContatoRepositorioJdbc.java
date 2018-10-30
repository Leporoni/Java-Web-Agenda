package com.leporonitech.agenda.repositorios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.leporonitech.repositorios.interfaces.AgendaRepositorio;

import com.leporonitech.agenda.entidades.Contato;

public class ContatoRepositorioJdbc implements AgendaRepositorio<Contato> {

	@Override
	public List<Contato> selecionar() throws SQLException, IOException {
		/*
		 * List<Contato> contatos = new ArrayList<Contato>(); try (Connection conexao =
		 * FabricaConexaoJdbc.criarConexao()) { Statement comando =
		 * conexao.createStatement(); ResultSet rs =
		 * comando.executeQuery("SELECT * FROM contatos"); while (rs.next()) { Contato
		 * contato = new Contato(); contato.setId(rs.getInt("id"));
		 * contato.setIdade(rs.getInt("idade")); contato.setNome(rs.getString("nome"));
		 * contato.setTelefone(rs.getString("telefone")); contatos.add(contato); } }
		 * return contatos;
		 */

		Connection conexao = null;
		List<Contato> contatos = new ArrayList<Contato>();
		try {
			Properties props = new Properties();
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties");
			if (is == null) {
				throw new FileNotFoundException("O arquivo de configuração do banco de dados não foi encontrado");
			}
			props.load(is);
			conexao = DriverManager.getConnection(props.getProperty("urlConnection"),
					props.getProperty("userConnection"), props.getProperty("passConnection"));
			Statement comando = conexao.createStatement();
			ResultSet rs = comando.executeQuery("SELECT * FROM contatos");
			while (rs.next()) {
				Contato contato = new Contato();
				contato.setId(rs.getInt("id"));
				contato.setIdade(rs.getInt("idade"));
				contato.setNome(rs.getString("nome"));
				contato.setTelefone(rs.getString("telefone"));
				contatos.add(contato);
			}
		} finally {
			if (conexao != null) {
				conexao.close();
			}
		}
		return contatos;
	}

	@Override
	public void inserir(Contato entidade) throws SQLException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizar(Contato entidade) throws IOException, SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Contato entidade) throws IOException, SQLException {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public void inserir(Contato entidade) throws IOException,
	 * SQLException { Connection conexao = null; try { conexao =
	 * FabricaConexaoJdbc.criarConexao(); PreparedStatement comando =
	 * conexao.prepareStatement("INSERT INTO contatos (nome, idade, telefone) " +
	 * " VALUES (?, ?, ?)"); comando.setString(1, entidade.getNome());
	 * comando.setInt(2, entidade.getIdade()); comando.setString(3,
	 * entidade.getTelefone()); comando.execute(); } finally { if (conexao != null)
	 * { conexao.close(); } }
	 * 
	 * }
	 * 
	 * @Override public void atualizar(Contato entidade) throws IOException,
	 * SQLException { try (Connection conexao = FabricaConexaoJdbc.criarConexao()){
	 * PreparedStatement comando = conexao.
	 * prepareStatement("UPDATE contatos SET nome = ?, idade = ?, telefone = ? "+
	 * " WHERE id = ?"); comando.setString(1, entidade.getNome()); comando.setInt(2,
	 * entidade.getIdade()); comando.setString(3, entidade.getTelefone());
	 * comando.setInt(4, entidade.getId()); comando.execute(); }
	 * 
	 * }
	 * 
	 * @Override public void excluir(Contato entidade) throws IOException,
	 * SQLException { try (Connection conexao = FabricaConexaoJdbc.criarConexao()){
	 * PreparedStatement comando =
	 * conexao.prepareStatement("DELETE FROM contatos WHERE id = ?");
	 * comando.setInt(1, entidade.getId()); comando.execute(); }
	 * 
	 * }
	 */

}
