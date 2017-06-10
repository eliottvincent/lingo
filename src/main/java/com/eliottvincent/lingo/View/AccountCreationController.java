package com.eliottvincent.lingo.View;

import com.eliottvincent.lingo.Controller.AccountController;
import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.ConverterHelper;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Created by eliottvct on 28/05/17.
 */
public class AccountCreationController {


	//================================================================================
	// JavaFX Elements
	//================================================================================

	@FXML
	private VBox container;

	@FXML
	private Label titleLabel;

	@FXML
	private Label statusLabel;

	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField passwordBisField;

	@FXML
	private ChoiceBox dayChoiceBox;

	@FXML
	private ChoiceBox monthChoiceBox;

	@FXML
	private ChoiceBox yearChoiceBox;

	@FXML
	private ComboBox<Gender> genderComboBox;

	@FXML
	private ComboBox<Language> languageComboBox;


	//================================================================================
	// Other properties
	//================================================================================

	private ScreenController screenController;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	public AccountCreationController() {
		this.screenController = new ScreenController();
	}

	@FXML
	public void initialize() {

		// populating the gender combo
		genderComboBox.getItems().addAll(
			Gender.values()
		);


		// populating the language combo
		languageComboBox.getItems().addAll(
			Language.values()
		);

		/*
		 * old listener that used to look focusOut on passwordBisField
		 *
		 * there are risks of a memory leak when using the addListener() method on the PasswordField
		 * either we save the listener object and remove it in a finalize() overridden method or use a WeakInvalidationListener
		 * in the code bellow we use a WeakInvalidationListener because it doesn't require the extra follow up code
		 *
		 */
		// adding an event handler on the password bis field
		// lambda expression from Java 8
		/*InvalidationListener pfConfStorepassListener = (evt) -> {
			if( !passwordBisField.isFocused() ) {
				if (!passwordBisField.getText().equals(passwordField.getText())) {
					errorLabel.setText("Passwords don\'t match");
					errorLabel.setTextFill(Color.RED);
				}
				else {
					errorLabel.setText("");
					errorLabel.setTextFill(Color.WHITE);
				}
			}
		};
		passwordBisField.focusedProperty().addListener(
			new WeakInvalidationListener(pfConfStorepassListener)
		);*/


		// listening for passwordFieldBis value changes
		// lambda expression from Java 8
		passwordBisField.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!passwordBisField.getText().equals(passwordField.getText())) {
					statusLabel.setText("Passwords don\'t match");
				}
				else {
					statusLabel.setText("");
				}
			}
		);

		// the default focus is on the first text area
		// we use this Runnable (encapsulated in a lambda function) to focus on the container
		Platform.runLater(() -> container.requestFocus());

		// populating the choice boxes
		dayChoiceBox.setItems(FXCollections.observableArrayList(
			1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31
			)
		);

		monthChoiceBox.setItems(FXCollections.observableArrayList(
			"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre"
			)
		);

		yearChoiceBox.setItems(FXCollections.observableArrayList(
			1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017
			)
		);
	}


	//================================================================================
	// Event Handlers
	//================================================================================

	public void handleCreateAccount(ActionEvent actionEvent) {

		this.createAccountAction((Node) actionEvent.getSource());
	}

	public void handleCancel(ActionEvent actionEvent) {

		this.cancelAction((Node) actionEvent.getSource());
	}


	//================================================================================
	// Event Actions
	//================================================================================

	/**
	 *
	 * @param node
	 */
	private void createAccountAction(Node node) {

		// TODO : move this logic to UserController (or User) ??

		// the user entered some text in the username field
		if (usernameTextField.getText() != null
			&& !Objects.equals(usernameTextField.getText(), "")
			&& !Objects.equals(usernameTextField.getText(), " ")) {

			// the user entered some text in the first password field
			if (passwordField.getText() != null
				&& !Objects.equals(passwordField.getText(), "")
				&& !Objects.equals(passwordField.getText(), " ")) {

				// the user entered
				if (passwordBisField.getText() != null
					&& !Objects.equals(passwordBisField.getText(), "")
					&& !Objects.equals(passwordBisField.getText(), " ")
					&& Objects.equals(passwordField.getText(), passwordBisField.getText())) {

					if (dayChoiceBox.getValue() != null
						&& !Objects.equals(dayChoiceBox.getValue(), "")
						&& !Objects.equals(dayChoiceBox.getValue(), " ")) {

						if (genderComboBox.getValue() != null) {

							if (languageComboBox.getValue() != null) {

								// all values were filed, we can instantiate the userController
								UserController userController = new UserController();

								// if the username isn't already used
								if (!userController.usernameAlreadyExist(usernameTextField.getText())) {

									AccountController accountController = new AccountController();

									User createdUser = accountController.createNewAccount(usernameTextField.getText(),
										passwordField.getText(),
										new Date(),
										genderComboBox.getValue(),
										languageComboBox.getValue()
									);

									if (createdUser != null) {

										statusLabel.setText("");

										// redirecting the user to the home
										displayHome(createdUser, node);
									}

									else {

										statusLabel.setText("An error occurred");
									}
								}
								else {

									statusLabel.setText("The username is already taken, please choose another one.");
								}
							}
							else {

								statusLabel.setText("Please choose a valid language");
							}
						}
						else {

							statusLabel.setText("Please choose a valid gender");
						}
					}
					else {

						statusLabel.setText("Please enter a valid age");
					}
				}
				else {

					statusLabel.setText("Passwords don\'t match");
				}
			}
			else {

				statusLabel.setText("Please choose a password");
			}
		}
		else {

			statusLabel.setText("Please choose a username");

		}
	}

	private void cancelActionBackup(Node node) {

		LoginController loginController = new LoginController("cancel");
		this.screenController.addScreen("login", "../fxml/login.fxml", loginController);

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = node.getScene();
		this.screenController.activate(scene, "login", null);
	}

	private void cancelAction(Node node) {
		LoginController loginController = new LoginController("testtttttt");

		this.screenController.addScreen("loginBis", "../fxml/login.fxml", loginController);

		Scene scene = node.getScene();
		this.screenController.activate(scene, "loginBis", null);
	}


	//================================================================================
	// Calls to display methods
	//================================================================================

	public void displayHome(User user, Node node) {


		HomeController homeController = new HomeController(user);
		this.screenController.addScreen("home", "../fxml/home.fxml" , homeController);

		Scene scene = node.getScene();
		screenController.activate(scene, "home", null);
	}

}
