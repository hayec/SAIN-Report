package eventHandlers;

import java.util.EventObject;
import user.Administrator;

public class AdminBackEventObject extends EventObject
{
	private Administrator admin;
	public AdminBackEventObject(Object source, Administrator admin) {
		super(source);
		this.admin = admin;
	}
	public Administrator getUser()
	{
		return admin;
	}
}
