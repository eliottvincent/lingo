package com.eliottvincent.lingo.Model;

import java.util.Collection;

/**
 * Created by eliottvct on 17/05/17.
 */
public class History {

	private int id;

	private Collection<Session> sessions;

	public History() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<Session> getSessions() {
		return sessions;
	}

	public void setSessions(Collection<Session> sessions) {
		this.sessions = sessions;
	}

	public void addSession(Session session) {

		this.sessions.add(session);
	}
}
