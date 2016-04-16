package eventHandlers;

import java.util.EventListener;

public interface LogoutListener extends EventListener
{
	public void logout(LogoutEventObject ev);
}
