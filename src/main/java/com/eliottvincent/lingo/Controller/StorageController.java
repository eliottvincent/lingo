package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.CSVHelper;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.History;
import com.eliottvincent.lingo.Model.Session;
import com.eliottvincent.lingo.Model.User;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


/**
 * Created by eliottvct on 23/05/17.
 */
public class StorageController {

	// TODO : using CSV is a only a TEMPORARY solution
	// TODO : move to JDCB + sqlite3 before the final release


	public StorageController() {


	}

	void saveUser(User user) {

		// first we need to search if the username is available

		// calling the CSV helper to read the file where the users are stored
		List<String[]> users = CSVHelper.readCSV("src/main/java/com/eliottvincent/lingo/Data/users.csv");

		// we write in the file
		CSVHelper.writeCSV("src/main/java/com/eliottvincent/lingo/Data/users.csv", ConverterHelper.userToStringArray(user));

		// we also need to initialize the history csv file
		List<String> lines = Arrays.asList("action", "\"login\"");
		Path file = Paths.get("src/main/java/com/eliottvincent/lingo/Data/history" + this.getNumberOfUsers() + ".csv");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	User searchUser(String username, String password) {

		// creating a null User
		User tmpUser = null;

		// calling the CSV helper to read the file where the users are stored
		List<String[]> users = CSVHelper.readCSV("src/main/java/com/eliottvincent/lingo/Data/users.csv");

		for(String[] row : users) {
			System.out.println(Arrays.toString(row));
			if (row[0].equals(username) && row[1].equals(password)) {

				tmpUser = new User(
					row[0],
					row[1],
					ConverterHelper.stringToInteger(row[2]),
					ConverterHelper.stringToGender(row[3]),
					ConverterHelper.stringToLanguage(row[4]),
					searchHistory(ConverterHelper.stringToInteger(row[5]))
				);
			}

		}

		return tmpUser;
	}

	boolean usernameAlreadyExist(String username) {

		List<String[]> users = CSVHelper.readCSV("src/main/java/com/eliottvincent/lingo/Data/users.csv");

		for(String[] row : users) {

			if (row[0].equals(username)) {

				return true;
			}

		}

		return false;
	}

	History searchHistory(Integer id) {

		History tmpHistory = new History();
		tmpHistory.setId(id);

		// calling the CSV helper to read the file where the users are stored
		List<String[]> sessionsRows = CSVHelper.readCSV("src/main/java/com/eliottvincent/lingo/Data/history" + id + ".csv");

		for (String[] row : sessionsRows) {

			tmpHistory.addSession(new Session(row[0]));
		}

		return tmpHistory;
	}

	public Integer getNumberOfUsers() {

		// calling the CSV helper to read the file where the users are stored
		List<String[]> users = CSVHelper.readCSV("src/main/java/com/eliottvincent/lingo/Data/users.csv");

		int nb = 0;
		for(String[] row : users) {

			nb ++;
		}

		return nb;
	}
}
