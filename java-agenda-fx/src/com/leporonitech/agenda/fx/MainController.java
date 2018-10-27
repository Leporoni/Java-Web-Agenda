package com.leporonitech.agenda.fx;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.leporonitech.agenda.entidades.Contato;
import com.leporonitech.agenda.repositorios.impl.ContatoRepositorio;
import com.leporonitech.repositorios.interfaces.AgendaRepositorio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController implements Initializable {

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

	private Boolean ehInserir;

	private Contato contatoSelecionado;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.tabelaContatos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		habilitarEdicaoAgenda(false);

		// Capturando o elemento selecionado dentro de uma TableView (Modo de fazer com
		// Java Versões Anteriores ao 9)

		/*
		 * this.tabelaContatos.getSelectionModel().selectedItemProperty().addListener(
		 * new ChangeListener<Contato>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Contato> observable,
		 * Contato oldValue, Contato newValue) { if (newValue != null) {
		 * txfNome.setText(newValue.getNome());
		 * txfIdade.setText(String.valueOf(newValue.getIdade()));
		 * txfTelefone.setText(newValue.getTelefone()); } } });
		 */

		// Capturando o elemento selecionado dentro de uma TableView (Modo de fazer com
		// versões pós Java 9 utilizando lambda)

		this.tabelaContatos.getSelectionModel().selectedItemProperty()
				.addListener((observador, contatoAntigo, contatoNovo) -> {
					if (contatoNovo != null) {
						txfNome.setText(contatoNovo.getNome());
						txfIdade.setText(String.valueOf(contatoNovo.getIdade()));
						txfTelefone.setText(contatoNovo.getTelefone());
						this.contatoSelecionado = contatoNovo;
					}
				});

		try {
			carregarTabelaContatos();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buttonInserir_Action() {
		this.ehInserir = true;
		this.txfNome.setText("");
		this.txfIdade.setText("");
		this.txfTelefone.setText("");
		habilitarEdicaoAgenda(true);
	}

	public void buttonAlterar_Action() {
		habilitarEdicaoAgenda(true);
		this.ehInserir = false;
		this.txfNome.setText(this.contatoSelecionado.getNome());
		this.txfIdade.setText(Integer.toString(this.contatoSelecionado.getIdade()));
		this.txfTelefone.setText(this.contatoSelecionado.getTelefone());

	}

	public void buttonExcluir_Action() throws IOException, SQLException {
		Alert confirmacao = new Alert(AlertType.CONFIRMATION);
		confirmacao.setTitle("Confirmação");
		confirmacao.setHeaderText("Confirmação de Exclusão do Contato");
		confirmacao.setContentText("Tem certeza de que deseja excluir este contato?");
		Optional<ButtonType> resultadoConfirmacao = confirmacao.showAndWait();
		if (resultadoConfirmacao.isPresent() && resultadoConfirmacao.get() == ButtonType.OK) {
			AgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
			repositorioContato.excluir(this.contatoSelecionado);
			carregarTabelaContatos();
			this.tabelaContatos.getSelectionModel().selectFirst();
		}

		AgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
		repositorioContato.excluir(this.contatoSelecionado);
		carregarTabelaContatos();
		this.tabelaContatos.getSelectionModel().selectFirst();
	}

	public void buttonCancelar_Action() {
		habilitarEdicaoAgenda(false);
		this.tabelaContatos.getSelectionModel().selectFirst();
	}

	public void buttonSalvar_Action() throws SQLException, IOException {
		AgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
		Contato contato = new Contato();
		contato.setNome(txfNome.getText());
		contato.setIdade(Integer.parseInt(txfIdade.getText()));
		contato.setTelefone(txfTelefone.getText());
		if (this.ehInserir) {
			repositorioContato.inserir(contato);
		} else {
			repositorioContato.atualizar(contato);
		}
		habilitarEdicaoAgenda(false);
		carregarTabelaContatos();
		this.tabelaContatos.getSelectionModel().selectFirst();
	}

	private void carregarTabelaContatos() throws SQLException, IOException {
		AgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
		List<Contato> contatos = repositorioContato.selecionar();
		if (contatos.isEmpty()) {
			Contato contato = new Contato();
			contato.setNome("Alex Leporoni");
			contato.setIdade(43);
			contato.setTelefone("5516981844976");
			contatos.add(contato);
		}
		ObservableList<Contato> contatoObservalbleList = FXCollections.observableArrayList(contatos);
		this.tabelaContatos.getItems().setAll(contatoObservalbleList);
	}

	private void habilitarEdicaoAgenda(Boolean edicaoHabilitada) {
		this.txfNome.setDisable(!edicaoHabilitada);
		this.txfIdade.setDisable(!edicaoHabilitada);
		this.txfTelefone.setDisable(!edicaoHabilitada);
		this.buttonSalvar.setDisable(!edicaoHabilitada);
		this.buttonCancelar.setDisable(!edicaoHabilitada);
		this.buttonIncluir.setDisable(edicaoHabilitada);
		this.buttonAlterar.setDisable(edicaoHabilitada);
		this.buttonExcluir.setDisable(edicaoHabilitada);
		this.tabelaContatos.setDisable(edicaoHabilitada);
	}

}
