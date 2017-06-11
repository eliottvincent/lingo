package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.AccountController;
import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Created by eliottvct on 28/05/17.
 */
public class AccountCreationViewController {


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
	private JFXTextField usernameTextField;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private JFXPasswordField passwordBisField;

	@FXML
	private JFXDatePicker birthdatePicker;

	@FXML
	private JFXComboBox<Gender> genderComboBox;

	@FXML
	private JFXComboBox<Language> languageComboBox;


	//================================================================================
	// Other properties
	//================================================================================

	private ScreenController screenController;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	AccountCreationViewController() {

		this.screenController = ScreenController.getInstance();
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

		// removing week numbers
		birthdatePicker.setShowWeekNumbers(false);





		usernameTextField.setLabelFloat(true);
		usernameTextField.setPromptText("With Validation..");
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Input Required");
		//validator.setAwsomeIcon(new Info(AwesomeIcon.WARNING,"2em",";","error"));
		usernameTextField.getValidators().add(validator);
		usernameTextField.focusedProperty().addListener((o,oldVal,newVal)->{
			if(!newVal) usernameTextField.validate();
		});
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

					if (birthdatePicker.getValue() != null) {

						if (genderComboBox.getValue() != null) {

							if (languageComboBox.getValue() != null) {

								// all values were filed, we can instantiate the userController
								UserController userController = new UserController();

								// if the username isn't already used
								if (!userController.usernameAlreadyExist(usernameTextField.getText())) {

									AccountController accountController = new AccountController();

									User createdUser = accountController.createNewAccount(usernameTextField.getText(),
										passwordField.getText(),
										ConverterHelper.localeDateToDate(birthdatePicker.getValue()),
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

		LoginViewController loginViewController = new LoginViewController("cancel");

		this.screenController.activate(node.getScene(), "login", null, loginViewController);
	}

	private void cancelAction(Node node) {
		LoginViewController loginViewController = new LoginViewController("testtttttt");

		this.screenController.activate(node.getScene(), "loginBis", null, loginViewController);
	}


	//================================================================================
	// Calls to display methods
	//================================================================================

	public void displayHome(User user, Node node) {


		HomeViewController homeViewController = new HomeViewController(user);

		screenController.activate(node.getScene(), "home", null, homeViewController);
	}

}
