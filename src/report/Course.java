package report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Course implements Serializable
{
	private String courseCode;
	private String courseTitle;
	private String courseDescription;
	private boolean ammerman;
	private boolean grant;
	private boolean eastern;
	private String[] prerequisites;
	private String[] corequisites;
	private int credits;
	private boolean complete;
	private double courseGrade;
	private boolean transferCourse;
	public CourseAttributes CAttributes;
	
	public Course() {}
	public Course(CourseAttributes CAttributes)
	{
		this.CAttributes = CAttributes;
	}
	public Course(String courseCode, String courseTitle, String courseDescription, boolean ammerman, boolean grant,
			boolean eastern, String[] prerequisites, String[] corequisites, int credits, boolean complete,
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
	public Course(String courseCode, String courseTitle, String courseDescription, boolean ammerman, boolean grant,
			boolean eastern, String[] prerequisites, String[] corequisites, int credits) {
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
		CAttributes = new CourseAttributes();
	}
	public Course clone()
	{
		return new Course(courseCode, courseTitle, courseDescription, ammerman, grant, eastern, prerequisites, corequisites, credits, complete, courseGrade, transferCourse);
	}
	public String getLetterGrade()
	{
		switch((int) courseGrade * 10) {//Multiply courseGrade by 10 to make int then convert to int because switch is incompatible with double
		case 1: courseGrade = 40;
			return "A";
		case 2: courseGrade = 35;
			return "B+";
		case 3: courseGrade = 30;
			return "B";
		case 4: courseGrade = 25;
			return "C+";
		case 5: courseGrade = 20;
			return "C";
		case 6: courseGrade = 15;
			return "D+";
		case 7: courseGrade = 10;
			return "D";
		case 8: courseGrade = 0;
			return "F";
		default:
			return "F";//Necessary to prevent exception, should never occur at runtime
		}
	}
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
	public String[] getPrerequisites() 
	{
		return prerequisites;
	}
	public void setPrerequisites(String[] prerequisites) 
	{
		this.prerequisites = prerequisites;
	}
	public String[] getCorequisites() 
	{
		return corequisites;
	}
	public void setCorequisites(String[] corequisites) 
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
	public Course[] prerequisitesSatisfied(Course[] course, CourseBag courses)
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
				courseResults.add(courses.getCourse(prerequisites[i]));
			}
			subLoop = false;
		}
		if(loop)
		{
			return null;
		}
		else
		{
			return courseResults.toArray(new Course[courseResults.size()]);//Removed prerequisites
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CAttributes == null) ? 0 : CAttributes.hashCode());
		result = prime * result + (ammerman ? 1231 : 1237);
		result = prime * result + (complete ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(corequisites);
		result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
		result = prime * result + ((courseDescription == null) ? 0 : courseDescription.hashCode());
		long temp;
		temp = Double.doubleToLongBits(courseGrade);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((courseTitle == null) ? 0 : courseTitle.hashCode());
		result = prime * result + credits;
		result = prime * result + (eastern ? 1231 : 1237);
		result = prime * result + (grant ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(prerequisites);
		result = prime * result + (transferCourse ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (CAttributes == null) {
			if (other.CAttributes != null)
				return false;
		} else if (!CAttributes.equals(other.CAttributes))
			return false;
		if (ammerman != other.ammerman)
			return false;
		if (complete != other.complete)
			return false;
		if (!Arrays.equals(corequisites, other.corequisites))
			return false;
		if (courseCode == null) {
			if (other.courseCode != null)
				return false;
		} else if (!courseCode.equals(other.courseCode))
			return false;
		if (courseDescription == null) {
			if (other.courseDescription != null)
				return false;
		} else if (!courseDescription.equals(other.courseDescription))
			return false;
		if (Double.doubleToLongBits(courseGrade) != Double.doubleToLongBits(other.courseGrade))
			return false;
		if (courseTitle == null) {
			if (other.courseTitle != null)
				return false;
		} else if (!courseTitle.equals(other.courseTitle))
			return false;
		if (credits != other.credits)
			return false;
		if (eastern != other.eastern)
			return false;
		if (grant != other.grant)
			return false;
		if (!Arrays.equals(prerequisites, other.prerequisites))
			return false;
		if (transferCourse != other.transferCourse)
			return false;
		return true;
	}
}
