package com.eliottvincent.lingo;

import com.eliottvincent.lingo.Controller.QueryApp;
import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.View.LoginController;
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
		ScreenController screenController = new ScreenController();

		// then we create an instance of loginController
		// this instance is going to be the controller of our FXML template
		LoginController loginController = new LoginController();

		// we add a screen in our ScreenController
		// named "login", template from "../fxml/login.fxml", loginController as controller
		screenController.addScreen("login", "../fxml/login.fxml", loginController);

		Group root = new Group();
		Scene scene = new Scene(root, 1500, 750);
		screenController.activate(scene, "login", primaryStage);
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		QueryApp queryApp = new QueryApp();
		queryApp.executeQuery("select * from actor");
		launch(args);
	}

}
