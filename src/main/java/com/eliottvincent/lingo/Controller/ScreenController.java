package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Lingo;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by eliottvct on 27/05/17.
 */
public class ScreenController {

	private HashMap<String, String> screenMap;

	private static ScreenController instance;

	private ScreenController() {

		this.screenMap = new HashMap<>();
	}

	public static ScreenController getInstance(){

		if(instance == null) {
			instance = new ScreenController();
		}
		return instance;
	}

	/**
	 *  @param name
	 * @param pathToFXML
	 */
	public void addScreen(String name, String pathToFXML) {


		screenMap.put(name, pathToFXML);
	}

	/**
	 *
	 * @param scene
	 * @param name
	 * @param stage
	 * @param controller
	 */
	public void activate(Scene scene, String name, Stage stage, Object controller) {
		Stage mStage;

		// getting the corresponding path in ScreenMap
		String pathToFXML = screenMap.get(name);

		// create a loader with our pathToFXML as template
		FXMLLoader loader = new FXMLLoader(getClass().getResource(pathToFXML));

		// setting the controller as controller of this loader
		if (controller != null) {
			loader.setController(controller);
		}

		Pane loaderToPane = null;
		try {
			loaderToPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene.setRoot(loaderToPane);

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
