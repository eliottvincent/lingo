package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.History;
import com.eliottvincent.lingo.Model.Session;

import java.util.List;
import java.util.Map;

/**
 * Created by eliottvct on 17/05/17.
 */
class HistoryController {


	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController = DatabaseController.getInstance();


	//================================================================================
	// Constructor
	//================================================================================

	HistoryController() {

	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 *
	 * @param userId
	 * @return
	 */
	History getHistory(Integer userId) {

		String historiesQuery = "SELECT * FROM Histories " 	+
								"WHERE user_id LIKE '" 		+ 	userId	+ 	"'";

		List<Map<String, Object>> historiesList = databaseController.executeSelectQuery(historiesQuery);

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
	 *
	 * @param userId
	 * @return
	 */
	Integer createNewHistory(Integer userId) {

		History newHistory = new History();
		newHistory.setUserId(userId);

		// saving it in the database and returning the id of the saved statement
		return this.saveHistory(newHistory);
	}

	/**
	 *
	 * @param newHistory
	 * @return
	 */
	private Integer saveHistory(History newHistory) {

		// preparing the query
		String query = 	"INSERT INTO Histories (user_id) " +
			"VALUES ("	+
			"'"			+	newHistory.getUserId()	+	"'" 	+
			")";

		return databaseController.executeInsertQuery(query);
	}
}
