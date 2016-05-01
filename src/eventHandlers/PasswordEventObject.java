package eventHandlers;

import java.util.EventObject;

public class PasswordEventObject extends EventObject 
{
	private String oldPassword;
	private String newPassword;
	private String newPasswordConf;
	private boolean oldPassSuccessful;
	private boolean passMatch;
	private boolean successful;
	private String errorMessage;
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
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage += "\n" + errorMessage;
	}
}
