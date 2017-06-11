package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Model.Action;
import com.eliottvincent.lingo.Model.Session;
import com.eliottvincent.lingo.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by eliottvincent on 10/06/2017.
 */
public class AdminController {


	//================================================================================
	// JavaFX Elements
	//================================================================================

	@FXML
	private HBox container;

	@FXML
	private VBox usersVBox;

	@FXML
	private VBox sessionsVBox;

	@FXML
	private VBox actionsVBox;

	@FXML
	private ListView<User> usersListView;

	@FXML
	private ListView<Session> sessionsListView;

	@FXML
	private ListView<Action> actionsListView;


	//================================================================================
	// Other properties
	//================================================================================

	private ScreenController screenController;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 *
	 */
	public AdminController() {

		this.screenController = ScreenController.getInstance();
	}

	/**
	 *
	 */
	@FXML
	public void initialize() {

		this.initializeUsersListView();

		this.initializeSessionsListView();

		this.initializeActionsListView();
	}

	/**
	 *
	 */
	private void initializeUsersListView() {

		UserController userController = new UserController();
		List<User> users = userController.getUsers();

		ObservableList<User> data = FXCollections.observableArrayList(users);

		usersListView.setItems(data);
		usersListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {

			@Override
			public ListCell<User> call(ListView<User> param) {
				return new ListCell<User>() {

					private final Label label;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						label = new Label();
					}

					@Override
					protected void updateItem(User item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setGraphic(null);
						} else {
							label.setText(item.getUsername());
							setGraphic(label);
						}
					}
				};
			}
		});

		// event
		usersListView.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue) -> selectedUserChanged(newValue)
		);
	}

	/**
	 *
	 */
	private void initializeSessionsListView() {

		sessionsListView.setCellFactory(new Callback<ListView<Session>, ListCell<Session>>() {

			@Override
			public ListCell<Session> call(ListView<Session> param) {
				return new ListCell<Session>() {

					private final Label label;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						label = new Label();
					}

					@Override
					protected void updateItem(Session item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setGraphic(null);
						} else {
							label.setText("Session n°" + item.getId().toString());
							setGraphic(label);
						}
					}
				};
			}
		});

		// event
		sessionsListView.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue) -> selectedSessionChanged(newValue)
		);
	}

	/**
	 *
	 */
	private void initializeActionsListView() {

		actionsListView.setCellFactory(new Callback<ListView<Action>, ListCell<Action>>() {

			@Override
			public ListCell<Action> call(ListView<Action> param) {
				return new ListCell<Action>() {

					private final Label label;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						label = new Label();
					}

					@Override
					protected void updateItem(Action item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setGraphic(null);
						} else {
							label.setText("Action n°" + item.getId().toString());
							setGraphic(label);
						}
					}
				};
			}
		});

		// event
		actionsListView.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue) -> selectedActionChanged(newValue)
		);
	}



	//================================================================================
	// Event Handlers
	//================================================================================

	/**
	 *
	 * @param newUser
	 */
	private void selectedUserChanged(User newUser) {

		if (newUser != null) {

			// we clear the listviews
			sessionsListView.getItems().clear();
			actionsListView.getItems().clear();

			//
			List<Session> sessions = newUser.getHistory().getSessions();
			ObservableList<Session> data = FXCollections.observableArrayList(sessions);
			sessionsListView.setItems(data);

		}
	}

	/**
	 *
	 * @param newSession
	 */
	private void selectedSessionChanged(Session newSession) {

		if (newSession != null) {

			// we clear the listviews
			actionsListView.getItems().clear();

			List<Action> actions = newSession.getActions();
			ObservableList<Action> data = FXCollections.observableArrayList(actions);
			actionsListView.setItems(data);
		}
	}

	/**
	 *
	 * @param newAction
	 */
	private void selectedActionChanged(Action newAction) {

		if (newAction != null) {

			System.out.println("New action selected: " + newAction);
			// TODO : view action data somwhere
		}
	}




}
