package eventHandlers;

import java.util.EventObject;

public class ModelChangedEventObject extends EventObject {
	public ModelChangedEventObject(Object source) {
		super(source);
	}
}
