package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Model.*;

/**
 * Created by eliottvct on 17/05/17.
 */
public class UserController {

	private StorageController storageController = new StorageController();
	private User user;

	public UserController() {

	}

	public UserController(User user) {
		this.user = user;
	}

	public User createUser(String username, String password, Integer age, Gender gender, Language language) {

		User newUser = new User();
		newUser.setUserName(username);
		newUser.setPassword(password);
		newUser.setAge(age);
		newUser.setGender(gender);
		newUser.setLanguage(language);
		newUser.setHistory(new History());

		this.saveUser(newUser);

		return newUser;
	}

	/**
	 *
	 * @return boolean
	 */
	public User logIn() {

		return storageController.searchUser(this.user.getUserName(), this.user.getPassword());
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
		return new Session("ahah");
	}

	/**
	 *
	 * @return
	 * @param newUser
	 */
	public void saveUser(User newUser) {

		storageController.saveUser(newUser);

	}

	/**
	 *
	 * @param username
	 * @return
	 */
	public boolean usernameAlreadyExist(String username) {

		return storageController.usernameAlreadyExist(username);
	}
}
