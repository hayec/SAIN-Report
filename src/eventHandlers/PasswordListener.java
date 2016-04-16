package eventHandlers;

import java.util.EventListener;

public interface PasswordListener extends EventListener 
{
	public void changePassword(PasswordEventObject ev);
}
