package eventHandlers;

import java.util.EventObject;

import user.Major;
import user.Student;

public class SearchEventObject extends EventObject 
{
	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private int zipCode;
	private String socSecNum;
	private int birthYear;
	private int yearEnrolled;
	private String major;
	private double gpa;
	private Student[] studentResults;
	private boolean inputValid = false;
	public SearchEventObject(Object source)
	{
		super(source);
	}
	public SearchEventObject(Object source, int id, String firstName, String lastName, String address, String city, String state, int zipCode, String socSecNum, int birthYear, int yearEnrolled, String major, double gpa)
	{
		super(source);
	}
	public int getId() 
	{
		return id;
	}
	public String getFirstName() 
	{
		return firstName;
	}
	public String getLastName() 
	{
		return lastName;
	}
	public String getAddress() 
	{
		return address;
	}
	public String getCity() 
	{
		return city;
	}
	public String getState() 
	{
		return state;
	}
	public int getZipCode() 
	{
		return zipCode;
	}
	public String getSocSecNum() 
	{
		return socSecNum;
	}
	public int getBirthYear() 
	{
		return birthYear;
	}
	public int getYearEnrolled() 
	{
		return yearEnrolled;
	}
	public String getMajor() 
	{
		return major;
	}
	public double getGpa() 
	{
		return gpa;
	}
	public boolean isInputValid() 
	{
		return inputValid;
	}
	public void setInputValid(boolean inputValid) 
	{
		this.inputValid = inputValid;
	}
	public Student[] getStudentResults() 
	{
		return studentResults;
	}
	public void setStudentResults(Student[] studentResults) {
		this.studentResults = studentResults;
	}
}

/*String firstName, String lastName, String socSecNum, String address, String city, int zipCode, String state, int birthYear, int gpa, Major major, int yearEnrolled
*/