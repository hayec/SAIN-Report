package eventHandlers;

import java.util.EventListener;

public interface DeleteMajorListener extends EventListener 
{
	public void delete(DeleteMajorEventObject ev);
}
