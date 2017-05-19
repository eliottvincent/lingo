package com.eliottvincent.lingo.View;

import com.eliottvincent.lingo.Model.Menu;

import java.util.Iterator;

/**
 * Created by eliottvincent on 19/05/2017.
 */
public class TerminalView extends ApplicationView {

	private Menu menuModel;

	public TerminalView(Menu menuModel) {

		this.menuModel = menuModel;
	}

	public void showStartupMenu() {
		System.out.printf("-------------------------\n");
		System.out.printf("*                       *\n");
		System.out.printf("*          LINGO        *\n");
		System.out.printf("*                       *\n");
		System.out.printf("-------------------------\n");

		this.displayOptions();
	}

	private void displayOptions() {
		System.out.printf("What do you want to do?\n");

		Iterator<String> optionsIterator = this.menuModel.getOptions().iterator();
		int i = 1;
		while (optionsIterator.hasNext()) {
			System.out.println(i + ". " +  optionsIterator.next());
			i++;
		}
	}

	public void showQuit() {
		System.out.printf("Bye!");
	}
}
