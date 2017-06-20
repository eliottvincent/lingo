package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.ActionController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * <b>LoginViewController is the class responsible for the Login view.</b>
 *
 * @author eliottvincent
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
	private VBox buttonsVBox;

	@FXML
	@ActionTrigger("loginAction")
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

	private UserController userController;

	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 * The default constructor for the LoginViewController.
	 */
	public LoginViewController() {

		this.actionController = new ActionController();

		this.userController = new UserController();
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

		// handling key presses thanks to this lambda function
		container.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

			// if the ENTER key was pressed
            if (event.getCode().equals(KeyCode.ENTER)) {

                this.loginAction();

                // using .consume() so the event will not be dispatched to any further event listeners
				// it's also useful to avoid memory leak
                event.consume();
            }
        });

		this.initializeFields();
	}

	/**
	 * the initializeFields() method is responsible for initializing the credentials fields, especially their validators.
	 */
	private void initializeFields() {

		RequiredFieldValidator validator = new RequiredFieldValidator();

		usernameTextField.setStyle("-fx-label-float:true;");
		validator.setMessage("Input required");
		validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
			.glyph(FontAwesomeIcon.WARNING)
			.size("1em")
			.styleClass("error")
			.build());
		usernameTextField.getValidators().add(validator);
		usernameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				usernameTextField.validate();
			}
		});

		validator = new RequiredFieldValidator();
		passwordField.setStyle("-fx-label-float:true;");
		validator.setMessage("Password can't be empty");
		validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
			.glyph(FontAwesomeIcon.WARNING)
			.size("1em")
			.styleClass("error")
			.build());
		passwordField.getValidators().add(validator);
		passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				passwordField.validate();
			}
		});
	}

	/**
	 * the loginAction() method is responsible handling the user login
	 */
	@ActionMethod("loginAction")
	private void loginAction() {

		// if the credentials are "admin" and "admin"
		if (usernameTextField.getText().equals("admin") && passwordField.getText().equals("admin")) {

			// we show the Admin View
			try {
				this.navigateToAdmin();
			} catch (VetoException | FlowException e) {
				e.printStackTrace();
			}

		}

		else {

			User tmpUser = this.userController.getUserByCredentials(usernameTextField.getText(), passwordField.getText());

			// if the user has been found by the getUserByCredentials() method
			if (tmpUser != null) {

				// we create a "LOGIN" action
				this.actionController.createNewAction(tmpUser, ActionType.LOGIN, new Date(), null, null);

				// we save the User object in the ViewFlowContext
				flowContext.register("user", tmpUser);

				// we show the Home View
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
	 * the navigateToHome() method is responsible of opening the Home View.
	 *
	 * @throws VetoException the VetoException to throw, if any.
	 * @throws FlowException the FlowException to throw, if any.
	 */
	private void navigateToHome() throws VetoException, FlowException {

		actionHandler.navigate(HomeViewController.class);
	}

	/**
	 * the navigateToAdmin() method is responsible of opening the Admin View.
	 *
	 * @throws VetoException the VetoException to throw, if any.
	 * @throws FlowException the FlowException to throw, if any.
	 */
	private void navigateToAdmin() throws VetoException, FlowException {

		actionHandler.navigate(AdminViewController.class);
	}

}
