package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Model.User;

import java.util.Date;

/**
 * <b>AcountController is the class responsible for the account creation.</b>
 *
 * @see User
 *
 * @author eliottvincent
 */
public class AccountController {

	/**
	 * createNewAccount() is the method responsible for creating a new User object, and all the extra objects (History, Session).
	 *
	 * @param username the username of the new user
	 * @param password the password of the new user
	 * @param birthdate the birthdate of the new user
	 * @param gender the gender of the new user
	 * @param language the language of the new user
	 * @return an User object.
	 */
	public User createNewAccount(String username, String password, Date birthdate, Gender gender, Language language) {

		// creating a new USER
		UserController userController = new UserController();
		Integer userId = userController.createNewUser(username, password, birthdate, gender, language);

		// creating a new HISTORY
		HistoryController historyController = new HistoryController();
		Integer historyId = historyController.createNewHistory(userId);

		// creating a new SESSION
		SessionController sessionController = new SessionController();
		Integer sessionId = sessionController.createNewSession(historyId);

		// creating a new ACTION
		ActionController actionController = new ActionController();
		Integer actionId = actionController.createNewAction(userController.getUser(userId), ActionType.ACCOUNT_CREATION, new Date(), null, null);


		// at the end we need to query the db again
		// because we updated the user: we added an history, to which we added a session, to which we added an action
		return userController.getUser(userId);
	}
}
