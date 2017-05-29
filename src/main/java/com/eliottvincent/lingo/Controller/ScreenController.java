package com.eliottvincent.lingo.Controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by eliottvct on 27/05/17.
 */
public class ScreenController {

	private HashMap<String, Pane> screenMap = new HashMap<>();

	public ScreenController() {

	}

	public void addScreen(String name, Pane pane){
		screenMap.put(name, pane);
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
