package eventHandlers;

import java.util.EventObject;
import user.User;

public class NewAccountEventObject extends EventObject 
{
	private String username;
	private String password;
	private boolean isValidPassword;
	private boolean accountValid;
	private String errorMessage;
	private User user;
	public NewAccountEventObject(Object source, String username, String password)
	{
		super(source);
		this.username = username;
		this.password = password;
	}
	public NewAccountEventObject(Object source, User newUser)
	{
		super(source);
		this.user = newUser;
	}
	public String getUsername() 
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}
	public User getUser()
	{
		return user;
	}
	public boolean isValidPassword() 
	{
		return isValidPassword;
	}
	public boolean isValidAccount()
	{
		return accountValid;
	}
	public void setValidPassword(boolean isValidPassword) 
	{
		this.isValidPassword = isValidPassword;
	}
	public void setValidAccount(boolean accountValid)
	{
		this.accountValid = accountValid;
	}
	public void setErrorMessage(String message)
	{
		errorMessage = message;
	}
	public String getErrorMessage()
	{
		return errorMessage;
	}
}
