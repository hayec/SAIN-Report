package eventHandlers;

import java.util.EventObject;

public class LoginEventObject extends EventObject {
	private String username;
	private String password;
	private boolean credentialsValid = false;
	public LoginEventObject(Object source) {
		super(source);
	}
	public LoginEventObject(Object source, String username, String password) {
		super(source);
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public boolean isCredentialsValid() {
		return credentialsValid;
	}
	public void setCredentialsValid(boolean credentialsValid) {
		this.credentialsValid = credentialsValid;
	}
}
