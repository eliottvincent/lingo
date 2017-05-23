package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Helper.CSVHelper;
import com.eliottvincent.lingo.Model.*;

/**
 * Created by eliottvct on 17/05/17.
 */
public class UserController {

	private StorageController storageController = new StorageController();
	UserController() {

	}

	void createUser() {

	}

	/**
	 *
	 * @param username
	 * @param password
	 * @return
	 */
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

	/**
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	private User searchUser(String username, String password) {
		return storageController.searchUser(username, password);
	}

	/**
	 *
	 */
	public void logOut() {
		// TODO : log user out
	}

	/**
	 *
	 * @return
	 */
	public Session getLastSession() {
		// TODO : return last user's session
		return new Session();
	}


	User saveUser(String username, String password, Integer age, Gender gender, Language language) {

		User userToSave = new User(username, password, age, gender, language);
		storageController.saveUser(userToSave);

		return userToSave;
	}
}
