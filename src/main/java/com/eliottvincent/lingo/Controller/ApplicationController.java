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

		this.applicationView.showStartupMenu(this.menuModel.getOptions());
		this.waitUserInputMenu();
	}

	private void waitUserInputMenu() {
		Scanner sc = new Scanner(System.in);

		while (!sc.hasNextInt()) {
			System.out.println("You have to choose a number between 1 and " + this.menuModel.getOptions().size() + ".");
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

	private void onLogin(boolean isAnonymous ) {
		if (isAnonymous) {
			this.applicationView.showMainMenu();
		}
		else {
			this.applicationView.showLoginUserStepOne();
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

	private void waitUserInputLoginUserStepTwo(String userName) {

		Scanner sc = new Scanner(System.in);

		while(!sc.hasNextLine()) {
			sc.next();
		}
		String password = sc.nextLine();

		UserController userController = new UserController();

		User tmpUser = userController.logIn(userName, password);

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

			UserController userController = new UserController();
			userController.saveUser(username, password);
			this.currentUser = userController.logIn(username, password);

			this.applicationView.showMainMenu();
		}
		else {
			System.out.printf("invalid");

			// TODO : display error message
		}

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
	 * @return
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
