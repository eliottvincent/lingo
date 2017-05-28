package com.eliottvincent.lingo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class Lingo extends Application
{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
		Scene mScene = new Scene(root, 1500, 750);

		primaryStage.setScene(mScene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}
