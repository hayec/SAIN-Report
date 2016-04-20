package report;

import java.util.ArrayList;
import java.util.Arrays;

import user.User;

public class CourseBag 
{
	private ArrayList<Course> courses = new ArrayList<Course>();
	public void addCourse(Course course)
	{
		courses.add(course);
	}
	public void addCourse(Course[] newCourses)
	{
		for(int i = 0; i < newCourses.length; i++)
			courses.add(newCourses[i]);
	}
	public Course getCourse(String courseCode)
	{
		Course returnCourse = null;
		for(Course c : courses)
		{
			if(c.getCourseCode().equals(courseCode))
				returnCourse = c;
		}
		return returnCourse;
	}
	public void removeCourse(String courseCode)
	{
		for(Course c : courses)
		{
			if(c.getCourseCode().equals(courseCode))
			{
				courses.remove(c);
			}
		}
	}
	public Course[] getCourses()
	{
		return courses.toArray(new Course[courses.size()]);
	}
	public Course[] getCourses(String courseCode, String courseTitle, String courseDescription, String ammerman, String grant,
			String eastern, String[] prerequisites, String[] corequisites, int credits)
	{
		Course[] returnCourse = null;
		ArrayList<Course> courseResults = new ArrayList<Course>();
		ArrayList<Course> tempCourse = new ArrayList<Course>();
		boolean loop = true;
		boolean subLoop = false;
		if(courseCode != null && courseCode != "")
		{
			for(Course c : courses)
			{
				if(c.getCourseCode().equals(courseCode))
				{
					courseResults.add(c);
				}
			}
		}
		if(courseTitle != null && courseTitle != "")
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			for(Course c : tempCourse)
			{
				if(c.getCourseTitle().equals(courseTitle))
				{
					courseResults.add(c);
				}
			}
		}
		if(courseDescription != null && courseDescription != "")
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			for(Course c : tempCourse)
			{
				if(c.getCourseDescription().equals(courseDescription))
				{
					courseResults.add(c);
				}
			}
		}
		if(ammerman != null && ammerman != "")
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			for(Course c : tempCourse)
			{
				if(Boolean.toString(c.isAmmerman()).equals(ammerman))
				{
					courseResults.add(c);
				}
			}
		}
		if(grant != null && grant != "")
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			for(Course c : tempCourse)
			{
				if(Boolean.toString(c.isGrant()).equals(grant))
				{
					courseResults.add(c);
				}
			}
		}
		if(eastern != null && eastern != "")
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			for(Course c : tempCourse)
			{
				if(Boolean.toString(c.isEastern()).equals(eastern))
				{
					courseResults.add(c);
				}
			}
		}
		if(prerequisites != null && prerequisites.length != 0)
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			loop = true;
			subLoop = false;
			for(Course c : tempCourse)
			{
				for(int i = 0; i < prerequisites.length; i++)
				{
					for(int j = 0; j < c.getPrerequisites().length; j++)
					{
						if(c.getPrerequisites()[j].equals(prerequisites[i]))
							subLoop = true;
					}
					if(subLoop == false)
					{
						loop = false;
					}
					subLoop = false;
				}
				if(loop)
				{
					courseResults.add(c);
				}
			}
		}
		if(corequisites != null && corequisites.length != 0)
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			loop = true;
			subLoop = false;
			for(Course c : tempCourse)
			{
				for(int i = 0; i < corequisites.length; i++)
				{
					for(int j = 0; j < c.getCorequisites().length; j++)
					{
						if(c.getCorequisites()[j].equals(corequisites[i]))
							subLoop = true;
					}
					if(subLoop == false)
					{
						loop = false;
					}
					subLoop = false;
				}
				if(loop)
				{
					courseResults.add(c);
				}
			}
		}
		if(credits >= 0)
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			for(Course c : tempCourse)
			{
				if(c.getCredits() == credits)
				{
					courseResults.add(c);
				}
			}
		}
		returnCourse = courseResults.toArray(new Course[courseResults.size()]);
		return returnCourse;
	}
	public Course[] getCourses(String courseCode, String courseTitle, String courseDescription, String ammerman, String grant,
			String eastern, String[] prerequisites, String[] corequisites, int credits, String successful, String complete, String transferCourse)
	{
		Course[] returnCourse = null;
		ArrayList<Course> courseResults = new ArrayList<Course>();
		ArrayList<Course> tempCourse = new ArrayList<Course>();
		tempCourse = new ArrayList<Course>(Arrays.asList(getCourses(courseCode, courseTitle, courseDescription, ammerman, grant,
			eastern, prerequisites, corequisites, credits)));
		if(successful != null && successful != "")
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			for(Course c : tempCourse)
			{
				if(Boolean.toString(c.isSuccessful()).equals(successful))
				{
					courseResults.add(c);
				}
			}
		}
		if(complete != null && complete != "")
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			for(Course c : tempCourse)
			{
				if(Boolean.toString(c.isComplete()).equals(complete))
				{
					courseResults.add(c);
				}
			}
		}
		if(transferCourse != null && transferCourse != "")
		{
			tempCourse = (ArrayList<Course>) courseResults.clone();
			courseResults.clear();
			for(Course c : tempCourse)
			{
				if(Boolean.toString(c.isTransferCourse()).equals(transferCourse))
				{
					courseResults.add(c);
				}
			}
		}
		returnCourse = courseResults.toArray(new Course[courseResults.size()]);
		return returnCourse;
	}
}
