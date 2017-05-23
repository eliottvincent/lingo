package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.*;
import com.eliottvincent.lingo.View.*;

import java.util.Scanner;

/**
 * Created by eliottvincent on 19/05/2017.
 */

public class ApplicationController {

	private ApplicationView applicationView;
	private Menu menuModel;
	private User currentUser;

	public ApplicationController() {

		this.menuModel = new Menu();

		this.applicationView = new TerminalView();
		this.onStartup();
	}

	/**
	 *
	 */
	private void onStartup() {

		this.applicationView.showStartupMenu(this.menuModel.getMenuOptions());
		this.waitUserInputMenu();
	}

	/**
	 *
	 */
	private void waitUserInputMenu() {
		Scanner sc = new Scanner(System.in);

		while (!sc.hasNextInt()) {
			System.out.println("You have to choose a number between 1 and " + this.menuModel.getMenuOptions().size() + ".");
			sc.next();
		}
		int i = sc.nextInt();
		switch(i) {
			case 1:
				this.onCreateUser();
				break;
			case 2:
				this.onLogin(false);
				break;
			case 3:
				this.onLogin(true);
				break;
			case 4:
				this.quitProgram();
				break;
			default:
				System.out.println("You have to choose a number between 1 and 4.");
				this.waitUserInputMenu();
		}
	}





	/*
	 *
	 * STEPS WHEN LOGIN
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
	 * @param isAnonymous 	identifies whether the user wants to login anonymously or not
	 */
	private void onLogin(boolean isAnonymous) {

		// if the user wants to log in as anonymous
		if (isAnonymous) {

			// then we don't have to verify his credentials
			this.applicationView.showMainMenu();
		}
		else {

			// displaying the login step one
			this.applicationView.showLoginUserStepOne();

			// waiting for input
			this.waitUserInputLoginUserStepOne();
		}
	}

	/**
	 *
	 */
	private void waitUserInputLoginUserStepOne() {
		Scanner sc = new Scanner(System.in);

		while (!sc.hasNextLine()) {
			sc.next();
		}
		String userName = sc.nextLine();

		this.applicationView.showLoginUserStepTwo();
		this.waitUserInputLoginUserStepTwo(userName);
	}

	/**
	 *
	 * @param username 	the username fetched in the first step
	 */
	private void waitUserInputLoginUserStepTwo(String username) {

		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String password = sc.nextLine();

		UserController userController = new UserController();
		User tmpUser = userController.logIn(username, password);

		if (tmpUser != null) {
			this.currentUser = tmpUser;
			this.applicationView.showMainMenu();
		}
		else {
			this.applicationView.showLoginError();
			this.applicationView.showLoginUserStepOne();
			this.waitUserInputLoginUserStepOne();
		}
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
	private void onCreateUser() {
		this.applicationView.showCreateUserStepOne();

		this.waitUserInputCreateUserStepOne();
	}

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

		this.applicationView.showCreateUserStepTwo();
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

		this.applicationView.showCreateUserStepThree();
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

			this.applicationView.showCreateUserStepFour();
			this.waitUserInputCreateUserStepFour(username, password);
		}
		else {

			this.applicationView.displayErrorMessage("Password don’t match. Please try again.\n");
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

		this.applicationView.showCreateUserStepFive();
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

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String gender = sc.nextLine();

		this.applicationView.showCreateUserStepSix(this.menuModel.getLanguageOptions());
		this.waitUserInputCreateUserStepSix(username, password, age, gender);
	}

	/**
	 *
	 * @param username 	the username fetched in previous steps
	 * @param password 	the password fetched in previous steps
	 * @param age 		the age fetched in previous steps
	 * @param gender 	the gender fetched in previous steps
	 */
	private void waitUserInputCreateUserStepSix(String username, String password, Integer age, String gender) {

		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String language = sc.nextLine();
		System.out.println(language);
		UserController userController = new UserController();
		this.currentUser = userController.saveUser(
			username,
			password,
			age,
			ConverterHelper.stringToGender(gender),
			ConverterHelper.stringToLanguage(language)
		);


		this.applicationView.showMainMenu();
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

		this.applicationView.showQuit();
		System.exit(0);
	}


	/*
	 *
	 * GETTERS AND SETTERS
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
	 * @return ApplicationView
	 */
	public ApplicationView getApplicationView() {
		return applicationView;
	}

	/**
	 *
	 * @param applicationView
	 */
	public void setApplicationView(ApplicationView applicationView) {
		this.applicationView = applicationView;
	}

	/**
	 *
	 * @return menuModel
	 */
	public Menu getMenuModel() {
		return menuModel;
	}

	/**
	 *
	 * @param menuModel
	 */
	public void setMenuModel(Menu menuModel) {
		this.menuModel = menuModel;
	}

	/**
	 *
	 * @return
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 *
	 * @param currentUser
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
