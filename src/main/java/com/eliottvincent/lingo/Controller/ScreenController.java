package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Lingo;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by eliottvct on 27/05/17.
 */
public class ScreenController {

	private HashMap<String, Pane> screenMap;

	public ScreenController() {
		this.screenMap = new HashMap<>();
	}

	public void addScreen(String name, String pathToFXML, Object controller) {

		// create a loader with our pathToFXML as template
		FXMLLoader loader = new FXMLLoader(getClass().getResource(pathToFXML));

		// setting loginView (created above) as controller of this loader
		if (controller != null) {
			loader.setController(controller);
		}

		// "converting" the loader to a Panel object
		try {
			Pane loaderToPane = loader.load();
			screenMap.put(name, loaderToPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeScreen(String name){
		screenMap.remove(name);
	}

	public void activate(Scene scene, String name, Stage stage) {
		Stage mStage;

		scene.setRoot(screenMap.get(name));

		// if the developer wants to override the Stage object
		if (stage != null) {
			mStage = stage;
		}

		// else we get the Stage from the scene
		else {
			mStage = (Stage) scene.getWindow();
		}

		// fonts
		final ObservableList<String> stylesheets = scene.getStylesheets();
		stylesheets.addAll(Lingo.class.getResource("css/fonts.css").toExternalForm());

		mStage.setScene(scene);
		mStage.setResizable(false);
		mStage.show();
	}
}
