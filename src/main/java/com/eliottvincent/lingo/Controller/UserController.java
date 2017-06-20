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
 * <b>UserController is the class responsible for the actions performed on a User object.</b>
 *
 * @see User
 *
 * @author eliottvincent
 */
public class UserController {


	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController;


	//================================================================================
	// Constructor
	//================================================================================

	/**
	 * The default constructor for a UserController.
	 */
	public UserController() {

		this.databaseController = DatabaseController.getInstance();
	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 * the getUser() method is responsible for retrieving a specific User object.
	 *
	 * @param userId the id of the user to retrieve.
	 * @return the retrieved User object.
	 */
	User getUser(Integer userId) {

		// preparing the query
		String query = 	"SELECT * FROM Users " 	+
						"WHERE id LIKE '"		+ 	userId	+ "'";

		// executing the query
		List<Map<String, Object>> usersList = this.databaseController.executeSelectQuery(query);

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
	 * the getUserByCredentials() method is responsible for retrieving a User according to a pair of credentials.
	 * this method is used for the login.
	 *
	 * @param username the username of the user to retrieve.
	 * @param password the password of the user to retrieve.
	 * @return teh retrieved User (if any)
	 */
	public User getUserByCredentials(String username, String password) {

		// preparing the query
		String query = 	"SELECT * FROM Users " 		+
						"WHERE username LIKE '"		+	username	+	"' " +
						"AND password LIKE '" 		+ 	password 	+ 	"'";

		// executing the query
		List<Map<String, Object>> usersList = this.databaseController.executeSelectQuery(query);

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
	 * the getUsers() method is responsible for getting all the users.
	 *
	 * @return a list of User objects.
	 */
	public List<User> getUsers() {

		// preparing the query
		String query = "SELECT * FROM Users";

		// executing the query
		List<Map<String, Object>> usersList = this.databaseController.executeSelectQuery(query);
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

	/**
	 * the createNewUser() method is responsible for creating a new User object.
	 *
	 * @param username the username of the new user.
	 * @param password the password of the new user.
	 * @param birthdate the birthdate of the new user.
	 * @param gender the gender of the new user.
	 * @param language the language of the new user.
	 * @return the id of the saved statement.
	 */
	Integer createNewUser(String username, String password, Date birthdate, Gender gender, Language language) {

		// creating the User object
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setBirthdate(birthdate);
		newUser.setGender(gender);
		newUser.setLanguage(language);

		return this.saveUser(newUser);
	}


	/**
	 * the saveUser() method is responsible for saving a User object in the database.
	 *
	 * @param newUser the User to save.
	 * @return the id of the saved statement.
	 *
	 * @see DatabaseController
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

		return this.databaseController.executeInsertQuery(query);
	}


	//================================================================================
	// OTHER
	//================================================================================

	/**
	 * the usernameAlreadyExist() is the method responsible for checking if the specified username is already assigned ot a User object.s
	 *
	 * @param username the username to search for.
	 * @return if the username is already assigned or not.
	 */
	public boolean usernameAlreadyExist(String username) {

		// preparing the query
		String query =	"SELECT * FROM Users "		+
						"WHERE username LIKE '" 	+ username	+	"'";

		// executing the query
		List usersList = this.databaseController.executeSelectQuery(query);

		// we return true if the list isn't null and isn't empty
		return usersList != null && !usersList.isEmpty();
	}
}
