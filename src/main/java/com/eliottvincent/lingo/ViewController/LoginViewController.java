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

	private String statusText;

	private static final String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";

	private static final String EM1 = "1em";

	private static final String ERROR = "error";

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

                this.loginAction();

                // using .consume() so the event will not be dispatched to any further event listeners
				// it's also useful to avoid memory leak
                event.consume();
            }
        });

		RequiredFieldValidator validator = new RequiredFieldValidator();

		usernameTextField.setStyle(FX_LABEL_FLOAT_TRUE);
		validator.setMessage("Input required");
		validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
			.glyph(FontAwesomeIcon.WARNING)
			.size(EM1)
			.styleClass(ERROR)
			.build());
		usernameTextField.getValidators().add(validator);
		usernameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				usernameTextField.validate();
			}
		});

		validator = new RequiredFieldValidator();
		passwordField.setStyle(FX_LABEL_FLOAT_TRUE);
		validator.setMessage("Password can't be empty");
		validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
			.glyph(FontAwesomeIcon.WARNING)
			.size(EM1)
			.styleClass(ERROR)
			.build());
		passwordField.getValidators().add(validator);
		passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				passwordField.validate();
			}
		});

		buttonsVBox.setSpacing(30);
		buttonsVBox.setStyle("-fx-padding: 50px 0 0 0; -fx-border-insets: 50px 0 0 0; -fx-background-insets: 50px 0 0 0;");


	}

	/**
	 *
	 */
	@ActionMethod("loginAction")
	private void loginAction() {

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

				// saving the User object in context
				flowContext.register("user", tmpUser);
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
