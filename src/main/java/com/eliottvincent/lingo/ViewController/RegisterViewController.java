package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.AccountController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.BackAction;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * Created by eliottvct on 28/05/17.
 */

@ViewController("/fxml/register.fxml")
public class RegisterViewController {


	//================================================================================
	// GUI JavaFX Elements
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

	@FXML
	@ActionTrigger("handleCreateAccount")
	private JFXButton createAccountJFXButton;

	@FXML
	@BackAction
	private JFXButton cancelJFXButton;


	//================================================================================
	// DataFX Elements
	//================================================================================

	@ActionHandler
	private FlowActionHandler actionHandler;

	@FXMLViewFlowContext
	private ViewFlowContext flowContext;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	public RegisterViewController() {

	}

	@PostConstruct
	public void init() {

		// populating the gender combo
		genderComboBox.getItems().addAll(
			Gender.values()
		);


		// populating the language combo
		languageComboBox.getItems().addAll(
			Language.values()
		);



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
	// Event Actions
	//================================================================================

	/**
	 *
	 */
	@ActionMethod("handleCreateAccount")
	private void createAccountAction() {

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

										flowContext.register("user", createdUser);
										try {
											this.navigateToHome();
										} catch (VetoException | FlowException e) {
											e.printStackTrace();
										}
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


	//================================================================================
	// Calls to display methods
	//================================================================================

	public void displayHome(User user, Node node) {


	}


	/**
	 *
	 * @throws VetoException
	 * @throws FlowException
	 */
	void navigateToHome() throws VetoException, FlowException {

		actionHandler.navigate(HomeViewController.class);
	}

}
