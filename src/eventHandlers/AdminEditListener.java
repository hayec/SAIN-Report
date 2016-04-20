package eventHandlers;

import java.util.EventListener;

public interface AdminEditListener extends EventListener 
{
	public void verify(AdminEditEventObject ev);
}
