package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Helper.CSVHelper;
import com.eliottvincent.lingo.Model.User;

import org.apache.commons.csv.CSVRecord;

/**
 * Created by eliottvct on 23/05/17.
 */
public class StorageController {

	private CSVHelper CSV = new CSVHelper();

	StorageController() {


	}

	public void saveUser(User user) {

	}

	User searchUser(String username, String password) {

		// creating a null User
		User tmpUser = null;

		// calling the CSV helper to read the file where the users are stored
		Iterable<CSVRecord> records = CSV.readCSV("src/main/java/com/eliottvincent/lingo/Helper/users.csv");

		for (CSVRecord record : records) {


			System.out.printf(record.get("username"));
			System.out.printf(record.get("password"));




			// if the current record has the same username and password of the target
			if (record.get("username").equals(username) && record.get("password").equals(password)) {

				// we populate tmpUser
				tmpUser = new User(
					record.get("username"),
					record.get("password"),
					stringToGender(record.get("gender")),
					stringToInteger(record.get("age")),
					stringToLanguage(record.get("language"))
				);
			}
		}

		return tmpUser;
	}

	private Language stringToLanguage(String language) {

		return Language.FRENCH;
	}

	private int stringToInteger(String s) {

		return Integer.parseInt(s);
	}

	private Gender stringToGender(String s) {

		return Gender.FEMALE;
	}
}
