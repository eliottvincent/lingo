package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Model.Action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by eliottvincent on 10/06/2017.
 */
class ActionController {


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
	ActionController() {

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
	 * @param sessionId
	 * @return
	 */
	Integer createNewAction(Integer sessionId, ActionType type, Date date, String exerciceId, String data) {

		Action action = new Action();

		action.setSessionId(sessionId);
		action.setType(type);
		action.setDate(date);
		action.setExerciceId(exerciceId);
		action.setData(data);

		return this.saveAction(action);
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


}
