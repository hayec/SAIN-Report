package jUnitTesters;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import user.Major;
import user.Student;

public class StudentTest {

	@Test
	public void testStudentIntStringStringLocalDateLocalDateStringStringStringIntStringStringMajor() {
		Student s = new Student(5, "TestFirst", "TestLast", LocalDate.now(), LocalDate.now(), "testSocial", "testAddress", "testCity", 10, "testState", "testCampus", new Major());
		assert(s.getFirstName().equals("TestFirst"));
		assert(s.getLastName().equals("TestLast"));
		assert(s.getSocialSecNum().equals("TestSocial"));
		assert(s.getAddress().equals("TestAddress"));
		assert(s.getCity().equals("TestCity"));
		assert(s.getState().equals("TestState"));
		assert(s.getCampus().equals("TestCampus"));
		assert(s.getZipCode() == 10);
	}

	@Test
	public void testIsStudent() {
		Student s = new Student();
		assert(s.isStudent());
	}

	@Test
	public void testIsFaculty() {
		Student s = new Student();
		assert(!s.isStudent());
	}

	@Test
	public void testIsAdministrator() {
		Student s = new Student();
		assert(!s.isStudent());
	}

	@Test
	public void testGetFirstName() {
		Student s = new Student();
		s.setFirstName("Test");
		assert(s.getFirstName().equals("Test"));
	}

	@Test
	public void testSetFirstName() {
		Student s = new Student();
		s.setFirstName("Test");
		assert(s.getFirstName().equals("Test"));
	}

	@Test
	public void testGetLastName() {
		Student s = new Student();
		s.setLastName("Test");
		assert(s.getLastName().equals("Test"));
	}

	@Test
	public void testSetLastName() {
		Student s = new Student();
		s.setLastName("Test");
		assert(s.getLastName().equals("Test"));
	}

	@Test
	public void testGetDateEnrolled() {
		LocalDate date = LocalDate.now();
		Student s = new Student();
		s.setDateEnrolled(date);
		assert(s.getDateEnrolled().equals(date));
	}

	@Test
	public void testSetDateEnrolled() {
		LocalDate date = LocalDate.now();
		Student s = new Student();
		s.setDateEnrolled(date);
		assert(s.getDateEnrolled().equals(date));
	}

	@Test
	public void testGetDateOfBirth() {
		LocalDate date = LocalDate.now();
		Student s = new Student();
		s.setDateOfBirth(date);
		assert(s.getDateOfBirth().equals(date));
	}

	@Test
	public void testSetDateOfBirth() {
		LocalDate date = LocalDate.now();
		Student s = new Student();
		s.setDateOfBirth(date);
		assert(s.getDateOfBirth().equals(date));
	}

}
