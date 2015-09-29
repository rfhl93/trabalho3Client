package br.univel.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.univel.model.Disciplina;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DisciplinaController implements Initializable {

    @FXML
    private Button btnSalvar;

    @FXML
    private TableColumn<Disciplina, Integer> colCodigo;

    @FXML
    private TableColumn<Disciplina, Integer> colNumeroAulas;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField txfDescricao;

    @FXML
    private TableColumn<Disciplina, String> colDescricao;

    @FXML
    private TextField txfNumeroAulas;

    @FXML
    private TableView<Disciplina> tbCurso;

    @FXML
    private Button btnNovo;

    @FXML
    private Label lblMensagem;
    
    private List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
    @FXML
    void novo(ActionEvent event) {
    	novo();
    }

    private void novo() {
		// Implementar.
	}

	@FXML
    void salvar(ActionEvent event) {
		salvar();
    }

    private void salvar() {
    	try {
			Disciplina disciplina = new Disciplina();
			disciplina.setDescricao(txfDescricao.getText());
			disciplina.setNumeroAulas(Integer.parseInt(txfNumeroAulas.getText()));
			
			String json;
			ClientRequest request = new ClientRequest("");
			request.accept("application/json");
			
			ObjectMapper map = new ObjectMapper();
			json = map.writeValueAsString(disciplina);
			request.body("application/json", json);
			
			ClientResponse<String> response = request.post(String.class);
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
			}
			
			buscarDisciplinas();
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buscarDisciplinas() {
		try {
			disciplinas.clear();
			ClientRequest request = new ClientRequest("");
			request.accept("application/json");
			
			ClientResponse<String> response = request.post(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
			
			String output;
			while ((output = br.readLine()) != null) {
				ObjectMapper map = new ObjectMapper();
				Disciplina[] disciplinaAux = map.readValue(output, Disciplina[].class);
				for (Disciplina disciplina : disciplinaAux) {
					disciplinas.add(disciplina);
				}
			}
			
			atualizarTabela();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void atualizarTabela() {
		// Implementar.
		
	}

	@FXML
    void voltar(ActionEvent event) {
		voltar();
    }

	private void voltar() {
		// Implementar.
	}

}
