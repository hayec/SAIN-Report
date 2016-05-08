package eventHandlers;

import java.util.EventObject;
import report.Course;
import user.Major;

public class AddMajorEventObject extends EventObject
{
	private boolean valid;
	private String name;
	private String minGPA;
	private String numOfCreditsReq;
	private String physEdReq;
	private String hisReq;
	private String labSciReq;
	private String mathReq;
	private String humReq;
	private String busReq;
	private String engReq;
	private String comReq;
	private String amerHisReq;
	private String socSciReq;
	private String langReq;
	private String phlReq;
	private Course[] reqCourses;
	private String errorMessage = new String();
	private Major[] majors;
	private boolean edit;
	public AddMajorEventObject(Object source, String name, String minGPA, String numOfCreditsReq, String physEdReq, String hisReq,
			String labSciReq, String mathReq, String humReq, String busReq, String engReq, String comReq,
			String amerHisReq, String socSciReq, String langReq, String phlReq, Course[] reqCourses, boolean edit) {
		super(source);
		this.name = name;
		this.minGPA = minGPA;
		this.numOfCreditsReq = numOfCreditsReq;
		this.physEdReq = physEdReq;
		this.hisReq = hisReq;
		this.labSciReq = labSciReq;
		this.mathReq = mathReq;
		this.humReq = humReq;
		this.busReq = busReq;
		this.engReq = engReq;
		this.comReq = comReq;
		this.amerHisReq = amerHisReq;
		this.socSciReq = socSciReq;
		this.langReq = langReq;
		this.phlReq = phlReq;
		this.reqCourses = reqCourses;
		this.edit = edit;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMinGPA(String minGPA) {
		this.minGPA = minGPA;
	}
	public void setNumOfCreditsReq(String numOfCreditsReq) {
		this.numOfCreditsReq = numOfCreditsReq;
	}
	public void setPhysEdReq(String physEdReq) {
		this.physEdReq = physEdReq;
	}
	public void setHisReq(String hisReq) {
		this.hisReq = hisReq;
	}
	public void setLabSciReq(String labSciReq) {
		this.labSciReq = labSciReq;
	}
	public void setMathReq(String mathReq) {
		this.mathReq = mathReq;
	}
	public void setHumReq(String humReq) {
		this.humReq = humReq;
	}
	public void setBusReq(String busReq) {
		this.busReq = busReq;
	}
	public void setEngReq(String engReq) {
		this.engReq = engReq;
	}
	public void setComReq(String comReq) {
		this.comReq = comReq;
	}
	public void setAmerHisReq(String amerHisReq) {
		this.amerHisReq = amerHisReq;
	}
	public void setSocSciReq(String socSciReq) {
		this.socSciReq = socSciReq;
	}
	public void setLangReq(String langReq) {
		this.langReq = langReq;
	}
	public void setPhlReq(String phlReq) {
		this.phlReq = phlReq;
	}
	public void setReqCourses(Course[] reqCourses) {
		this.reqCourses = reqCourses;
	}
	public Major[] getMajors() {
		return majors;
	}
	public void setMajors(Major[] majors) {
		this.majors = majors;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage += "\n" + errorMessage;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getName() {
		return name;
	}
	public String getMinGPA() {
		return minGPA;
	}
	public String getNumOfCreditsReq() {
		return numOfCreditsReq;
	}
	public String getPhysEdReq() {
		return physEdReq;
	}
	public String getHisReq() {
		return hisReq;
	}
	public String getLabSciReq() {
		return labSciReq;
	}
	public String getMathReq() {
		return mathReq;
	}
	public String getHumReq() {
		return humReq;
	}
	public String getBusReq() {
		return busReq;
	}
	public String getEngReq() {
		return engReq;
	}
	public String getComReq() {
		return comReq;
	}
	public String getAmerHisReq() {
		return amerHisReq;
	}
	public String getSocSciReq() {
		return socSciReq;
	}
	public String getLangReq() {
		return langReq;
	}
	public String getPhlReq() {
		return phlReq;
	}
	public Course[] getReqCourses() {
		return reqCourses;
	}
}
