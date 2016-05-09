package eventHandlers;

import java.util.EventListener;

public interface SQLListener extends EventListener {
	public void setSQL(SQLEventObject ev);
}
