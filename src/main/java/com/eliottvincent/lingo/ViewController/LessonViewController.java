package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.ActionController;
import com.eliottvincent.lingo.Controller.LessonController;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Exercise;
import com.eliottvincent.lingo.Model.Lesson;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;

/**
 * Created by eliottvincent on 12/06/2017.
 */

@ViewController("/fxml/lesson.fxml")
public class LessonViewController {


	//================================================================================
	// JavaFX Elements
	//================================================================================

	@FXML
	private BorderPane container;

	@FXML
	private Label titleLabel;

	@FXML
	private JFXTabPane exercisesTabPane;

	@FXML
	private JFXDialog dialog;

	@FXML
	private JFXButton stayButton;

	@FXML
	private JFXButton leaveButton;


	//================================================================================
	// DataFX Elements
	//================================================================================

	@ActionHandler
	private FlowActionHandler actionHandler;

	@FXMLViewFlowContext
	private ViewFlowContext flowContext;


	//================================================================================
	// Other properties
	//================================================================================

	private LessonController lessonController;

	private ActionController actionController;

	private Language language;

	private Lesson lesson;

	private User user;

	private Integer points;

	private Random randomResult;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 *
	 */
	public LessonViewController() {

		this.lessonController = new LessonController();

		this.actionController = new ActionController();

		this.points = 0;

		this.randomResult = new Random();
	}


	@PostConstruct
	public void init() {

		// cannot be done in the constructor, because FXML injections are done just before the init() method
		this.language = (Language) flowContext.getRegisteredObject("language");

		this.lesson = (Lesson) flowContext.getRegisteredObject("lesson");

		this.user = (User) flowContext.getRegisteredObject("user");

		// the .clear() call is mandatory!
		// without it, nothing works (???)

		// removing the dialog, because we want to display it only at the end
		container.getChildren().remove(dialog);
		container.getChildren().clear();

		initializeExercisesTabPane();

		titleLabel.setText(ConverterHelper.languageToString(language) + ": " + ConverterHelper.lessonTypeToString(lesson.getType()) + " lesson");
		container.setTop(titleLabel);

		initializeDialogButtons();


	}

	/**
	 *
	 */
	private void initializeExercisesTabPane() {

		for(Exercise exercise : this.lesson.getExercises()) {

			// TODO : maybe it's possible to add a FXML file as a TabPane content ?
			// building the tab
			Tab tmpTab = new Tab();
			tmpTab.setText("Exercise n°" + exercise.getId());

			// building the container
			StackPane container = new StackPane();

			// building the content
			VBox content = new VBox();
			content.setAlignment(Pos.CENTER);

			// building the content
			Label title = new Label("Exercise n°" + exercise.getId());
			Label exerciseContent = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n" +
				"Vivamus convallis turpis in tellus sagittis maximus. Aliquam eu lorem ipsum. \n" +
				"ras sollicitudin lacinia nisi, eu sollicitudin ex consequat sed. \n" +
				"Aenean eu volutpat odio. Ut et felis nisl. Aenean euismod augue tellus, nec finibus ante pellentesque et. \n" +
				"Sed quam ligula, ullamcorper ac mattis vel, malesuada nec neque. \n" +
				"Praesent sagittis mattis feugiat. Praesent ut dolor nec nisl tincidunt mattis eu sed nisl. \n" +
				"Maecenas at ornare lacus. Curabitur posuere cursus arcu, vel mollis neque rhoncus nec. \n" +
				"In sem justo, consequat quis nibh vel, egestas lacinia lectus. Ut est lacus, consequat quis libero at, commodo hendrerit dolor. \n" +
				"Pellentesque sem felis, maximus auctor lacus a, finibus feugiat urna.");

			// adding a textfield for the answer
			JFXTextField answerField = new JFXTextField();

			// adding a button to finish the exercise
			JFXButton finishButton = new JFXButton("Finish exercise");
			finishButton.getStyleClass().add("button-raised");

			EventHandler<ActionEvent> eventHandler = event ->
				finishExerciseAction((Node) event.getSource(), exercise);
			finishButton.setOnAction(eventHandler);

			// wrapping everything together
			content.getChildren().addAll(title, exerciseContent, answerField, finishButton );
			container.getChildren().add(content);

			tmpTab.setContent(container);
			exercisesTabPane.getTabs().add(tmpTab);
		}

		container.setCenter(exercisesTabPane);
	}

	/**
	 *
	 */
	private void initializeDialogButtons() {

		EventHandler<ActionEvent> eventHandler = event ->
			leaveAction((Node) event.getSource());
		leaveButton.setOnAction(eventHandler);

		EventHandler<ActionEvent> eventHandler1 = event ->
			stayAction((Node) event.getSource());
		stayButton.setOnAction(eventHandler1);
	}



	//================================================================================
	// Event Actions
	//================================================================================

	/**
	 *
	 * @param source
	 * @param exercise
	 */
	private void finishExerciseAction(Node source, Exercise exercise) {

		// action for the end of the exercise
		this.actionController.createNewAction(this.user, ActionType.EXERCICE_END, new Date(), ConverterHelper.integerToString(exercise.getId()), null);

		// since the exercises are fake, we just do a randomize result
		if (this.getRandomBoolean()) {

			this.points ++;
		}
		else {

			this.points --;
		}

		SelectionModel selectionModel = exercisesTabPane.getSelectionModel();
		Integer index = selectionModel.getSelectedIndex();
		if (index == this.lesson.getExercises().size() - 1) {

			dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
			dialog.show((StackPane) source.getParent().getParent());
		}
		else {

			// action for the start of the new exercise
			this.actionController.createNewAction(this.user, ActionType.EXERCICE_START, new Date(), ConverterHelper.integerToString(exercise.getId()) + 1, null);

			selectionModel.clearAndSelect(index + 1);
		}
	}

	private void stayAction(Node source) {

		dialog.close();
	}

	private void leaveAction(Node source) {

		String lessonId = ConverterHelper.languageToString(language) +
			"_" +
			ConverterHelper.lessonTypeToString(lesson.getType());
		this.actionController.createNewAction(this.user, ActionType.LESSON_END, new Date(), null, lessonId);

		dialog.close();

		try {
			this.navigateToHome();
		} catch (VetoException | FlowException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @throws VetoException
	 * @throws FlowException
	 */
	void navigateToHome() throws VetoException, FlowException {

		actionHandler.navigate(HomeViewController.class);
	}

	public boolean getRandomBoolean() {

		return randomResult.nextBoolean();
	}



}
