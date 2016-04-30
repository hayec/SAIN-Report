package eventHandlers;

import java.util.EventListener;

public interface AddMajorListener extends EventListener 
{
	public void add(AddMajorEventObject ev);
}
