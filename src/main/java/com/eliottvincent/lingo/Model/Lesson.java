package com.eliottvincent.lingo.Model;

import com.eliottvincent.lingo.Language;
import com.eliottvincent.lingo.LessonType;

import java.util.Collection;

/**
 * Created by eliottvct on 17/05/17.
 */
public abstract class Lesson {

	private int id;
	private Collection<Exercise> exercises;
	private Language language;
	private LessonType type;

	public Lesson(Collection<Exercise> exercises, Language language, LessonType type) {
		this.exercises = exercises;
		this.language = language;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Collection<Exercise> exercises) {
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
