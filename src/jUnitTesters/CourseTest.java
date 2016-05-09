package jUnitTesters;

import static org.junit.Assert.*;

import org.junit.Test;

import report.Course;

public class CourseTest {

	@Test
	public void testCourseStringStringStringBooleanBooleanBooleanStringArrayStringArrayInt() {
		Course c = new Course("Code", "Title", "Descr", true, false, false, new String[0], new String[0], 3);
		assert(c.getCourseCode().equals("Code"));
		assert(c.getCourseTitle().equals("Title"));
		assert(c.getCourseDescription().equals("Descr"));
		assert(c.isAmmerman());
		assert(!c.isEastern());
		assert(!c.isGrant());
		assert(c.getCredits() == 3);
	}

	@Test
	public void testGetLetterGrade() {
		Course c = new Course();
		c.setCourseGrade("B+");
		assert(c.getCourseGrade() == 3.5);
	}

	@Test
	public void testSetCourseGradeString() {
		Course c = new Course();
		c.setCourseGrade("B+");
		assert(c.getCourseGrade() == 3.5);
	}

	@Test
	public void testIsSuccessful() {
		Course c = new Course();
		c.setCourseGrade("B+");
		assert(c.isSuccessful());
		c.setCourseGrade("D");
		assert(!c.isSuccessful());
	}

}
