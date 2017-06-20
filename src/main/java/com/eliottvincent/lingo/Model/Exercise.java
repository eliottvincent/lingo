package com.eliottvincent.lingo.Model;

/**
 * <b>Exercise is the class representing an exercise in Lingo.</b>
 * <p>An exercise is part of a lesson.</p>
 * <p>For the moment, the exercises in Lingo are simulated (fake ones), so this class isn't fully used.</p>
 *
 * @see Lesson
 *
 * @author eliottvincent
 */
public class Exercise {


	//================================================================================
	// Properties
	//================================================================================

	/**
	 * The id of the exercise.
	 *
	 * @see Exercise#getId()
	 * @see Exercise#setId(Integer)
	 */
	private Integer id;

	/**
	 * The id of the lesson linked to exercise.
	 *
	 * @see Exercise#getLessonId()
	 * @see Exercise#setLessonId(Integer)
	 * @see Lesson
	 */
	private Integer lessonId;

	/**
	 * The number of points to win by succeeding to the exercise.
	 *
	 * @see Exercise#getPoints()
	 * @see Exercise#setPoints(Integer)
	 */
	private Integer points;


	//================================================================================
	// Constructors
	//================================================================================

	/**
	 * The default constructor for an exercise.
	 */
	public Exercise() {

	}

	/**
	 * The parameterized constructor for an exercise.
	 *
	 * @param id the id of the new exercise.
	 * @param lessonId the id of the lesson linked to the new exercise.
	 * @param points the number of points to win by succeeding to the new exercise.
	 */
	public Exercise(Integer id, Integer lessonId, Integer points) {

		this.id = id;
		this.lessonId = lessonId;
		this.points = points;
	}


	//================================================================================
	// Getters and setters
	//================================================================================

	/**
	 *
	 * @return the id of the exercise.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 *
	 * @param id the id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 *
	 * @return the lessonId of the exercise.
	 */
	public Integer getLessonId() {
		return lessonId;
	}

	/**
	 *
	 * @param lessonId the lessonId to set.
	 */
	public void setLessonId(Integer lessonId) {
		this.lessonId = lessonId;
	}

	/**
	 *
	 * @return the points of the exercise.
	 */
	public Integer getPoints() {
		return points;
	}

	/**
	 *
	 * @param points the points to set.
	 */
	public void setPoints(Integer points) {
		this.points = points;
	}
}
