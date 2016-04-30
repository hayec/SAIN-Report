package user;

import java.util.ArrayList;

import report.Course;

public class MajorBag 
{
	public static ArrayList<Major> majors = new ArrayList<Major>();
	public static void addMajor(Major major)
	{
		majors.add(major);
	}
	public static void addMajor(Major[] newMajors)
	{
		for(int i = 0; i < newMajors.length; i++)
			majors.add(newMajors[i]);
	}
	
	public static Major getMajor(String major)
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
	public static void removeMajor(String major)
	{
		for(Major m : majors)
		{
			if(m.getName().equals(major))
			{
				majors.remove(m);
			}
		}
	}
	public static Major[] getMajors()
	{
		return majors.toArray(new Major[majors.size()]);
	}
	public static String[] getMajorNames()
	{
		ArrayList<String> majorNames = new ArrayList<String>();
		for(Major m : majors)
			majorNames.add(m.getName());
		return majorNames.toArray(new String[majorNames.size()]);
	}
}
