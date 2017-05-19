package com.eliottvincent.lingo.View;

import java.util.List;

/**
 * Created by eliottvincent on 19/05/2017.
 */
public abstract class ApplicationView {


	public abstract void showStartupMenu(List<String> menuOptions);

	public abstract void showQuit();

	public abstract void showCreateUserStepOne();

	public abstract void showCreateUserStepTwo();

	public abstract void showCreateUserStepThree();

	public abstract void showMainMenu();

	public abstract void showLoginUserStepOne();

	public abstract void showLoginUserStepTwo();

	public abstract void showLoginError();

	public abstract void displayErrorMessage(String s);
}
