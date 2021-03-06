package report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import eventHandlers.ModelChangedEventObject;
import eventHandlers.ModelListener;
import user.User;
/*
 * Contains an ArrayList of Courses.  Implements a variety algorithms for conveniently adding, removing, and searching for courses.
 */
public class CourseBag implements Serializable {
    private ModelListener listenerModel;
    private ArrayList < Course > courses = new ArrayList < Course > ();
    public CourseBag() {}
    public CourseBag(Course[] newCourses) {
        for (int i = 0; i < newCourses.length; i++) {
            courses.add(newCourses[i]);
        }
    }
    /*
     * Adds the specified course to the ArrayList.
     * @param course Courses to add.
     */
    public void addCourse(Course course) {
        courses.add(course);
        if (listenerModel != null) {
            listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
        }
    }
    /*
     * Adds all courses in the specified array to the ArrayList.
     * @param newCourses Array of courses to add.
     */
    public void addCourse(Course[] newCourses) {
        for (int i = 0; i < newCourses.length; i++) {
            courses.add(newCourses[i]);
        }
        if (listenerModel != null) {
            listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
        }
    }
    /*
     * Returns the specified course from the ArrayList.  If the course is not found, null is returned instead.
     * @param courseCode Course code of the course to be searched for
     */
    public Course getCourse(String courseCode) {
        Course returnCourse = null;
        for (Course c: courses) {
            if (c.getCourseCode().equals(courseCode))
                returnCourse = c;
        }
        return returnCourse;
    }
    /*
     * Removes the specified course from the ArrayList
     * @param courseCode Course code of the course to be removed
     */
    public void removeCourse(String courseCode) {
        for (Course c: (ArrayList < Course > ) courses.clone()) {
            if (c.getCourseCode().equals(courseCode)) {
                courses.remove(c);
            }
        }
        if (listenerModel != null) {
            listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
        }
    }
    /*
     * Returns all courses in the ArrayList
     * @return The array of all courses in the ArrayList
     */
    public Course[] getCourses() {
        return courses.toArray(new Course[courses.size()]);
    }
    /*
     * Returns all courses in the ArrayList with the specified values.  Blank or null strings are ignored as search terms, as are integers with negative values.
     * @return The array of all courses in the ArrayList which meet the criteria.
     */
    public Course[] getCourses(String courseCode, String courseTitle, String courseDescription, String ammerman, String grant,
        String eastern, String[] prerequisites, String[] corequisites, int credits) {
        Course[] returnCourse = null;
        ArrayList < Course > courseResults = new ArrayList < Course > ();
        ArrayList < Course > tempCourse = new ArrayList < Course > ();
        boolean loop = true;
        boolean subLoop = false;
        if (courseCode != null && courseCode != "") {
            for (Course c: courses) {
                if (c.getCourseCode().equals(courseCode)) {
                    courseResults.add(c);
                }
            }
        }
        if (courseTitle != null && courseTitle != "") {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            for (Course c: tempCourse) {
                if (c.getCourseTitle().equals(courseTitle)) {
                    courseResults.add(c);
                }
            }
        }
        if (courseDescription != null && courseDescription != "") {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            for (Course c: tempCourse) {
                if (c.getCourseDescription().equals(courseDescription)) {
                    courseResults.add(c);
                }
            }
        }
        if (ammerman != null && ammerman != "") {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            for (Course c: tempCourse) {
                if (Boolean.toString(c.isAmmerman()).equals(ammerman)) {
                    courseResults.add(c);
                }
            }
        }
        if (grant != null && grant != "") {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            for (Course c: tempCourse) {
                if (Boolean.toString(c.isGrant()).equals(grant)) {
                    courseResults.add(c);
                }
            }
        }
        if (eastern != null && eastern != "") {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            for (Course c: tempCourse) {
                if (Boolean.toString(c.isEastern()).equals(eastern)) {
                    courseResults.add(c);
                }
            }
        }
        if (prerequisites != null && prerequisites.length != 0) {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            loop = true;
            subLoop = false;
            for (Course c: tempCourse) {
                for (int i = 0; i < prerequisites.length; i++) {
                    for (int j = 0; j < c.getPrerequisites().length; j++) {
                        if (c.getPrerequisites()[j].equals(prerequisites[i]))
                            subLoop = true;
                    }
                    if (subLoop == false) {
                        loop = false;
                    }
                    subLoop = false;
                }
                if (loop) {
                    courseResults.add(c);
                }
            }
        }
        if (corequisites != null && corequisites.length != 0) {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            loop = true;
            subLoop = false;
            for (Course c: tempCourse) {
                for (int i = 0; i < corequisites.length; i++) {
                    for (int j = 0; j < c.getCorequisites().length; j++) {
                        if (c.getCorequisites()[j].equals(corequisites[i]))
                            subLoop = true;
                    }
                    if (subLoop == false) {
                        loop = false;
                    }
                    subLoop = false;
                }
                if (loop) {
                    courseResults.add(c);
                }
            }
        }
        if (credits >= 0) {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            for (Course c: tempCourse) {
                if (c.getCredits() == credits) {
                    courseResults.add(c);
                }
            }
        }
        returnCourse = courseResults.toArray(new Course[courseResults.size()]);
        return returnCourse;
    }
    /*
     * Returns all courses in the ArrayList with the specified values.  Blank or null strings are ignored as search terms, as are integers with negative values.
     * @return The array of all courses in the ArrayList which meet the criteria.
     */
    public Course[] getCourses(String courseCode, String courseTitle, String courseDescription, String ammerman, String grant,
        String eastern, String[] prerequisites, String[] corequisites, int credits, String successful, String complete, String transferCourse) {
        Course[] returnCourse = null;
        ArrayList < Course > courseResults = new ArrayList < Course > ();
        ArrayList < Course > tempCourse = new ArrayList < Course > ();
        tempCourse = new ArrayList < Course > (Arrays.asList(getCourses(courseCode, courseTitle, courseDescription, ammerman, grant,
            eastern, prerequisites, corequisites, credits)));
        if (successful != null && successful != "") {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            for (Course c: tempCourse) {
                if (Boolean.toString(c.isSuccessful()).equals(successful)) {
                    courseResults.add(c);
                }
            }
        }
        if (complete != null && complete != "") {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            for (Course c: tempCourse) {
                if (Boolean.toString(c.isComplete()).equals(complete)) {
                    courseResults.add(c);
                }
            }
        }
        if (transferCourse != null && transferCourse != "") {
            tempCourse = (ArrayList < Course > ) courseResults.clone();
            courseResults.clear();
            for (Course c: tempCourse) {
                if (Boolean.toString(c.isTransferCourse()).equals(transferCourse)) {
                    courseResults.add(c);
                }
            }
        }
        returnCourse = courseResults.toArray(new Course[courseResults.size()]);
        return returnCourse;
    }
    public void setModelListener(ModelListener listenerModel) {
        this.listenerModel = listenerModel;
    }
}