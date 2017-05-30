package com.eliottvincent.lingo.View;

import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by eliottvct on 27/05/17.
 */
public class LoginView {


	//================================================================================
	// JavaFX Elements
	//================================================================================

	@FXML
	private VBox container;

	@FXML
	public Label titleLabel;

	@FXML
	public Label statusLabel;

	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button loginButton;

	@FXML
	private Button guestLoginButton;

	@FXML
	private Button createAccountButton;


	//================================================================================
	// Other properties
	//================================================================================

	private ScreenController screenController;

	private String statusText;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 *
	 */
	public LoginView() {
		this.screenController = new ScreenController();
		// TODO : find a way to load FXML files here rather than in handlers
	}

	/**
	 *
	 * @param status
	 */
	public LoginView(String status) {
		this.screenController = new ScreenController();
		if (status != null) {
			this.statusText = status;
		}
	}

	@FXML
	public void initialize() {


		// the default focus is on the first text area
		// we use this Runnable (encapsulated in a lambda function) to focus on the container
		Platform.runLater(() -> {
			container.requestFocus();
		});

		// ENTER key = login
		// using lambda function 💪
		container.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

			// if the TAB key was pressed
            if (event.getCode().equals(KeyCode.ENTER)) {

                //Node node = (Node) event.getSource();
                this.loginAction((Node) event.getSource());

                // avoid memory leak
                event.consume();
            }
        });

		// if the controller has been instantiated with a "status" in parameters
		// then we set the text of the statusLabel
		if (this.statusText != null) {
			this.statusLabel.setText(this.statusText);
		}
	}


	//================================================================================
	// Event Handlers
	//================================================================================

	/**
	 *
	 * @param actionEvent
	 */
	public void handleCreateAcount(ActionEvent actionEvent) {


		this.createAccountAction((Node) actionEvent.getSource());
	}

	/**
	 *
	 * @param actionEvent
	 */
	public void handleLogin(ActionEvent actionEvent) {

		this.loginAction((Node) actionEvent.getSource());
	}

	/**
	 *
	 * @param actionEvent
	 */
	public void handleGuestLogin(ActionEvent actionEvent) {
		this.guestLoginAction((Node) actionEvent.getSource());
	}


	//================================================================================
	// Event Actions
	//================================================================================

	/**
	 *
	 * @param node
	 */
	private void loginAction(Node node) {
		UserController userController = new UserController(new User(usernameTextField.getText(), passwordField.getText()));

		User tmpUser = userController.logIn();
		if (tmpUser != null) {
			this.screenController.addScreen("home", "../fxml/home.fxml" , null);

			Scene scene = node.getScene();
			screenController.activate(scene, "home", null);
		}
		else {
			statusLabel.setText("Incorrect username or password");
		}
	}

	/**
	 *
	 * @param node
	 */
	private void guestLoginAction(Node node) {
		this.screenController.addScreen("home", "../fxml/home.fxml", null);

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = node.getScene();
		screenController.activate(scene, "home", null);
	}

	/**
	 *
	 * @param node
	 */
	private void createAccountAction(Node node) {
		this.screenController.addScreen("accountCreation", "../fxml/accountCreation.fxml", null);

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = node.getScene();
		screenController.activate(scene, "accountCreation", null);
	}
}
