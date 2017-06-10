package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;

import java.util.Date;

/**
 * Created by eliottvct on 17/05/17.
 */

public class User {

	private int id;
	private String username;
	private String password;
	private Gender gender;
	private Date birthdate;
	private Language language;
	private boolean isGuest;
	private History history;

	// TODO : abstract class User + GuestUser + NormalUser ??????

	public User() {
		this.isGuest = true;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.isGuest = false;
	}

	public User(String username, String password, Date birthdate, Gender gender, Language language, History history) {
		this.username = username;
		this.password = password;
		this.birthdate = birthdate;
		this.gender = gender;
		this.language = language;
		this.isGuest = false;
		this.history = history;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public boolean isGuest() {
		return isGuest;
	}

	public void setGuest(boolean guest) {
		isGuest = guest;
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
