package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>ExerciseController is the class responsible for the actions performed on an Exercise object.</b>
 * <p>Since the exercises are faked (n oreal content), this class isn't really finished.</p>
 *
 * @see Exercise
 *
 * @author eliottvincent
 */
class ExerciseController {

	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController;


	//================================================================================
	// Constructor
	//================================================================================

	/**
	 * The default constructor for an ExerciseController
	 */
	ExerciseController() {

		databaseController = DatabaseController.getInstance();
	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 * getExercises() is the method responsible for getting all exercises for a specific lesson.
	 * This method isn't used at all, since there is no real exercises in the database.
	 *
	 * @param lessonId the id of the targeted lesson.
	 * @return a List of Exercise objects.
	 */
	List<Exercise> getExercises(Integer lessonId) {

		// preparing the query.
		String exerciseQuery =	"SELECT * FROM Exercises "	+
								"WHERE lesson_id LIKE '"	+	lessonId	+	"'";

		List<Map<String, Object>> exercisesList = databaseController.executeSelectQuery(exerciseQuery);
		List<Exercise> exercises = new ArrayList<Exercise>();

		for (Map<String, Object> exerciceMap : exercisesList) {

			Exercise tmpExercise = new Exercise();
			tmpExercise.setId(ConverterHelper.stringToInteger((String) exerciceMap.get("id")));
			tmpExercise.setLessonId(ConverterHelper.stringToInteger((String) exerciceMap.get("lesson_id")));
			tmpExercise.setPoints(ConverterHelper.stringToInteger((String) exerciceMap.get("points")));

			exercises.add(tmpExercise);
		}

		return exercises;
	}

	/**
	 * getFakeExercises() is the method responsible for getting fake exercises.
	 *
	 * @param lessonId the id of the targeted lesson.
	 * @return a List of Exercise objects.
	 */
	List<Exercise> getFakeExercises(Integer lessonId) {

		List<Exercise> exercises = new ArrayList<>();

		for (int i = 1; i < 6; i ++) {

			Exercise tmpExercise = new Exercise();
			tmpExercise.setId(i);
			tmpExercise.setLessonId(lessonId);
			tmpExercise.setPoints(i * 5);

			exercises.add(tmpExercise);
		}

		return exercises;
	}
}
