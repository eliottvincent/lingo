package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.AccountController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.*;
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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * <b>RegisterViewController is the class responsible for the Register view.</b>
 *
 * @author eliottvincent
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
	@ActionTrigger("onCreateAccount")
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
	// Other properties
	//================================================================================

	private UserController userController;

	private AccountController accountController;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 * The default constructor for the RegisterViewController.
	 */
	public RegisterViewController() {

		this.userController = new UserController();

		this.accountController = new AccountController();
	}

	/**
	 * the init() method is responsible for doing the necessary initialization of some components.
	 * By adding the @PostConstruct annotation to the method, the DataFX flow container will call this method once all injectable values of the controller instance are injected.
	 */
	@PostConstruct
	public void init() {

		// by default, the initial focus is on the first text area
		// we use this Runnable (encapsulated in a lambda function) to focus on the container
		Platform.runLater(() -> container.requestFocus());

		// populating the gender ComboBox
		genderComboBox.getItems().addAll(
			Gender.values()
		);

		// populating the language ComboBox
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

		// removing week numbers in the DatePicker
		birthdatePicker.setShowWeekNumbers(false);
	}


	//================================================================================
	// Event Actions
	//================================================================================

	/**
	 * the onCreateAccount() method is responsible for creating an User account.
	 */
	@ActionMethod("onCreateAccount")
	private void onCreateAccount() {

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


								// if the username isn't already used
								if (!this.userController.usernameAlreadyExist(usernameTextField.getText())) {

									User createdUser = this.accountController.createNewAccount(usernameTextField.getText(),
										passwordField.getText(),
										ConverterHelper.localeDateToDate(birthdatePicker.getValue()),
										genderComboBox.getValue(),
										languageComboBox.getValue()
									);

									if (createdUser != null) {

										// clearing the status label to remove any previous error message
										statusLabel.setText("");

										// registering the created User object in the context
										// so that it's available in the whole application
										flowContext.register("user", createdUser);

										// "jumping" to the home
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


	/**
	 * the navigateToHome() method is responsible of opening the Home View.
	 *
	 * @throws VetoException the VetoException to throw, if any.
	 * @throws FlowException the FlowException to throw, if any.
	 */
	private void navigateToHome() throws VetoException, FlowException {

		actionHandler.navigate(HomeViewController.class);
	}

}
