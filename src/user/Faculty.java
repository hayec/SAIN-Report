package user;

import java.io.Serializable;
import java.time.LocalDate;

public class Faculty implements User, Serializable
{
	String username;
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String address;
	private String city;
	private String state;
	private int zipCode;
	private String socialSecNum;
	private String password;//Note that this is NOT the plain text, rather it is the 128-bit MD5 hash
	@Override
	public String getName()
	{
		return firstName + " " + lastName;
	}
	public Faculty(int id, String firstName, String lastName, LocalDate dateOfBirth, String address, String city,
			String state, int zipCode, String socialSecNum, String username, String password) 
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.username = username;
		this.socialSecNum = socialSecNum;
		this.password = password;
	}
	@Override
	public boolean isStudent() 
	{
		return false;
	}
	@Override
	public boolean isFacutly() 
	{
		return true;
	}
	@Override
	public boolean isAdministrator() 
	{
		return false;
	}
	@Override
	public boolean correctPassword(String password)
	{
		if(this.password.equals(password))
			return true;
		else
			return false;
	}
	@Override
	public Object getUser() 
	{
		return new Faculty(id, firstName, lastName, dateOfBirth, address, city, state, zipCode, socialSecNum, username, password);
	}
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getFirstName() 
	{
		return firstName;
	}
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	public String getLastName() 
	{
		return lastName;
	}
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	public LocalDate getDateOfBirth() 
	{
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) 
	{
		this.dateOfBirth = dateOfBirth;
	}
	public int getBirthYear()
	{
		return dateOfBirth.getYear();
	}
	public String getAddress() 
	{
		return address;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	public String getCity() 
	{
		return city;
	}
	public void setCity(String city) 
	{
		this.city = city;
	}
	public String getState() 
	{
		return state;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	public int getZipCode() 
	{
		return zipCode;
	}
	public void setZipCode(int zipCode)
	{
		this.zipCode = zipCode;
	}
	public String getSocialSecNum() 
	{
		return socialSecNum;
	}
	public void setSocialSecNum(String socialSecNum)
	{
		this.socialSecNum = socialSecNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
}
