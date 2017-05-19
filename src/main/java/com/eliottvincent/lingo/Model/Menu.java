package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.View.TerminalView;

/**
 * Created by eliottvincent on 19/05/2017.
 */
public class Menu {


	private TerminalView terminalView;

	public Menu() {
		this.terminalView = new TerminalView();
		this.display();
	}

	public void display() {
		this.terminalView.showStartupMenu();
	}
}
