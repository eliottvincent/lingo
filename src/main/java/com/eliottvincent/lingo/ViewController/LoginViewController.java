package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.AdminController;
import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Model.User;
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

	private Vector<StackPane> mCards = new Vector<StackPane>();


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 *
	 */
	public LoginViewController() {

		this.screenController = new ScreenController();
		// TODO : find a way to load FXML files here rather than in handlers (<-- ??)

	}

	/**
	 *
	 * @param status
	 */
	public LoginViewController(String status) {

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
			AdminController adminController = new AdminController();
			this.screenController.addScreen("admin", "../fxml/admin.fxml", adminController);

			screenController.activate(node.getScene(), "admin", null);
		}
		else {

			UserController userController = new UserController();
			User tmpUser = userController.getUserByCredentials(usernameTextField.getText(), passwordField.getText());

			if (tmpUser != null) {
				HomeViewController homeViewController = new HomeViewController(tmpUser);
				this.screenController.addScreen("home", "../fxml/home.fxml" , homeViewController);

				screenController.activate(node.getScene(), "home", null);
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
		this.screenController.addScreen("home", "../fxml/home.fxml", homeViewController);

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = node.getScene();
		screenController.activate(scene, "home", null);
	}

	/**
	 *
	 * @param node
	 */
	private void createAccountAction(Node node) {

		AccountCreationViewController accountCreationViewController = new AccountCreationViewController();
		this.screenController.addScreen("accountCreation", "../fxml/accountCreation.fxml", accountCreationViewController);

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = node.getScene();
		screenController.activate(scene, "accountCreation", null);
	}
}