package com.eliottvincent.lingo;

import com.eliottvincent.lingo.ViewController.HomeViewController;
import com.eliottvincent.lingo.ViewController.LoginViewController;
import com.jfoenix.controls.JFXDecorator;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Hello world!
 *
 */
public class Lingo extends Application {

	@FXMLViewFlowContext
	public ViewFlowContext flowContext;

	//================================================================================
	// Main and initialization
	//================================================================================

	/**
	 *
	 * @param stage
	 */
	@Override
	public void start(Stage stage) throws Exception {


		Flow flow = new Flow(LoginViewController.class);

		DefaultFlowContainer container = new DefaultFlowContainer();
		flowContext = new ViewFlowContext();
		flowContext.register("Stage", stage);
		flowContext.register("myvalue", "wahoueeee");
		flow.createHandler(flowContext).start(container);

		JFXDecorator decorator = new JFXDecorator(stage, container.getView());
		decorator.setCustomMaximize(true);
		Scene scene = new Scene(decorator, 1500, 750);
		final ObservableList<String> stylesheets = scene.getStylesheets();

		stylesheets.addAll(
			Lingo.class.getResource("/css/fonts.css").toExternalForm(),
			Lingo.class.getResource("/css/home.css").toExternalForm()
		);
		stage.setMinWidth(1500);
		stage.setMinHeight(750);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		launch(args);
	}

}
