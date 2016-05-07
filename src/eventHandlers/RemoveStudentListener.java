package eventHandlers;

import java.util.EventListener;

public interface RemoveStudentListener extends EventListener {
	public void removeStudent(RemoveStudentEventObject ev);
}
