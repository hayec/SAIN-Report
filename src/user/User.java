package user;

public interface User 
{
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
}
