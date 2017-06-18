package com.eliottvincent.lingo;

import com.eliottvincent.lingo.Controller.ActionController;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Model.User;
import com.eliottvincent.lingo.ViewController.LoginViewController;

import com.jfoenix.controls.JFXDecorator;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;


/**
 * <b>Lingo is the main class of the application.</b>
 *
 * @author eliottvincent
 */
public class Lingo extends Application {


	//================================================================================
	// DataFX elements
	//================================================================================

	@FXMLViewFlowContext
	private ViewFlowContext flowContext;


	//================================================================================
	// Other properties
	//================================================================================

	public User user;

	private ActionController actionController;


	//================================================================================
	// Main and initialization
	//================================================================================

	/**
	 * start() is the method responsible for starting the application
	 *
	 * @param stage the primary stage of the Application
	 */
	@Override
	public void start(Stage stage) throws Exception {

		// creating a new Flow linked to the LoginViewController
		Flow flow = new Flow(LoginViewController.class);
		DefaultFlowContainer container = new DefaultFlowContainer();

		// creating a new ViewFlowContext
		// so we can save some data between flows
		flowContext = new ViewFlowContext();

		// registering the stage in the context
		flowContext.register("Stage", stage);
		flow.createHandler(flowContext).start(container);

		// creating a decorator
		JFXDecorator decorator = new JFXDecorator(stage, container.getView());
		decorator.setCustomMaximize(true);
		Scene scene = new Scene(decorator, 1500, 750);

		// adding the stylesheets to the application
		final ObservableList<String> stylesheets = scene.getStylesheets();
		stylesheets.addAll(
			Lingo.class.getResource("/css/fonts.css").toExternalForm(),
			Lingo.class.getResource("/css/home.css").toExternalForm()
		);

		// setting sizes
		stage.setMinWidth(1500);
		stage.setMinHeight(750);
		stage.setScene(scene);

		// showing the application
		stage.show();
	}

	/**
	 * stop() is the method responsible for stopping the application.
	 *
	 */
	@Override
	public void stop() {

		this.user = (User) flowContext.getRegisteredObject("user");

		this.actionController = new ActionController();

		this.actionController.createNewAction(
			this.user,
			ActionType.LOGOUT,
			new Date(),
			null,
			null
		);

		// closing the application
		Platform.exit();
	}

	/**
	 *
	 * @param args the arguments passed to the application, if any
	 */
	public static void main(String[] args) {

		launch(args);
	}

}
