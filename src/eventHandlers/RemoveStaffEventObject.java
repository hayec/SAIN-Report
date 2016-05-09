package eventHandlers;

import java.util.EventObject;

public class RemoveStaffEventObject extends EventObject {
	private int id;
	private String errorMessage = new String();
	private boolean valid;
	public RemoveStaffEventObject(Object sender, int id) {
		super(sender);
		this.id = id;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getId() {
		return id;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
