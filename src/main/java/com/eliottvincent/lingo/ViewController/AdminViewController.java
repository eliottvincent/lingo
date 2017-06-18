package com.eliottvincent.lingo.ViewController;

import com.eliottvincent.lingo.Controller.UserController;
import com.eliottvincent.lingo.Helper.ConverterHelper;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by eliottvincent on 10/06/2017.
 */

@ViewController("/fxml/admin.fxml")
public class AdminViewController {


	//================================================================================
	// JavaFX elements
	//================================================================================

	@FXML
	private HBox container;

	@FXML
	private JFXTreeTableView<User> treeView;

	@FXML
	private ListView<Session> sessionsListView;

	@FXML
	private ListView<Action> actionsListView;

	@FXML
	private JFXTextField filterJFXTextField;


	//================================================================================
	// DataFX elements
	//================================================================================

	@FXMLViewFlowContext
	public ViewFlowContext flowContext;


	//================================================================================
	// Constructors and initialization
	//================================================================================

	/**
	 * The default constructor for the AdminViewController.
	 */
	public AdminViewController() {

	}

	/**
	 *
	 */
	@PostConstruct
	public void init() {

		initUserTreeTableView();
		initializeSessionsListView();
		initializeActionsListView();
	}

	private void initUserTreeTableView() {

		JFXTreeTableColumn<User, String> idColumn = new JFXTreeTableColumn<>("Id");
		idColumn.setPrefWidth(50);
		idColumn.setResizable(false);
		idColumn.setCellValueFactory(param -> ConverterHelper.simpleIntegerPropertyToSimpleStringProperty(param.getValue().getValue().idProperty()));

		JFXTreeTableColumn<User, String> nameColumn = new JFXTreeTableColumn<>("Name");
		nameColumn.setPrefWidth(150);
		nameColumn.setResizable(false);
		nameColumn.setCellValueFactory(param -> param.getValue().getValue().usernameProperty());

		JFXTreeTableColumn<User, String> passwordColumn = new JFXTreeTableColumn<>("Password");
		passwordColumn.setPrefWidth(150);
		passwordColumn.setResizable(false);
		passwordColumn.setCellValueFactory(param -> param.getValue().getValue().passwordProperty());

		JFXTreeTableColumn<User, String> genderColumn = new JFXTreeTableColumn<>("Gender");
		genderColumn.setPrefWidth(100);
		genderColumn.setResizable(false);
		genderColumn.setCellValueFactory(param -> ConverterHelper.genderSimpleObjectPropertyToSimpleStringProperty(param.getValue().getValue().genderProperty()));

		JFXTreeTableColumn<User, String> birthdateColumn = new JFXTreeTableColumn<>("Birthdate");
		birthdateColumn.setPrefWidth(200);
		birthdateColumn.setResizable(false);
		birthdateColumn.setCellValueFactory(param -> ConverterHelper.dateSimpleObjectPropertyToSimpleStringProperty(param.getValue().getValue().birthdateProperty()));

		UserController userController = new UserController();
		ObservableList<User> users = FXCollections.observableArrayList(userController.getUsers());

		final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
		treeView.setRoot(root);
		treeView.setShowRoot(false);
		treeView.getColumns().setAll(idColumn, nameColumn, passwordColumn, genderColumn, birthdateColumn);

		filterJFXTextField.textProperty().addListener((observable, oldValue, newValue) ->
			treeView.setPredicate(userTreeItem -> {

				Boolean flag = userTreeItem.getValue().getUsername().contains(newValue) ||
					userTreeItem.getValue().getPassword().contains(newValue);
				return flag;

			})
		);

		treeView.getSelectionModel().selectedItemProperty().addListener(
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
							label.setText(
								item.getType().toString() +
									" on " +
									ConverterHelper.dateToString(item.getDate())
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
	 *
	 * @param newTreeItemUser
	 */
	private void selectedUserChanged(TreeItem<User> newTreeItemUser) {

		if (newTreeItemUser != null) {

			System.out.println("super newTreeItemUser: " + newTreeItemUser.getValue().usernameProperty().get());


			// we clear the listviews
			sessionsListView.getItems().clear();
			actionsListView.getItems().clear();

			List<Session> sessions = newTreeItemUser.getValue().getHistory().getSessions();
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
}


