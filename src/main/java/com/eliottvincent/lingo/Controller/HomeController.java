package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by eliottvct on 27/05/17.
 */
public class HomeController {

	@FXML
	private TextArea username;

	@FXML
	private TextArea password;

	public void handleCreateAccount(ActionEvent actionEvent) {
		System.out.println("restart");
	}

	public void handleLogin(ActionEvent actionEvent) {

		UserController userController = new UserController();
		User tmpUser = userController.logIn(username.getText(), password.getText());

		if (tmpUser != null) {
			System.out.println("logged in!");
		}
		else {
			System.out.println("not logged in :(");
		}
	}
}
