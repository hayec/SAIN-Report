package eventHandlers;

import java.util.EventListener;

public interface SearchListener extends EventListener
{
	public void search(SearchEventObject ev);
}
