package com.eliottvincent.lingo.Helper;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Model.User;

/**
 * Created by eliottvct on 23/05/17.
 */
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
	 * @param user the user to be converted
	 * @return
	 */
	public static String[] userToStringArray(User user) {

		return new String[]{
			user.getUserName(),
			user.getPassword(),
			ConverterHelper.integerToString(user.getAge()),
			ConverterHelper.genderToString(user.getGender()),
			ConverterHelper.languageToString(user.getLanguage())
		};
	}

}
