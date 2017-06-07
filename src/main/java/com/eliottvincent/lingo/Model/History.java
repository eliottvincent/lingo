package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Controller.StorageController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by eliottvct on 17/05/17.
 */
public class History {

	private int id;

	private Collection<Session> sessions = new ArrayList<>();

	private StorageController storageController = new StorageController();

	public History() {

		// we need to search what will be the next id
		// this will be removed once we move to JDBC + sqlite3 💪 (auto-increment id)
		this.setId(storageController.getNumberOfUsers() + 1);
	}

	public History(Integer id, Collection<Session> sessions) {

		this.id = id;
		this.sessions = sessions;

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
