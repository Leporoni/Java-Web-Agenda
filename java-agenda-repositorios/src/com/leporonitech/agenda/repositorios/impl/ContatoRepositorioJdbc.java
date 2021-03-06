package com.leporonitech.agenda.repositorios.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.leporonitech.agenda.entidades.Contato;
import com.leporonitech.agenda.fabricas.FabricaConexaoJdbc;
import com.leporonitech.repositorios.interfaces.AgendaRepositorio;

public class ContatoRepositorioJdbc implements AgendaRepositorio<Contato> {

	@Override
	public List<Contato> selecionar() throws SQLException, IOException {

		List<Contato> contatos = new ArrayList<Contato>();
		try (Connection conexao = FabricaConexaoJdbc.criarConexao()) {
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
		}
		return contatos;
	}

	@Override
	public void inserir(Contato entidade) throws SQLException, IOException {
		Connection conexao = null;
		try {
			conexao = FabricaConexaoJdbc.criarConexao();
			PreparedStatement comando = conexao.prepareStatement("INSERT INTO contatos (nome, idade, telefone) " + " VALUES (?, ?, ?)");
			comando.setString(1, entidade.getNome());
			comando.setInt(2, entidade.getIdade());
			comando.setString(3, entidade.getTelefone());
			comando.execute();
		} finally {
			if (conexao != null) {
				conexao.close();
			}
		}
	}

	@Override
	public void atualizar(Contato entidade) throws IOException, SQLException {
		Connection conexao = null;
		try {
			conexao = FabricaConexaoJdbc.criarConexao();
			PreparedStatement comando = conexao.prepareStatement("UPDATE contatos SET nome=?, idade=?, telefone=?");
			comando.setString(1, entidade.getNome());
			comando.setInt(2, entidade.getIdade());
			comando.setString(3, entidade.getTelefone());
			comando.execute();
		} finally {
			if (conexao != null) {
				conexao.close();
			}
		}
	}

	@Override
	public void excluir(Contato entidade) throws IOException, SQLException {
		Connection conexao = null;
		try {
			conexao = FabricaConexaoJdbc.criarConexao();
			PreparedStatement comando = conexao.prepareStatement("DELETE FROM contatos (nome, idade, telefone)");
			comando.setString(1, entidade.getNome());
			comando.setInt(2, entidade.getIdade());
			comando.setString(3, entidade.getTelefone());
			comando.execute();
		} finally {
			if (conexao != null) {
				conexao.close();
			}
		}
	}
}
