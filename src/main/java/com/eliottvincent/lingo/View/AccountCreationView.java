package com.eliottvincent.lingo.View;

import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Data.Status;
import com.eliottvincent.lingo.Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by eliottvct on 28/05/17.
 */
public class AccountCreationView {

	private ScreenController screenController;

	@FXML
	private VBox container;

	@FXML
	private Label titleLabel;

	@FXML
	private Label errorLabel;

	@FXML
	private TextArea usernameTextArea;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField passwordBisField;

	@FXML
	private Spinner ageSpinner;

	@FXML
	private ComboBox<Gender> genderComboBox;

	@FXML
	private ComboBox<Language> languageComboBox;

	public AccountCreationView() {
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
		passwordBisField.textProperty().addListener(
			(observable, oldValue, newValue) -> {
				if (!passwordBisField.getText().equals(passwordField.getText())) {
					errorLabel.setText("Passwords don\'t match");
				}
				else {
					errorLabel.setText("");
				}
			}
		);

		// the default focus is on the first text area
		// we this Runnable (encapsulated in a lambda function) to focus on the container
		Platform.runLater(() -> container.requestFocus());

	}

	public void handleCreateAccount(ActionEvent actionEvent) {

		// TODO : move this logic to UserController (or User)
		if (usernameTextArea.getText() != null
			&& !Objects.equals(usernameTextArea.getText(), "")
			&& !Objects.equals(usernameTextArea.getText(), " ")) {

			if (passwordField.getText() != null
				&& !Objects.equals(passwordField.getText(), "")
				&& !Objects.equals(passwordField.getText(), " ")) {

				if (passwordBisField.getText() != null
					&& !Objects.equals(passwordBisField.getText(), "")
					&& !Objects.equals(passwordBisField.getText(), " ")
					&& Objects.equals(passwordField.getText(), passwordBisField.getText())) {

					if (ageSpinner.getValue() != null
						&& !Objects.equals(ageSpinner.getValue(), "")
						&& !Objects.equals(ageSpinner.getValue(), " ")) {

						if (genderComboBox.getValue() != null) {

							if (languageComboBox.getValue() != null) {


							User tmpUser = new User(
								usernameTextArea.getText(),
								passwordField.getText(),
								(Integer) ageSpinner.getValue(),
								genderComboBox.getValue(),
								languageComboBox.getValue()
								);

								UserController userController = new UserController(tmpUser);

								Status saveUserStatus = userController.saveUser();
								if (saveUserStatus == Status.OK) {

									errorLabel.setText("Congratttts");

								}
								else {
									switch (saveUserStatus) {
										case USER_ALREADY_EXISTS:
											errorLabel.setText("User already exists");
											break;
										case PASSWORD_TOO_SHORT:
											errorLabel.setText("Password too short");
											break;
										case USERNAME_NOT_AVAILABLE:
											errorLabel.setText("Username not available");
											break;
										default:
											errorLabel.setText("An error occurred");
									}
								}
							}
							else {

								errorLabel.setText("Please choose a valid language");
							}
						}
						else {

							errorLabel.setText("Please choose a valid gender");
						}
					}
					else {

						errorLabel.setText("Please enter a valid age");
					}
				}
				else {

					errorLabel.setText("Passwords don\'t match");
				}
			}
			else {

				errorLabel.setText("Please choose a password");
			}
		}
		else {

			errorLabel.setText("Please choose a username");

		}
	}

	public void handleCancel(ActionEvent actionEvent) {

		try {
			this.screenController.addScreen("login", FXMLLoader.load(getClass().getResource( "../fxml/login.fxml" )));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// need to cast to (Node) in order to use the getScene() method
		Scene scene = ((Node) actionEvent.getSource()).getScene();
		screenController.activate(scene, "login");
	}
}
