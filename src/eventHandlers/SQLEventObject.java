package eventHandlers;

import java.util.EventObject;

public class SQLEventObject extends EventObject {
	private boolean sql = false;
	public SQLEventObject(Object source, boolean sql) {
		super(source);
		this.sql = sql;
	}
	public boolean isSQL() {
		return sql;
	}
}
