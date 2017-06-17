package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Data.LessonType;

import java.util.List;

/**
 * <b>Lesson is the class representing a lesson in Lingo.</b>
 * <p>A lesson is linked to a specific language, and contains a list of exercises.</p>
 *
 * @see Exercise
 *
 * @author eliottvincent
 */
public class Lesson {


	//================================================================================
	// Properties
	//================================================================================

	/**
	 * The id of the lesson.
	 *
	 * @see Lesson#getId()
	 * @see Lesson#setId(Integer)
	 */
	private Integer id;

	/**
	 * The list of exercises of the lesson.
	 *
	 * @see Lesson#getExercises()
	 * @see Lesson#setExercises(List)
	 */
	private List<Exercise> exercises;

	/**
	 * The language of the lesson.
	 *
	 * @see Lesson#getLanguage()
	 * @see Lesson#setLanguage(Language)
	 * @see Language
	 */
	private Language language;

	/**
	 * The type of the lesson.
	 * The possible values for this type are defined into the enumeration LessonType.
	 *
	 * @see Lesson#getType()
	 * @see Lesson#setType(LessonType)
	 * @see LessonType
	 */
	private LessonType type;


	//================================================================================
	// Properties
	//================================================================================

	/**
	 * The default constructor for a lesson.
	 */
	public Lesson() {

	}

	/**
	 * The parameterized constructor for an lesson.
	 *
	 * @param id the id of the new lesson.
	 * @param exercises the list of exercises of the new lesson.
	 * @param language the language of the new lesson.
	 * @param type the type of the new lesson.
	 */
	public Lesson(Integer id, List<Exercise> exercises, Language language, LessonType type) {

		this.id = id;
		this.exercises = exercises;
		this.language = language;
		this.type = type;
	}


	//================================================================================
	// Getters and setters
	//================================================================================

	/**
	 *
	 * @return the id of the lesson.
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
	 * @return the list of exercises of the lesson.
	 */
	public List<Exercise> getExercises() {
		return exercises;
	}

	/**
	 *
	 * @param exercises the list of exercises to set.
	 */
	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	/**
	 *
	 * @return the language of the lesson.
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 *
	 * @param language the language to set.
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 *
	 * @return the type of the lesson.
	 */
	public LessonType getType() {
		return type;
	}

	/**
	 *
	 * @param type the type to set.
	 */
	public void setType(LessonType type) {
		this.type = type;
	}
}
