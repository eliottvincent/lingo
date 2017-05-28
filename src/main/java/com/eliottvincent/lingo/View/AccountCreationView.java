package com.eliottvincent.lingo.View;

import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Data.Status;
import com.eliottvincent.lingo.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
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
	private TextArea passwordTextArea;

	@FXML
	private TextArea passwordBisTextArea;

	@FXML
	private Spinner ageSpinner;

	@FXML
	private ComboBox<Gender> genderComboBox;

	@FXML
	private ComboBox<Language> languageComboBox;

	public AccountCreationView() {
		this.screenController = new ScreenController();
	}

	/**
	 *
	 */
	public void initialize() {

		// populating the gender combo
		genderComboBox.getItems().addAll(
			Gender.values()
		);


		// populating the language combo
		languageComboBox.getItems().addAll(
			Language.values()
		);
	}

	public void handleCreateAccount(ActionEvent actionEvent) {

		// TODO : move this logic to UserController (or User)
		if (usernameTextArea.getText() != null
			&& !Objects.equals(usernameTextArea.getText(), "")
			&& !Objects.equals(usernameTextArea.getText(), " ")) {

			if (passwordTextArea.getText() != null
				&& !Objects.equals(passwordTextArea.getText(), "")
				&& !Objects.equals(passwordTextArea.getText(), " ")) {

				if (passwordBisTextArea.getText() != null
					&& !Objects.equals(passwordBisTextArea.getText(), "")
					&& !Objects.equals(passwordBisTextArea.getText(), " ")
					&& Objects.equals(passwordTextArea.getText(), passwordBisTextArea.getText())) {

					if (ageSpinner.getValue() != null
						&& !Objects.equals(ageSpinner.getValue(), "")
						&& !Objects.equals(ageSpinner.getValue(), " ")) {

						if (genderComboBox.getValue() != null) {

							if (languageComboBox.getValue() != null) {


							User tmpUser = new User(
								usernameTextArea.getText(),
								passwordTextArea.getText(),
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
