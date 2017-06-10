package com.eliottvincent.lingo.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by eliottvct on 17/05/17.
 */
public class Session {

	private Integer id;

	private Integer historyId;

	private Date startDate;

	private Date endDate;

	private List<Action> actions = new ArrayList<>();

	public Session() {

	}


	/**
	 *
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 *
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 *
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 *
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 *
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 *
	 * @return
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 *
	 * @param actions
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	/**
	 *
	 * @return
	 */
	public Integer getHistoryId() {
		return historyId;
	}

	/**
	 *
	 * @param historyId
	 */
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
}
