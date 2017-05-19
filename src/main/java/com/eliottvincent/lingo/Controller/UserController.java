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

	User logIn(String username, String password) {
		boolean exists = false;

		User tmpUser = this.searchUser(username, password);

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


	void saveUser(String username, String password) {

	}
}
