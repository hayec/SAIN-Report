package eventHandlers;

import java.util.EventObject;

import user.Student;

public class EditEventObject extends EventObject
{
	Student student;
	public EditEventObject(Object source, Student student)
	{
		super(source);
		this.student = student;
	}
	public Student getStudent() 
	{
		return student;
	}
}
