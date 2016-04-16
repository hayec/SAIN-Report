package eventHandlers;

import java.util.EventObject;

public class PasswordEventObject extends EventObject 
{
	private String oldPassword;
	private String newPassword;
	private String newPasswordConf;
	private boolean oldPassSuccessful;
	private boolean passMatch;
	public PasswordEventObject(Object source)
	{
		super(source);
	}
	public PasswordEventObject(Object source, String oldPassword, String newPassword, String newPasswordConf)
	{
		super(source);
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.newPasswordConf = newPasswordConf;
	}
	public boolean isOldPassSuccessful()
	{
		return oldPassSuccessful;
	}
	public void setOldPassSuccessful(boolean oldPassSuccessful)
	{
		this.oldPassSuccessful = oldPassSuccessful;
	}
	public boolean isPassMatch() 
	{
		return passMatch;
	}
	public void setPassMatch(boolean passMatch)
	{
		this.passMatch = passMatch;
	}
	public String getOldPassword() 
	{
		return oldPassword;
	}
	public String getNewPassword() 
	{
		return newPassword;
	}
	public String getNewPasswordConf() 
	{
		return newPasswordConf;
	}
}
