package eventHandlers;

import java.time.LocalDate;
import java.util.EventObject;

public class StaffEditEventObject extends EventObject 
{
	private String username;
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String socialSecNum;
	private String password;
	private String errorMessage;
	private boolean valid;
	private boolean admin;
	public StaffEditEventObject(Object source, String username, int id, String firstName, String lastName,
			LocalDate dateOfBirth, String address, String city, String state, String zipCode, String socialSecNum,
			String password, boolean admin) {
		super(source);
		this.username = username;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.socialSecNum = socialSecNum;
		this.password = password;
		this.admin = admin;
	}
	public boolean isAdmin() {
		return admin;
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
	public String getUsername() {
		return username;
	}
	public int getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
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
	public String getSocialSecNum() {
		return socialSecNum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
