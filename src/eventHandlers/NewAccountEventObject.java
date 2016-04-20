package eventHandlers;

import java.util.EventObject;

public class NewAccountEventObject extends EventObject 
{
	private String username;
	private String password;
	private boolean isValidPassword;
	private String errorMessage;
	public NewAccountEventObject(Object source, String username, String password)
	{
		super(source);
		this.username = username;
		this.password = password;
	}
	public String getUsername() 
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}
	public boolean isValidPassword() 
	{
		return isValidPassword;
	}
	public void setValidPassword(boolean isValidPassword) 
	{
		this.isValidPassword = isValidPassword;
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
