package jUnitTesters;

import static org.junit.Assert.*;

import org.junit.Test;

import report.Course;
import report.CourseBag;

public class CourseBagTest {

	@Test
	public void testAddCourseCourse() {
		CourseBag c = new CourseBag();
		Course course = new Course();
		course.setCourseCode("TST001");
		course.setCourseTitle("Testing 101");
		c.addCourse(course);
		assert(c.getCourse("TST001") != null);
	}

	@Test
	public void testAddCourseCourseArray() {
		CourseBag c = new CourseBag();
		Course[] courses = new Course[2];
		courses[0] = new Course();
		courses[0].setCourseCode("TST001");
		courses[0].setCourseTitle("Testing 101");
		courses[1] = new Course();
		courses[1].setCourseCode("TST002");
		courses[1].setCourseTitle("Testing 102");
		c.addCourse(courses);
		assert(c.getCourses().length == 2);
	}

	@Test
	public void testGetCourse() {
		CourseBag c = new CourseBag();
		Course course = new Course();
		course.setCourseCode("TST001");
		course.setCourseTitle("Testing 101");
		c.addCourse(course);
		assert(c.getCourse("TST001").getCourseCode().equals("TST001"));
		assert(c.getCourse("TST001").getCourseTitle().equals("Testing 001"));
	}

	@Test
	public void testRemoveCourse() {
		CourseBag c = new CourseBag();
		Course course = new Course();
		course.setCourseCode("TST001");
		course.setCourseTitle("Testing 101");
		c.addCourse(course);
		assert(c.getCourse("TST001").getCourseCode().equals("TST001"));
		assert(c.getCourse("TST001").getCourseTitle().equals("Testing 001"));
		c.removeCourse(course.getCourseCode());
		assert(c.getCourse("TST001") == null);
	}

	@Test
	public void testGetCourses() {
		CourseBag c = new CourseBag();
		Course course = new Course();
		course.setCourseCode("TST001");
		course.setCourseTitle("Testing 101");
		Course course2 = new Course();
		course.setCourseCode("TST002");
		course.setCourseTitle("Testing 102");
		c.addCourse(course);
		c.addCourse(course2);
		assert(c.getCourses().length == 2);
	}

	@Test
	public void testGetCoursesStringStringStringStringStringStringStringArrayStringArrayInt() {
		CourseBag c = new CourseBag();
		Course course = new Course();
		course.setCourseCode("TST001");
		course.setCourseTitle("Testing 101");
		Course course2 = new Course();
		course.setCourseCode("TST002");
		course.setCourseTitle("Testing 102");
		c.addCourse(course);
		c.addCourse(course2);
		assert(c.getCourses("TST001", "", "", "", "", "", null, null, -1)[0].getCourseTitle().equals("Testing 101"));
		assert(c.getCourses("", "", "", "", "", "", null, null, -1).length == 2);
		assert(c.getCourses("test", "", "", "", "", "", null, null, -1).length == 0);
	}

}
