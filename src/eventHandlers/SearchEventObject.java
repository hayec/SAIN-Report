package eventHandlers;

import java.time.LocalDate;
import java.util.EventObject;

import user.Major;
import user.Student;

public class SearchEventObject extends EventObject 
{
	private String id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String socSecNum;
	private String major;
	private String gpa;
	private String username;
	private Student[] studentResults;
	private boolean inputValid = false;
	public SearchEventObject(Object source)
	{
		super(source);
	}
	public SearchEventObject(Object source, String id, String firstName, String lastName, String address, String city, String state, String zipCode, String socSecNum, String major, String gpa, String username)
	{
		super(source);
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.socSecNum = socSecNum;
		this.major = major;
		this.gpa = gpa;
		this.username = username;
	}
	public String getUsername()
	{
		return username;
	}
	public String getId() 
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
	public String getZipCode() 
	{
		return zipCode;
	}
	public String getSocSecNum() 
	{
		return socSecNum;
	}
	public String getMajor() 
	{
		return major;
	}
	public String getGpa() 
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