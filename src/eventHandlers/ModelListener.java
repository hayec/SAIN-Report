package eventHandlers;

import java.util.EventListener;

public interface ModelListener extends EventListener 
{
	public void modelChanged(ModelChangedEventObject ev);
}
