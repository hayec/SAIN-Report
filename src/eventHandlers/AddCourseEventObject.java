package eventHandlers;

import java.util.EventObject;

import report.Course;

public class AddCourseEventObject extends EventObject 
{
	private String courseCode;
	private String courseTitle;
	private String courseDescription;
	private boolean ammerman;
	private boolean grant;
	private boolean eastern;
	private String[] prerequisites;
	private String[] corequisites;
	private String credits;
	private boolean physEd;
	private boolean history;
	private boolean labScience;
	private boolean math;
	private boolean humanities;
	private boolean business;
	private boolean english;
	private boolean communications;
	private boolean amerHis;
	private boolean socScience;
	private boolean language;
	private boolean philosophy;
	private boolean valid;
	private String errorMessage = new String();
	private Course[] courses;
	public AddCourseEventObject(Object source, String courseCode, String courseTitle, String courseDescription, boolean ammerman,
			boolean grant, boolean eastern, String[] prerequisites, String[] corequisites, String credits,
			boolean physEd, boolean history, boolean labScience, boolean math, boolean humanities, boolean business,
			boolean english, boolean communications, boolean amerHis, boolean socScience, boolean language,
			boolean philosophy) {
		super(source);
		this.courseCode = courseCode;
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
		this.ammerman = ammerman;
		this.grant = grant;
		this.eastern = eastern;
		this.prerequisites = prerequisites;
		this.corequisites = corequisites;
		this.credits = credits;
		this.physEd = physEd;
		this.history = history;
		this.labScience = labScience;
		this.math = math;
		this.humanities = humanities;
		this.business = business;
		this.english = english;
		this.communications = communications;
		this.amerHis = amerHis;
		this.socScience = socScience;
		this.language = language;
		this.philosophy = philosophy;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage += "\n" + errorMessage;
	}
	public Course[] getCourses() {
		return courses;
	}
	public void setCourses(Course[] courses) {
		this.courses = courses;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public boolean isAmmerman() {
		return ammerman;
	}
	public boolean isGrant() {
		return grant;
	}
	public boolean isEastern() {
		return eastern;
	}
	public String[] getPrerequisites() {
		return prerequisites;
	}
	public String[] getCorequisites() {
		return corequisites;
	}
	public String getCredits() {
		return credits;
	}
	public boolean isPhysEd() {
		return physEd;
	}
	public boolean isHistory() {
		return history;
	}
	public boolean isLabScience() {
		return labScience;
	}
	public boolean isMath() {
		return math;
	}
	public boolean isHumanities() {
		return humanities;
	}
	public boolean isBusiness() {
		return business;
	}
	public boolean isEnglish() {
		return english;
	}
	public boolean isCommunications() {
		return communications;
	}
	public boolean isAmerHis() {
		return amerHis;
	}
	public boolean isSocScience() {
		return socScience;
	}
	public boolean isLanguage() {
		return language;
	}
	public boolean isPhilosophy() {
		return philosophy;
	}
}
