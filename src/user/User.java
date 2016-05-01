package user;

import java.io.Serializable;
import java.time.LocalDate;

public interface User extends Serializable
{
	public String getUsername();
	public String getName();	
	public boolean isStudent();
	public boolean isFacutly();
	public boolean isAdministrator();
	public boolean correctPassword(String password);
	public int getId();
	public String getFirstName();
	public String getLastName();
	public int getZipCode();
	public String getSocialSecNum();
	public String getAddress();
	public String getCity();
	public String getState();
	public Object getUser();
	public int getBirthYear();
	public void setUsername(String username);
	public void setId(int id);
	public void setFirstName(String firstName);
	public void setLastName(String lastName);
	public void setZipCode(int zipCode);
	public void setSocialSecNum(String socSecNum);
	public void setAddress(String address);
	public void setCity(String city);
	public void setState(String state);
	public void setDateOfBirth(LocalDate birthDate);
	public void setPassword(String password);
}
