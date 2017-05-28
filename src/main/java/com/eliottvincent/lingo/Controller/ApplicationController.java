package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.*;
import com.eliottvincent.lingo.View.*;

import java.util.Scanner;

/**
 * Created by eliottvincent on 19/05/2017.
 */

public class ApplicationController {

	private Menu menuModel;
	private User currentUser;

	public ApplicationController() {

		this.menuModel = new Menu();

	}

	/*
	 *
	 * STEPS WHEN CREATING A USER
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 */

	/**
	 *
	 */
	private void waitUserInputCreateUserStepOne() {
		Scanner sc = new Scanner(System.in);

		while (!sc.hasNextLine()) {
			sc.next();
		}
		String userName = sc.nextLine();

		// TODO : validate userName
		// TODO : check if username available or not

		this.waitUserInputCreateUserStepTwo(userName);
	}

	/**
	 *
	 * @param username 	the username fetched in the first step
	 */
	private void waitUserInputCreateUserStepTwo(String username) {
		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String password = sc.nextLine();

		this.waitUserInputCreateUserStepThree(username, password);
	}

	/**
	 *
	 * @param username 	the username fetched in previous steps
	 * @param password 	the password fetched in previous steps
	 */
	private void waitUserInputCreateUserStepThree(String username, String password) {

		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String passwordBis = sc.nextLine();

		if (password.equals(passwordBis)) {

			this.waitUserInputCreateUserStepFour(username, password);
		}
		else {

			this.waitUserInputCreateUserStepThree(username, password);
		}

	}

	/**
	 *
	 * @param username	the username fetched in previous steps
	 * @param password	the password fetched in previous steps
	 */
	private void waitUserInputCreateUserStepFour(String username, String password) {
		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextInt()) {
			sc.next();
		}
		Integer age = sc.nextInt();

		this.waitUserInputCreateUserStepFive(username, password, age);
	}

	/**
	 *
	 * @param username 	the username fetched in previous steps
	 * @param password 	the password fetched in previous steps
	 * @param age 		the age fetched in previous steps
	 */
	private void waitUserInputCreateUserStepFive(String username, String password, Integer age) {

		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextInt()) {
			sc.next();
		}
		Integer genderIndex = sc.nextInt();
		Gender genderValue = Gender.values()[genderIndex - 1];

		this.waitUserInputCreateUserStepSix(username, password, age, genderValue);
	}

	/**
	 *
	 * @param username 	the username fetched in previous steps
	 * @param password 	the password fetched in previous steps
	 * @param age 		the age fetched in previous steps
	 * @param gender 	the gender fetched in previous steps
	 */
	private void waitUserInputCreateUserStepSix(String username, String password, Integer age, Gender gender) {

		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextInt()) {
			sc.next();
		}
		Integer languageIndex = sc.nextInt();
		Language languageValue = Language.values()[languageIndex - 1];


	}

	/*
	 *
	 * MISC FUNCTIONS
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 */

	/**
	 *
	 */
	private void quitProgram() {

		System.exit(0);
	}
}
