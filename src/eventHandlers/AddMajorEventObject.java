package eventHandlers;

import java.util.EventObject;
import report.Course;

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
	private String errorMessage;
	public AddMajorEventObject(Object source, String name, String minGPA, String numOfCreditsReq, String physEdReq, String hisReq,
			String labSciReq, String mathReq, String humReq, String busReq, String engReq, String comReq,
			String amerHisReq, String socSciReq, String langReq, String phlReq, Course[] reqCourses) {
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
