package getWebsiteData;

import java.io.Serializable;

public class CourseDownload implements Serializable
{
	String courseCode = "";
	String description = "";
	boolean ammerman;
	boolean grant;
	boolean eastern;
	String[] prerequisites = new String[5];
	String[] attributes = new String[15];
	int credits = 0;
	String level;
	public CourseDownload(String courseCode, String description, boolean ammerman, boolean grant, boolean eastern,
			String[] prerequisites, String[] attributes, int credits, String level) {
		super();
		this.courseCode = courseCode;
		this.description = description;
		this.ammerman = ammerman;
		this.grant = grant;
		this.eastern = eastern;
		this.prerequisites = prerequisites;
		this.attributes = attributes;
		this.credits = credits;
		this.level = level;
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
