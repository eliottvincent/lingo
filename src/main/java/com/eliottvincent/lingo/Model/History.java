package com.eliottvincent.lingo.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by eliottvct on 17/05/17.
 */
public class History {

	private int id;

	private List<Session> sessions = new ArrayList<>();

	private Integer userId;

	public History() {

	}

	public History(Integer id, List<Session> sessions, Integer userId) {

		this.id = id;
		this.sessions = sessions;
		this.userId = userId;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public void addSession(Session session) {

		this.sessions.add(session);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
