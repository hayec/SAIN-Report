package eventHandlers;

import java.util.EventListener;

public interface AddCourseListener extends EventListener 
{
	public void add(AddCourseEventObject ev);
}
