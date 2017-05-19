package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.View.TerminalView;

import java.util.*;

/**
 * Created by eliottvincent on 19/05/2017.
 */
public class Menu {

	private List<String> options = new ArrayList<String>() {{
		add("Create an account");
		add("Log in");
		add("Log in as anonymous");
		add("Quit");
	}};

	public Menu() {

	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}
}
