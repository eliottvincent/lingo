package com.eliottvincent.lingo;

import com.eliottvincent.lingo.Controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class Main extends Application
{
    /*public static void main( String[] args )
    {
		new ApplicationController();
    }*/

	@Override
	public void start(Stage primaryStage) throws Exception{
		//Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
		Group root = new Group();

		// Text
		Text txt = new Text();
		txt.setText("Say sup!");
		txt.setY(50);

		// Text field
		TextField txtField = new TextField();

		// Button
		Button btn = new Button();
		btn.setText("sup");
		btn.setOnAction(evt -> System.out.printf("Sup %s!%n", txtField.getText()));


		// Box
		VBox box = new VBox();
		box.getChildren().addAll(txt, txtField, btn);
		root.getChildren().add(box);

		primaryStage.setTitle("Lingo");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}
