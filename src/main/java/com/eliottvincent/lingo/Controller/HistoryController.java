package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.History;
import com.eliottvincent.lingo.Model.Session;

import java.util.List;
import java.util.Map;

/**
 * <b>HistoryController is the class responsible for the actions performed on an History object.</b>
 *
 * @see History
 *
 * @author eliottvincent
 */
class HistoryController {


	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController;


	//================================================================================
	// Constructor
	//================================================================================

	/**
	 * The default constructor for a HistoryController
	 */
	HistoryController() {

		databaseController = DatabaseController.getInstance();
	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 * the getHistory() method is responsible for getting the History object of a specific user.
	 *
	 * @param userId the id of the user we want to get history of.
	 * @return the retrieved History object.
	 *
	 * @see DatabaseController
	 */
	History getHistory(Integer userId) {

		// preparing the query
		String historiesQuery = "SELECT * FROM Histories " 	+
								"WHERE user_id LIKE '" 		+ 	userId	+ 	"'";

		// executing the query
		List<Map<String, Object>> historiesList = databaseController.executeSelectQuery(historiesQuery);

		// the statement list returned by the query's execution should contain only one statement
		if (historiesList.size() == 1) {

			Map<String, Object> historyMap = historiesList.get(0);

			History tmpHistory = new History();
			tmpHistory.setId(ConverterHelper.stringToInteger((String) historyMap.get("id")));
			tmpHistory.setUserId(ConverterHelper.stringToInteger((String) historyMap.get("user_id")));

			// for history's sessions, we need to query the database
			SessionController sessionController = new SessionController();
			List<Session> sessions = sessionController.getSessions(tmpHistory.getId());
			tmpHistory.setSessions(sessions);

			return tmpHistory;
		}

		else {

			return null;
		}
	}


	//================================================================================
	// CREATE
	//================================================================================

	/**
	 * createNewHistory() is the method responsible for created a new History object for a specific user.
	 *
	 * @param userId the id of the user we want to create the History object for.
	 * @return the id of the saved History.
	 */
	Integer createNewHistory(Integer userId) {

		History newHistory = new History();
		newHistory.setUserId(userId);

		// saving it in the database and returning the id of the saved statement
		return this.saveHistory(newHistory);
	}

	/**
	 * saveHistory() is the method responsible for saving an History object in the database.
	 *
	 * @param newHistory the History to save.
	 * @return the id of the saved History.
	 *
	 * @see DatabaseController
	 */
	private Integer saveHistory(History newHistory) {

		// preparing the query
		String query = 	"INSERT INTO Histories (user_id) " 	+
						"VALUES ("							+
						"'"									+	newHistory.getUserId()	+	"'" 	+
						")";

		return databaseController.executeInsertQuery(query);
	}
}
