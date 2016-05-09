package jUnitTesters;

import static org.junit.Assert.*;

import org.junit.Test;

import report.Course;
import user.Major;
import user.Student;

public class MajorTest {

	@Test
	public void testMajorStringIntIntIntIntIntIntIntIntIntIntIntIntIntDoubleCourseArray() {
		Major m = new Major("Test Major", 9, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 9, 65, 2.0, new Course[0]);
		assert(m.getName().equals("Test Major"));
		assert(m.getNumOfCreditsReq() == 65);
		assert(m.getPhysEdReq() == 9);
		assert(m.getHisReq() == 0);
		assert(m.getLabSciReq() == 1);
		assert(m.getMathReq() == 2);
		assert(m.getHumReq() == 3);
		assert(m.getBusReq() == 4);
		assert(m.getEngReq() == 5);
		assert(m.getComReq() == 4);
		assert(m.getAmerHisReq() == 3);
		assert(m.getSocSciReq() == 2);
		assert(m.getLangReq() == 1);
		assert(m.getPhlReq() == 9);
	}

	@Test
	public void testMajor() {
		Major m = new Major();
		assert(m.toString().equals("Undeclared"));
	}

	@Test
	public void testGetCoursesReq() {
		Major m = new Major();
		Course[] coursesReq = new Course[2];
		Course course1 = new Course();
		course1.setCourseCode("TST101");
		Course course2 = new Course();
		course2.setCourseCode("TST102");
		coursesReq[0] = course1;
		coursesReq[1] = course2;
		m.setCoursesReq(coursesReq);
		Student s = new Student();
		s.setCourseWork(coursesReq);
		assert(m.getCoursesReq(s).length == 0);
	}

	@Test
	public void testSetCoursesReq() {
		Major m = new Major();
		Course[] coursesReq = new Course[2];
		Course course1 = new Course();
		course1.setCourseCode("TST101");
		Course course2 = new Course();
		course2.setCourseCode("TST102");
		coursesReq[0] = course1;
		coursesReq[1] = course2;
		m.setCoursesReq(coursesReq);
		Student s = new Student();
		assert(m.getCoursesReq(s).length == 2);
	}

}
