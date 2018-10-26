package com.leporonitech.agenda.fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.leporonitech.agenda.entidades.Contato;
import com.leporonitech.agenda.repositorios.impl.ContatoRepositorio;
import com.leporonitech.repositorios.interfaces.AgendaRepositorio;

public class MainController implements Initializable{

	@FXML
	private TableView<Contato> tabelaContatos;
	
	@FXML
	private Button buttonIncluir;
	
	@FXML
	private Button buttonAlterar;

	@FXML
	private Button buttonExcluir;
	
	@FXML
	private TextField txfNome;
	
	@FXML
	private TextField txfIdade;
	
	@FXML
	private TextField txfTelefone;
	
	@FXML
	private Button buttonSalvar;
	
	@FXML
	private Button buttonCancelar;	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.tabelaContatos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		try {
			carregarTabelaContatos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
	private void carregarTabelaContatos() throws SQLException, IOException {
		AgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
		List<Contato> contatos = repositorioContato.selecionar();
		if(contatos.isEmpty()) {
			Contato contato = new Contato();
			contato.setNome("Alex Leporoni");
			contato.setIdade(43);
			contato.setTelefone("5516981844976");
			contatos.add(contato);
		}
		ObservableList<Contato> contatoObservalbleList = FXCollections.observableArrayList(contatos);
		this.tabelaContatos.getItems().setAll(contatoObservalbleList);
	}

}
