package eventHandlers;

import java.util.EventObject;
import user.Student;

public class AdminEditEventObject extends EventObject 
{
	String id;
	Student student;
	boolean valid = false;
	public AdminEditEventObject(Object source, String id)
	{
		super(source);
		this.id = id;
	}
	public String getId() 
	{
		return id;
	}
	public boolean isValid() 
	{
		return valid;
	}
	public void setValid(boolean valid) 
	{
		this.valid = valid;
	}
	public Student getStudent() 
	{
		return student;
	}
	public void setStudent(Student student) 
	{
		this.student = student;
	}
}
