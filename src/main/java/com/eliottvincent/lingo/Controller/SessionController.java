package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.ConverterHelper;
import com.eliottvincent.lingo.Model.Action;
import com.eliottvincent.lingo.Model.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by eliottvct on 17/05/17.
 */
class SessionController {

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
	SessionController() {


	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 *
	 * @param sessionId
	 * @return
	 */
	Session getSession(Integer sessionId) {


		String sessionQuery =	"SELECT * FROM Sessions "	+
			"WHERE id LIKE '"			+	sessionId	+	"'";

		List<Map<String, Object>> sessionsList = databaseController.executeSelectQuery(sessionQuery);

		if (sessionsList.size() == 1) {

			Map<String, Object> sessionMap = sessionsList.get(0);

			Session tmpSession = new Session();
			tmpSession.setId((Integer) sessionMap.get("id"));
			tmpSession.setHistoryId((Integer) sessionMap.get("history_id"));
			tmpSession.setStartDate((Date) sessionMap.get("start_date"));
			tmpSession.setEndDate((Date) sessionMap.get("end_date"));

			// for session's actions, we need to query the database
			ActionController actionController = new ActionController();
			List<Action> actions = actionController.getActions(tmpSession.getId());
			tmpSession.setActions(actions);

			return tmpSession;
		}

		else {
			return null;
		}
	}

	/**
	 *
	 * @param historyId
	 * @return
	 */
	List<Session> getSessions(Integer historyId) {

		String sessionQuery =	"SELECT * FROM Sessions "	+
			"WHERE history_id LIKE '"	+	historyId	+	"'";

		List<Map<String, Object>> sessionsList = databaseController.executeSelectQuery(sessionQuery);
		List<Session> sessions = new ArrayList<Session>();

		for (Map<String, Object> sessionMap : sessionsList) {

			Session tmpSession = new Session();
			tmpSession.setId(ConverterHelper.stringToInteger((String) sessionMap.get("id")));
			tmpSession.setHistoryId(ConverterHelper.stringToInteger((String) sessionMap.get("history_id")));
			tmpSession.setStartDate(ConverterHelper.stringToDate((String) sessionMap.get("start_date")));
			tmpSession.setEndDate(ConverterHelper.stringToDate((String) sessionMap.get("end_date")));

			// for session's actions, we need to query the database
			ActionController actionController = new ActionController();
			List<Action> actions = actionController.getActions(tmpSession.getId());
			tmpSession.setActions(actions);

			sessions.add(tmpSession);
		}

		return sessions;
	}


	//================================================================================
	// CREATE
	//================================================================================

	/**
	 *
	 * @param historyId
	 * @return
	 */
	Integer createNewSession(Integer historyId) {

		Session session = new Session();
		session.setHistoryId(historyId);
		session.setStartDate(new Date());

		// saving it in the database and returning the id of the saved statement
		return this.saveSession(session);
	}

	/**
	 *
	 * @param session
	 * @return
	 */
	private Integer saveSession(Session session) {

		String query = 	"INSERT INTO Sessions (history_id, start_date) " +
			"VALUES (" 	+
			"'"			+ 	session.getHistoryId()	+	"', "	+
			"'" 		+	session.getStartDate() 	+	"'" 	+
			")";

		return databaseController.executeInsertQuery(query);

	}
}
