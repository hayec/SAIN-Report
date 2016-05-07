package eventHandlers;

import java.util.EventListener;

public interface StaffEditListener extends EventListener
{
	public void editStaff(StaffEditEventObject ev);
}
