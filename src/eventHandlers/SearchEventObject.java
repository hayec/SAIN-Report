package eventHandlers;

import java.util.EventObject;
import user.Student;
import user.User;

public class SearchEventObject extends EventObject {
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
	private User[] userResults;
	private boolean inputValid = false;
	private String errorMessage = new String();
	public SearchEventObject(Object source)	{
		super(source);
	}
	public SearchEventObject(Object source, String id, String firstName, String lastName,
			String address, String city, String state, String zipCode, String socSecNum,
			String major, String gpa, String username) {
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
	public SearchEventObject(Object source, String id, String firstName, String lastName, 
			String address, String city, String state, String zipCode, String socSecNum, String username) {
		super(source);
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.socSecNum = socSecNum;
		this.username = username;
	}
	public String getUsername()	{
		return username;
	}
	public String getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public String getSocSecNum() {
		return socSecNum;
	}
	public String getMajor() {
		return major;
	}
	public String getGpa() {
		return gpa;
	}
	public boolean isInputValid() {
		return inputValid;
	}
	public void setInputValid(boolean inputValid) {
		this.inputValid = inputValid;
	}
	public Student[] getStudentResults() {
		return studentResults;
	}
	public User[] getUserResults() {
		return userResults;
	}
	public void setUserResults(User[] userResults) {
		this.userResults = userResults;
	}
	public void setStudentResults(Student[] studentResults) {
		this.studentResults = studentResults;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public void setSocSecNum(String socSecNum) {
		this.socSecNum = socSecNum;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public void setGpa(String gpa) {
		this.gpa = gpa;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}