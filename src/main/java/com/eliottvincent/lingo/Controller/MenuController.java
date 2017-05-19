package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Model.Menu;
import com.eliottvincent.lingo.View.TerminalView;

/**
 * Created by eliottvincent on 19/05/2017.
 */
public class MenuController {

	private TerminalView terminalView;
	private Menu menuModel;

	public MenuController(Menu menuModel, TerminalView terminalView) {
		this.terminalView = terminalView;
		this.menuModel = menuModel;
	}

	public void onStartup() {

	}

	public void waitUserInputTerminal() {

	}



	/*
	GETTERS AND SETTER
	 */

	/**
	 *
	 * @return
	 */
	public TerminalView getTerminalView() {
		return terminalView;
	}

	/**
	 *
	 * @param terminalView
	 */
	public void setTerminalView(TerminalView terminalView) {
		this.terminalView = terminalView;
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
