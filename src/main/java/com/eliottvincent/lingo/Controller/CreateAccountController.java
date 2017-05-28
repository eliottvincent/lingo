package com.eliottvincent.lingo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.awt.*;
import java.io.IOException;

/**
 * Created by eliottvct on 28/05/17.
 */
public class CreateAccountController {

	private ScreenController screenController;


	public CreateAccountController() {
		this.screenController = new ScreenController();
	}

	public void handleLogin(ActionEvent actionEvent) {


	}

	public void handleCreateAccount(ActionEvent actionEvent) throws IOException {

		this.screenController.addScreen("login", FXMLLoader.load(getClass().getResource( "../fxml/login.fxml" )));

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = ((Node) actionEvent.getSource()).getScene();
		screenController.activate(scene, "login");
	}

	public void handleCancel(ActionEvent actionEvent) {


	}
}
