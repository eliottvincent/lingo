package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Action;
import com.eliottvincent.lingo.Model.Session;
import com.eliottvincent.lingo.Model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>SessionController is the class responsible for the actions performed on a Session object.</b>
 *
 * @see Session
 *
 * @author eliottvincent
 */
class SessionController {


	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController;


	//================================================================================
	// Constructor
	//================================================================================

	/**
	 * The default constructor for a SessionController.
	 */
	SessionController() {

		this.databaseController  = new DatabaseController();

	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 * the getSession() method is responsible for retrieving a specific Session object.
	 *
	 * @param sessionId the id of the Session to retrieve.
	 * @return the retrieved Session object.
	 */
	Session getSession(Integer sessionId) {

		// preparing the query
		String sessionQuery =	"SELECT * FROM Sessions "	+
								"WHERE id LIKE '"			+	sessionId	+	"'";

		// executing the query
		List<Map<String, Object>> sessionsList = this.databaseController.executeSelectQuery(sessionQuery);

		// the statement list returned by the query's execution should contain only one statement
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
	 * the getSessions() method is responsible for retrieving all the Sessions stored in a specific History.
	 *
	 * @param historyId the id of the History to get the Sessions from.
	 * @return a list of Sessions.
	 */
	List<Session> getSessions(Integer historyId) {

		// preparing the query
		String sessionQuery =	"SELECT * FROM Sessions "	+
								"WHERE history_id LIKE '"	+	historyId	+	"'";

		// executing the query
		List<Map<String, Object>> sessionsList = this.databaseController.executeSelectQuery(sessionQuery);
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
	 * the createNewSession() method is responsible for creating a Session object.
	 *
	 * @param historyId the id of the History containing the Session to save.
	 * @return the id of the saved statement.
	 */
	Integer createNewSession(Integer historyId) {

		Session session = new Session();
		session.setHistoryId(historyId);
		session.setStartDate(new Date());

		// saving it in the database and returning the id of the saved statement
		return this.saveSession(session);
	}

	/**
	 * the saveSession() method is responsible for saving a Session object in the database.
	 *
	 * @param session the Session object to save.
	 * @return the id of the saved statement.
	 *
	 * @see DatabaseController
	 */
	private Integer saveSession(Session session) {

		// preparing the query
		String query = 	"INSERT INTO Sessions (history_id, start_date) "	+
						"VALUES (" 											+
						"'"													+ 	session.getHistoryId()	+	"', "	+
						"'"													+	session.getStartDate() 	+	"'" 	+
						")";

		return this.databaseController.executeInsertQuery(query);
	}


	//================================================================================
	// OTHER
	//================================================================================

	/**
	 * the getLastSession() is the method responsible for retrieving the last session of a specific User.
	 *
	 * @param user the User to search for.
	 * @return the retrieved Session object.
	 */
	Session getLastSession(User user) {

		List<Session> sessions = this.getSessions(user.getHistory().getId());

		return sessions.get(sessions.size() - 1);

	}
}
