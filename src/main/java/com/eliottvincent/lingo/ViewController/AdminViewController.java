package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Model.Action;
import com.eliottvincent.lingo.Model.Session;
import com.eliottvincent.lingo.Model.User;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by eliottvincent on 10/06/2017.
 */

@ViewController("/fxml/admin.fxml")
public class AdminViewController {


	//================================================================================
	// JavaFX Elements
	//================================================================================

	@FXML
	private HBox container;

	@FXML
	private JFXTreeTableView<User> treeView;

	@FXML
	private JFXTextField filterJFXTextField;

	@FXMLViewFlowContext
	public ViewFlowContext flowContext;

	//================================================================================
	// Other properties
	//================================================================================


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 *
	 */
	public AdminViewController() {

	}

	/**
	 *
	 */
	@PostConstruct
	public void init() {

		//this.initializeUsersListView();

		//this.initializeSessionsListView();

		//this.initializeActionsListView();

		testTableTreeView();
	}

	public void testTableTreeView() {

		JFXTreeTableColumn<User, String> deptColumn = new JFXTreeTableColumn<>("Id");
		deptColumn.setPrefWidth(400);
		deptColumn.setResizable(true);
		deptColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
				return param.getValue().getValue().username;
			}
		});

		JFXTreeTableColumn<User, String> deptNamColumn = new JFXTreeTableColumn<>("Name");
		deptNamColumn.setPrefWidth(400);
		deptNamColumn.setResizable(true);
		deptNamColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
				return param.getValue().getValue().username;
			}
		});

		JFXTreeTableColumn<User, String> ageColumn = new JFXTreeTableColumn<>("Password");
		ageColumn.setPrefWidth(400);
		ageColumn.setResizable(true);
		ageColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
				return param.getValue().getValue().password;
			}
		});

		UserController userController = new UserController();
		ObservableList<User> users = FXCollections.observableArrayList(userController.getUsers());

		final TreeItem<User> root = new RecursiveTreeItem<User>(users, RecursiveTreeObject::getChildren);
		treeView.setRoot(root);
		treeView.setShowRoot(false);
		treeView.getColumns().setAll(deptNamColumn, deptColumn, ageColumn);


		filterJFXTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				treeView.setPredicate(new Predicate<TreeItem<User>>() {
					@Override
					public boolean test(TreeItem<User> userTreeItem) {

						Boolean flag = userTreeItem.getValue().username.getValue().contains(newValue) ||
							userTreeItem.getValue().password.getValue().contains(newValue);

						return flag;
					}
				});
			}
		});

	}

	/**
	 *
	 */
	private void initializeUsersListView() {

		UserController userController = new UserController();
		List<User> users = userController.getUsers();

		ObservableList<User> data = FXCollections.observableArrayList(users);
/*

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

		*/
	}

	/**
	 *
	 */
	private void initializeSessionsListView() {
/*

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
				*/

	}

	/**
	 *
	 */
	private void initializeActionsListView() {
/*
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
				*/

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

			/*
			// we clear the listviews
			sessionsListView.getItems().clear();
			actionsListView.getItems().clear();

			//
			List<Session> sessions = newUser.getHistory().getSessions();
			ObservableList<Session> data = FXCollections.observableArrayList(sessions);
			sessionsListView.setItems(data);
		*/
		}
	}

	/**
	 *
	 * @param newSession
	 */
	private void selectedSessionChanged(Session newSession) {

		if (newSession != null) {

			/*
			// we clear the listviews
			actionsListView.getItems().clear();

			List<Action> actions = newSession.getActions();
			ObservableList<Action> data = FXCollections.observableArrayList(actions);
			actionsListView.setItems(data);
			*/
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


	private static final class UserUser extends RecursiveTreeObject<UserUser> {
		final StringProperty userName;
		final StringProperty age;
		final StringProperty department;

		UserUser(String department, String age, String userName) {
			this.department = new SimpleStringProperty(department);
			this.userName = new SimpleStringProperty(userName);
			this.age = new SimpleStringProperty(age);
		}
	}

}


