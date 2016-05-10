package user;

import report.Course;
import report.CourseAttributes;

/**
 * Factory class for elective courses.
 * @author Christopher
 */
public class ElectiveFactory {
	/**
	 * 
	 * @param elective Name of elective desired
	 * @return A Course object with only CourseAttributes class defined with the corresponding attribute
	 */
	public Course getElective(String elective) {
		if(elective.equals("Physical Education")) {
			return new Course(new CourseAttributes(true, false, false, false, false, false, false, false, false, false, false, false));
		} else if(elective.equals("History")) {
			return new Course(new CourseAttributes(false, true, false, false, false, false, false, false, false, false, false, false));
		}else if(elective.equals("Laboratory Science")) {
			return new Course(new CourseAttributes(false, false, true, false, false, false, false, false, false, false, false, false));	
		} else if(elective.equals("Mathematics")) {
			return new Course(new CourseAttributes(false, false, false, true, false, false, false, false, false, false, false, false));
		} else if(elective.equals("Humanities")) {
			return new Course(new CourseAttributes(false, false, false, false, true, false, false, false, false, false, false, false));
		} else if(elective.equals("Business")) {
			return new Course(new CourseAttributes(false, false, false, false, false, true, false, false, false, false, false, false));
		} else if(elective.equals("English")) {
			return new Course(new CourseAttributes(false, false, false, false, false, false, true, false, false, false, false, false));
		} else if(elective.equals("Communications")) {
			return new Course(new CourseAttributes(false, false, false, false, false, false, false, true, false, false, false, false));
		} else if(elective.equals("American History")) {
			return new Course(new CourseAttributes(false, false, false, false, false, false, false, false, true, false, false, false));
		} else if(elective.equals("Social Science")) {
			return new Course(new CourseAttributes(false, false, false, false, false, false, false, false, false, true, false, false));
		} else if(elective.equals("Language")) {
			return new Course(new CourseAttributes(false, false, false, false, false, false, false, false, false, false, true, false));
		} else if(elective.equals("Philosophy")) {
			return new Course(new CourseAttributes(false, false, false, false, false, false, false, false, false, false, false, true));
		}
		return new Course();
	}
}
