package report;

import java.util.ArrayList;

public class Course 
{
	private String courseCode;
	private String courseTitle;
	private String courseDescription;
	private boolean ammerman;
	private boolean grant;
	private boolean eastern;
	private Course[] prerequisites;
	private Course[] corequisites;
	private int credits;
	private boolean complete;
	private double courseGrade;
	private boolean transferCourse;
	public CourseAttributes CAttributes;
	public String getCourseCode() 
	{
		return courseCode;
	}
	public void setCourseCode(String courseCode) 
	{
		this.courseCode = courseCode;
	}
	public String getCourseTitle() 
	{
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) 
	{
		this.courseTitle = courseTitle;
	}
	public String getCourseDescription() 
	{
		return courseDescription;
	}
	public Course(CourseAttributes CAttributes)
	{
		this.CAttributes = CAttributes;
	}
	public Course(String courseCode, String courseTitle, String courseDescription, boolean ammerman, boolean grant,
			boolean eastern, Course[] prerequisites, Course[] corequisites, int credits, boolean complete,
			double courseGrade, boolean transferCourse) {
		super();
		this.courseCode = courseCode;
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
		this.ammerman = ammerman;
		this.grant = grant;
		this.eastern = eastern;
		this.prerequisites = prerequisites;
		this.corequisites = corequisites;
		this.credits = credits;
		this.complete = complete;
		this.courseGrade = courseGrade;
		this.transferCourse = transferCourse;
		CAttributes = new CourseAttributes();
	}
	public void setCourseDescription(String courseDescription)
	{
		this.courseDescription = courseDescription;
	}
	public boolean isAmmerman() 
	{
		return ammerman;
	}
	public void setAmmerman(boolean ammerman) 
	{
		this.ammerman = ammerman;
	}
	public boolean isGrant() 
	{
		return grant;
	}
	public void setGrant(boolean grant)
	{
		this.grant = grant;
	}
	public boolean isEastern()
	{
		return eastern;
	}
	public void setEastern(boolean eastern)
	{
		this.eastern = eastern;
	}
	public Course[] getPrerequisites() 
	{
		return prerequisites;
	}
	public void setPrerequisites(Course[] prerequisites) 
	{
		this.prerequisites = prerequisites;
	}
	public Course[] getCorequisites() 
	{
		return corequisites;
	}
	public void setCorequisites(Course[] corequisites) 
	{
		this.corequisites = corequisites;
	}
	public int getCredits() 
	{
		return credits;
	}
	public void setCredits(int credits)
	{
		this.credits = credits;
	}
	public boolean isComplete() 
	{
		return complete;
	}
	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}
	public double getCourseGrade()
	{
		return courseGrade;
	}
	public void setCourseGrade(double courseGrade) 
	{
		this.courseGrade = courseGrade;
	}
	public boolean isTransferCourse() 
	{
		return transferCourse;
	}
	public void setTransferCourse(boolean transferCourse) 
	{
		this.transferCourse = transferCourse;
	}
	public boolean isSuccessful() 
	{
		if(courseGrade >= 2.0)
			return true;
		else
			return false;
	}
	public String toString()
	{
		return courseCode + " " + courseTitle + " " + credits + " credits";
	}
	public Course[] prerequisitesSatisfied(Course[] course)
	{
		ArrayList<Course> courseResults = new ArrayList<Course>();
		//tempCourse = (ArrayList<Course>) courseResults.clone();
		boolean loop = true;
		boolean subLoop = false;
		for(int i = 0; i < prerequisites.length; i++)
		{
			for(int j = 0; j < course.length; j++)
			{
				if(course[j].getCourseCode().equals(prerequisites[i]))
					subLoop = true;
			}
			if(subLoop == false)
			{
				loop = false;
			}
			else
			{
				courseResults.add(prerequisites[i]);
			}
			subLoop = false;
		}
		if(loop)
		{
			return null;
		}
		else
		{
			return prerequisites;
		}
	}
}
