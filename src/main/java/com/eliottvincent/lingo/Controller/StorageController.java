package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Data.Status;
import com.eliottvincent.lingo.Helper.CSVHelper;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.User;

import java.util.Arrays;
import java.util.List;


/**
 * Created by eliottvct on 23/05/17.
 */
class StorageController {


	StorageController() {


	}

	Status saveUser(User user) {

		Status status = Status.OK;

		// first we need to search if the username is available

		// calling the CSV helper to read the file where the users are stored
		List<String[]> users = CSVHelper.readCSV("src/main/java/com/eliottvincent/lingo/Data/users.csv");

		for(String[] row : users){
			System.out.println(Arrays.toString(row));

			// this case means that the username+password pair already exist
			// maybe the user has an account but don't remember it...
			if (row[0].equals(user.getUserName()) && row[1].equals((user.getPassword()))) {
				status = Status.USER_ALREADY_EXISTS;
			}

			// this case means that the username is already taken
			if (row[0].equals(user.getUserName())) {
				status = Status.USERNAME_NOT_AVAILABLE;
			}
		}

		// we write in the file
		// only if the status hasn't been modified by the loop
		if (status.equals(Status.OK)) {
			CSVHelper.writeCSV("src/main/java/com/eliottvincent/lingo/Data/users.csv", ConverterHelper.userToStringArray(user));
		}

		return status;
	}

	User searchUser(String username, String password) {

		// creating a null User
		User tmpUser = null;

		// calling the CSV helper to read the file where the users are stored
		List<String[]> users = CSVHelper.readCSV("src/main/java/com/eliottvincent/lingo/Data/users.csv");

		for(String[] row : users){
			System.out.println(Arrays.toString(row));
			if (row[0].equals(username) && row[1].equals(password)) {

				tmpUser = new User(
					row[0],
					row[1],
					ConverterHelper.stringToInteger(row[2]),
					ConverterHelper.stringToGender(row[3]),
					ConverterHelper.stringToLanguage(row[4])
				);
			}

		}

		return tmpUser;
	}
}
