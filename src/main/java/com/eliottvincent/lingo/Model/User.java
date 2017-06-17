package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.ViewController.AdminViewController;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

import java.util.Date;

/**
 * Created by eliottvct on 17/05/17.
 */

public class User extends RecursiveTreeObject<User> {

	public SimpleIntegerProperty id;
	public SimpleStringProperty username;
	public SimpleStringProperty password;
	public SimpleObjectProperty<Gender> gender;
	public SimpleObjectProperty<Date> birthdate;
	public SimpleObjectProperty<Language> language;
	public SimpleBooleanProperty isGuest;
	public SimpleObjectProperty<History> history;

	// TODO : abstract class User + GuestUser + NormalUser ??????

	public User() {
		this.isGuest = new SimpleBooleanProperty(true);
	}

	public User(String username, String password) {
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.isGuest = new SimpleBooleanProperty(false);
	}

	public User(String username, String password, Date birthdate, Gender gender, Language language, History history) {

		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.birthdate = new SimpleObjectProperty<>(birthdate);
		this.gender = new SimpleObjectProperty<>(gender);
		this.language = new SimpleObjectProperty<>(language);
		this.isGuest = new SimpleBooleanProperty(false);
		this.history = new SimpleObjectProperty<>(history);
	}


	public Integer getId() {
		return id.get();
	}

	public void setId(Integer idValue) {

		this.id = new SimpleIntegerProperty(idValue);
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String usernameValue) {

		this.username = new SimpleStringProperty(usernameValue);
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String passwordValue) {

		this.password = new SimpleStringProperty(passwordValue);

	}

	public Gender getGender() {
		return gender.get();
	}

	public void setGender(Gender genderValue) {

		this.gender = new SimpleObjectProperty<>(genderValue);
	}

	public Language getLanguage() {
		return language.get();
	}

	public void setLanguage(Language languageValue) {

		this.language = new SimpleObjectProperty<>(languageValue);
	}

	public boolean isGuest() {
		return isGuest.get();
	}

	public void setGuest(boolean guestValue) {

		this.isGuest = new SimpleBooleanProperty(guestValue);
	}

	public History getHistory() {
		return history.get();
	}

	public void setHistory(History historyValue) {

		this.history = new SimpleObjectProperty<>(historyValue);
	}

	public Date getBirthdate() {
		return birthdate.get();
	}

	public void setBirthdate(Date birthdateValue) {

		this.birthdate = new SimpleObjectProperty<>(birthdateValue);
	}

	public final SimpleIntegerProperty idProperty() {
		return id;
	};
	public final SimpleStringProperty usernameProperty() {
		return username;
	};
	public final SimpleStringProperty passwordProperty() {
		return password;
	};
	public final SimpleObjectProperty<Gender> genderProperty() {
		return gender;
	};
	public final SimpleObjectProperty<Date> birthdateProperty() {
		return birthdate;
	};
	public final SimpleObjectProperty<Language> languageProperty() {
		return language;
	};
	public final SimpleBooleanProperty isGuestProperty() {
		return isGuest;
	};
	public final SimpleObjectProperty<History> historyProperty() {
		return history;
	};
}
