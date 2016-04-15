package user;

import java.time.LocalDate;

public class Student implements User 
{
	private int id;
	private String firstName;
	private String lastName;
	private double gpa;
	private LocalDate dateEnrolled;
	private LocalDate dateOfBirth;
	private String socialSecNum;
	private String address;
	private String city;
	private String state;
	private int zipCode;
	private String campus;
	private Major major;
	private String password;//Note that this is NOT the plain text, rather it is the 128-bit MD5 hash
	public Student(int id, String firstName, String lastName, double gpa, LocalDate dateEnrolled, LocalDate dateOfBirth, String socialSecNum, String address, String city, int zipCode, String campus, Major major)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gpa = gpa;
		this.dateEnrolled = dateEnrolled;
		this.dateOfBirth = dateOfBirth;
		this.socialSecNum = socialSecNum;
		this.major = major;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.campus = campus;
	}
	@Override
	public boolean isStudent() 
	{
		return true;
	}
	@Override
	public boolean isFacutly() 
	{
		return false;
	}
	@Override
	public boolean isAdministrator() 
	{
		return false;
	}
	@Override
	public Object getUser() 
	{
		return new Student(id, firstName, lastName, gpa, dateEnrolled, dateOfBirth, socialSecNum, address, city, zipCode, campus, major);
	}
	@Override
	public String getName() 
	{
		return firstName + lastName;
	}
	@Override
	public boolean correctPassword(String password) 
	{
		if(this.password.equals(password))
			return true;
		else
			return false;
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
	public double getGpa() 
	{
		return gpa;
	}
	public void setGpa(double gpa) 
	{
		this.gpa = gpa;
	}
	public LocalDate getDateEnrolled() 
	{
		return dateEnrolled;
	}
	public void setDateEnrolled(LocalDate dateEnrolled) 
	{
		this.dateEnrolled = dateEnrolled;
	}
	public LocalDate getDateOfBirth() 
	{
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) 
	{
		this.dateOfBirth = dateOfBirth;
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
	public String getCampus() 
	{
		return campus;
	}
	public void setCampus(String campus) 
	{
		this.campus = campus;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	public String getSocialSecNum() 
	{
		return socialSecNum;
	}
	public void setSocialSecNum(String socialSecNum) 
	{
		this.socialSecNum = socialSecNum;
	}
	public int getBirthYear()
	{
		return dateOfBirth.getYear();
	}
	public int getYearEnrolled()
	{
		return dateEnrolled.getYear();
	}
	public Major getMajor() 
	{
		return major;
	}
	public void setMajor(Major major) 
	{
		this.major = major;
	}
	public String getPassword() 
	{
		return password;
	}
	public boolean setPassword(String oldPassword, String newPassword)//Returns a boolean 
	{
		if(correctPassword(oldPassword))
		{
			password = newPassword;
			return true;
		}
		else
			return false;
	}

}