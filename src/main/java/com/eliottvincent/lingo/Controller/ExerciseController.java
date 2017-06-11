package com.eliottvincent.lingo.Controller;

import com.eliottvincent.lingo.Helper.ConverterHelper;
import com.eliottvincent.lingo.Model.Exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by eliottvct on 17/05/17.
 */
class ExerciseController {

	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController = DatabaseController.getInstance();


	//================================================================================
	// Constructor
	//================================================================================

	/**
	 *
	 */
	ExerciseController() {


	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 *
	 * @param exerciseId
	 * @return
	 */
	Exercise getExercise(Integer exerciseId) {

		return new Exercise();
	}


	List<Exercise> getExercisesReal(Integer lessonId) {

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
	 *
	 * @param lessonId
	 * @return
	 */
	List<Exercise> getExercises(Integer lessonId) {

		List<Exercise> exercises = new ArrayList<>();

		for (int i = 0; i < 5; i ++) {

			Exercise tmpExercise = new Exercise();
			tmpExercise.setId(i);
			tmpExercise.setLessonId(lessonId);
			tmpExercise.setPoints(i * 5);

			exercises.add(tmpExercise);
		}

		return exercises;
	}
}
