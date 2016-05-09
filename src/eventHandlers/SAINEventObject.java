package eventHandlers;

import java.util.EventObject;

import user.Major;
import user.Student;
import user.User;

public class SAINEventObject extends EventObject {
	private Major major;
	private User user;
	private Student student;
	private String errorMessage = new String();
	private boolean valid = false;
	public SAINEventObject(Object source, Major major, User user, Student student)	{
		super(source);
		this.user = user;
		this.major = major;
		this.student = student;
	}
	public Major getMajor() {
		return major;
	}
	public User getUser() {
		return user;
	}
	public Student getStudent() {
		return student;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
}
