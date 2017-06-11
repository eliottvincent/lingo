package com.eliottvincent.lingo.Model;

/**
 * Created by eliottvct on 17/05/17.
 */

public class Exercise {


	private Integer id;

	private Integer lessonId;

	private Integer points;


	/**
	 *
	 */
	public Exercise() {
	}

	/**
	 *
	 * @param id
	 * @param lessonId
	 * @param points
	 */
	public Exercise(Integer id, Integer lessonId, Integer points) {
		this.id = id;
		this.lessonId = lessonId;
		this.points = points;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLessonId() {
		return lessonId;
	}

	public void setLessonId(Integer lessonId) {
		this.lessonId = lessonId;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
}
