package br.univel.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class PrincipalController implements Initializable {

    @FXML
    private Button btnDisciplina;

    @FXML
    private Button btnCurso;
	
	public void initialize(URL location, ResourceBundle resources) {
		// Inicializar componentes.
	}

    @FXML
    void abrirCadastroCurso(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/br/univel/view/CursoView.fxml"));
			MainController.PRIMARYSTAGE.setScene(new Scene(root));
			MainController.PRIMARYSTAGE.centerOnScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void abrirCadastroDisciplina(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/br/univel/view/DisciplinaView.fxml"));
			MainController.PRIMARYSTAGE.setScene(new Scene(root));
			MainController.PRIMARYSTAGE.centerOnScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
}
