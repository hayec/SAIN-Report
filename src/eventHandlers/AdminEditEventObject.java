package eventHandlers;

import java.util.EventObject;
import user.Student;
import user.User;

public class AdminEditEventObject extends EventObject  {
	private String id;
	private Student student;
	private User user;
	private boolean userValid = false;
	private boolean valid = false;
	private String errorMessage = new String();
	public AdminEditEventObject(Object source, String id) {
		super(source);
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid)	{
		this.valid = valid;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student)	{
		this.student = student;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isUserValid() {
		return userValid;
	}
	public void setUserValid(boolean userValid) {
		this.userValid = userValid;
	}
}
