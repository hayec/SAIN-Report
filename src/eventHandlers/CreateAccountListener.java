package eventHandlers;

import java.util.EventListener;

public interface CreateAccountListener extends EventListener 
{
	public void createAccount(CreateAccountEventObject ev);
}
