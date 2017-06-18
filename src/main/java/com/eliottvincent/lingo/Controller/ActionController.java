package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Model.Action;
import com.eliottvincent.lingo.Model.Session;
import com.eliottvincent.lingo.Model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>ActionController is the class responsible for creating and retrieving user actions.</b>
 *
 * @see Action
 *
 * @author eliottvincent
 */
public class ActionController {


	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController = DatabaseController.getInstance();


	//================================================================================
	// Constructor
	//================================================================================


	/**
	 * The default constructor for an ActionController.
	 */
	public ActionController() {

	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 * getAction() is the method used to retrieve a specific Action.
	 *
	 * @param actionId the id of the action to retrieve.
	 * @return the retrieved action
	 *
	 * @see DatabaseController
	 */
	Action getAction(Integer actionId) {

		// preparing the query
		String query =	"SELECT * FROM Actions "	+
						"WHERE id LIKE '" 			+	actionId	+	"'";

		// executing the query
		List<Map<String, Object>> actionsList = databaseController.executeSelectQuery(query);

		// we should have only 1 statement
		if (actionsList.size() == 1) {

			Map<String, Object> actionMap = actionsList.get(0);
			Action tmpAction = new Action();
			tmpAction.setId((Integer) actionMap.get("id"));
			tmpAction.setSessionId((Integer) actionMap.get("session_id"));
			tmpAction.setType(ConverterHelper.stringToActionType((String) actionMap.get("type")));
			tmpAction.setDate(ConverterHelper.stringToDate((String) actionMap.get("date")));
			tmpAction.setData((String) actionMap.get("data"));
			tmpAction.setExerciceId((String) actionMap.get("exercice_id"));

			return tmpAction;
		}

		else {

			return null;
		}
	}

	/**
	 * getActions() is the method used to return a list of all actions with the same sessionId
	 *
	 * @param sessionId the id of the session containing the actions to retrieve
	 * @return a list of actions
	 *
	 * @see DatabaseController
	 */
	List<Action> getActions(Integer sessionId) {

		// preparing the query
		String query = 	"SELECT * FROM Actions "	+
						"WHERE session_id LIKE '"	+	sessionId	+	"'";

		// executing the query
		List<Map<String, Object>> actionsList = databaseController.executeSelectQuery(query);

		// creating a list to host the retrieved actions
		List<Action> actions = new ArrayList<Action>();

		// for each action in actionsList...
		for (Map<String, Object> actionMap : actionsList) {

			// we need to convert it to a plain Action object
			Action tmpAction = new Action();
			tmpAction.setId(ConverterHelper.stringToInteger((String) actionMap.get("id")));
			tmpAction.setSessionId(ConverterHelper.stringToInteger((String) actionMap.get("session_id")));
			tmpAction.setType(ConverterHelper.stringToActionType((String) actionMap.get("type")));
			tmpAction.setDate(ConverterHelper.stringToDate((String) actionMap.get("date")));
			tmpAction.setData((String) actionMap.get("data"));
			tmpAction.setExerciceId((String) actionMap.get("exercice_id"));

			// once the Action object has been created and filled, we add it to the actions List
			actions.add(tmpAction);
		}

		return actions;
	}


	//================================================================================
	// CREATE
	//================================================================================

	/**
	 * createNewAction() is the method responsible for creating a new Action object.
	 *
	 * @param user the user who performed the action.
	 * @param type the type of the action.
	 * @param date the date on which the action was performed.
	 * @param exerciceId the id of the exercise concerned by the action, if any.
	 * @param data the data concerned by the action, if any.
	 * @return the id of the database statement for the created action.
	 */
	public Integer createNewAction(User user, ActionType type, Date date, String exerciceId, String data) {

		if (user != null) {

			// creating the Action object
			Action action = new Action();
			action.setType(type);
			action.setDate(date);
			action.setExerciceId(exerciceId);
			action.setData(data);

			// getting the last session of the user who performed the action
			UserController userController = new UserController();
			Session lastSession = userController.getLastSession(user);

			// if the user's last session doesn't contain any LOGOUT action
			if (!containsLogout(lastSession.getActions())) {

				// we can continue to add actions to his last session
				// adding the id of the last session to the action
				action.setSessionId(lastSession.getId());
			}

			// else we have to create a new session
			else {

				// creating a new Session for the user
				SessionController sessionController = new SessionController();
				Integer sessionId = sessionController.createNewSession(user.getHistory().getId());

				// adding the id of the created session to the action
				action.setSessionId(sessionId);
			}

			// calling the saveAction() method
			return this.saveAction(action);
		}
		else {

			return null;
		}
	}

	/**
	 * saveAction() is the method responsible for saving an Action object in the database.
	 *
	 * @param action the Action object to save.
	 * @return the id of the database statement for the created action.
	 *
	 * @see DatabaseController
	 */
	private Integer saveAction(Action action) {

		// preparing the query
		String query = 	"INSERT INTO Actions (session_id, type, date, data, exercice_id) " +
						"VALUES (" 	+
						"'"			+	action.getSessionId()	+ 	"', " +
						"'"			+	action.getType()		+ 	"', " +
						"'"			+	action.getDate()		+ 	"', " +
						"'"			+	action.getExerciceId()	+ 	"', " +
						"'"			+	action.getData()		+ 	"')";

		// executing the query thanks to the DatabaseController
		return databaseController.executeInsertQuery(query);
	}


	/**
	 * containsLogout() is the method responsible for checking if a session contains a LOGOUT action.
	 *
	 * @param list the list of actions to check in.
	 * @return true or false, according to the result.
	 */
	private boolean containsLogout(final List<Action> list) {

		return list.stream().anyMatch(o -> o.getType().equals(ActionType.LOGOUT));
	}


}
