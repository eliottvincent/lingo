package com.eliottvincent.lingo.Data;

/**
 * <b>ActionType is the enumeration defining the possible types for an Action object.</b>
 * <p>ActionType is mostly used in Action and in ActionController</p>
 *
 * @see com.eliottvincent.lingo.Model.Action
 * @see com.eliottvincent.lingo.Controller.ActionController
 *
 * @author eliottvincent
 */
public enum ActionType {
	ACCOUNT_CREATION,
	LOGIN,
	LOGOUT,
	EXERCICE_START,
	EXERCICE_END,
	LESSON_START,
	LESSON_END
}
