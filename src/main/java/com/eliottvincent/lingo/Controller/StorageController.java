package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
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

	void saveUser(User user) {

		CSVHelper.writeCSV("src/main/java/com/eliottvincent/lingo/Helper/users.csv", ConverterHelper.userToStringArray(user));
	}

	User searchUser(String username, String password) {

		// creating a null User
		User tmpUser = null;

		// calling the CSV helper to read the file where the users are stored
		List<String[]> users = CSVHelper.readCSV("src/main/java/com/eliottvincent/lingo/Helper/users.csv");

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
