package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Model.*;

import java.util.Date;

/**
 * Created by eliottvct on 17/05/17.
 */
public class UserController {


	private DatabaseLayer databaseLayer = new DatabaseLayer();

	private User user;

	public UserController() {

	}

	public UserController(User user) {
		this.user = user;
	}

	public User createUser(String username, String password, Date birthdate, Gender gender, Language language) {

		// creating the user object
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setBirthdate(birthdate);
		newUser.setGender(gender);
		newUser.setLanguage(language);

		// saving it in the database and getting the id of the statement
		Integer userId = this.saveUser(newUser);

		// instantiating a new HistoryController
		HistoryController historyController = new HistoryController();
		// we use the userId to create a new History object
		History history = historyController.createHistory(userId);

		// we set the newly created history to the user
		newUser.setHistory(history);

		// finally, we return the complete user object
		return newUser;
	}

	/**
	 *
	 * @return boolean
	 */
	public User logIn() {

		return databaseLayer.searchUser(this.user.getUsername(), this.user.getPassword());
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
	public Integer saveUser(User newUser) {

		return this.databaseLayer.saveUser(newUser);
	}

	/**
	 *
	 * @param user
	 */
	private void updateUser(User user) {

		this.databaseLayer.updateUser(user);
	}

	/**
	 *
	 * @param username
	 * @return
	 */
	public boolean usernameAlreadyExist(String username) {

		return databaseLayer.usernameAlreadyExist(username);
	}
}
