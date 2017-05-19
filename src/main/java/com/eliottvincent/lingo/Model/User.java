package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;

import java.util.Collection;

/**
 * Created by eliottvct on 17/05/17.
 */

public class User {

	private int id;
	private String userName;
	private String password;
	private Gender gender;
	private int age;
	private Language language;
	private Collection<Session> sessions;
	private Collection<History> history;

	public User(String userName, String password, Gender gender, int age, Language language) {
		this.userName = userName;
		this.password = password;
		this.gender = gender;
		this.age = age;
		this.language = language;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Collection<Session> getSessions() {
		return sessions;
	}

	public void setSessions(Collection<Session> sessions) {
		this.sessions = sessions;
	}

	public Collection<History> getHistory() {
		return history;
	}

	public void setHistory(Collection<History> history) {
		this.history = history;
	}
}
