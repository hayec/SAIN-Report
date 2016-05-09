package report;

import java.io.Serializable;
/*
 * Stores the attributes of a course
 */
public class CourseAttributes implements Serializable {
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
        boolean language, boolean philosophy) {
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
    public CourseAttributes(CourseAttributes c) {
    	this.physEd = c.physEd;
    	this.history = c.history;
        this.labScience = c.labScience;
        this.math = c.math;
        this.humanities = c.humanities;
        this.business = c.business;
        this.english = c.english;
        this.communications = c.communications;
        this.amerHis = c.amerHis;
        this.socScience = c.socScience;
        this.language = c.language;
        this.philosophy = c.philosophy;
    }
    public boolean isPhysEd() {
        return physEd;
    }
    public void setPhysEd(boolean physEd) {
        this.physEd = physEd;
    }
    public boolean isHistory() {
        return history;
    }
    public void setHistory(boolean history) {
        this.history = history;
    }
    public boolean isLabScience() {
        return labScience;
    }
    public void setLabScience(boolean labScience) {
        this.labScience = labScience;
    }
    public boolean isMath() {
        return math;
    }
    public void setMath(boolean math) {
        this.math = math;
    }
    public boolean isHumanities() {
        return humanities;
    }
    public void setHumanities(boolean humanities) {
        this.humanities = humanities;
    }
    public boolean isBusiness() {
        return business;
    }
    public void setBusiness(boolean business) {
        this.business = business;
    }
    public boolean isEnglish() {
        return english;
    }
    public void setEnglish(boolean english) {
        this.english = english;
    }
    public boolean isCommunications() {
        return communications;
    }
    public void setCommunications(boolean communications) {
        this.communications = communications;
    }
    public boolean isAmerHis() {
        return amerHis;
    }
    public void setAmerHis(boolean amerHis) {
        this.amerHis = amerHis;
    }
    public boolean isSocScience() {
        return socScience;
    }
    public void setSocScience(boolean socScience) {
        this.socScience = socScience;
    }
    public boolean isLanguage() {
        return language;
    }
    public void setLanguage(boolean language) {
        this.language = language;
    }
    public boolean isPhilosophy() {
        return philosophy;
    }
    public void setPhilosophy(boolean philosophy) {
        this.philosophy = philosophy;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (amerHis ? 1231 : 1237);
        result = prime * result + (business ? 1231 : 1237);
        result = prime * result + (communications ? 1231 : 1237);
        result = prime * result + (english ? 1231 : 1237);
        result = prime * result + (history ? 1231 : 1237);
        result = prime * result + (humanities ? 1231 : 1237);
        result = prime * result + (labScience ? 1231 : 1237);
        result = prime * result + (language ? 1231 : 1237);
        result = prime * result + (math ? 1231 : 1237);
        result = prime * result + (philosophy ? 1231 : 1237);
        result = prime * result + (physEd ? 1231 : 1237);
        result = prime * result + (socScience ? 1231 : 1237);
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CourseAttributes other = (CourseAttributes) obj;
        if (amerHis != other.amerHis) {
            return false;
        }
        if (business != other.business) {
            return false;
        }
        if (communications != other.communications) {
            return false;
        }
        if (english != other.english) {
            return false;
        }
        if (history != other.history) {
            return false;
        }
        if (humanities != other.humanities) {
            return false;
        }
        if (labScience != other.labScience) {
            return false;
        }
        if (language != other.language) {
            return false;
        }
        if (math != other.math) {
            return false;
        }
        if (philosophy != other.philosophy) {
            return false;
        }
        if (physEd != other.physEd) {
            return false;
        }
        if (socScience != other.socScience) {
            return false;
        }
        return true;
    }
}