package eventHandlers;

import java.util.EventListener;

public interface DeleteCourseListener extends EventListener
{
	public void delete(DeleteCourseEventObject ev);
}
