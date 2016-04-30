package getWebsiteData;

import java.io.Serializable;

public class CourseDownload implements Serializable
{
	String courseCode;
	String title;
	String description;
	boolean ammerman;
	boolean grant;
	boolean eastern;
	String[] prerequisites = new String[5];
	String[] corequisites = new String[5];
	String[] attributes = new String[15];
	int credits = 0;
	String level;
	public CourseDownload(String courseCode, String title, String description, boolean ammerman, boolean grant, boolean eastern,
			String[] prerequisites, String[] corequisites, String[] attributes, int credits, String level) {
		super();
		this.courseCode = courseCode;
		this.title = title;
		this.description = description;
		this.ammerman = ammerman;
		this.grant = grant;
		this.eastern = eastern;
		this.prerequisites = prerequisites;
		this.corequisites = corequisites;
		this.attributes = attributes;
		this.credits = credits;
		this.level = level;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getCorequisites() {
		return corequisites;
	}
	public void setCorequisites(String[] corequisites) {
		this.corequisites = corequisites;
	}
	public boolean isAmmerman() {
		return ammerman;
	}
	public void setAmmerman(boolean ammerman) {
		this.ammerman = ammerman;
	}
	public boolean isGrant() {
		return grant;
	}
	public void setGrant(boolean grant) {
		this.grant = grant;
	}
	public boolean isEastern() {
		return eastern;
	}
	public void setEastern(boolean eastern) {
		this.eastern = eastern;
	}
	public String[] getPrerequisites() {
		return prerequisites;
	}
	public void setPrerequisites(String[] prerequisites) {
		this.prerequisites = prerequisites;
	}
	public String[] getAttributes() {
		return attributes;
	}
	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}
