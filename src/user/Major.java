package user;

import java.util.ArrayList;
import java.util.Arrays;

import report.Course;
import report.CourseAttributes;

public class Major 
{
	String name;
	int reqCredits;
	double minGPA;
	int numOfCreditsReq;
	final int creditsAtSCCCReq = 36;
	final int maxSemCredits = 19;
	int physEdReq;
	int hisReq;
	int labSciReq;
	int mathReq;
	int humReq;
	int busReq;
	int engReq;
	int comReq;
	int amerHisReq;
	int socSciReq;
	int langReq;
	int phlReq;
	int creditsReq;
	Course[] reqCourses;
	public int getCreditsReq(Student student)
	{
		ArrayList<Course> studentCourses = new ArrayList<Course>(Arrays.asList(student.getCourseWork()));
		int creditTemp = 0;
		for(Course c : studentCourses)
			creditTemp += c.getCredits();
		creditTemp = numOfCreditsReq - creditTemp;
		if(creditTemp >= 0)
			return creditTemp;
		else
			return 0;
	}
	public Course[] getMajorCoursesDone(Student student)
	{
		ArrayList<Course> studentCourses = new ArrayList<Course>(Arrays.asList(student.getCourseWork()));
		ArrayList<Course> returnCourse = new ArrayList<Course>();
		for(int i = 0; i < studentCourses.size(); i++)
		{
			for(int j = 0; j < reqCourses.length; j++)
			{
				if(reqCourses[j].equals(studentCourses.get(i)))//Make equals method
					returnCourse.add(studentCourses.get(i));
			}
		}
		return returnCourse.toArray(new Course[returnCourse.size()]);
	}
	public Course[] getMajorCoursesReq(ArrayList<Course> studentCourses)
	{
		for(int i = 0; i < studentCourses.size(); i++)
		{
			for(int j = 0; j < reqCourses.length; j++)
			{
				if(reqCourses[j].equals(studentCourses.get(i)))//Make equals method
					studentCourses.remove(i);
			}
		}
		return studentCourses.toArray(new Course[studentCourses.size()]);
	}
	public Course[] getMajorCoursesReq(Student student)
	{
		ArrayList<Course> studentCourses = new ArrayList<Course>(Arrays.asList(student.getCourseWork()));
		return getMajorCoursesReq(studentCourses);
	}
	public Course[] getGenEdsReq(Student student)
	{
		ArrayList<Course> studentCourses = new ArrayList<Course>(Arrays.asList(student.getCourseWork()));;
		ArrayList<Course> results = new ArrayList<Course>();
		for(int i = 0; i < studentCourses.size(); i++)
		{
			for(int j = 0; j < reqCourses.length; j++)
			{
				if(reqCourses[j].equals(studentCourses.get(i)))//Make equals method
					studentCourses.remove(i);
			}
		}
		int[] coursesSatisfied = getReqsNeeded(studentCourses);//Instantiate after major requirements are removed
		for(int i = 0; i < coursesSatisfied[0]; i++)
			results.add(new Course(new CourseAttributes(true, false, false, false, false, false, false, false, false, false, false, false)));
		for(int i = 0; i < coursesSatisfied[1]; i++)
			results.add(new Course(new CourseAttributes(false, true, false, false, false, false, false, false, false, false, false, false)));
		for(int i = 0; i < coursesSatisfied[2]; i++)
			results.add(new Course(new CourseAttributes(false, false, true, false, false, false, false, false, false, false, false, false)));
		for(int i = 0; i < coursesSatisfied[3]; i++)
			results.add(new Course(new CourseAttributes(false, false, false, true, false, false, false, false, false, false, false, false)));
		for(int i = 0; i < coursesSatisfied[4]; i++)
			results.add(new Course(new CourseAttributes(false, false, false, false, true, false, false, false, false, false, false, false)));
		for(int i = 0; i < coursesSatisfied[5]; i++)
			results.add(new Course(new CourseAttributes(false, false, false, false, false, true, false, false, false, false, false, false)));
		for(int i = 0; i < coursesSatisfied[6]; i++)
			results.add(new Course(new CourseAttributes(false, false, false, false, false, false, true, false, false, false, false, false)));
		for(int i = 0; i < coursesSatisfied[7]; i++)
			results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, true, false, false, false, false)));
		for(int i = 0; i < coursesSatisfied[8]; i++)
			results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, false, true, false, false, false)));
		for(int i = 0; i < coursesSatisfied[9]; i++)
			results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, false, false, true, false, false)));
		for(int i = 0; i < coursesSatisfied[10]; i++)
			results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, false, false, false, true, false)));
		for(int i = 0; i < coursesSatisfied[11]; i++)
			results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, false, false, false, false, true)));
		return results.toArray(new Course[studentCourses.size()]);
	}
	public Course[] getCoursesReq(Student student)
	{
		Course[] majReqs = getMajorCoursesReq(new ArrayList<Course>(Arrays.asList(student.getCourseWork())));
		Course[] genReqs = getMajorCoursesReq(new ArrayList<Course>(Arrays.asList(student.getCourseWork())));
		Course[] results = new Course[majReqs.length + genReqs.length];
		for(int i = 0; i < majReqs.length; i++)
			results[i] = majReqs[i];
		for(int i = majReqs.length; i < majReqs.length + genReqs.length; i++)			
			results[i] = genReqs[i - majReqs.length];
		return results;
	}
	public Course[] getUnnessecaryCourses(Student student)
	{
		ArrayList<Course> studentCourses = new ArrayList<Course>(Arrays.asList(student.getCourseWork()));
		for(int i = 0; i < studentCourses.size(); i++)
		{
			for(int j = 0; j < reqCourses.length; j++)
			{
				if(reqCourses[j].equals(studentCourses.get(i)))//Make equals method
					studentCourses.remove(i);
			}
		}
		ArrayList<Course> coursesSatisfied = getGenReqsSatisfied(studentCourses);//Instantiate after major requirements are removed
		for(int i = 0; i < studentCourses.size(); i++)
		{
			for(int j = 0; j < coursesSatisfied.size(); j++)
			{
				if(coursesSatisfied.get(i).equals(studentCourses.get(i)))//Make equals method
					studentCourses.remove(i);
			}
		}
		return studentCourses.toArray(new Course[studentCourses.size()]);
	}
	public int getNumOfSemestersReq(Student student)
	{
		ArrayList<Course> studentCourses = new ArrayList<Course>(Arrays.asList(student.getCourseWork()));
		ArrayList<Course> prerequisites = new ArrayList<Course>();
		ArrayList<Course> temp = new ArrayList<Course>();
		int semesters = 0;
		prerequisites = studentCourses;
		boolean loop = true;
		while(loop)
		{
			for(Course c : prerequisites)
			{
				if(c.getPrerequisites() != null)
					for(int i = 0; i < c.getPrerequisites().length; i++)
						if(!student.courseWork.getCourse(c.getPrerequisites()[i].getCourseCode()).isSuccessful())
							temp.add(c.getPrerequisites()[i]);
			}
			if(temp.size() > 0)
			{
				semesters++;
				prerequisites = temp;
				temp = new ArrayList<Course>();
			}
			else
			{
				loop = false;
			}
		}
		return semesters;
	}
	private ArrayList<Course> getGenReqsSatisfied(ArrayList<Course> studentCourses)
	{
		int physEdReqTemp = physEdReq;
		int hisReqTemp = hisReq;
		int labSciReqTemp = labSciReq;
		int mathReqTemp = mathReq;
		int humReqTemp = humReq;
		int busReqTemp = busReq;
		int engReqTemp = engReq;
		int comReqTemp = comReq;
		int amerHisReqTemp = amerHisReq;
		int socSciReqTemp = socSciReq;
		int langReqTemp = langReq;
		int phlReqTemp = phlReq;
		ArrayList<Course> returnCourses = new ArrayList<Course>();
		for(int i = 0; i < studentCourses.size(); i++)
		{
			if(labSciReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isLabScience())
				{
					returnCourses.add(studentCourses.get(i));
					labSciReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(mathReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isMath())
				{
					returnCourses.add(studentCourses.get(i));
					mathReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(langReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isLanguage())
				{
					returnCourses.add(studentCourses.get(i));
					langReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(physEdReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isPhysEd())
				{
					returnCourses.add(studentCourses.get(i));
					physEdReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(amerHisReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isAmerHis())
				{
					returnCourses.add(studentCourses.get(i));
					amerHisReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(phlReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isPhilosophy())
				{
					returnCourses.add(studentCourses.get(i));
					phlReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(comReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isCommunications())
				{
					returnCourses.add(studentCourses.get(i));
					comReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(engReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isEnglish())
				{
					returnCourses.add(studentCourses.get(i));
					engReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			
			if(busReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isBusiness())
				{
					returnCourses.add(studentCourses.get(i));
					busReq--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(hisReqTemp > 0)	
				if(studentCourses.get(i).CAttributes.isHistory())
				{
					returnCourses.add(studentCourses.get(i));
					hisReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(socSciReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isSocScience())
				{
					returnCourses.add(studentCourses.get(i));
					socSciReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(humReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isHumanities())
				{
					returnCourses.add(studentCourses.get(i));
					humReq--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
		}
		return returnCourses;
	}
	private int[] getReqsNeeded(ArrayList<Course> studentCourses)
	{
		int physEdReqTemp = physEdReq;
		int hisReqTemp = hisReq;
		int labSciReqTemp = labSciReq;
		int mathReqTemp = mathReq;
		int humReqTemp = humReq;
		int busReqTemp = busReq;
		int engReqTemp = engReq;
		int comReqTemp = comReq;
		int amerHisReqTemp = amerHisReq;
		int socSciReqTemp = socSciReq;
		int langReqTemp = langReq;
		int phlReqTemp = phlReq;
		for(int i = 0; i < studentCourses.size(); i++)
		{
			if(labSciReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isLabScience())
				{
					labSciReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(mathReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isMath())
				{
					mathReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(langReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isLanguage())
				{
					langReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(physEdReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isPhysEd())
				{
					physEdReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(amerHisReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isAmerHis())
				{
					amerHisReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(phlReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isPhilosophy())
				{
					phlReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(comReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isCommunications())
				{
					comReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(engReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isEnglish())
				{
					engReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			
			if(busReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isBusiness())
				{
					busReq--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(hisReqTemp > 0)	
				if(studentCourses.get(i).CAttributes.isHistory())
				{
					hisReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(socSciReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isSocScience())
				{
					socSciReqTemp--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
			if(humReqTemp > 0)
				if(studentCourses.get(i).CAttributes.isHumanities())
				{
					humReq--;
					studentCourses.get(i).CAttributes = new CourseAttributes();//Prevents course from being counted twice by wiping all data
				}
		}
		int[] returnTemp = {physEdReqTemp, hisReqTemp, labSciReqTemp, mathReqTemp, humReqTemp, busReqTemp,
				engReqTemp, comReqTemp, amerHisReqTemp, socSciReqTemp, langReqTemp, phlReqTemp};
		return returnTemp;
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
