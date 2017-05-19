package com.eliottvincent.lingo.View;

import com.eliottvincent.lingo.Controller.MenuController;

/**
 * Created by eliottvincent on 19/05/2017.
 */
public class TerminalView {

	public TerminalView() {

	}

	public void showStartupMenu() {
		System.out.printf("-----------------\n");
		System.out.printf("*               *\n");
		System.out.printf("*     LINGO     *\n");
		System.out.printf("*               *\n");
		System.out.printf("-----------------\n");

		this.displayOptions();
	}

	private void displayOptions() {
		System.out.printf("What do you want to do?\n");


	}
}
