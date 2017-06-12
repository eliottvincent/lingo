package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.ActionController;
import com.eliottvincent.lingo.Controller.LessonController;
import com.eliottvincent.lingo.Controller.ScreenController;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Lesson;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.sun.tools.javac.util.Convert;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javafx.animation.Interpolator.EASE_BOTH;

/**
 * Created by eliottvincent on 28/05/2017.
 */
public class HomeViewController {


	//================================================================================
	// JavaFX Elements
	//================================================================================

	@FXML
	private BorderPane container;

	@FXML
	private Label titleLabel;

	@FXML
	private HBox cardsHBox;


	//================================================================================
	// Other properties
	//================================================================================

	private User user;

	private ScreenController screenController;

	private ActionController actionController;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	HomeViewController() {

		this.screenController = ScreenController.getInstance();

	}

	HomeViewController(User user) {

		this.user = user;

		this.screenController = ScreenController.getInstance();

		this.actionController = new ActionController();

	}

	@FXML
	public void initialize() {

		if (this.user != null) {
			titleLabel.setText("Hello " + this.user.getUsername() + "!");
		}
		else {
			titleLabel.setText("Hello guest!");
		}

		container.getChildren().clear();
		cardsHBox.getChildren().clear();
		cardsHBox.setSpacing(10);

		cardsHBox.getChildren().addAll(generateLanguageCards());
		container.setBottom(cardsHBox);

		container.setTop(titleLabel);
	}



	//================================================================================
	// Event Handlers
	//================================================================================

	/**
	 *
	 * @param source
	 * @param language
	 */
	private void handleLanguageCardClick(Node source, Language language) {

		cardsHBox.getChildren().clear();
		cardsHBox.getChildren().addAll(generateLessonsCards(language));
	}

	/**
	 *
	 * @param source
	 */
	private void handleLanguageCardClickBack(Node source) {

		cardsHBox.getChildren().clear();
		cardsHBox.getChildren().addAll(generateLanguageCards());
	}

	/**
	 *
	 * @param node
	 * @param lesson
	 * @param language
	 */
	private void handleLessonCardClick(Node node, Lesson lesson, Language language) {

		String id =	ConverterHelper.languageToString(language) +
					"_" +
					ConverterHelper.lessonTypeToString(lesson.getType());
		actionController.createNewAction(this.user, ActionType.LESSON_START, new Date(), null, id);

		LessonViewController lessonViewController = new LessonViewController(language, lesson, this.user);

		this.screenController.activate(node.getScene(), "lesson", null, lessonViewController);

	}



	//================================================================================
	// Event Actions
	//================================================================================



	//================================================================================
	// Cards generation
	//================================================================================

	/**
	 *
	 * @return
	 */
	private ArrayList<Node> generateLanguageCards() {

		ArrayList<Node> children = new ArrayList<>();

		Integer i = 0;
		for (Language language: Language.values()) {

			children.add(generateLanguageCard(language, i));
			i++;
		}

		return children;
	}

	/**
	 *
	 * @param language
	 * @param i
	 * @return
	 */
	private StackPane generateLanguageCard(Language language, Integer i) {

		final Integer widthValue = 250;
		final Integer headerHeight = 150;
		final Integer bodyHeight = 75;

		EventHandler<ActionEvent> eventHandler = event ->
			handleLanguageCardClick((Node) event.getSource(), language);

		return generateCard(ConverterHelper.languageToString(language), eventHandler, widthValue, headerHeight, bodyHeight, i, getDefaultColor(i));
	}

	/**
	 *
	 * @param language
	 * @param handler
	 * @param i
	 * @return
	 */
	private StackPane generateExpandedLanguageCard(Language language, EventHandler<ActionEvent> handler, Integer i) {

		final Integer widthValue = 250;
		final Integer headerHeight = 75;
		final Integer bodyHeight = 150;

		EventHandler<ActionEvent> eventHandler = null;

		if (handler != null) {

			eventHandler = handler;
		}
		else {

			eventHandler = event ->
				handleLanguageCardClick((Node) event.getSource(), language
				);
		}
		String text = ConverterHelper.languageToString(language) + ": First line\nSecond line";
		return generateCard(text, eventHandler, widthValue, headerHeight, bodyHeight, i, getDefaultColor(i));
	}

	/**
	 *
	 * @param language
	 * @return
	 */
	private ArrayList<Node> generateLessonsCards(Language language) {

		ArrayList<Node> children = new ArrayList<>();

		// first we have to regenerate the language card (because we want to keep it)
		// we also want to change the eventhandler
		EventHandler<ActionEvent> eventHandler = event ->
			handleLanguageCardClickBack((Node) event.getSource());
		children.add(generateExpandedLanguageCard(language, eventHandler, 0));

		// then we generate the lesson cards
		LessonController lessonController = new LessonController();
		List<Lesson> lessons = lessonController.getLessons(language);

		Integer i = 1;
		for (Lesson lesson : lessons) {

			children.add(generateLessonCard(lesson, language, i));
			i++;
		}

		return children;
	}

	private StackPane generateLessonCard(Lesson lesson, Language language, Integer index) {

		final Integer widthValue = 100;
		final Integer headerHeight = 150;
		final Integer bodyHeight = 75;

		EventHandler<ActionEvent> eventHandler = event ->
			handleLessonCardClick((Node) event.getSource(), lesson, language
		);

		return generateCard(
			ConverterHelper.lessonTypeToString(lesson.getType()),
			eventHandler,
			widthValue,
			headerHeight,
			bodyHeight,
			index,
			getDefaultColor(index)
		);
	}


	/**
	 *
	 * @param title
	 * @param eventHandler
	 * @param widthValue
	 * @param headerHeight
	 *@param bodyHeight
	 * @param index
	 * @param colorValue   @return
	 */
	private StackPane generateCard(String title, EventHandler<ActionEvent> eventHandler, Integer widthValue, Integer headerHeight, Integer bodyHeight, Integer index, String colorValue) {

		// creating container
		StackPane child = new StackPane();
		child.setMinWidth(widthValue);
		child.setMaxWidth(widthValue);
		child.setPrefWidth(widthValue);
		JFXDepthManager.setDepth(child, 1);

		// creating content
		StackPane header = new StackPane();
		header.setMinHeight(headerHeight);
		header.setMaxHeight(headerHeight);
		header.setPrefHeight(headerHeight);
		header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + colorValue);
		VBox.setVgrow(header, Priority.ALWAYS);
		StackPane body = new StackPane();
		body.setMinHeight(bodyHeight);
		body.setMaxHeight(bodyHeight);
		body.setPrefHeight(bodyHeight);
		body.getChildren().add(new Label(title));
		VBox content = new VBox();
		content.getChildren().addAll(header, body);
		body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");

		// create button
		JFXButton button = new JFXButton("GO");
		button.setButtonType(JFXButton.ButtonType.RAISED);
		button.setStyle("-fx-text-fill: white; -fx-background-radius: 40;-fx-background-color: " + getDefaultColor(index));
		button.setPrefSize(40, 40);
		button.setRipplerFill(Color.valueOf(colorValue));
		button.setScaleX(0);
		button.setScaleY(0);
		SVGGlyph glyph = new SVGGlyph(-1,
			"test",
			"M1008 6.286q18.857 13.714 15.429 36.571l-146.286 877.714q-2.857 16.571-18.286 25.714-8 4.571-17.714 4.571-6.286 "
				+ "0-13.714-2.857l-258.857-105.714-138.286 168.571q-10.286 13.143-28 13.143-7.429 "
				+ "0-12.571-2.286-10.857-4-17.429-13.429t-6.571-20.857v-199.429l493.714-605.143-610.857 "
				+ "528.571-225.714-92.571q-21.143-8-22.857-31.429-1.143-22.857 18.286-33.714l950.857-548.571q8.571-5.143 18.286-5.143"
				+ " 11.429 0 20.571 6.286z",
			Color.WHITE);
		glyph.setSize(20, 20);
		//button.setGraphic(glyph);
		button.translateYProperty().bind(Bindings.createDoubleBinding(() ->
			header.getBoundsInParent().getHeight() - button.getHeight() / 2, header.boundsInParentProperty(), button.heightProperty())
		);
		StackPane.setMargin(button, new Insets(0, 12, 0, 0));
		StackPane.setAlignment(button, Pos.TOP_RIGHT);

		// button action
		button.setOnAction(eventHandler);

		Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
			new KeyValue(button.scaleXProperty(), 1, EASE_BOTH),
			new KeyValue(button.scaleYProperty(), 1, EASE_BOTH))
		);
		animation.setDelay(Duration.millis(100 * index + 250));
		animation.play();
		child.getChildren().addAll(content, button);

		return child;
	}

	private String getDefaultColor(int i) {
		String color = "#FFFFFF";
		switch (i) {
			case 0:
				color = "#8F3F7E";
				break;
			case 1:
				color = "#B5305F";
				break;
			case 2:
				color = "#CE584A";
				break;
			case 3:
				color = "#DB8D5C";
				break;
			case 4:
				color = "#DA854E";
				break;
			case 5:
				color = "#E9AB44";
				break;
			case 6:
				color = "#FEE435";
				break;
			case 7:
				color = "#99C286";
				break;
			case 8:
				color = "#01A05E";
				break;
			case 9:
				color = "#4A8895";
				break;
			case 10:
				color = "#16669B";
				break;
			case 11:
				color = "#2F65A5";
				break;
			case 12:
				color = "#4E6A9C";
				break;
			default:
				break;
		}
		return color;
	}
}
