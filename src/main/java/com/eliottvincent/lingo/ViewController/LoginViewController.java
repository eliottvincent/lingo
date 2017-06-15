package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.ActionController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by eliottvct on 27/05/17.
 */

@ViewController("/fxml/login.fxml")
public class LoginViewController {


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
	private JFXButton loginButton;

	@FXML
	@LinkAction(HomeViewController.class)
	private JFXButton guestLoginButton;

	@FXML
	@LinkAction(RegisterViewController.class)
	private JFXButton createAccountButton;


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

	private ActionController actionController;

	private String statusText;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 *
	 */
	public LoginViewController() {

		this.actionController = new ActionController();
	}


	@PostConstruct
	public void init() {

		// the default focus is on the first text area
		// we use this Runnable (encapsulated in a lambda function) to focus on the container
		Platform.runLater(() -> container.requestFocus());

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

		System.out.println("From LoginViewController:" + flowContext.getRegisteredObject("myvalue"));

	}

	/**
	 *
	 * @param node
	 */
	private void loginAction(Node node) {

		if (usernameTextField.getText().equals("admin") &&
			passwordField.getText().equals("admin")) {

			try {
				this.navigateToAdmin();
			} catch (VetoException | FlowException e) {
				e.printStackTrace();
			}

		}
		else {

			UserController userController = new UserController();
			User tmpUser = userController.getUserByCredentials(usernameTextField.getText(), passwordField.getText());

			// user does exist
			if (tmpUser != null) {

				// saving the action
				this.actionController.createNewAction(tmpUser, ActionType.LOGIN, new Date(), null, null);

				try {
					this.navigateToHome();
				} catch (VetoException | FlowException e) {
					e.printStackTrace();
				}

			}

			// user doesn't exist
			else {
				statusLabel.setText("Incorrect username or password");
			}
		}
	}

	/**
	 *
	 * @throws VetoException
	 * @throws FlowException
	 */
	void navigateToHome() throws VetoException, FlowException {

		actionHandler.navigate(HomeViewController.class);
	}


	/**
	 *
	 * @throws VetoException
	 * @throws FlowException
	 */
	void navigateToAdmin() throws VetoException, FlowException {

		actionHandler.navigate(AdminViewController.class);
	}

}
