package eventHandlers;

import java.time.LocalDate;
import java.util.EventObject;

import report.Course;
import user.Major;
import user.Student;

public class EditEventObject extends EventObject
{
	private String errorMessage = new String();
	private boolean valid;
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate dateEnrolled;
	private LocalDate dateOfBirth;
	private String socialSecNum;
	private Major major;
	private String address;
	private String city;
	private String zipCode;
	private String campus;
	private String username;
	private String state;
	private String password;
	private Course[] coursework;
	public EditEventObject (Object source, int id, String firstName, String lastName, LocalDate dateEnrolled, LocalDate dateOfBirth, String socialSecNum, String address, String city, String zipCode, String state, String campus, Major major, String username, String password, Course[] coursework)
	{
		super(source);
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateEnrolled = dateEnrolled;
		this.dateOfBirth = dateOfBirth;
		this.socialSecNum = socialSecNum;
		this.major = major;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.campus = campus;
		this.username = username;
		this.password = password;
		this.coursework = coursework;
	}
	public Course[] getCoursework() {
		return coursework;
	}
	public int getId() {
		return id;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public LocalDate getDateEnrolled() {
		return dateEnrolled;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public String getSocialSecNum() {
		return socialSecNum;
	}
	public Major getMajor() {
		return major;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public String getCampus() {
		return campus;
	}
	public String getUsername() {
		return username;
	}
	public String getState() {
		return state;
	}
	public String getPassword() {
		return password;
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
