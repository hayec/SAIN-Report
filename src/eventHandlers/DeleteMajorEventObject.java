package eventHandlers;

import java.util.EventObject;

import user.Major;

public class DeleteMajorEventObject extends EventObject 
{
	Major target;
	public DeleteMajorEventObject(Object source, Major target) {
		super(source);
		this.target = target;
	}
	public Major getTarget() {
		return target;
	}
}
