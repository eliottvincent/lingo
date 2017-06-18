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
 * <b>LessonController is the class responsible for the actions performed on an Lesson object.</b>
 *
 * @see Lesson
 *
 * @author eliottvincent
 */
public class LessonController {


	//================================================================================
	// Properties
	//================================================================================

	private DatabaseController databaseController;

	private ExerciseController exerciseController;


	//================================================================================
	// Constructor
	//================================================================================

	/**
	 * The default constructor for a LessonController
	 */
	public LessonController() {

		this.databaseController = DatabaseController.getInstance();

		this.exerciseController = new ExerciseController();
	}


	//================================================================================
	// GETTERS
	//================================================================================

	/**
	 * the getLesson() method is responsible for retrieving a specific lesson.
	 *
	 * @param language the language of the lesson to retrieve.
	 * @param lessonType the type of the lesson to retrieve.
	 * @return the retrieved Lesson object.
	 */
	Lesson getLesson(Language language, LessonType lessonType) {

		// preparing the query
		String lessonsQueries = "SELECT * FROM Lessons " 	+
								"WHERE language LIKE '"		+	language	+	"' "	+
								"AND lesson_type LIKE '" 	+ 	lessonType 	+ 	"'";

		List<Map<String, Object>> lessonsList = this.databaseController.executeSelectQuery(lessonsQueries);

		if (lessonsList.size() == 1) {

			Map<String, Object> lessonMap = lessonsList.get(0);

			Lesson tmpLesson = new Lesson();
			tmpLesson.setId(ConverterHelper.stringToInteger((String) lessonMap.get("id")));
			tmpLesson.setLanguage(ConverterHelper.stringToLanguage((String) lessonMap.get("language")));
			tmpLesson.setType(ConverterHelper.stringToLessonType((String) lessonMap.get("lesson_type")));

			// for exercises, we need to query the database
			List<Exercise> exercises = this.exerciseController.getFakeExercises(tmpLesson.getId());
			tmpLesson.setExercises(exercises);

			return tmpLesson;
		}

		else {

			return null;
		}
	}

	/**
	 * the getLessons() is responsible for retrieving an ensemble of lessons concerning a specific language.
	 * since we don't store any Lesson in database, we just create a Lesson per LessonType.
	 * in the future, the Lesson objects could be stored in the database, with mutliple Lesson per LessonType.
	 *
	 * @param language the language that the lessons should concern.
	 * @return a list of Lesson objects.
	 *
	 * @see LessonType
	 */
	public List<Lesson> getLessons(Language language) {

		List<Lesson> lessons = new ArrayList<Lesson>();

		// for each LessonType...
		for (LessonType lessonType : LessonType.values()) {

			// ... we create a Lesson object
			Lesson tmpLesson = new Lesson();

			tmpLesson.setId(1);
			tmpLesson.setType(lessonType);
			tmpLesson.setLanguage(language);

			// for exercises, we need to query the database
			List<Exercise> exercises = this.exerciseController.getFakeExercises(tmpLesson.getId());
			tmpLesson.setExercises(exercises);

			lessons.add(tmpLesson);
		}

		return lessons;
	}
}
