package com.eliottvincent.lingo;

import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.View.LoginView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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

		// then we create an instance of loginView
		// this instance is going to be the controller of our FXML template
		LoginView loginView = new LoginView();

		screenController.addScreen("loginTer", "../fxml/login.fxml", loginView);

		Group root = new Group();
		Scene terScene = new Scene(root, 1500, 750);
		screenController.activate(terScene, "loginTer", primaryStage);
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
