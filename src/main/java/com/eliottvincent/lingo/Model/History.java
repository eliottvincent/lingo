package com.eliottvincent.lingo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>History is the class representing an history in Lingo.</b>
 * <p>Each user has one and only one history.</p>
 * <p>An history contains a list of sessions.</p>
 *
 * @see Session
 *
 * @author eliottvincent
 */
public class History {


	//================================================================================
	// Properties
	//================================================================================

	/**
	 * The id of the history.
	 *
	 * @see History#getId()
	 * @see History#setId(Integer)
	 */
	private Integer id;

	/**
	 * The sessions contained in the history.
	 *
	 * @see History#getSessions()
	 * @see History#setSessions(List)
	 */
	private List<Session> sessions = new ArrayList<>();

	/**
	 * The id of the user linked to the history.
	 *
	 * @see History#getUserId()
	 * @see History#setUserId(Integer)
	 */
	private Integer userId;


	//================================================================================
	// Constructors
	//================================================================================

	/**
	 * The default constructor for an exercise.
	 */
	public History() {

	}

	/**
	 * The parameterized constructor for an exercise.
	 *
	 * @param id the id of the new history.
	 * @param sessions the list of sessions of the new history.
	 * @param userId the userId of
	 */
	public History(Integer id, List<Session> sessions, Integer userId) {

		this.id = id;
		this.sessions = sessions;
		this.userId = userId;

	}


	//================================================================================
	// Getters and setters
	//================================================================================

	/**
	 *
	 * @return the id of the history.
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
	 * @return the sessions of the history.
	 */
	public List<Session> getSessions() {
		return sessions;
	}

	/**
	 *
	 * @param sessions the sessions to set.
	 */
	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	/**
	 *
	 * @param session the session to add to the list of sessions of the history.
	 */
	public void addSession(Session session) {

		this.sessions.add(session);
	}

	/**
	 *
	 * @return the userId of the history.
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 *
	 * @param userId the userId to set.
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
