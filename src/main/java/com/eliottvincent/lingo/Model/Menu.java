package com.eliottvincent.lingo.Model;

import java.util.*;

/**
 * Created by eliottvincent on 19/05/2017.
 */
public class Menu {

	// double brackets initialization
	private List<String> menuOptions = new ArrayList<String>() {{
		add("Create an account");
		add("Log in");
		add("Log in as anonymous");
		add("Quit");
	}};

	private List<String> languageOptions = new ArrayList<String>() {{
		add("French");
		add("English");
		add("German");
		add("Spanish");
	}};

	public Menu() {

	}

	public List<String> getMenuOptions() {
		return menuOptions;
	}

	public void setMenuOptions(List<String> menuOptions) {
		this.menuOptions = menuOptions;
	}

	public List<String> getLanguageOptions() {
		return languageOptions;
	}

	public void setLanguageOptions(List<String> languageOptions) {
		this.languageOptions = languageOptions;
	}
}
