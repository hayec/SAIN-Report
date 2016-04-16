package eventHandlers;

import java.util.EventListener;

public interface LoginListener extends EventListener 
{
	public void login(LoginEventObject ev);
}
