package eventHandlers;

import java.util.EventObject;

import user.Student;

public class EditEventObject extends EventObject
{
	private Student student;
	private String errorMessage = new String();
	private boolean valid;
	public EditEventObject(Object source, Student student)
	{
		super(source);
		this.student = student;
	}
	public Student getStudent() 
	{
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
}
