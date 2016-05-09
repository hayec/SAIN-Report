package eventHandlers;

import java.util.EventObject;

import user.Major;

public class DeleteMajorEventObject extends EventObject {
	private Major target;
	private Major[] majors;
	public DeleteMajorEventObject(Object source, Major target) {
		super(source);
		this.target = target;
	}
	public Major getTarget() {
		return target;
	}
	public void setMajors(Major[] majors) {
		this.majors = majors;
	}
	public Major[] getMajors() {
		return majors;
	}
}
