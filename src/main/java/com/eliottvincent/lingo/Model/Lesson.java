package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Data.LessonType;

import java.util.Collection;
import java.util.List;

/**
 * Created by eliottvct on 17/05/17.
 */
public class Lesson {

	private Integer id;

	private List<Exercise> exercises;

	private Language language;

	private LessonType type;

	public Lesson() {
	}

	public Lesson(Integer id, List<Exercise> exercises, Language language, LessonType type) {

		this.id = id;
		this.exercises = exercises;
		this.language = language;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Collection<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public LessonType getType() {
		return type;
	}

	public void setType(LessonType type) {
		this.type = type;
	}
}
