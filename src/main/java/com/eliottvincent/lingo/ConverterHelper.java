package com.eliottvincent.lingo;

/**
 * Created by eliottvincent on 09/06/2017.
 */

import com.eliottvincent.lingo.Data.ActionType;
import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Model.History;
import com.eliottvincent.lingo.Model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class ConverterHelper {


	/**
	 *
	 * @param language the language to be converted
	 * @return
	 */
	public static Language stringToLanguage(String language) {

		return Language.valueOf(language);
	}

	/**
	 *
	 * @param s the string to be converted
	 * @return
	 */
	public static int stringToInteger(String s) {

		return Integer.parseInt(s);
	}

	/**
	 *
	 * @param gender the gender to be converted
	 * @return
	 */
	public static Gender stringToGender(String gender) {

		return Gender.valueOf(gender);
	}

	/**
	 *
	 * @param actionType
	 * @return
	 */
	public static ActionType stringToActionType(String actionType) {

		return ActionType.valueOf(actionType);
	}

	/**
	 *
	 * @param language the language to be converted
	 * @return
	 */
	private static String languageToString(Language language) {

		return language.toString();
	}

	/**
	 *
	 * @param gender the gender to be converted
	 * @return
	 */
	private static String genderToString(Gender gender) {

		return gender.toString();
	}

	/**
	 *
	 * @param i the integer to be converted
	 * @return
	 */
	private static String integerToString(Integer i) {

		return i.toString();
	}

	/**
	 *
	 * @param history
	 * @return
	 */
	private static String historyToString(History history) {

		return ConverterHelper.integerToString(history.getId());
	}

	/**
	 *
	 * @param user the user to be converted
	 * @return
	 */
	public static String[] userToStringArray(User user) {

		// before converting it to an array, we need to find the id of
		return new String[]{
			user.getUsername(),
			user.getPassword(),
//			ConverterHelper.integerToString(user.getBirthdate()),
			ConverterHelper.genderToString(user.getGender()),
			ConverterHelper.languageToString(user.getLanguage()),
			ConverterHelper.historyToString(user.getHistory()),
		};
	}

	/**
	 *
	 * @param birthdateString
	 * @return
	 */
	public static Date stringToDate(String birthdateString) {


		String str = "01/01/2015";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate dateTime = LocalDate.parse(str, formatter);

		return java.sql.Date.valueOf(dateTime);
	}

	/**
	 *
	 * @param localDate
	 * @return
	 */
	public static Date localeDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 *
	 * @param localDateTime
	 * @return
	 */
	public static Date localeDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static LocalDate dateToLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 *
	 * @param userMap
	 * @return
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
}
