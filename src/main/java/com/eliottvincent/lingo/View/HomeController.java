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

	@FXML
	Label sessionLabel;


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

		assert this.currentUser != null;

		titleLabel.setText("Hello " + this.currentUser.getUserName() + "!");
		sessionLabel.setText("Historique : " + this.currentUser.getHistory());

		statusLabel.setText("Le reste de l'interface est en cours de développement");




	}

	//================================================================================
	// Event Handlers
	//================================================================================

	//================================================================================
	// Event Actions
	//================================================================================
}
