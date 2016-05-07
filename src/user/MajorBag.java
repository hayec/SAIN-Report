package user;

import java.util.ArrayList;

import eventHandlers.ModelChangedEventObject;
import eventHandlers.ModelListener;
import report.Course;

public class MajorBag 
{
	private ArrayList<Major> majors = new ArrayList<Major>();
	private ModelListener listenerModel; 
	public void addMajor(Major major)
	{
		majors.add(major);
		if(listenerModel != null) {
			listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
		}
	}
	public void addMajor(Major[] newMajors)
	{
		for(int i = 0; i < newMajors.length; i++)
			majors.add(newMajors[i]);
		if(listenerModel != null) {
			listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
		}
	}
	public Major getMajor(String major)
	{
		Major returnMajor = null;
		for(Major m : majors)
		{
			if(m.getName().equals(major))
			{
				returnMajor = m;
				break;
			}
		}
		return returnMajor;
	}
	public void removeMajor(String major)
	{
		for(Major m : majors)
		{
			if(m.getName().equals(major))
			{
				majors.remove(m);
			}
		}
		if(listenerModel != null) {
			listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
		}
	}
	public Major[] getMajors()
	{
		return majors.toArray(new Major[majors.size()]);
	}
	public String[] getMajorNames()
	{
		ArrayList<String> majorNames = new ArrayList<String>();
		for(Major m : majors)
			majorNames.add(m.getName());
		return majorNames.toArray(new String[majorNames.size()]);
	}
	public void setModelListener(ModelListener listenerModel)
	{
		this.listenerModel = listenerModel;
	}
}
