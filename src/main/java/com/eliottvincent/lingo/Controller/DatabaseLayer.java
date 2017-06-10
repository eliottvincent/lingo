package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Model.History;
import com.eliottvincent.lingo.Model.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliottvincent on 08/06/2017.
 */
class DatabaseLayer {

	private DatabaseController databaseController = new DatabaseController();

	public DatabaseLayer() {

	}

	/**
	 *
	 * @param newUser
	 * @return
	 */
	Integer saveUser(User newUser) {

		// preparing the query
		String query = "INSERT INTO Users (username, password, birthdate, gender, language) " +
			"VALUES (" +
			"'"		+	newUser.getUsername() 	+	 "', " 	+
			"'"		+	newUser.getPassword() 	+	 "', "	+
			"'"		+	newUser.getBirthdate() 	+	 "', " 	+
			"'"		+	newUser.getGender() 	+	 "', " 	+
			"'"		+	newUser.getLanguage() 	+	 "')";

		return databaseController.executeCreateQuery(query);
	}

	Integer saveHistory(History newHistory) {

		String query = "INSERT INTO Histories (user_id) " +
			"VALUES (" +
			"'" + newHistory.getUserId() + "'" +
			")";

		return databaseController.executeCreateQuery(query);
	}

	/**
	 *
	 * @param user
	 */
	void updateUser(User user) {

		// preparing the query
		String query = "UPDATE Users " +
			"SET username = '" + user.getUsername() + "', " +
			"password = '" + user.getPassword() + "', " +
			"birthdate = '" + user.getBirthdate() + "', " +
			"gender = '" + user.getGender() + "', " +
			"language = '" + user.getLanguage() + "' " +
			"WHERE id = " + user.getId() + "";
		databaseController.executeUpdateQuery(query);
	}

	void updateHistory(History history) {

		String query = "UPDATE Histories " +
			"SET id = '" + history.getId() + "', " +
			"SET user_id = '" + history.getUserId() + "' " +
				"WHERE id = " + history.getId() + "";

		databaseController.executeUpdateQuery(query);
	}

	User searchUser(String username, String password) {

		User tmpUser = new User();

		String query = "SELECT * FROM Users " +
			"WHERE username LIKE '" + username + "' " +
			"AND password LIKE '" + password + "'";

		List usersList = databaseController.executeSelectQuery(query);

		tmpUser.setId(1);

		return tmpUser;
	}

	boolean usernameAlreadyExist(String username) {

		String query = "SELECT * FROM Users " +
			"WHERE username LIKE '" + username + "'";

		List usersList = databaseController.executeSelectQuery(query);

		// we return true if the list isn't null and isn't empty
		return usersList != null && !usersList.isEmpty();
	}
}
