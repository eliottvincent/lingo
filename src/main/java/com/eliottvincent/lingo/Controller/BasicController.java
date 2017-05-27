package com.eliottvincent.lingo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by eliottvct on 27/05/17.
 */
public class BasicController {

	@FXML private TextField firstName;

	public void handleSaySup(ActionEvent actionEvent) {
		System.out.printf("Sup %s!", firstName);
	}
}
