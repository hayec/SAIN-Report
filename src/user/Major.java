package user;

import report.Course;

public class Major 
{
	String name;
	int reqCredits;
	double minGPA;
	int numOfCreditsReq;
	final int creditsAtSCCCReq = 36;
	final int maxSemCredits = 19;
	public static Major getMajor(String major)
	{
		return null;//Placeholder
	}
	//public Course[] coursesNeeded(Student student)
	{
		
	}
	
	//public int numOfSemestersNeeded(Student student)
	{
		
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String toString()
	{
		return name;
	}
}
