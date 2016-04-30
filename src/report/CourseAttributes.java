package report;

import java.io.Serializable;

public class CourseAttributes implements Serializable
{
	boolean physEd;
	boolean history;
	boolean labScience;
	boolean math;
	boolean humanities;
	boolean business;
	boolean english;
	boolean communications;
	boolean amerHis;
	boolean socScience;
	boolean language;
	boolean philosophy;
	public CourseAttributes() {}
	public CourseAttributes(boolean physEd, boolean history, boolean labScience, boolean math, boolean humanities,
			boolean business, boolean english, boolean communications, boolean amerHis, boolean socScience,
			boolean language, boolean philosophy) 
	{
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
	public boolean isPhysEd() 
	{
		return physEd;
	}
	public void setPhysEd(boolean physEd) 
	{
		this.physEd = physEd;
	}
	public boolean isHistory() 
	{
		return history;
	}
	public void setHistory(boolean history) 
	{
		this.history = history;
	}
	public boolean isLabScience() 
	{
		return labScience;
	}
	public void setLabScience(boolean labScience) 
	{
		this.labScience = labScience;
	}
	public boolean isMath() 
	{
		return math;
	}
	public void setMath(boolean math) 
	{
		this.math = math;
	}
	public boolean isHumanities() 
	{
		return humanities;
	}
	public void setHumanities(boolean humanities) 
	{
		this.humanities = humanities;
	}
	public boolean isBusiness() 
	{
		return business;
	}
	public void setBusiness(boolean business) 
	{
		this.business = business;
	}
	public boolean isEnglish() 
	{
		return english;
	}
	public void setEnglish(boolean english) 
	{
		this.english = english;
	}
	public boolean isCommunications()
	{
		return communications;
	}
	public void setCommunications(boolean communications) 
	{
		this.communications = communications;
	}
	public boolean isAmerHis() 
	{
		return amerHis;
	}
	public void setAmerHis(boolean amerHis) 
	{
		this.amerHis = amerHis;
	}
	public boolean isSocScience() 
	{
		return socScience;
	}
	public void setSocScience(boolean socScience) 
	{
		this.socScience = socScience;
	}
	public boolean isLanguage() 
	{
		return language;
	}
	public void setLanguage(boolean language) 
	{
		this.language = language;
	}
	public boolean isPhilosophy() 
	{
		return philosophy;
	}
	public void setPhilosophy(boolean philosophy) 
	{
		this.philosophy = philosophy;
	}
}
