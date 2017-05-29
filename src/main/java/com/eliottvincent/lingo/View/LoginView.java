package com.eliottvincent.lingo.View;

import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Model.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by eliottvct on 27/05/17.
 */
public class LoginView {

	@FXML
	private VBox container;

	@FXML
	public Label titleLabel;

	@FXML
	private Button guestLoginButton;

	@FXML
	private TextArea usernameTextArea;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button loginButton;

	@FXML
	private Button createAccountButton;

	private ScreenController screenController;

	public LoginView() {
		this.screenController = new ScreenController();
		// TODO : find a way to load FXML files here rather than in handlers
	}

	@FXML
	public void initialize() {


		// the default focus is on the first text area
		// we this Runnable (encapsulated in a lambda function) to focus on the container
		Platform.runLater(() -> container.requestFocus());



	}

	/**
	 *
	 * @param actionEvent
	 */
	public void handleCreateAccount(ActionEvent actionEvent) {

		try {
			this.screenController.addScreen("accountCreation", FXMLLoader.load(getClass().getResource( "../fxml/accountCreation.fxml" )));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = ((Node) actionEvent.getSource()).getScene();
		screenController.activate(scene, "accountCreation");
	}

	/**
	 *
	 * @param actionEvent
	 */
	public void handleLogin(ActionEvent actionEvent) {

		UserController userController = new UserController(new User(usernameTextArea.getText(), passwordField.getText()));

		User tmpUser = userController.logIn();
		if (tmpUser != null) {
			try {
				this.screenController.addScreen("home", FXMLLoader.load(getClass().getResource( "../fxml/home.fxml" )));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// need to cast to (Node) in order to use the getScene() method
			Scene scene = ((Node) actionEvent.getSource()).getScene();
			screenController.activate(scene, "home");
		}
		else {
			// TODO :
			System.out.printf("not logged in :(");
		}
	}

	public void handleGuestLogin(ActionEvent actionEvent) {
		try {
			this.screenController.addScreen("home", FXMLLoader.load(getClass().getResource( "../fxml/home.fxml" )));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = ((Node) actionEvent.getSource()).getScene();
		screenController.activate(scene, "home");

	}
}
