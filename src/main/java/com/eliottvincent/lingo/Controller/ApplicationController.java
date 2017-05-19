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

	public ApplicationController() {

		this.menuModel = new Menu();

		this.applicationView = new TerminalView(this.menuModel);

		this.onStartup();
	}

	private void onStartup() {

		this.applicationView.showStartupMenu();
		this.waitUserInputTerminal();
	}

	private void waitUserInputTerminal() {
		Scanner sc = new Scanner(System.in);


		while (!sc.hasNextInt()) {
			System.out.println("You have to choose a number between 1 and " + this.menuModel.getOptions().size() + ".");
			sc.next();
		}
		int i = sc.nextInt();
		switch(i) {
			case 1:
				UserController userController = new UserController();
				userController.createUser();
				break;
			case 2:
				UserController userController1 = new UserController();
				userController1.logIn(false);
				break;
			case 3:
				UserController userController2 = new UserController();
				userController2.logIn(true);
				break;
			case 4:
				this.quitProgram();
				break;
			default:
				System.out.println("You have to choose a number between 1 and 4.");
				this.waitUserInputTerminal();
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
}
