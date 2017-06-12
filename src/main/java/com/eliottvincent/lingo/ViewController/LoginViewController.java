package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Vector;

/**
 * Created by eliottvct on 27/05/17.
 */
public class LoginViewController {


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
	private JFXTextField usernameTextField;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private JFXButton loginButton;

	@FXML
	private JFXButton guestLoginButton;

	@FXML
	private JFXButton createAccountButton;


	//================================================================================
	// Other properties
	//================================================================================

	private ScreenController screenController;

	private String statusText;

	private Vector<StackPane> mCards = new Vector<StackPane>();


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 *
	 */
	public LoginViewController() {

		this.screenController = ScreenController.getInstance();

	}


	@FXML
	public void initialize() {

		// the default focus is on the first text area
		// we use this Runnable (encapsulated in a lambda function) to focus on the container
		Platform.runLater(() -> {
			container.requestFocus();
		});

		// handling key presses thanks to this lambda function 💪
		container.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

			// if the ENTER key was pressed
            if (event.getCode().equals(KeyCode.ENTER)) {

                this.loginAction((Node) event.getSource());

                // using .consume() so the event will not be dispatched to any further event listeners
				// it's also useful to avoid memory leak
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
	public void handleCreateAccount(ActionEvent actionEvent) {

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

		if (usernameTextField.getText().equals("admin") &&
			passwordField.getText().equals("admin")) {
			AdminViewController adminViewController = new AdminViewController();

			screenController.activate(node.getScene(), "admin", null, adminViewController);
		}
		else {

			UserController userController = new UserController();
			User tmpUser = userController.getUserByCredentials(usernameTextField.getText(), passwordField.getText());

			if (tmpUser != null) {
				HomeViewController homeViewController = new HomeViewController(tmpUser);

				screenController.activate(node.getScene(), "home", null, homeViewController);
			}
			else {
				statusLabel.setText("Incorrect username or password");
			}
		}
	}

	/**
	 *
	 * @param node
	 */
	private void guestLoginAction(Node node) {

		HomeViewController homeViewController = new HomeViewController(null);

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = node.getScene();
		screenController.activate(scene, "home", null, homeViewController);
	}

	/**
	 *
	 * @param node
	 */
	private void createAccountAction(Node node) {

		RegisterViewController registerViewController = new RegisterViewController();

		screenController.activate(node.getScene(), "register", null, registerViewController);
	}
}
