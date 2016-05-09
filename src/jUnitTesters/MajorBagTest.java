package jUnitTesters;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import user.Major;
import user.MajorBag;

public class MajorBagTest {

	@Test
	public void testAddMajorMajor() {
		MajorBag m = new MajorBag();
		Major major = new Major();
		major.setName("Test");
		m.addMajor(major);
		assert(m.getMajor("Test").getName().equals("Test"));
	}

	@Test
	public void testAddMajorMajorArray() {
		MajorBag m = new MajorBag();
		Major[] majors = new Major[2];
		majors[0] = new Major();
		majors[1] = new Major();
		majors[0].setName("Test");
		majors[1].setName("TestTwo");
		m.addMajor(majors);
		assert(m.getMajor("Test").getName().equals("Test"));
		assert(m.getMajor("TestTwo").getName().equals("TestTwo"));
	}

	@Test
	public void testGetMajor() {
		MajorBag m = new MajorBag();
		Major major = new Major();
		major.setName("Test");
		m.addMajor(major);
		assert(m.getMajor("Test").getName().equals("Test"));
	}

	@Test
	public void testRemoveMajor() {
		MajorBag m = new MajorBag();
		Major major = new Major();
		major.setName("Test");
		m.addMajor(major);
		assert(m.getMajor("Test").getName().equals("Test"));
		m.removeMajor(major.getName());
		assert(m.getMajor("Test") == null);
	}

	@Test
	public void testGetMajors() {
		MajorBag m = new MajorBag();
		Major[] majors = new Major[2];
		majors[0] = new Major();
		majors[1] = new Major();
		majors[0].setName("Test");
		majors[1].setName("TestTwo");
		m.addMajor(majors);
		assert(m.getMajor("Test").getName().equals("Test"));
		assert(m.getMajor("TestTwo").getName().equals("TestTwo"));
		assert(m.getMajors().length == 2);
		assert(m.getMajors()[0].getName().equals("Test"));
	}

	@Test
	public void testGetMajorNames() {
		MajorBag m = new MajorBag();
		Major[] majors = new Major[2];
		majors[0] = new Major();
		majors[1] = new Major();
		majors[0].setName("Test");
		majors[1].setName("TestTwo");
		m.addMajor(majors);
		assert(m.getMajor("Test").getName().equals("Test"));
		assert(m.getMajor("TestTwo").getName().equals("TestTwo"));
		assert(Arrays.asList(m.getMajorNames()).contains("Test"));
		assert(Arrays.asList(m.getMajorNames()).contains("TestTwo"));
	}

}
