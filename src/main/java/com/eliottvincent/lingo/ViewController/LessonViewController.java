package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.ActionController;
import com.eliottvincent.lingo.Controller.LessonController;
import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Exercise;
import com.eliottvincent.lingo.Model.Lesson;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.Date;

/**
 * Created by eliottvincent on 12/06/2017.
 */
class LessonViewController {


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
	// Other properties
	//================================================================================

	private ScreenController screenController;

	private LessonController lessonController;

	private ActionController actionController;

	private Language language;

	private Lesson lesson;

	private User user;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 *
	 */
	LessonViewController() {

		this.screenController = ScreenController.getInstance();

		this.lessonController = new LessonController();

		this.actionController = new ActionController();

	}

	/**
	 *
	 * @param language
	 * @param lesson
	 */
	LessonViewController(Language language, Lesson lesson, User user) {

		this.screenController = ScreenController.getInstance();

		this.lessonController = new LessonController();

		this.actionController = new ActionController();

		this.language = language;

		this.lesson = lesson;

		this.user = user;

	}


	@FXML
	public void initialize() {

		// the .clear() call is mandatory!
		// without it, nothing works (???)
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

			// building the tab
			Tab tmpTab = new Tab();
			tmpTab.setText("Exercise n°" + exercise.getId());

			// building the content
			StackPane content = new StackPane();
			Label title = new Label("Exercise n°" + exercise.getId());
			Label exerciseContent = new Label("");

			// adding a button to finish the exercise
			JFXButton finishButton = new JFXButton("Finish exercise");
			finishButton.getStyleClass().add("button-raised");

			EventHandler<ActionEvent> eventHandler = event ->
				finishExerciseAction((Node) event.getSource(), exercise);
			finishButton.setOnAction(eventHandler);

			// wrapping everything together
			content.getChildren().addAll(title, exerciseContent, finishButton );
			tmpTab.setContent(content);
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

		SelectionModel selectionModel = exercisesTabPane.getSelectionModel();
		Integer index = selectionModel.getSelectedIndex();
		if (index == this.lesson.getExercises().size() - 1) {

			dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
			dialog.show((StackPane) source.getParent());
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

		String lessonId =	ConverterHelper.languageToString(language) +
			"_" +
			ConverterHelper.lessonTypeToString(lesson.getType());
		this.actionController.createNewAction(this.user, ActionType.LESSON_END, new Date(), null, lessonId);
		dialog.close();
		HomeViewController homeViewController = new HomeViewController(this.user);

		screenController.activate(source.getScene(), "home", null, homeViewController);
	}



}
