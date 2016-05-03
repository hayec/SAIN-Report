package eventHandlers;

import java.util.EventListener;
import user.Major;
import user.Student;
import user.User;

public interface SAINListener extends EventListener 
{
	public void getReport(SAINEventObject ev);
}
