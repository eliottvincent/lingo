package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Data.ActionType;
import java.util.Date;

/**
 * <b>Action is the class representing an action performed by the user in Lingo.</b>
 * <p>An action is part of a session.</p>
 *
 * @see Session
 * @see History
 *
 * @author eliottvincent
 */
public class Action {


	//================================================================================
	// Properties
	//================================================================================

	/**
	 * The id of the action.
	 *
	 * @see Action#getId()
	 * @see Action#setId(Integer)
	 */
	private Integer id;

	/**
	 * The id of the session linked to the action.
	 *
	 * @see Action#getSessionId()
	 * @see Action#setSessionId(Integer)
	 */
	private Integer sessionId;

	/**
	 * The type of the action.
	 * The possible values for this type are defined into the enumeration ActionType.
	 *
	 * @see Action#getType()
	 * @see Action#setType(ActionType)
	 * @see ActionType
	 */
	private ActionType type;

	/**
	 * The date when the action was performed.
	 *
	 * @see Action#getDate()
	 * @see Action#setDate(Date)
	 */
	private Date date;

	/**
	 * The data of the action (if any).
	 *
	 * @see Action#getData()
	 * @see Action#setData(String)
	 */
	private String data;

	/**
	 * The id of the exercise (if any).
	 * If the action is not linked to an exercise event, there is no exerciseId
	 *
	 * @see Action#getExerciceId()
	 * @see Action#setExerciceId(String)
	 */
	private String exerciceId;


	//================================================================================
	// Constructors
	//================================================================================

	/**
	 * The default constructor for an action.
	 */
	public Action() {

	}


	//================================================================================
	// Getters and setters
	//================================================================================

	/**
	 *
	 * @return the id of the action.
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
	 * @return the sessionId of the action.
	 */
	public Integer getSessionId() {
		return sessionId;
	}

	/**
	 *
	 * @param sessionId the sessionId to set.
	 */
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 *
	 * @return the type of the action.
	 */
	public ActionType getType() {
		return type;
	}

	/**
	 *
	 * @param type the type to set.
	 */
	public void setType(ActionType type) {
		this.type = type;
	}

	/**
	 *
	 * @return the date of the action.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 *
	 * @param date the date to set.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 *
	 * @return the data of the action.
	 */
	public String getData() {
		return data;
	}

	/**
	 *
	 * @param data the data to set.
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 *
	 * @return the exerciceId of the action.
	 */
	public String getExerciceId() {
		return exerciceId;
	}

	/**
	 *
	 * @param exerciceId the exerciceId to set.
	 */
	public void setExerciceId(String exerciceId) {
		this.exerciceId = exerciceId;
	}



}
