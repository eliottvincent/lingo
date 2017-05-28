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
    /*public static void main( String[] args )
    {
		new ApplicationController();
    }*/

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load the font
		//Font.loadFont(getClass().getResource("/fonts/VarelaRound-Regular.ttf").toExternalForm(), 10);

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
