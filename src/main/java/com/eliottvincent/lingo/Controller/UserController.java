package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Model.*;

/**
 * Created by eliottvct on 17/05/17.
 */
public class UserController {

	UserController() {

	}

	void createUser() {

	}

	User logIn(String username, String password, Integer age, String gender, String language) {
		boolean exists = false;

		User tmpUser = this.searchUser(gender, language);

		if (tmpUser != null) {
			return tmpUser;
		}
		else {
			return null;
		}
	}

	User searchUser(String username, String password) {

		return null;
	}

	public void logOut() {
		// TODO : log user out
	}

	public Session getLastSession() {
		// TODO : return last user's session
		return new Session();
	}


	void saveUser(String username1, String s, Integer age, String username, String password) {

		System.out.printf("Il faut sauvegarder l\'utilisateur --------------------");
	}
}
