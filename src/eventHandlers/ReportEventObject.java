package eventHandlers;

import java.util.EventObject;

import user.Student;
import user.User;

public class ReportEventObject extends EventObject {
	private User user;
	private Student student;
	public ReportEventObject(Object source, User user, Student student)	{
		super(source);
		this.user = user;
		this.student = student;
	}
	public User getUser() {
		return user;
	}
	public Student getStudent() {
		return student;
	}
}
