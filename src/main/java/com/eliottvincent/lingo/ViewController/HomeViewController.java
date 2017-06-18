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
 * <b>HomeViewController is the class responsible for the actions performed on a the Home view.</b>
 *
 * @author eliottvincent
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
	private JFXToolbar homeJFXToolbar;

	@FXML
	private StackPane homeButtonContainer;

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

	private static final String SVG_RIGHT_ARROW = "M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z";

	private static final String SVG_LEFT_ARROW = "M15.41 16.09l-4.58-4.59 4.58-4.59L14 5.5l-6 6 6 6z";

	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 * The default constructor for the HomeViewController.
	 */
	public HomeViewController() {

		this.actionController = new ActionController();
	}

	/**
	 * the init() method is responsible for doing the necessary initialization of some components.
	 * By adding the @PostConstruct annotation to the method, the DataFX flow container will call this method once all injectable values of the controller instance are injected.
	 */
	@PostConstruct
	public void init() {

		// getting the current User from the ViewFlowContext
		this.user = (User) flowContext.getRegisteredObject("user");

		// initializing the language cards
		this.initializeLanguageCards();

		// doing some stuff to the logo
		logo.setPreserveRatio(true);
		logo.setFitHeight(200);
		VBox.setMargin(logo, new Insets(0,0,100,0));

		// initializing the toolbar
		try {
			initializeTopBar();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * the initializeTopBar() is responsible for initializing the toolbar.
	 *
	 * @throws Exception the exception to throw, if any.
	 */
	private void initializeTopBar() throws Exception {

		// ternary expression to fill the userLabel either with the username of the current User, either with "Guest"
		userLabel.setText((this.user != null) ? this.user.getUsername() : "Guest");

		// adding a listener on the home button
		homeButtonContainer.setOnMouseClicked(e -> {

			// creating a new Action with "LOGOUT" type
			actionController.createNewAction(
				this.user,
				ActionType.LOGOUT,
				new Date(),
				null,
				null
			);

			// going back to the login view
			try {
				actionHandler.navigate(LoginViewController.class);
			} catch (VetoException | FlowException e1) {
				e1.printStackTrace();
			}
		});

		// loading the popup content
		// there are cases in which we can't use a Flow, like this one
		// so we are forced to use a classic FXMLLoader
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainpopup.fxml"));
		loader.setController(new PopUpController(this.user));
		toolbarPopup = new JFXPopup(loader.load());

		// adding a listener to handle clicks on the different
		optionsBurger.setOnMouseClicked(e -> toolbarPopup.show(optionsBurger,
			JFXPopup.PopupVPosition.TOP,
			JFXPopup.PopupHPosition.RIGHT,
			-12,
			15)
		);

		BorderPane.setAlignment(homeJFXToolbar, Pos.CENTER);
		container.setTop(homeJFXToolbar);
	}


	//================================================================================
	// Event Handlers
	//================================================================================

	/**
	 * the handleLanguageCardClick() method is responsible for performing the necessary actions when the user clicks on a language card.
	 *
	 * @param source the source of the event.
	 * @param language the Language concerned by the clicked card.
	 */
	private void handleLanguageCardClick(Node source, Language language) {

		initializeLessonsCards(language);
	}

	/**
	 * the handleLanguageCardClickBack() method is responsible for performing the necessary actions when the user clicks on the "back" button of a language card.
	 *
	 * @param source the source of the event.
	 */
	private void handleLanguageCardClickBack(Node source) {

		this.initializeLanguageCards();
	}

	/**
	 * the handleLessonCardClick() method is responsible for performing the necessary actions when the user clicks on a lesson card.
	 *
	 * @param node the source of the event.
	 * @param lesson the Lesson concerned by the clicked card.
	 * @param language the Language concerned by the clicked card.
	 */
	private void handleLessonCardClick(Node node, Lesson lesson, Language language) {

		// as the user is going to start a new lesson
		// we save this action
		actionController.createNewAction(
			this.user,
			ActionType.LESSON_START,
			new Date(),
			null,
			ConverterHelper.integerToString(lesson.getId())
		);

		// then we open the Lesson View
		try {
			this.navigateToLessonView(language, lesson);
		} catch (VetoException | FlowException e) {
			e.printStackTrace();
		}
	}

	/**
	 * the navigateToLessonView() method is responsible of opening the Lesson View.
	 *
	 * @throws VetoException the VetoException to throw, if any.
	 * @throws FlowException the FlowException to throw, if any.
	 */
	private void navigateToLessonView(Language language, Lesson lesson) throws VetoException, FlowException {

		// saving the needed objects in the ViewFlowContext
		flowContext.register("user", this.user);
		flowContext.register("language", language);
		flowContext.register("lesson", lesson);

		// opening the Lesson View, thanks to the FlowActionHandler
		actionHandler.navigate(LessonViewController.class);
	}


	//================================================================================
	// Cards generation
	//================================================================================

	/**
	 * the initializeLanguageCards() method is responsible for generating the different language cards.
	 */
	private void initializeLanguageCards() {

		ArrayList<Node> children = new ArrayList<>();

		Integer i = 0;
		for (Language language: Language.values()) {

			children.add(generateLanguageCard(language, i));
			i++;
		}

		cardsHBox.getChildren().clear();
		cardsHBox.getChildren().addAll(children);
	}

	/**
	 * the generateLanguageCard() method is responsible for generating a specific language card.
	 *
	 * @param language the Language of the card to generate.
	 * @param i the index of the card to generate.
	 */
	private StackPane generateLanguageCard(Language language, Integer i) {

		final Integer widthValue = 240;
		final Integer headerHeight = 100;
		final Integer bodyHeight = 75;

		EventHandler<ActionEvent> eventHandler = event -> handleLanguageCardClick((Node) event.getSource(), language);

		return generateCard(
			ConverterHelper.languageToString(language),
			eventHandler,
			widthValue,
			headerHeight,
			bodyHeight,
			i,
			getDefaultColor(i),
			null
		);
	}

	/**
	 * the generateExpandedLanguageCard() method is responsible for generating an expanded language card.
	 *
	 * @param language the Language of the card to generate.
	 * @return the generated card, as a StackPane.
	 */
	private StackPane generateExpandedLanguageCard(Language language) {

		final Integer widthValue = 250;
		final Integer headerHeight = 75;
		final Integer bodyHeight = 100;

		EventHandler<ActionEvent> eventHandler = event -> handleLanguageCardClickBack((Node) event.getSource());

		String text = "Want to learn " + ConverterHelper.languageToString(language) + "?\nChoose among the following lessons:";
		return generateCard(
			text,
			eventHandler,
			widthValue,
			headerHeight,
			bodyHeight,
			0,
			getDefaultColor(0),
			SVG_LEFT_ARROW
		);
	}

	/**
	 * the initializeLessonsCards() method is responsible for generating the different lesson cards.
	 * @param language the Language of the card to generate.
	 */
	private void initializeLessonsCards(Language language) {

		ArrayList<Node> children = new ArrayList<>();

		// first we have to regenerate the language card (because we want to keep it)
		children.add(generateExpandedLanguageCard(language));

		// then we generate the lesson cards
		LessonController lessonController = new LessonController();
		List<Lesson> lessons = lessonController.getLessons(language);

		Integer i = 1;
		for (Lesson lesson : lessons) {

			children.add(generateLessonCard(lesson, language, i));
			i++;
		}

		cardsHBox.getChildren().clear();
		cardsHBox.getChildren().addAll(children);
	}

	/**
	 * the generateLessonCard() method is responsible for generating a specific lesson card.
	 *
	 * @param lesson the Lesson of the card to generate.
	 * @param language the Language of the card to generate.
	 * @param index the index of the card to generate.
	 * @return the generated card, as a StackPane.
	 */
	private StackPane generateLessonCard(Lesson lesson, Language language, Integer index) {

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
			null
		);
	}

	/**
	 * the generateCard() method is responsible for generating a specific card.
	 *
	 * @param title the title of the card to generate.
	 * @param eventHandler the eventHandler of the card to generate.
	 * @param widthValue the widthValue of the card to generate.
	 * @param headerHeight the headerHeight of the card to generate.
	 * @param bodyHeight the bodyHeight of the card to generate.
	 * @param index the index of the card to generate.
	 * @param colorValue the colorValue of the card to generate.
	 * @return the generated card, as a StackPane.
	 */
	private StackPane generateCard(String title, EventHandler<ActionEvent> eventHandler, Integer widthValue, Integer headerHeight, Integer bodyHeight, Integer index, String colorValue, String svgPath) {

		// creating the container for the card
		StackPane child = new StackPane();
		child.setMinWidth(widthValue);
		child.setMaxWidth(widthValue);
		child.setPrefWidth(widthValue);
		JFXDepthManager.setDepth(child, 1);

		// creating the content of the card
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

		// creating the button
		JFXButton button = new JFXButton();
		button.setButtonType(JFXButton.ButtonType.RAISED);
		button.setStyle("-fx-text-fill: white; -fx-background-radius: 40;-fx-background-color: " + getDefaultColor(index));
		button.setPrefSize(40, 40);
		button.setRipplerFill(Color.valueOf(colorValue));
		button.setScaleX(0);
		button.setScaleY(0);
		if (svgPath == null) {
			svgPath = SVG_RIGHT_ARROW;
		}
		SVGGlyph glyph = new SVGGlyph(-1, "test", svgPath, Color.WHITE);
		glyph.setSize(12.5, 20);
		button.setGraphic(glyph);
		button.translateYProperty().bind(Bindings.createDoubleBinding(() ->
			header.getBoundsInParent().getHeight() - button.getHeight() / 2, header.boundsInParentProperty(), button.heightProperty())
		);
		StackPane.setAlignment(button, Pos.TOP_RIGHT);

		// adding the EventHandler to the button
		button.setOnAction(eventHandler);

		// adding a little animation
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
			new KeyValue(button.scaleXProperty(), 1, EASE_BOTH),
			new KeyValue(button.scaleYProperty(), 1, EASE_BOTH))
		);
		animation.setDelay(Duration.millis(100 * index + 250));
		animation.play();

		// wrapping everything up
		child.getChildren().addAll(content, button);
		return child;
	}

	/**
	 * the getDefaultColor() method is responsible for returning a specific color value according to an index.
	 *
	 * @param i the index of the desired color.
	 * @return a String containing the hexadecimal value of the color.
	 */
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

	/**
	 * <b>PopUpController is the class responsible for the actions performed on the popup.</b>
	 *
	 * @author eliottvincent
	 */
	private static final class PopUpController {


		//================================================================================
		// JavaFX elements
		//================================================================================

		@FXML
		private JFXListView<?> toolbarPopupList;


		//================================================================================
		// DataFX elements
		//================================================================================

		@FXMLViewFlowContext
		private ViewFlowContext flowContext;


		//================================================================================
		// Other properties
		//================================================================================

		private User user;

		private ActionController actionController;


		//================================================================================
		// Constructor and initialization
		//================================================================================

		/**
		 * The default constructor for the PopUpController.
		 *
		 * @param user the user concerned.
		 */
		PopUpController(User user) {

			this.user = user;

			this.actionController = new ActionController();
		}

		/**
		 * the submit() method is responsible for performing the necessary actions when the user clicks on an item of the popup.
		 */
		@FXML
		private void submit() {

			if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 1) {

				// as the user clicked on the "logout" item
				// we create a new "LOGOUT" action
				this.actionController.createNewAction(
					this.user,
					ActionType.LOGOUT,
					new Date(),
					null,
					null
				);

				// and we quit the application
				Platform.exit();
			}

			else {
				System.out.println("clicked on about");
			}
		}
	}
}
