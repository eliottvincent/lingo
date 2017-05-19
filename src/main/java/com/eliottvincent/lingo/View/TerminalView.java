package com.eliottvincent.lingo.View;

import com.eliottvincent.lingo.Model.Menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by eliottvincent on 19/05/2017.
 */
public class TerminalView extends ApplicationView {

	private Menu menuModel;

	public TerminalView() {

	}

	public void showStartupMenu(List<String> menuOptions) {
		System.out.printf("-------------------------\n");
		System.out.printf("*                       *\n");
		System.out.printf("*          LINGO        *\n");
		System.out.printf("*                       *\n");
		System.out.printf("-------------------------\n");

		System.out.printf("What do you want to do?\n");

		Iterator<String> optionsIterator = menuOptions.iterator();
		int i = 1;
		while (optionsIterator.hasNext()) {
			System.out.println(i + ". " +  optionsIterator.next());
			i++;
		}

	}


	public void showQuit() {
		System.out.printf("Bye!");
	}

	public void showCreateUserStepOne() {
		System.out.printf("-------------------------\n");
		System.out.printf("*    Account creation   *\n");
		System.out.printf("-------------------------\n");
		System.out.printf("Please choose a username:\n");
	}

	public void showCreateUserStepTwo() {
		System.out.println("\nPlease choose password:");
	}

	public void showCreateUserStepThree() {
		System.out.println("\nPlease enter your password again:");
	}


	public void showMainMenu() {
		System.out.printf("Welcome to your account");
	}

	public void showLoginUserStepOne() {

	}

	public void showLoginUserStepTwo() {

	}

	public void showLoginError() {
		System.out.println("Invalid username or password.\n");
		System.out.println("Please try again.\n");
	}
}
