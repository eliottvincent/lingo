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
public class Main extends Application
{
    /*public static void main( String[] args )
    {
		new ApplicationController();
    }*/

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));

		/*Group root = new Group();

		// Text
		Text txt = new Text();
		txt.setText("Say sup!");
		txt.setY(50);

		// Text field
		TextField txtField = new TextField();

		// Label
		Label lbl = new Label("Name:");

		// Button
		Button btn = new Button();
		btn.setText("sup");
		btn.setOnAction(evt -> System.out.printf("Sup %s!%n", txtField.getText()));

		// GridPane
		GridPane grid = new GridPane();
		grid.setGridLinesVisible(true);
		grid.add(lbl, 0, 0);
		grid.add(txtField, 1, 0);
		grid.setHgap(20);
		grid.add(btn, 1, 1);

		// Box
		VBox box = new VBox();
		box.getChildren().addAll(txt, grid);
		root.getChildren().add(box);
		*/

		primaryStage.setTitle("Lingo");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}
