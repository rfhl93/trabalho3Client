package br.univel.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.univel.model.Curso;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CursoController implements Initializable {

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField txfCargaHoraria;

    @FXML
    private TableColumn<Curso, Long> colCodigo;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField txfDescricao;

    @FXML
    private TableColumn<Curso, String> colDescricao;

    @FXML
    private TableColumn<Curso, Integer> colCargaHoraria;

    @FXML
    private TableView<Curso> tbCurso;

    @FXML
    private Button btnNovo;

    @FXML
    private Label lblMensagem;
    
    private List<Curso> cursos = new ArrayList<Curso>();
	
	public void initialize(URL location, ResourceBundle resources) {
		buscarCursos();
	}
	
    @FXML
    void novo(ActionEvent event) {
    	novo();
    }

    private void novo() {
		txfDescricao.setText("");
		txfCargaHoraria.setText("");
	}

	@FXML
    void salvar(ActionEvent event) {
		salvar();
    }

    private void salvar() {
		try {
			Curso curso = new Curso();
			curso.setDescricao(txfDescricao.getText());
			curso.setCargaHoraria(Integer.parseInt(txfCargaHoraria.getText()));
			
			String json;
			ClientRequest request = new ClientRequest("http://trabalho3-rfhl93.rhcloud.com/trabalho3/rest/cursos");
			request.accept("application/json");
			
			ObjectMapper map = new ObjectMapper();
			json = map.writeValueAsString(curso);
			request.body("application/json", json);
			
			ClientResponse<String> response = request.post(String.class);
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
			}
			
			buscarCursos();
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buscarCursos() {
		try {
			cursos.clear();
			ClientRequest request = new ClientRequest("http://trabalho3-rfhl93.rhcloud.com/trabalho3/rest/cursos");
			request.accept("application/json");
			
			ClientResponse<String> response = request.get(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
			
			String output;
			while ((output = br.readLine()) != null) {
				ObjectMapper map = new ObjectMapper();
				Curso[] cursosAux = map.readValue(output, Curso[].class);
				for (Curso curso : cursosAux) {
					cursos.add(curso);
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
		if (!cursos.isEmpty()) {
			List<Curso> listaCursos = cursos;
			tbCurso.getItems().clear();
			colCodigo.setCellValueFactory(new PropertyValueFactory<Curso, Long>("id"));
			colDescricao.setCellValueFactory(new PropertyValueFactory<Curso, String>("descricao"));
			colCargaHoraria.setCellValueFactory(new PropertyValueFactory<Curso, Integer>("cargaHoraria"));
			tbCurso.setItems(FXCollections.observableArrayList(listaCursos));
		}
	}

	@FXML
    void voltar(ActionEvent event) {
		voltar();
    }

	private void voltar() {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/br/univel/view/PrincipalView.fxml"));
			MainController.PRIMARYSTAGE.setScene(new Scene(root));
			MainController.PRIMARYSTAGE.centerOnScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
