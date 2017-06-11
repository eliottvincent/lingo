package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by eliottvct on 17/05/17.
 */
public class UserController {


	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController = new DatabaseController();


	//================================================================================
	// Constructor
	//================================================================================

	/**
	 *
	 */
	public UserController() {

	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 *
	 * @param userId
	 * @return
	 */
	User getUser(Integer userId) {

		// preparing the query
		String query = 	"SELECT * FROM Users " 	+
			"WHERE id LIKE '"		+ 	userId	+ "'";

		List<Map<String, Object>> usersList = databaseController.executeSelectQuery(query);

		// the query should have returned only one statement
		if (usersList.size() == 1) {

			User tmpUser = new User();

			Map<String, Object> userMap = usersList.get(0);

			// TODO : move this mechanic to ConverterHelper : User finalUser = ConverterHelper.mapToUser(userMap);
			tmpUser.setId(ConverterHelper.stringToInteger((String) userMap.get("id")));
			tmpUser.setUsername((String) userMap.get("username"));
			tmpUser.setPassword((String) userMap.get("password"));
			tmpUser.setBirthdate(ConverterHelper.stringToDate((String) userMap.get("birthdate")));
			tmpUser.setGender(ConverterHelper.stringToGender((String) userMap.get("gender")));
			tmpUser.setLanguage(ConverterHelper.stringToLanguage((String) userMap.get("language")));

			// we need to populate the user's history
			HistoryController historyController = new HistoryController();
			tmpUser.setHistory(historyController.getHistory(tmpUser.getId()));

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
	public User getUserByCredentials(String username, String password) {

		// preparing the query
		String query = 	"SELECT * FROM Users " 		+
						"WHERE username LIKE '"		+	username	+	"' " +
						"AND password LIKE '" 		+ 	password 	+ 	"'";

		List<Map<String, Object>> usersList = databaseController.executeSelectQuery(query);

		// the query should have returned only one statement
		if (usersList.size() == 1) {

			Map<String, Object> userMap = usersList.get(0);

			User finalUser = ConverterHelper.mapToUser(userMap);

			// we need to populate the user's history
			HistoryController historyController = new HistoryController();
			finalUser.setHistory(historyController.getHistory(finalUser.getId()));

			return finalUser;
		}

		else {

			return null;
		}

	}

	/**
	 *
	 * @return
	 */
	List<User> getUsers() {

		String query = "SELECT * FROM Users";

		List<Map<String, Object>> usersList = databaseController.executeSelectQuery(query);
		List<User> users = new ArrayList<User>();

		for (Map<String, Object> userMap : usersList) {

			User tmpUser = new User();
			tmpUser.setId(ConverterHelper.stringToInteger((String) userMap.get("id")));
			tmpUser.setUsername((String) userMap.get("username"));
			tmpUser.setPassword((String) userMap.get("password"));
			tmpUser.setBirthdate(ConverterHelper.stringToDate((String) userMap.get("birthdate")));
			tmpUser.setGender(ConverterHelper.stringToGender((String) userMap.get("gender")));
			tmpUser.setLanguage(ConverterHelper.stringToLanguage((String) userMap.get("language")));

			// we need to populate the user's history
			HistoryController historyController = new HistoryController();
			tmpUser.setHistory(historyController.getHistory(tmpUser.getId()));

			users.add(tmpUser);
		}

		return users;
	}


	//================================================================================
	// CREATE
	//================================================================================

	Integer createNewUser(String username, String password, Date birthdate, Gender gender, Language language) {

		// creating the user object
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setBirthdate(birthdate);
		newUser.setGender(gender);
		newUser.setLanguage(language);

		return this.saveUser(newUser);
	}


	/**
	 *
	 * @param newUser
	 * @return
	 */
	private Integer saveUser(User newUser) {

		// preparing the query
		String query = 	"INSERT INTO Users (username, password, birthdate, gender, language) " +
						"VALUES (" 	+
						"'"			+	newUser.getUsername() 	+	 "', " 	+
						"'"			+	newUser.getPassword() 	+	 "', "	+
						"'"			+	newUser.getBirthdate() 	+	 "', " 	+
						"'"			+	newUser.getGender() 	+	 "', " 	+
						"'"			+	newUser.getLanguage() 	+	 "')";

		return databaseController.executeInsertQuery(query);
	}


	//================================================================================
	// UPDATE
	//================================================================================

	/**
	 *
	 * @param user
	 */
	void updateUser(User user) {

		// preparing the query
		String query = 	"UPDATE Users " 	+
						"SET username = '"	+	user.getUsername()	+ 	"', "	+
						"password = '" 		+ 	user.getPassword()	+ 	"', " 	+
						"birthdate = '" 	+ 	user.getBirthdate()	+ 	"', " 	+
						"gender = '" 		+ 	user.getGender()	+ 	"', " 	+
						"language = '" 		+ 	user.getLanguage()	+ 	"' " 	+
						"WHERE id LIKE " 	+ 	user.getId()		+ 	"";
		databaseController.executeUpdateQuery(query);
	}


	//================================================================================
	// OTHER
	//================================================================================

	/**
	 *
	 * @param username
	 * @return
	 */
	public boolean usernameAlreadyExist(String username) {

		// preparing the query
		String query =	"SELECT * FROM Users "		+
						"WHERE username LIKE '" 	+ username	+	"'";

		List usersList = databaseController.executeSelectQuery(query);

		// we return true if the list isn't null and isn't empty
		return usersList != null && !usersList.isEmpty();
	}
}
