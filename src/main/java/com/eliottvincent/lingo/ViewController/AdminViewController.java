package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.ActionController;
import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Action;
import com.eliottvincent.lingo.Model.Session;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <b>AdminViewController is the class responsible for the Admin view.</b>
 *
 * @author eliottvincent
 */
@ViewController("/fxml/admin.fxml")
public class AdminViewController {


	//================================================================================
	// JavaFX elements
	//================================================================================

	@FXML
	private StackPane root;

	@FXML
	public BorderPane container;

	@FXML
	private HBox content;

	@FXML
	private JFXTreeTableView<User> usersJFXTreeTableView;

	@FXML
	private ListView<Session> sessionsListView;

	@FXML
	private ListView<Action> actionsListView;

	@FXML
	private HBox bottomTableHBox;

	@FXML
	private JFXToolbar adminJFXToolbar;

	@FXML
	private StackPane homeButtonContainer;

	@FXML
	private StackPane optionsBurger;

	@FXML
	private JFXRippler optionsRippler;

	@FXML
	private JFXTextField searchJFXTextField;

	@FXML
	private JFXDialog dialog;

	@FXML
	private JFXButton okButton;


	//================================================================================
	// DataFX elements
	//================================================================================

	@ActionHandler
	private FlowActionHandler actionHandler;


	//================================================================================
	// Other properties
	//================================================================================

	private UserController userController;

	private JFXPopup toolbarPopup;


	//================================================================================
	// Constructors and initialization
	//================================================================================

	/**
	 * The default constructor for the AdminViewController.
	 */
	public AdminViewController() {

		this.userController = new UserController();
	}

	/**
	 * the init() method is responsible for doing the necessary initialization of some components.
	 * By adding the @PostConstruct annotation to the method, the DataFX flow container will call this method once all injectable values of the controller instance are injected.
	 */
	@PostConstruct
	public void init() {

		// initializing the toolbar
		try {
			initializeTopBar();
		} catch (Exception e) {
			e.printStackTrace();
		}

		initUsersTreeTableView();

		initializeSessionsListView();

		initializeActionsListView();
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
				actionHandler.navigate(LoginViewController.class);
			} catch (VetoException | FlowException e1) {
				e1.printStackTrace();
			}
		});

		// loading the popup content
		// there are cases in which we can't use a Flow, like this one
		// so we are forced to use a classic FXMLLoader
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainpopup.fxml"));
		loader.setController(new AdminPopUpController(dialog ,root));
		toolbarPopup = new JFXPopup(loader.load());

		// adding a listener to handle clicks on the different
		optionsBurger.setOnMouseClicked(e -> toolbarPopup.show(optionsBurger,
			JFXPopup.PopupVPosition.TOP,
			JFXPopup.PopupHPosition.RIGHT,
			-12,
			15)
		);

		BorderPane.setAlignment(adminJFXToolbar, Pos.CENTER);
		container.setTop(adminJFXToolbar);

		// removing the dialog, because we want to display it only at the end
		container.getChildren().remove(dialog);

		// adding EventHandler to the dialog button
		EventHandler<ActionEvent> eventHandler = event -> onOk((Node) event.getSource());
		okButton.setOnAction(eventHandler);
	}

	/**
	 * the initUsersTreeTableView() method is responsible for initializing the TreeTableView displaying all the Users.
	 */
	private void initUsersTreeTableView() {

		// creating a new column
		JFXTreeTableColumn<User, String> idColumn = new JFXTreeTableColumn<>("Id");
		idColumn.setPrefWidth(50);
		idColumn.setResizable(false);
		idColumn.setCellValueFactory(param -> ConverterHelper.simpleIntegerPropertyToSimpleStringProperty(param.getValue().getValue().idProperty()));

		// creating a new column
		JFXTreeTableColumn<User, String> nameColumn = new JFXTreeTableColumn<>("Name");
		nameColumn.setPrefWidth(150);
		nameColumn.setResizable(false);
		nameColumn.setCellValueFactory(param -> param.getValue().getValue().usernameProperty());

		// creating a new column
		JFXTreeTableColumn<User, String> passwordColumn = new JFXTreeTableColumn<>("Password");
		passwordColumn.setPrefWidth(150);
		passwordColumn.setResizable(false);
		passwordColumn.setCellValueFactory(param -> param.getValue().getValue().passwordProperty());

		// creating a new column
		JFXTreeTableColumn<User, String> genderColumn = new JFXTreeTableColumn<>("Gender");
		genderColumn.setPrefWidth(100);
		genderColumn.setResizable(false);
		genderColumn.setCellValueFactory(param -> ConverterHelper.genderSimpleObjectPropertyToSimpleStringProperty(param.getValue().getValue().genderProperty()));

		// creating a new column
		JFXTreeTableColumn<User, String> birthdateColumn = new JFXTreeTableColumn<>("Birthdate");
		birthdateColumn.setPrefWidth(200);
		birthdateColumn.setResizable(false);
		birthdateColumn.setCellValueFactory(param -> ConverterHelper.dateSimpleObjectPropertyToSimpleStringProperty(param.getValue().getValue().birthdateProperty()));

		// getting all the users
		ObservableList<User> users = FXCollections.observableArrayList(this.userController.getUsers());

		final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
		usersJFXTreeTableView.setRoot(root);
		usersJFXTreeTableView.setShowRoot(false);

		// adding all the columns to the TreeTableView
		usersJFXTreeTableView.getColumns().setAll(idColumn, nameColumn, passwordColumn, genderColumn, birthdateColumn);

		// adding a listener to the searchJFXTextField so we can dynamically filter the TreeTable
		searchJFXTextField.textProperty().addListener((observable, oldValue, newValue) ->

			usersJFXTreeTableView.setPredicate(userTreeItem -> {
				return userTreeItem.getValue().getUsername().contains(newValue) || userTreeItem.getValue().getPassword().contains(newValue);

			})
		);

		// adding a listener on the selected item in the TreeTableView
		// so we can dynamically the content of the sessionsListView according to the newly selected User
		usersJFXTreeTableView.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue) -> selectedUserChanged(newValue)
		);

		BorderPane.setAlignment(content, Pos.CENTER);
	}

	/**
	 * the initializeSessionsListView() method is responsible for initializing the ListView displaying the Sessions of the selected User.
	 */
	private void initializeSessionsListView() {

		// creating a CellFactory, so we can choose what's displayed in the cells of sessionsListView
		sessionsListView.setCellFactory(new Callback<ListView<Session>, ListCell<Session>>() {

			@Override
			public ListCell<Session> call(ListView<Session> param) {
				return new ListCell<Session>() {

					// creating a Label to display the text we want
					private final Label label;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						label = new Label();
					}

					@Override
					protected void updateItem(Session session, boolean empty) {
						super.updateItem(session, empty);

						if (session == null || empty) {
							setGraphic(null);
						} else {

							// filling the label's text with the session's data
							label.setText("Session n°" + session.getId().toString());

							setGraphic(label);
						}
					}
				};
			}
		});

		// adding a listener on the selected item in the TreeTableView
		// so we can dynamically the content of the sessionsListView according to the newly selected User
		sessionsListView.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue) -> selectedSessionChanged(newValue)
		);
	}

	/**
	 * the initializeActionsListView() method is responsible for initializing the ListView displaying the Actions of the selected Session.
	 */
	private void initializeActionsListView() {

		// creating a CellFactory, so we can choose what's displayed in the cells of sessionsListView
		actionsListView.setCellFactory(new Callback<ListView<Action>, ListCell<Action>>() {

			@Override
			public ListCell<Action> call(ListView<Action> param) {
				return new ListCell<Action>() {

					// creating a Label to display the text we want
					private final Label label;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						label = new Label();
					}

					@Override
					protected void updateItem(Action action, boolean empty) {
						super.updateItem(action, empty);

						if (action == null || empty) {
							setGraphic(null);
						} else {

							// filling the label's text with the action's data
							label.setText(
								action.getType().toString() +
									" on " +
									ConverterHelper.dateToString(action.getDate())
							);
							setGraphic(label);
						}
					}
				};
			}
		});
	}


	//================================================================================
	// Event Handlers
	//================================================================================

	/**
	 * the selectedUserChanged() method is responsible for performing the necessary actions when the selected User in usersJFXTreeTableView has changed.
	 *
	 * @param newTreeItemUser the newly selected User
	 */
	private void selectedUserChanged(TreeItem<User> newTreeItemUser) {

		if (newTreeItemUser != null) {

			// clearing sessionsListView and actionsListView
			sessionsListView.getItems().clear();
			actionsListView.getItems().clear();

			List<Session> sessions = newTreeItemUser.getValue().getHistory().getSessions();
			ObservableList<Session> data = FXCollections.observableArrayList(sessions);
			sessionsListView.setItems(data);
		}
	}

	/**
	 * the selectedSessionChanged() method is responsible for performing the necessary actions when the selected Session in sessionsListView has changed.
	 *
	 * @param newSession the newly selected Session
	 */
	private void selectedSessionChanged(Session newSession) {

		if (newSession != null) {

			// clearing actionsListView
			actionsListView.getItems().clear();

			List<Action> actions = newSession.getActions();
			ObservableList<Action> data = FXCollections.observableArrayList(actions);
			actionsListView.setItems(data);
		}
	}

	/**
	 * the onOk() method is responsible for performing the necessary action whe the user clicks on the "Ok" button of the dialog.
	 * @param source the source of the event.
	 */
	private void onOk(Node source) {

		dialog.close();
	}

	/**
	 * <b>HomePopUpController is the class responsible for the actions performed on the popup.</b>
	 *
	 * @author eliottvincent
	 */
	private static final class AdminPopUpController {


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
		 * The default constructor for the HomePopUpController.
		 *
		 * @param root the root concerned.
		 */
		AdminPopUpController(JFXDialog dialog, StackPane root) {

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


