package com.eliottvincent.lingo.Model;

/**
 * Created by eliottvct on 17/05/17.
 */
public class Session {


	private String action;


	public Session(String action) {

		this.action = action;
	}

	/**
	 *
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 *
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}
}
