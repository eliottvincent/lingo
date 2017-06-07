package com.eliottvincent.lingo.View;

import com.eliottvincent.lingo.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by eliottvincent on 28/05/2017.
 */
public class HomeController {

	//================================================================================
	// JavaFX Elements
	//================================================================================

	@FXML
	Label statusLabel;

	@FXML
	Label titleLabel;


	//================================================================================
	// Other properties
	//================================================================================

	User currentUser;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	HomeController() {

	}

	HomeController(User currentUser) {
		this.currentUser = currentUser;
	}

	@FXML
	public void initialize() {

		if (currentUser != null) {
			titleLabel.setText("Hello " + this.currentUser.getUserName() + "!");
		}




	}

	//================================================================================
	// Event Handlers
	//================================================================================

	//================================================================================
	// Event Actions
	//================================================================================
}
