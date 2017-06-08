package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Model.History;

/**
 * Created by eliottvct on 17/05/17.
 */
public class HistoryController {

	private DatabaseLayer databaseLayer = new DatabaseLayer();

	public HistoryController() {

	}

	public History createHistory(Integer userId) {


		History newHistory = new History();
		newHistory.setUserId(userId);

		// saving it in the database and getting the id of the statement
		Integer id = this.saveHistory(newHistory);

		// updating the History object with the id
		newHistory.setId(id);

		return newHistory;
	}

	/**
	 *
	 * @param history
	 */
	private void updateHistory(History history) {

		this.databaseLayer.updateHistory(history);
	}


	/**
	 *
	 * @return
	 * @param newHistory
	 */
	public Integer saveHistory(History newHistory) {

		return this.databaseLayer.saveHistory(newHistory);
	}
}
