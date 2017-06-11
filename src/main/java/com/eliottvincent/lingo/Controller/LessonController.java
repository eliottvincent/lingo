package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Data.Language;
import com.eliottvincent.lingo.Data.LessonType;
import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Exercise;
import com.eliottvincent.lingo.Model.Lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by eliottvct on 17/05/17.
 */
public class LessonController {


	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController = new DatabaseController();


	//================================================================================
	// Constructor
	//================================================================================

	public LessonController() {

	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 *
	 * @param language
	 * @param lessonType
	 * @return
	 */
	Lesson getLessonReal(Language language, LessonType lessonType) {

		String lessonsQueries = "SELECT * FROM Lessons " +
			"WHERE language LIKE '" + language + "' " +
			"AND lesson_type LIKE '" + lessonType + "'";

		List<Map<String, Object>> lessonsList = databaseController.executeSelectQuery(lessonsQueries);

		if (lessonsList.size() == 1) {

			Map<String, Object> lessonMap = lessonsList.get(0);

			Lesson tmpLesson = new Lesson();
			tmpLesson.setId(ConverterHelper.stringToInteger((String) lessonMap.get("id")));
			tmpLesson.setLanguage(ConverterHelper.stringToLanguage((String) lessonMap.get("language")));
			tmpLesson.setType(ConverterHelper.stringToLessonType((String) lessonMap.get("lesson_type")));

			// for exercises, we need to query the database
			ExerciseController exerciseController = new ExerciseController();
			List<Exercise> exercises = exerciseController.getExercises(tmpLesson.getId());
			tmpLesson.setExercises(exercises);

			return tmpLesson;
		}

		else {

			return null;
		}
	}

	/**
	 *
	 * @param language
	 * @param lessonType
	 * @return
	 */
	public Lesson getLesson(Language language, LessonType lessonType) {

		Lesson tmpLesson = new Lesson();

		tmpLesson.setId(1);
		tmpLesson.setLanguage(language);
		tmpLesson.setType(lessonType);

		// for exercises, we need to query the database
		ExerciseController exerciseController = new ExerciseController();
		List<Exercise> exercises = exerciseController.getExercises(tmpLesson.getId());
		tmpLesson.setExercises(exercises);

		return tmpLesson;
	}

	/**
	 *
	 * @param language
	 * @return
	 */
	public List<Lesson> getLessons(Language language) {

		List<Lesson> lessons = new ArrayList<Lesson>();

		for (LessonType lessonType : LessonType.values()) {

			Lesson tmpLesson = new Lesson();

			tmpLesson.setId(1);
			tmpLesson.setType(lessonType);
			tmpLesson.setLanguage(language);

			// for exercises, we need to query the database
			ExerciseController exerciseController = new ExerciseController();
			List<Exercise> exercises = exerciseController.getExercises(tmpLesson.getId());
			tmpLesson.setExercises(exercises);

			lessons.add(tmpLesson);
		}

		return lessons;
	}

	//================================================================================
	// CREATE
	//================================================================================

	// TODO
}
