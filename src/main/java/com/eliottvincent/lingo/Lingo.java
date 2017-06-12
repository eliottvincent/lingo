package com.eliottvincent.lingo;

import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.ViewController.LoginViewController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class Lingo extends Application
{

	//================================================================================
	// Main and initialization
	//================================================================================

	/**
	 *
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {

		// we need to instantiate the ScreenController
		ScreenController screenController = ScreenController.getInstance();

		// then we create an instance of loginViewController
		// this instance is going to be the controller of our FXML template
		LoginViewController loginViewController = new LoginViewController();

		// we add a screen in our ScreenController
		// named "login", template from "../fxml/login.fxml", loginViewController as controller
		screenController.addScreen("login", "../fxml/login.fxml");
		screenController.addScreen("home", "../fxml/home.fxml");
		screenController.addScreen("lesson", "../fxml/lesson.fxml");
		screenController.addScreen("register", "../fxml/register.fxml");
		screenController.addScreen("admin", "../fxml/admin.fxml");


		// TODO : add les autres

		Group root = new Group();
		Scene scene = new Scene(root, 1500, 750);
		screenController.activate(scene, "login", primaryStage, loginViewController);
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		launch(args);
	}

}
