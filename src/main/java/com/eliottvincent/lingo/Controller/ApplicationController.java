package com.eliottvincent.lingo.Controller;

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

	private void onStartup() {

		this.applicationView.showStartupMenu(this.menuModel.getMenuOptions());
		this.waitUserInputMenu();
	}

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

	/**
	 *
	 * @param isAnonymous
	 */
	private void onLogin(boolean isAnonymous ) {

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

	private void waitUserInputLoginUserStepOne() {
		Scanner sc = new Scanner(System.in);

		while (!sc.hasNextLine()) {
			sc.next();
		}
		String userName = sc.nextLine();

		this.applicationView.showLoginUserStepTwo();
		this.waitUserInputLoginUserStepTwo(userName);
	}

	private void waitUserInputLoginUserStepTwo(String username) {

		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String password = sc.nextLine();

		UserController userController = new UserController();

		User tmpUser = userController.logIn(username, password, null, null, null);

		if (tmpUser != null) {
			this.currentUser = tmpUser;
			// TODO : save user in context
			// TODO : display main application
		}
		else {
			this.applicationView.showLoginError();
			// TODO : display error message
		}
	}

	private void onCreateUser() {
		this.applicationView.showCreateUserStepOne();

		this.waitUserInputCreateUserStepOne();
	}

	private void waitUserInputCreateUserStepOne() {
		Scanner sc = new Scanner(System.in);

		while (!sc.hasNextLine()) {
			sc.next();
		}
		String userName = sc.nextLine();

		// TODO : validate userName

		this.applicationView.showCreateUserStepTwo();
		this.waitUserInputCreateUserStepTwo(userName);
	}

	private void waitUserInputCreateUserStepTwo(String username) {
		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String password = sc.nextLine();

		this.applicationView.showCreateUserStepThree();
		this.waitUserInputCreateUserStepThree(username, password);
	}

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

			this.applicationView.displayErrorMessage("Password don’t match. Please try again.");
			this.waitUserInputCreateUserStepThree(username, password);
		}

	}

	private void waitUserInputCreateUserStepFour(String username, String password) {
		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextInt()) {
			sc.next();
		}
		Integer age = sc.nextInt();

		this.applicationView.showCreateUserStepFive();
		this.waitUserInputCreateUserStepFive(username, password, age);
	}

	private void waitUserInputCreateUserStepFive(String username, String password, Integer age) {

		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String gender = sc.nextLine();

		this.applicationView.showCreateUserStepSix(this.menuModel.getLanguageOptions());
		this.waitUserInputCreateUserStepSix(username, password, age, gender);
	}

	private void waitUserInputCreateUserStepSix(String username, String password, Integer age, String gender) {

		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String language = sc.nextLine();

		UserController userController = new UserController();
		userController.saveUser(username, password, age, gender, language);
		this.currentUser = userController.logIn(username, password, age, gender, language);

		this.applicationView.showMainMenu();
	}

	private void quitProgram() {

		this.applicationView.showQuit();
		System.exit(0);
	}


	// GETTERS AND SETTERS

	public ApplicationView getApplicationView() {
		return applicationView;
	}

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

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
