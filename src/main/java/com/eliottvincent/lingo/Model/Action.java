package com.eliottvincent.lingo.Model;


import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Data.Language;

import java.util.Date;

/**
 * Created by eliottvincent on 10/06/2017.
 */
public class Action {

	private Integer id;

	private Integer sessionId;

	private ActionType type;

	private Date date;

	private String data;

	private String exerciceId;

	public Action() {

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
	public Integer getSessionId() {
		return sessionId;
	}

	/**
	 *
	 * @param sessionId
	 */
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 *
	 * @return
	 */
	public ActionType getType() {
		return type;
	}

	/**
	 *
	 * @param type
	 */
	public void setType(ActionType type) {
		this.type = type;
	}

	/**
	 *
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 *
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 *
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 *
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 *
	 * @return
	 */
	public String getExerciceId() {
		return exerciceId;
	}

	/**
	 *
	 * @param exerciceId
	 */
	public void setExerciceId(String exerciceId) {
		this.exerciceId = exerciceId;
	}
}
