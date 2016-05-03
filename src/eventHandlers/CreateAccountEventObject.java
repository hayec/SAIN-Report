package eventHandlers;

import java.time.LocalDate;
import java.util.EventObject;

import user.Major;

public class CreateAccountEventObject extends EventObject 
{
	private boolean validAccount = false;
	private String errorMessage = new String();
	private String id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zipCode;
	private String state;
	private String socSecNum;
	private String campus;
	private LocalDate dateEnrolled;
	private LocalDate dateOfBirth;
	private Major major;
	private boolean admin = false;
	public CreateAccountEventObject(Object source) {
		super(source);
	}
	public CreateAccountEventObject(Object source, String id, String username, String password, String firstName,
			String lastName, String address, String city, String zipCode, String state, String socSecNum, String campus,
			LocalDate dateEnrolled, LocalDate dateOfBirth, Major major) {
		super(source);
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
		this.socSecNum = socSecNum;
		this.campus = campus;
		this.dateEnrolled = dateEnrolled;
		this.dateOfBirth = dateOfBirth;
		this.major = major;
	}
	public CreateAccountEventObject(Object source, String id, String username, String password, String firstName,
			String lastName, String address, String city, String zipCode, String state, String socSecNum,
			LocalDate dateOfBirth, boolean admin) {
		super(source);
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
		this.socSecNum = socSecNum;
		this.dateOfBirth = dateOfBirth;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage += "\n" + errorMessage;
	}
	public void setId(int id)
	{
		this.id = Integer.toString(id);
	}
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public boolean isValidAccount()
	{
		return validAccount;
	}
	public void setValidAccount(boolean validAccount)
	{
		this.validAccount = validAccount;
	}
	public boolean isAdmin()
	{
		return admin;
	}
	public String getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
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
	public String getZipCode() {
		return zipCode;
	}
	public String getState() {
		return state;
	}
	public String getSocSecNum() {
		return socSecNum;
	}
	public String getCampus() {
		return campus;
	}
	public LocalDate getDateEnrolled() {
		return dateEnrolled;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public Major getMajor() {
		return major;
	}
}
