package user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import report.Course;
import report.CourseAttributes;
import report.CourseBag;
/*
 * A class which stores the data for one major.
 */
public class Major implements Serializable {
    String name;
    double minGPA;
    int numOfCreditsReq;
    final int CREDITS_AT_SCCC = 36;
    final int MAX_SEM_CREDITS = 19;
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
    Course[] reqCourses;
    public Major(String name, int physEdReq, int hisReq, int labSciReq, int mathReq, int humReq, int busReq, int engReq, int comReq, int amerHisReq, int socSciReq, int langReq, int phlReq, int numOfCreditsReq, double minGPA, Course[] reqCourses) {
        this.name = name;
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
        this.numOfCreditsReq = numOfCreditsReq;
        this.minGPA = minGPA;
        this.reqCourses = reqCourses;
    }
    public Major() {

    }
    /*
     * Returns the number of credits required for the student to graduate, based on the fixed number of credits necessary for the major.
     * @param student The student for which the calculation is to be performed
     * @return The number of credits necessary for that student to graduate
     */
    public int getCreditsReq(Student student) {
        ArrayList<Course> studentCourses = new ArrayList<Course> (Arrays.asList(student.getCourseWork()));
        int creditTemp = 0;
        for (Course c: studentCourses)
            creditTemp += c.getCredits();
        creditTemp = numOfCreditsReq - creditTemp;
        if (creditTemp >= 0)
            return creditTemp;
        else
            return 0;
    }
    /*
     * Returns the array of courses required for the student's major which the student has completed.
     * @param student The student for which the calculation is to be performed
     * @return The array of courses necessary for the student's major which the student has completed.
     */
    public Course[] getMajorCoursesDone(Student student) {
        ArrayList<Course> studentCourses = new ArrayList<Course> (Arrays.asList(student.getCourseWork()));
        ArrayList<Course> returnCourse = new ArrayList<Course> ();
        for (int i = 0; i < studentCourses.size(); i++) {
            for (int j = 0; j < reqCourses.length; j++) {
                if (reqCourses[j].getCourseCode().equals(studentCourses.get(i).getCourseCode()))
                    returnCourse.add(studentCourses.get(i));
            }
        }
        return returnCourse.toArray(new Course[returnCourse.size()]);
    }
    /*
     * Returns the courses which the student needs to complete to fulfill the core major requirements.
     * @param studentCourses The ArrayList of courses which the student has completed
     * @return The array of courses necessary for the student's major which the student has not yet completed.
     */
    public Course[] getMajorCoursesReq(ArrayList<Course> studentCourses) {
        ArrayList<Course> reqCoursesTemp = new ArrayList<Course> (Arrays.asList(reqCourses));
        for (int i = 0; i < studentCourses.size(); i++) {
            for (int j = 0; j < reqCourses.length; j++) {
                if (reqCourses[j].getCourseCode().equals(studentCourses.get(i).getCourseCode())) //Make equals method
                    reqCoursesTemp.remove(reqCourses[j]);
            }
        }
        return reqCoursesTemp.toArray(new Course[reqCoursesTemp.size()]);
    }
    /*
     * Returns the courses which the student needs to complete to fulfill the core major requirements.
     * @param student The student for which the calculation is to be performed
     * @return The array of courses necessary for the student's major which the student has not yet completed.
     */
    public Course[] getMajorCoursesReq(Student student) {
        ArrayList<Course> studentCourses = new ArrayList<Course> (Arrays.asList(student.getCourseWork()));
        return getMajorCoursesReq(studentCourses);
    }
    /*
     * Returns the courses which the student needs to complete to fulfill the general education requirements.  These courses
     * are returned as generic courses, which are displayed via a custom Course.toString() method.
     * @param student The student for which the calculation is to be performed
     * @return The array of courses necessary for the student's major which the student has not yet completed.
     */
    public Course[] getGenEdsReq(Student student) {
        ArrayList<Course> studentCourses = new ArrayList<Course> (Arrays.asList(student.getCourseWork()));;
        ArrayList<Course> results = new ArrayList<Course> ();
        ArrayList<Course> tempCourse = (ArrayList<Course>) studentCourses.clone();
        for (int i = 0; i < studentCourses.size(); i++) {
            for (int j = 0; j < reqCourses.length; j++) {
            	try {
            		if (reqCourses[j].getCourseCode().equals(studentCourses.get(i).getCourseCode())) {
            			tempCourse.remove(studentCourses.get(i));
            		}
            	} catch(Exception e) {
            		
            	}
            }
        }
        studentCourses = tempCourse;
        int[] coursesSatisfied = getReqsNeeded(studentCourses); //Instantiate after major requirements are removed
        for (int i = 0; i < coursesSatisfied[0]; i++)
            results.add(new Course(new CourseAttributes(true, false, false, false, false, false, false, false, false, false, false, false)));
        for (int i = 0; i < coursesSatisfied[1]; i++)
            results.add(new Course(new CourseAttributes(false, true, false, false, false, false, false, false, false, false, false, false)));
        for (int i = 0; i < coursesSatisfied[2]; i++)
            results.add(new Course(new CourseAttributes(false, false, true, false, false, false, false, false, false, false, false, false)));
        for (int i = 0; i < coursesSatisfied[3]; i++)
            results.add(new Course(new CourseAttributes(false, false, false, true, false, false, false, false, false, false, false, false)));
        for (int i = 0; i < coursesSatisfied[4]; i++)
            results.add(new Course(new CourseAttributes(false, false, false, false, true, false, false, false, false, false, false, false)));
        for (int i = 0; i < coursesSatisfied[5]; i++)
            results.add(new Course(new CourseAttributes(false, false, false, false, false, true, false, false, false, false, false, false)));
        for (int i = 0; i < coursesSatisfied[6]; i++)
            results.add(new Course(new CourseAttributes(false, false, false, false, false, false, true, false, false, false, false, false)));
        for (int i = 0; i < coursesSatisfied[7]; i++)
            results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, true, false, false, false, false)));
        for (int i = 0; i < coursesSatisfied[8]; i++)
            results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, false, true, false, false, false)));
        for (int i = 0; i < coursesSatisfied[9]; i++)
            results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, false, false, true, false, false)));
        for (int i = 0; i < coursesSatisfied[10]; i++)
            results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, false, false, false, true, false)));
        for (int i = 0; i < coursesSatisfied[11]; i++)
            results.add(new Course(new CourseAttributes(false, false, false, false, false, false, false, false, false, false, false, true)));
        return results.toArray(new Course[studentCourses.size()]);
    }
    /*
     * Returns the courses which the student needs to complete to fulfill the full major requirements.
     * @param student The student for which the calculation is to be performed
     * @return The array of courses necessary for the student's major which the student has not yet completed.
     */
    public Course[] getCoursesReq(Student student) {
    	ArrayList<Course> studentCourses = new ArrayList<Course>(Arrays.asList(student.getCourseWork()));
    	for(Course c : (ArrayList<Course>) studentCourses.clone()) {
    		if(!c.isSuccessful()) {
    			studentCourses.remove(c);//Remove failed courses
    		}
    	}
        Course[] majReqs = getMajorCoursesReq(studentCourses);
        Course[] genReqs = getGenEdsReq(student);
        Course[] results = new Course[majReqs.length + genReqs.length];
        for (int i = 0; i < majReqs.length; i++) {
            results[i] = majReqs[i];
        }
        for (int i = majReqs.length; i < majReqs.length + genReqs.length; i++) {
            results[i] = genReqs[i - majReqs.length];
        }
        return results;
    }
    /*
     * Returns the courses which the student has completed which don't count toward the major.
     * @param student The student for which the calculation is to be performed
     * @return The array of courses which are not applicable to the students major
     */
    public Course[] getUnnessecaryCourses(Student student) {
        ArrayList<Course> studentCourses = new ArrayList<Course> (Arrays.asList(student.getCourseWork()));
        for (int i = 0; i < studentCourses.size(); i++) {
            for (int j = 0; j < reqCourses.length; j++) {
                if (reqCourses[j].getCourseCode().equals(studentCourses.get(i).getCourseCode())) //Make equals method
                    studentCourses.remove(reqCourses[j]);
            }
        }
        ArrayList<Course> coursesSatisfied = getGenReqsSatisfied(studentCourses); //Instantiate after major requirements are removed
        for (int i = 0; i < studentCourses.size(); i++) {
            for (int j = 0; j < coursesSatisfied.size(); j++) {
                if (coursesSatisfied.get(i).getCourseCode().equals(studentCourses.get(i).getCourseCode())) //Make equals method
                    studentCourses.remove(coursesSatisfied.get(i));
            }
        }
        return studentCourses.toArray(new Course[studentCourses.size()]);
    }
    /*
     * Calculates the approximate number of semesters necessary for the student to complete the selected degree.  Assumes a credit 
     * load of 18 credits per semester.
     * @param student The student for which the calculation is to be performed
     * @param courses A CourseBag containing all courses which are currently defined.
     * @return The number of semesters needed for the student to complete his or her 
     */
    public int getNumOfSemestersReq(Student student, CourseBag courses) {
        ArrayList<Course> studentCourses = new ArrayList<Course> (Arrays.asList(student.getCourseWork()));
        ArrayList<Course> prerequisites = new ArrayList<Course> ();
        ArrayList<Course> temp = new ArrayList<Course> ();
        int semesters = 0;
        prerequisites = studentCourses;
        boolean loop = true;
        while (loop) {
            for (Course c: prerequisites) {
                if (c.getPrerequisites() != null)
                    for (int i = 0; i < c.getPrerequisites().length; i++) {
                        try {
                            if (!student.getCourseBag().getCourse(c.getPrerequisites()[i]).isSuccessful()) {
                                temp.add(courses.getCourse(c.getPrerequisites()[i]));
                            }
                        } catch (Exception e) {

                        }
                    }
            }
            if (temp.size() > 0) {
                semesters++;
                prerequisites = temp;
                temp = new ArrayList<Course> ();
            } else {
                loop = false;
            }
        }
        int semesters2 = 0;
        for (Course c: getCoursesReq(student)) {
            semesters2 += c.getCredits();
        }
        semesters2 = (int) Math.ceil(semesters2 / 18.0);
        if (semesters > semesters2) {
            return semesters; //Return whichever is greater
        } else {
            return semesters2;
        }
    }
    /*
     * Returns the courses which the student has completed which satisfy general education requirements for the major.
     * @param student The student for which the calculation is to be performed
     * @return The ArrayList of courses which were satisfied
     */
    private ArrayList<Course> getGenReqsSatisfied(ArrayList<Course> studentCourses) {
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
        ArrayList<Course> returnCourses = new ArrayList<Course> ();
        for (int i = 0; i < studentCourses.size(); i++) {
        	CourseAttributes temp = studentCourses.get(i).CAttributes;
            if (labSciReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isLabScience()) {
                    returnCourses.add(studentCourses.get(i));
                    labSciReqTemp--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (mathReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isMath()) {
                    returnCourses.add(studentCourses.get(i));
                    mathReqTemp--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (langReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isLanguage()) {
                    returnCourses.add(studentCourses.get(i));
                    langReqTemp--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (physEdReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isPhysEd()) {
                    returnCourses.add(studentCourses.get(i));
                    physEdReqTemp--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (amerHisReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isAmerHis()) {
                    returnCourses.add(studentCourses.get(i));
                    amerHisReqTemp--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (phlReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isPhilosophy()) {
                    returnCourses.add(studentCourses.get(i));
                    phlReqTemp--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (comReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isCommunications()) {
                    returnCourses.add(studentCourses.get(i));
                    comReqTemp--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (engReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isEnglish()) {
                    returnCourses.add(studentCourses.get(i));
                    engReqTemp--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (busReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isBusiness()) {
                    returnCourses.add(studentCourses.get(i));
                    busReq--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (hisReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isHistory()) {
                    returnCourses.add(studentCourses.get(i));
                    hisReqTemp--;
                   studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (socSciReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isSocScience()) {
                    returnCourses.add(studentCourses.get(i));
                    socSciReqTemp--;
                   studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            if (humReqTemp > 0) {
                if (studentCourses.get(i).CAttributes.isHumanities()) {
                    returnCourses.add(studentCourses.get(i));
                    humReq--;
                    studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                }
            }
            studentCourses.get(i).CAttributes = temp;
        }
        return returnCourses;
    }
    /*
     * Calculates the number of each requirement needed to satisfy the general education requirements of the major
     * @param studentCourses The courses which the student has completed
     * @return The array of integers which contains the number of each general education requirement necessary for graduation
     */
    private int[] getReqsNeeded(ArrayList<Course> studentCourses) {
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
            for (int i = 0; i < studentCourses.size(); i++) {
                if (labSciReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isLabScience()) {
                        labSciReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (mathReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isMath()) {
                        mathReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (langReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isLanguage()) {
                        langReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (physEdReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isPhysEd()) {
                        physEdReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (amerHisReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isAmerHis()) {
                        amerHisReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (phlReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isPhilosophy()) {
                        phlReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (comReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isCommunications()) {
                        comReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (engReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isEnglish()) {
                        engReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (busReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isBusiness()) {
                        busReq--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (hisReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isHistory()) {
                        hisReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (socSciReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isSocScience()) {
                        socSciReqTemp--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
                if (humReqTemp > 0) {
                    if (studentCourses.get(i).CAttributes.isHumanities()) {
                        humReq--;
                        studentCourses.get(i).CAttributes = new CourseAttributes(); //Prevents course from being counted twice by wiping all data
                    }
                }
            }
            int[] returnTemp = {
                physEdReqTemp,
                hisReqTemp,
                labSciReqTemp,
                mathReqTemp,
                humReqTemp,
                busReqTemp,
                engReqTemp,
                comReqTemp,
                amerHisReqTemp,
                socSciReqTemp,
                langReqTemp,
                phlReqTemp
            };
            return returnTemp;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getMinGPA() {
        return minGPA;
    }
    public int getNumOfCreditsReq() {
        return numOfCreditsReq;
    }
    public int getPhysEdReq() {
        return physEdReq;
    }
    public int getHisReq() {
        return hisReq;
    }
    public int getLabSciReq() {
        return labSciReq;
    }
    public int getMathReq() {
        return mathReq;
    }
    public int getHumReq() {
        return humReq;
    }
    public int getBusReq() {
        return busReq;
    }
    public int getEngReq() {
        return engReq;
    }
    public int getComReq() {
        return comReq;
    }
    public int getAmerHisReq() {
        return amerHisReq;
    }
    public int getSocSciReq() {
        return socSciReq;
    }
    public int getLangReq() {
        return langReq;
    }
    public int getPhlReq() {
        return phlReq;
    }
    public Course[] getReqCourses() {
        return reqCourses;
    }
    public String toString() {
        if (name == null || name.equals("")) {
            return "Undeclared";
        } else {
            return name;
        }
    }
    public boolean equals(Major m) {
        if (m.getName().equals(getName())) { //All majors have a unique name, this is used as the unique identifier
            return true;
        } else {
            return false;
        }
    }
    public void setCoursesReq(Course[] reqCourses) {
        this.reqCourses = reqCourses;
    }
}