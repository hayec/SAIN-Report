package eventHandlers;

import java.util.EventObject;
import report.Course;

public class DeleteCourseEventObject extends EventObject {
	private Course target;
	private Course[] courses;
	public DeleteCourseEventObject(Object source, Course target) {
		super(source);
		this.target = target;
	}
	public Course getTarget() {
		return target;
	}
	public void setCourses(Course[] courses) {
		this.courses = courses;
	}
	public Course[] getCourses() {
		return courses;
	}
}
