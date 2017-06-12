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
 * Created by eliottvincent on 10/06/2017.
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
	 *
	 */
	public ActionController() {

	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 *
	 * @param actionId
	 * @return
	 */
	Action getAction(Integer actionId) {

		String query =	"SELECT * FROM Actions "	+
						"WHERE id LIKE '" 			+	actionId	+	"'";

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
	 * used to return a list of all actions with the same session_id in db
	 * @param sessionId
	 * @return
	 */
	List<Action> getActions(Integer sessionId) {

		String query = 	"SELECT * FROM Actions "	+
						"WHERE session_id LIKE '"	+	sessionId	+	"'";

		List<Map<String, Object>> actionsList = databaseController.executeSelectQuery(query);
		List<Action> actions = new ArrayList<Action>();

		for (Map<String, Object> actionMap : actionsList) {
			Action tmpAction = new Action();
			tmpAction.setId(ConverterHelper.stringToInteger((String) actionMap.get("id")));
			tmpAction.setSessionId(ConverterHelper.stringToInteger((String) actionMap.get("session_id")));
			tmpAction.setType(ConverterHelper.stringToActionType((String) actionMap.get("type")));
			tmpAction.setDate(ConverterHelper.stringToDate((String) actionMap.get("date")));
			tmpAction.setData((String) actionMap.get("data"));
			tmpAction.setExerciceId((String) actionMap.get("exercice_id"));

			actions.add(tmpAction);
		}

		return actions;
	}


	//================================================================================
	// CREATE
	//================================================================================

	/**
	 *
	 * @param user
	 * @return
	 */
	public Integer createNewAction(User user, ActionType type, Date date, String exerciceId, String data) {

		if (user != null) {

			UserController userController = new UserController();
			Session lastSession = userController.getLastSession(user);

			Action action = new Action();

			action.setType(type);
			action.setDate(date);
			action.setExerciceId(exerciceId);
			action.setData(data);


			// if the user's last session doesn't contain any "logout"
			// we can continue to add actions to it
			if (!containsLogout(lastSession.getActions())) {

				action.setSessionId(lastSession.getId());
			}

			// else we have to create a new session
			else {

				SessionController sessionController = new SessionController();
				Integer sessionId = sessionController.createNewSession(user.getHistory().getId());

				action.setSessionId(sessionId);

			}

			return this.saveAction(action);
		}
		else {

			return null;
		}
	}

	/**
	 *
	 * @param action
	 * @return
	 */
	private Integer saveAction(Action action) {

		String query = 	"INSERT INTO Actions (session_id, type, date, data, exercice_id) " +
						"VALUES (" 	+
						"'"			+	action.getSessionId()	+ 	"', " +
						"'"			+	action.getType()		+ 	"', " +
						"'"			+	action.getDate()		+ 	"', " +
						"'"			+	action.getExerciceId()	+ 	"', " +
						"'"			+	action.getData()		+ 	"')";

		return databaseController.executeInsertQuery(query);
	}


	/**
	 *
	 * @param list
	 * @return
	 */
	public boolean containsLogout(final List<Action> list) {

		return list.stream().anyMatch(o -> o.getType().equals(ActionType.LOGOUT));
	}


}
