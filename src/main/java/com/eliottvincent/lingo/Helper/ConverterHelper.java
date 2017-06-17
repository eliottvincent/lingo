package com.eliottvincent.lingo.Helper;

/**
 * Created by eliottvincent on 09/06/2017.
 */

import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Data.LessonType;
import com.eliottvincent.lingo.Model.History;
import com.eliottvincent.lingo.Model.User;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * <b>ConverterHelper is the class responsible for any object conversion.</b>
 * <p>It's an helper .</p>
 *
 * @author eliottvincent
 */
public class ConverterHelper {

	/**
	 *
	 * @param s the string to be converted
	 * @return the string converted to an integer
	 */
	public static int stringToInteger(String s) {

		return Integer.parseInt(s);
	}

	/**
	 *
	 * @param i the integer to be converted
	 * @return
	 */
	public static String integerToString(Integer i) {

		return i.toString();
	}

	/**
	 *
	 * @param language the language to be converted
	 * @return the language converted to a string
	 */
	public static Language stringToLanguage(String language) {
		return Language.valueOf(language);
	}

	/**
	 *
	 * @param gender the gender to be converted
	 * @return the string converted to a Gender
	 */
	public static Gender stringToGender(String gender) {

		return Gender.valueOf(gender);
	}

	/**
	 *
	 * @param actionType the string to be converted
	 * @return the string converted to an Action
	 */
	public static ActionType stringToActionType(String actionType) {

		return ActionType.valueOf(actionType);
	}

	/**
	 *
	 * @param lessonType the string to be converted
	 * @return the String converted to a LessonType
	 */
	public static LessonType stringToLessonType(String lessonType) {

		return LessonType.valueOf(lessonType);
	}

	/**
	 *
	 * @param language the Language to be converted
	 * @return the Language converted to a string
	 */
	public static String languageToString(Language language) {

		String tmpLanguageString = language.toString().toLowerCase();

		return tmpLanguageString.substring(0, 1).toUpperCase() + tmpLanguageString.substring(1);
	}

	/**
	 *
	 * @param gender the Gender to be converted
	 * @return the Gender converted to a string
	 */
	public static String genderToString(Gender gender) {

		String tmpGenderString = gender.toString().toLowerCase();

		return tmpGenderString.substring(0, 1).toUpperCase() + tmpGenderString.substring(1);
	}

	/**
	 *
	 * @param lessonType the LessonType to be converted
	 * @return the LessonType converted to a string
	 */
	public static String lessonTypeToString(LessonType lessonType) {

		String tmpLessonTypeString = lessonType.toString().toLowerCase();

		return tmpLessonTypeString.substring(0, 1).toUpperCase() + tmpLessonTypeString.substring(1);
	}

	/**
	 *
	 * @param history the History to be converted
	 * @return the History converted to a string
	 */
	public static String historyToString(History history) {

		return ConverterHelper.integerToString(history.getId());
	}

	/**
	 *
	 * @param user the user to be converted
	 * @return the User converted to an array of string
	 */
	public static String[] userToStringArray(User user) {

		// before converting it to an array, we need to find the id of
		return new String[]{
			user.getUsername(),
			user.getPassword(),
			user.getBirthdate().toString(),
			ConverterHelper.genderToString(user.getGender()),
			ConverterHelper.languageToString(user.getLanguage()),
			ConverterHelper.historyToString(user.getHistory()),
		};
	}

	/**
	 *
	 * @param dateString the string to be converted
	 * @return the string converted to a date
	 */
	public static Date stringToDate(String dateString) {

		String str = "01/01/2015";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateTime = LocalDate.parse(str, formatter);

		return java.sql.Date.valueOf(dateTime);
	}

	/**
	 *
	 * @param date the Date to be converted
	 * @return the date converted to a String
	 */
	public static String dateToString(Date date) {

		Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(date);
	}

	/**
	 *
	 * @param localDate the LocalDate to be converted
	 * @return the LocalDate converted to a Date
	 */
	public static Date localeDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 *
	 * @param localDateTime the LocalDateTime to be converted
	 * @return the LocalDateTime converted to Date
	 */
	public static Date localeDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 *
	 * @param date the Date to be converted
	 * @return the Date converted to a LocalDate
	 */
	public static LocalDate dateToLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 *
	 * @param date the Date to be converted
	 * @return the Date convertred to a LocalDateTime
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 *
	 * @param userMap the Map to be converted
	 * @return the Map converted to an User
	 */
	public static User mapToUser(Map<String, Object> userMap) {

		User user = new User();
		user.setId(ConverterHelper.stringToInteger((String) userMap.get("id")));
		user.setUsername((String) userMap.get("username"));
		user.setPassword((String) userMap.get("password"));
		user.setBirthdate(ConverterHelper.stringToDate((String) userMap.get("birthdate")));
		user.setGender(ConverterHelper.stringToGender((String) userMap.get("gender")));
		user.setLanguage(ConverterHelper.stringToLanguage((String) userMap.get("language")));

		return user;
	}

	/**
	 *
	 * @param simpleIntegerProperty the SimpleIntegerProperty to be converted
	 * @return the SimpleIntegerProperty converted to SimpleStringProperty
	 */
	public static SimpleStringProperty simpleIntegerPropertyToSimpleStringProperty(SimpleIntegerProperty simpleIntegerProperty) {

		// extracts the Integer of the SimpleIntegerProperty
		Integer i = simpleIntegerProperty.getValue();

		// converts the integer to a String
		String s = ConverterHelper.integerToString(i);

		// encapsulates the string in a SimpleStringProperty
		return new SimpleStringProperty(s);
	}

	/**
	 *
	 * @param genderSimpleObjectProperty the SimpleObjectProperty<Gender> to be converted
	 * @return the SimpleObjectProperty<Gender> converted to a SimpleStringProperty
	 */
	public static SimpleStringProperty genderSimpleObjectPropertyToSimpleStringProperty(SimpleObjectProperty<Gender> genderSimpleObjectProperty) {

		// extracts the Gender value of the SimpleObjectProperty<Gender>
		Gender g = genderSimpleObjectProperty.getValue();

		// converts the gender to a String
		String s = ConverterHelper.genderToString(g);

		// encapsulates the string in a SimpleStringProperty
		return new SimpleStringProperty(s);
	}

	/**
	 *
	 * @param dateSimpleObjectProperty the SimpleObjectProperty<Date> to be converted
	 * @return the SimpleObjectProperty<Date> converted to SimpleStringProperty
	 */
	public static SimpleStringProperty dateSimpleObjectPropertyToSimpleStringProperty(SimpleObjectProperty<Date> dateSimpleObjectProperty) {

		// extracts the Date value of the SimpleObjectProperty<Date>
		Date d = dateSimpleObjectProperty.getValue();

		// converts the date to a String
		String s = ConverterHelper.dateToString(d);

		// encapsulates the string in a SimpleStringProperty
		return new SimpleStringProperty(s);
	}
}
