package com.eliottvincent.lingo;

import com.eliottvincent.lingo.Controller.MenuController;
import com.eliottvincent.lingo.Model.Menu;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
		Menu menuModel = new Menu();
		MenuController menuController = new MenuController(menuModel, null);

		menuController.onStartup();

    }

}
