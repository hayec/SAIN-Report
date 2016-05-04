package eventHandlers;

import java.util.EventListener;

public interface ReportListener extends EventListener 
{
	public void report(ReportEventObject ev);
}
