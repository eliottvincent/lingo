package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Data.Status;
import com.eliottvincent.lingo.Helper.CSVHelper;
import com.eliottvincent.lingo.Model.*;

/**
 * Created by eliottvct on 17/05/17.
 */
public class UserController {

	private StorageController storageController = new StorageController();
	private User user;

	public UserController(User user) {
		this.user = user;
	}

	void createUser() {

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
		return new Session();
	}

	/**
	 *
	 * @return
	 */
	public Status saveUser() {

		return storageController.saveUser(this.user);

	}
}
