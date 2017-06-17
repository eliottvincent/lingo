package com.eliottvincent.lingo.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <b>Session is the class representing a session in Lingo.</b>
 * <p>A session is linked to a specific history, and contains a list of actions.</p>
 * <p>A session always start with a ACCOUNT_CREATION or LOGIN action type.</p>
 * <p>A session always end with a LOGOUT action type.</p>
 *
 * @see History
 * @see Action
 * @see com.eliottvincent.lingo.Data.ActionType
 *
 * @author eliottvincent
 */
public class Session {


	//================================================================================
	// Properties
	//================================================================================

	/**
	 * The id of the session.
	 *
	 * @see Session#getId()
	 * @see Session#setId(Integer)
	 */
	private Integer id;

	/**
	 * The id of the history linked to the session.
	 *
	 * @see Session#getHistoryId()
	 * @see Session#setHistoryId(Integer)
	 */
	private Integer historyId;

	/**
	 * The starting date of the session.
	 *
	 * @see Session#getStartDate()
	 * @see Session#setStartDate(Date)
	 */
	private Date startDate;

	/**
	 * The ending date of the session.
	 *
	 * @see Session#getEndDate()
	 * @see Session#setEndDate(Date)
	 */
	private Date endDate;

	/**
	 * The list of actions contained in the session.
	 *
	 * @see Session#getActions()
	 * @see Session#setActions(List)
	 */
	private List<Action> actions = new ArrayList<>();


	//================================================================================
	// Constructors
	//================================================================================

	/**
	 * The default constructor for a session.
	 */
	public Session() {

	}


	//================================================================================
	// Getters and setters
	//================================================================================

	/**
	 *
	 * @return the id of the session.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 *
	 * @param id the id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 *
	 * @return the starting date of the session.
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 *
	 * @param startDate the starting date to set.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 *
	 * @return the ending date of the session.
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 *
	 * @param endDate the ending date to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 *
	 * @return the list of actions of the session.
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 *
	 * @param actions the list of actions to set.
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	/**
	 *
	 * @return the id of the history linked to the session.
	 */
	public Integer getHistoryId() {
		return historyId;
	}

	/**
	 *
	 * @param historyId the id of the history to set.
	 */
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
}
