package eventHandlers;

import java.util.EventListener;

public interface EditListener extends EventListener 
{
	public void edit(EditEventObject ev);
}
