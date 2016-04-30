package eventHandlers;

import java.util.EventObject;
import report.Course;

public class DeleteCourseEventObject extends EventObject 
{
	Course target;
	public DeleteCourseEventObject(Object source, Course target) {
		super(source);
		this.target = target;
	}
	public Course getTarget() {
		return target;
	}
}
