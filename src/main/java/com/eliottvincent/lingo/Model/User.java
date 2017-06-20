package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Date;

/**
 * <b>User is the class representing an user in Lingo.</b>
 * <p>A user contains one and only one history.</p>
 *
 * @see History
 *
 * @author eliottvincent
 */
public class User extends RecursiveTreeObject<User> {


	//================================================================================
	// Properties
	//================================================================================

	/**
	 * The id of the user.
	 *
	 * @see User#getId() ()
	 * @see User#setId(Integer)
	 */
	private SimpleIntegerProperty id;

	/**
	 * The username of the user.
	 *
	 * @see User#getUsername()
	 * @see User#setUsername(String)
	 */
	private SimpleStringProperty username;

	/**
	 * The password of the user.
	 *
	 * @see User#getPassword()
	 * @see User#setPassword(String)
	 */
	private SimpleStringProperty password;

	/**
	 * The gender of the user.
	 * The possible values for the gender are defined into the enumeration Gender.
	 *
	 * @see User#getGender()
	 * @see User#setGender(Gender)
	 * @see Gender
	 */
	private SimpleObjectProperty<Gender> gender;

	/**
	 * The birthdate of the user.
	 *
	 * @see User#getBirthdate()
	 * @see User#setBirthdate(Date)
	 */
	private SimpleObjectProperty<Date> birthdate;

	/**
	 * The language of the user.
	 * The possible values for the language are defined into the enumeration Language.
	 *
	 * @see User#getLanguage()
	 * @see User#setLanguage(Language)
	 * @see Language
	 */
	private SimpleObjectProperty<Language> language;

	/**
	 * If the user is a guest or not.
	 *
	 * @see User#isGuest()
	 * @see User#setGuest(boolean)
	 */
	private SimpleBooleanProperty guest;

	/**
	 * The history of the user.
	 *
	 * @see User#getHistory()
	 * @see User#setHistory(History)
	 */
	private SimpleObjectProperty<History> history;


	//================================================================================
	// Constructors
	//================================================================================

	/**
	 * The default constructor for a user.
	 * This constructor creates an user with no properties, except the guest property that is set to true;
	 */
	public User() {

		this.guest = new SimpleBooleanProperty(true);
	}

	/**
	 *
	 * @param username the username of the new user.
	 * @param password the password of the new user.
	 */
	public User(String username, String password) {
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.guest = new SimpleBooleanProperty(false);
	}

	/**
	 *
	 * @param username the username of the new user.
	 * @param password the password of the new user.
	 * @param birthdate the birthdate of the new user.
	 * @param gender the gender of the new user.
	 * @param language the language of the new user.
	 * @param history the history of the new user.
	 */
	public User(String username, String password, Date birthdate, Gender gender, Language language, History history) {

		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.birthdate = new SimpleObjectProperty<>(birthdate);
		this.gender = new SimpleObjectProperty<>(gender);
		this.language = new SimpleObjectProperty<>(language);
		this.guest = new SimpleBooleanProperty(false);
		this.history = new SimpleObjectProperty<>(history);
	}


	//================================================================================
	// Getters and setters
	//================================================================================

	/**
	 *
	 * @return the id of the user.
	 */
	public Integer getId() {
		return id.get();
	}

	/**
	 *
	 * @param idValue the id to set.
	 */
	public void setId(Integer idValue) {
		this.id = new SimpleIntegerProperty(idValue);
	}

	/**
	 *
	 * @return the username of the user.
	 */
	public String getUsername() {
		return username.get();
	}

	/**
	 *
	 * @param usernameValue the username to set.
	 */
	public void setUsername(String usernameValue) {
		this.username = new SimpleStringProperty(usernameValue);
	}

	/**
	 *
	 * @return the password of the user.
	 */
	public String getPassword() {
		return password.get();
	}

	/**
	 *
	 * @param passwordValue the password to set.
	 */
	public void setPassword(String passwordValue) {
		this.password = new SimpleStringProperty(passwordValue);

	}

	/**
	 *
	 * @return the gender of the user.
	 */
	public Gender getGender() {
		return gender.get();
	}

	/**
	 *
	 * @param genderValue the gender to set.
	 */
	public void setGender(Gender genderValue) {
		this.gender = new SimpleObjectProperty<>(genderValue);
	}

	/**
	 *
	 * @return the language of the user.
	 */
	public Language getLanguage() {
		return language.get();
	}

	/**
	 *
	 * @param languageValue the language to set.
	 */
	public void setLanguage(Language languageValue) {
		this.language = new SimpleObjectProperty<>(languageValue);
	}

	/**
	 *
	 * @return if the user is a guest or not.
	 */
	public boolean isGuest() {
		return guest.get();
	}

	/**
	 *
	 * @param guestValue the
	 */
	public void setGuest(boolean guestValue) {
		this.guest = new SimpleBooleanProperty(guestValue);
	}

	/**
	 *
	 * @return the history of the user.
	 */
	public History getHistory() {
		return history.get();
	}

	/**
	 *
	 * @param historyValue the history to set.
	 */
	public void setHistory(History historyValue) {
		this.history = new SimpleObjectProperty<>(historyValue);
	}

	/**
	 *
	 * @return the birthdate of the user.
	 */
	public Date getBirthdate() {
		return birthdate.get();
	}

	/**
	 *
	 * @param birthdateValue the birthdate to set.
	 */
	public void setBirthdate(Date birthdateValue) {
		this.birthdate = new SimpleObjectProperty<>(birthdateValue);
	}

	/**
	 *
	 * @return the idProperty of the user
	 */
	public final SimpleIntegerProperty idProperty() {
		return id;
	};

	/**
	 *
	 * @return the usernameProperty of the user
	 */
	public final SimpleStringProperty usernameProperty() {
		return username;
	};

	/**
	 *
	 * @return the passwordProperty of the user
	 */
	public final SimpleStringProperty passwordProperty() {
		return password;
	};

	/**
	 *
	 * @return the genderProperty of the user
	 */
	public final SimpleObjectProperty<Gender> genderProperty() {
		return gender;
	};

	/**
	 *
	 * @return the birthdateProperty of the user
	 */
	public final SimpleObjectProperty<Date> birthdateProperty() {
		return birthdate;
	};

	/**
	 *
	 * @return the languageProperty of the user
	 */
	public final SimpleObjectProperty<Language> languageProperty() {
		return language;
	};

	/**
	 *
	 * @return the guestProperty of the user
	 */
	public final SimpleBooleanProperty guestProperty() {
		return guest;
	};

	/**
	 *
	 * @return the historyProperty of the user
	 */
	public final SimpleObjectProperty<History> historyProperty() {
		return history;
	};
}
