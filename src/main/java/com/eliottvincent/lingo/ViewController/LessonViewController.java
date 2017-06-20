package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.ActionController;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Exercise;
import com.eliottvincent.lingo.Model.Lesson;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * <b>LessonViewController is the class responsible for the Lesson view.</b>
 *
 * @author eliottvincent
 */
@ViewController("/fxml/lesson.fxml")
public class LessonViewController {


	//================================================================================
	// JavaFX Elements
	//================================================================================

	@FXML
	private StackPane root;

	@FXML
	private BorderPane container;

	@FXML
	private VBox contentVBox;

	@FXML
	private HBox topLabelsHBox;

	@FXML
	private Label titleLabel;

	@FXML
	private Label pointsLabel;

	@FXML
	private JFXTabPane exercisesTabPane;

	@FXML
	private JFXDialog dialog;

	@FXML
	private JFXDialog helpDialog;

	@FXML
	private JFXButton stayButton;

	@FXML
	private JFXButton leaveButton;

	@FXML
	private JFXToolbar lessonJFXToolbar;

	@FXML
	private StackPane homeButtonContainer;

	@FXML
	private StackPane optionsBurger;

	@FXML
	private JFXRippler optionsRippler;

	@FXML
	private JFXButton okButton;

	@FXML
	private Label usernameLabel;


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

	private ActionController actionController;

	private Language language;

	private Lesson lesson;

	private User user;

	private Integer points;

	private Random randomResult;

	private JFXPopup toolbarPopup;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 * The default constructor for the LessonViewController.
	 */
	public LessonViewController() {

		this.actionController = new ActionController();

		this.points = 0;

		this.randomResult = new Random();
	}

	/**
	 * the init() method is responsible for doing the necessary initialization of some components.
	 * By adding the @PostConstruct annotation to the method, the DataFX flow container will call this method once all injectable values of the controller instance are injected.
	 */
	@PostConstruct
	public void init() {

		// getting some objects from the ViewFlowContext
		this.language = (Language) flowContext.getRegisteredObject("language");
		this.lesson = (Lesson) flowContext.getRegisteredObject("lesson");
		this.user = (User) flowContext.getRegisteredObject("user");

		// setting the Label text
		usernameLabel.setText((this.user != null) ? this.user.getUsername() : "Guest");

		// initializing the toolbar
		try {
			initializeTopBar();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// removing the dialog, because we want to display it only at the end
		container.getChildren().remove(dialog);
		container.getChildren().remove(helpDialog);

		// adding EventHandlers to the dialog buttons
		EventHandler<ActionEvent> eventHandler = event -> onLeave((Node) event.getSource());
		leaveButton.setOnAction(eventHandler);
		EventHandler<ActionEvent> eventHandler1 = event -> onStay((Node) event.getSource());
		stayButton.setOnAction(eventHandler1);

		initializeExercisesTabPane();
	}

	/**
	 * the initializeTopBar() is responsible for initializing the toolbar.
	 *
	 * @throws Exception the exception to throw, if any.
	 */
	private void initializeTopBar() throws Exception {

		// adding a listener on the home button
		homeButtonContainer.setOnMouseClicked(e -> {

			// going back to the login view
			try {
				actionHandler.navigate(HomeViewController.class);
			} catch (VetoException | FlowException e1) {
				e1.printStackTrace();
			}
		});

		// loading the popup content
		// there are cases in which we can't use a Flow, like this one
		// so we are forced to use a classic FXMLLoader
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainpopup.fxml"));
		loader.setController(new LessonViewController.LessonPopUpController(helpDialog ,root));
		toolbarPopup = new JFXPopup(loader.load());

		// adding a listener to handle clicks on the different
		optionsBurger.setOnMouseClicked(e -> toolbarPopup.show(optionsBurger,
			JFXPopup.PopupVPosition.TOP,
			JFXPopup.PopupHPosition.RIGHT,
			-12,
			15)
		);

		BorderPane.setAlignment(lessonJFXToolbar, Pos.CENTER);
		container.setTop(lessonJFXToolbar);

		// adding EventHandler to the dialog button
		EventHandler<ActionEvent> eventHandler = event -> onOk((Node) event.getSource());
		okButton.setOnAction(eventHandler);
	}

	/**
	 * the initializeExercisesTabPane() method is responsible for building the TabPane containing the different exercises of the current lesson.
	 */
	private void initializeExercisesTabPane() {

		for(Exercise exercise : this.lesson.getExercises()) {

			// TODO : maybe it's possible to add a FXML file as a TabPane content?

			// building the tab
			Tab tmpTab = new Tab();
			tmpTab.setText("Exercise n°" + exercise.getId());

			// building the container
			StackPane exerciseContainerStackPane = new StackPane();

			// building the content
			VBox exerciseContentVBox = new VBox();
			exerciseContentVBox.setAlignment(Pos.CENTER);
			Label title = new Label("Exercise n°" + exercise.getId());
			title.setStyle("-fx-font-size: 20px; -fx-padding: 0 0 20px 0; -fx-border-insets: 0 0 20px 0; -fx-background-insets: 0 0 20px 0;");

			Label exerciseLabel = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n" +
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
			answerField.setId("answerField");
			answerField.setPromptText("Enter your answer");

			// adding a validator to the answer field
			RequiredFieldValidator validator = new RequiredFieldValidator();
			answerField.setStyle("-fx-label-float:true;");
			validator.setMessage("Input required");
			validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
				.glyph(FontAwesomeIcon.WARNING)
				.size("1em")
				.styleClass("error")
				.build());
			answerField.getValidators().add(validator);
			answerField.focusedProperty().addListener((o, oldVal, newVal) -> {
				if (!newVal) {
					answerField.validate();
				}
			});

			// adding a button to finish the exercise
			JFXButton finishButton = new JFXButton("Submit my answer");
			finishButton.getStyleClass().add("button-raised");
			EventHandler<ActionEvent> eventHandler = event -> onFinishExerciseAction((Node) event.getSource(), exercise, answerField);
			finishButton.setOnAction(eventHandler);

			HBox userHBox = new HBox();
			userHBox.setAlignment(Pos.CENTER);
			userHBox.setSpacing(30);
			userHBox.getChildren().setAll(answerField, finishButton);
			userHBox.setStyle("-fx-padding: 50px 0 0 0; -fx-border-insets: 50px 0 0 0; -fx-background-insets: 50px 0 0 0;");
			StackPane.setMargin(userHBox, new Insets(50, 0, 0, 0));

			// wrapping everything up
			exerciseContentVBox.setSpacing(20);
			exerciseContentVBox.setStyle("-fx-padding: 40px 0 0 0; -fx-border-insets: 40px 0 0 0; -fx-background-insets: 40px 0 0 0;");
			exerciseContentVBox.getChildren().setAll(title, exerciseLabel, userHBox);
			exerciseContainerStackPane.getChildren().add(exerciseContentVBox);

			tmpTab.setContent(exerciseContainerStackPane);
			exercisesTabPane.getTabs().add(tmpTab);
		}

		// filling the top labels
		titleLabel.setText(ConverterHelper.languageToString(language) + ": " + ConverterHelper.lessonTypeToString(lesson.getType()) + " lesson");
		pointsLabel.setText("Score: 0");
		topLabelsHBox.getChildren().setAll(titleLabel, pointsLabel);
		topLabelsHBox.setSpacing(750);

		container.setCenter(contentVBox);
		BorderPane.setAlignment(contentVBox, Pos.CENTER);
	}


	//================================================================================
	// Event actions
	//================================================================================

	/**
	 * the onFinishExerciseAction() method is responsible for performing the necessary actions when the user clicks on the "Finish" button for an exercise.
	 *  @param source the source of the event.
	 * @param exercise the Exercise concerned by the clicked button.
	 * @param answerField the TextField for the answer.
	 */
	private void onFinishExerciseAction(Node source, Exercise exercise, JFXTextField answerField) {

		if (!Objects.equals(answerField.getText(), "") && !Objects.equals(answerField.getText(), null)) {

			// as the user finished an exercise
			// we create an "EXERCICE_END" Action
			this.actionController.createNewAction(this.user, ActionType.EXERCICE_END, new Date(), ConverterHelper.integerToString(exercise.getId()), null);

			SelectionModel selectionModel = exercisesTabPane.getSelectionModel();
			Integer index = selectionModel.getSelectedIndex();

			// if we're at the end of the tabs
			if (index == this.lesson.getExercises().size() - 1) {

				// we display the dialog
				dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
				dialog.show(root);
			}

			else {

				// since the exercises are fake, we just create a randomized result
				if (this.getRandomBoolean()) {

					this.points += exercise.getPoints();
				}

				// updating the label
				pointsLabel.setText("");
				pointsLabel.setText("Score: " +  this.points);

				// action for the start of the new exercise
				this.actionController.createNewAction(this.user, ActionType.EXERCICE_START, new Date(), ConverterHelper.integerToString(exercise.getId()) + 1, null);

				selectionModel.clearAndSelect(index + 1);
			}
		}

		else {

			answerField.validate();
		}
	}

	/**
	 * the onStay() method is responsible for performing the necessary action whe the user clicks on the "Stay" button of the dialog.
	 * @param source the source of the event.
	 */
	private void onStay(Node source) {

		dialog.close();
	}

	/**
	 * the onLeave() method is responsible for performing the necessary action whe the user clicks on the "Leave" button of the dialog.
	 * @param source the source of the event.
	 */
	private void onLeave(Node source) {

		String lessonId = ConverterHelper.languageToString(language) +
			"_" +
			ConverterHelper.lessonTypeToString(lesson.getType());

		// since the user quits the lesson
		// we create a "LESSON_END" action
		this.actionController.createNewAction(
			this.user,
			ActionType.LESSON_END,
			new Date(),
			null,
			lessonId);

		dialog.close();

		try {
			this.navigateToHome();
		} catch (VetoException | FlowException e) {
			e.printStackTrace();
		}
	}

	/**
	 * the onOk() method is responsible for performing the necessary action whe the user clicks on the "Ok" button of the dialog.
	 * @param source the source of the event.
	 */
	private void onOk(Node source) {

		helpDialog.close();
	}

	/**
	 * the navigateToHome() method is responsible of opening the Home View.
	 *
	 * @throws VetoException the VetoException to throw, if any.
	 * @throws FlowException the FlowException to throw, if any.
	 */
	private void navigateToHome() throws VetoException, FlowException {

		actionHandler.navigate(HomeViewController.class);
	}

	/**
	 * the getRandomBoolean() is responsible of returning a randomized Boolean value.
	 * @return the randomized boolean value.
	 */
	private boolean getRandomBoolean() {

		return randomResult.nextBoolean();
	}


	/**
	 * <b>HomePopUpController is the class responsible for the actions performed on the popup.</b>
	 *
	 * @author eliottvincent
	 */
	private static final class LessonPopUpController {


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

		private JFXDialog dialog;

		private StackPane root;

		private ActionController actionController;


		//================================================================================
		// Constructor and initialization
		//================================================================================

		/**
		 * The default constructor for the LessonPopUpController.
		 *
		 * @param root the root concerned.
		 */
		LessonPopUpController(JFXDialog dialog, StackPane root) {

			this.root = root;

			this.dialog = dialog;
		}

		/**
		 * the submit() method is responsible for performing the necessary actions when the user clicks on an item of the popup.
		 */
		@FXML
		private void submit() {

			if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 1) {

				// and we quit the application
				Platform.exit();
			}

			else {
				// we display the dialog
				this.dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
				this.dialog.show(this.root);
			}
		}
	}


}
