package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.ActionController;
import com.eliottvincent.lingo.Controller.LessonController;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Lesson;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javafx.animation.Interpolator.EASE_BOTH;

/**
 * Created by eliottvincent on 28/05/2017.
 */

@ViewController("/fxml/home.fxml")
public class HomeViewController {


	//================================================================================
	// JavaFX elements
	//================================================================================

	@FXML
	public BorderPane container;

	@FXML
	private HBox cardsHBox;

	@FXML
	private ImageView logo;

	@FXML
	private VBox centerVBox;

	@FXML
	private VBox myTopBar;

	@FXML
	private StackPane titleBurgerContainer;

	@FXML
	private JFXHamburger titleBurger;

	@FXML
	private StackPane optionsBurger;

	@FXML
	private JFXRippler optionsRippler;

	@FXML
	private Label userLabel;


	//================================================================================
	// DataFX elements
	//================================================================================

	@ActionHandler
	private FlowActionHandler actionHandler;

	@FXMLViewFlowContext
	private ViewFlowContext flowContext;


	//================================================================================
	// Other properties
	//================================================================================

	public User user;

	private ActionController actionController;

	private JFXPopup toolbarPopup;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 *
	 */
	public HomeViewController() {

		this.actionController = new ActionController();
	}

	@PostConstruct
	public void init() {

		this.user = (User) flowContext.getRegisteredObject("user");

		cardsHBox.getChildren().clear();
		cardsHBox.setSpacing(30);
		cardsHBox.getChildren().addAll(generateLanguageCards());

		logo.setPreserveRatio(true);
		logo.setFitHeight(200);

		container.getChildren().clear();
		container.setCenter(centerVBox);

		try {
			initializeNewTopBar();
		} catch (Exception e) {
			e.printStackTrace();
		}

		VBox.setMargin(logo, new Insets(0,0,100,0));

	}

	/**
	 *
	 * @throws Exception
	 */
	private void initializeNewTopBar() throws Exception {

		userLabel.setText((this.user != null) ? this.user.getUsername() : "Guest");
		titleBurgerContainer.setOnMouseClicked(e -> {
			System.out.println("cliccccck");
		});

		// sometimes we just can't use a Flow :(
		// so we use a classic FXMLLoader
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainpopup.fxml"));
		loader.setController(new InputController(this.user));
		toolbarPopup = new JFXPopup(loader.load());

		optionsBurger.setOnMouseClicked(e -> toolbarPopup.show(optionsBurger,
			JFXPopup.PopupVPosition.TOP,
			JFXPopup.PopupHPosition.RIGHT,
			-12,
			15)
		);

		myTopBar.setMaxWidth(1100);
		myTopBar.setMinWidth(1100.0);
		myTopBar.setPrefWidth(1100.0);
		BorderPane.setAlignment(myTopBar, Pos.CENTER);
		container.setTop(myTopBar);

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

		actionController.createNewAction(
			this.user,
			ActionType.LESSON_START,
			new Date(),
			null,
			ConverterHelper.integerToString(lesson.getId())
		);

		try {
			this.navigate(language, lesson);
		} catch (VetoException | FlowException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @throws VetoException
	 * @throws FlowException
	 */
	private void navigate(Language language, Lesson lesson) throws VetoException, FlowException {

		flowContext.register("user", this.user);
		flowContext.register("language", language);
		flowContext.register("lesson", lesson);

		actionHandler.navigate(LessonViewController.class);
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
		Boolean isLast = false;
		for (Language language: Language.values()) {

			if (i+1 == Language.values().length) {

				isLast = true;
			}

			children.add(generateLanguageCard(language, i, isLast));
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
	private StackPane generateLanguageCard(Language language, Integer i, Boolean isLast) {

		final Integer widthValue = 240;
		final Integer headerHeight = 100;
		final Integer bodyHeight = 75;

		EventHandler<ActionEvent> eventHandler = event ->
			handleLanguageCardClick((Node) event.getSource(), language);

		return generateCard(
			ConverterHelper.languageToString(language),
			eventHandler,
			widthValue,
			headerHeight,
			bodyHeight,
			i,
			getDefaultColor(i),
			null,
			isLast
		);
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
		final Integer bodyHeight = 100;

		EventHandler<ActionEvent> eventHandler = null;

		if (handler != null) {

			eventHandler = handler;
		}
		else {

			eventHandler = event -> handleLanguageCardClick(
					(Node) event.getSource(), language
			);
		}
		String text = ConverterHelper.languageToString(language) + ": This language contains \nthe following lessons:";
		return generateCard(
			text,
			eventHandler,
			widthValue,
			headerHeight,
			bodyHeight,
			i,
			getDefaultColor(i),
			"M15.41 16.09l-4.58-4.59 4.58-4.59L14 5.5l-6 6 6 6z",
			false
		);
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
		Boolean isLast = false;
		for (Lesson lesson : lessons) {

			if (i+1 == lessons.size()) {

				isLast = true;
			}
			children.add(generateLessonCard(lesson, language, i, isLast));
			i++;
		}

		return children;
	}

	/**
	 *
	 * @param lesson
	 * @param language
	 * @param index
	 * @param isLast
	 * @return
	 */
	private StackPane generateLessonCard(Lesson lesson, Language language, Integer index, Boolean isLast) {

		final Integer widthValue = 100;
		final Integer headerHeight = 100;
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
			getDefaultColor(index),
			null,
			isLast
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
	private StackPane generateCard(String title, EventHandler<ActionEvent> eventHandler, Integer widthValue, Integer headerHeight, Integer bodyHeight, Integer index, String colorValue, String svgPath, Boolean isLast) {

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
		JFXButton button = new JFXButton();
		button.setButtonType(JFXButton.ButtonType.RAISED);
		button.setStyle("-fx-text-fill: white; -fx-background-radius: 40;-fx-background-color: " + getDefaultColor(index));
		button.setPrefSize(40, 40);
		button.setRipplerFill(Color.valueOf(colorValue));
		button.setScaleX(0);
		button.setScaleY(0);
		if (svgPath == null) {
			svgPath = "M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z";
		}
		SVGGlyph glyph = new SVGGlyph(-1, "test", svgPath, Color.WHITE);
		glyph.setSize(12.5, 20);
		button.setGraphic(glyph);

		button.translateYProperty().bind(Bindings.createDoubleBinding(() ->
			header.getBoundsInParent().getHeight() - button.getHeight() / 2, header.boundsInParentProperty(), button.heightProperty())
		);
		if (isLast) {
			StackPane.setMargin(button, new Insets(0, 12, 0, 0));
		}
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

	private static final class InputController {

		@FXMLViewFlowContext
		private ViewFlowContext flowContext;

		@FXML
		private JFXListView<?> toolbarPopupList;

		private User user;

		InputController(User user) {

			this.user = user;
		}

		// close application
		@FXML
		private void submit() {

			if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 1) {

				ActionController actionController = new ActionController();

				actionController.createNewAction(
					this.user,
					ActionType.LOGOUT,
					new Date(),
					null,
					null
				);
				Platform.exit();
			}

			else {
				System.out.println("clicked on about");
			}
		}
	}
}
