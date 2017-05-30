package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Data.Gender;
import com.eliottvincent.lingo.Data.Language;

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

	private List<Language> languageOptions = Arrays.asList(Language.values());

	private List<Gender> genderOptions = Arrays.asList(Gender.values());

	public Menu() {

	}

	public List<String> getMenuOptions() {
		return menuOptions;
	}

	public void setMenuOptions(List<String> menuOptions) {
		this.menuOptions = menuOptions;
	}

	public List<Language> getLanguageOptions() {
		return languageOptions;
	}

	public void setLanguageOptions(List<Language> languageOptions) {
		this.languageOptions = languageOptions;
	}

	public List<Gender> getGenderOptions() {
		return genderOptions;
	}
}
