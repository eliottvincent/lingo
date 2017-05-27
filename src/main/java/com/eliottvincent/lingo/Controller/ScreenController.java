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

	public void activate(Scene scene, String name) {
		scene.setRoot(screenMap.get(name));

		Stage stage = (Stage) scene.getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

}
