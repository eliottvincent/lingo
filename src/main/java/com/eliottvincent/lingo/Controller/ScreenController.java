package com.eliottvincent.lingo.Controller;

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

	private HashMap<String, Pane> screenMap = new HashMap<>();

	public ScreenController() {

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

		mStage.setScene(scene);
		mStage.setResizable(false);
		mStage.show();
	}

}
