package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by eliottvct on 27/05/17.
 */
public class LoginController {

	@FXML
	private TextArea username;

	@FXML
	private TextArea password;

	@FXML
	private Button loginBtn;

	@FXML
	private Button createAccountBtn;

	private ScreenController screenController;

	public LoginController() throws IOException {
		this.screenController = new ScreenController();
		// TODO : find a way to load FXML files here rather than in handlers
	}

	/**
	 *
	 * @param actionEvent
	 */
	public void handleCreateAccount(ActionEvent actionEvent) throws IOException {

		this.screenController.addScreen("accountCreation", FXMLLoader.load(getClass().getResource( "../fxml/accountCreation.fxml" )));

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = ((Node) actionEvent.getSource()).getScene();
		screenController.activate(scene, "accountCreation");
	}

	/**
	 *
	 * @param actionEvent
	 */
	public void handleLogin(ActionEvent actionEvent) {

		UserController userController = new UserController();
		User tmpUser = userController.logIn(username.getText(), password.getText());

		if (tmpUser != null) {
			System.out.println("logged in!");
		}
		else {
			System.out.println("not logged in :(");
		}
	}
}
