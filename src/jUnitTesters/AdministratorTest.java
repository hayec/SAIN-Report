package jUnitTesters;

import static org.junit.Assert.*;

import org.junit.Test;

import user.Administrator;

public class AdministratorTest {
/*
 * Faculty Class not tested because it is nearly identical to Administrator class
 */
	@Test
	public void testAdministratorIntStringStringStringString() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		assert(a.getPassword().equals("AdminPass"));
		assert(a.getId() == 5);
		assert(a.getUsername().equals("AdminUser"));
		assert(a.getFirstName().equals("AdminFirst"));
		assert(a.getLastName().equals("AdminLast"));
	}

	@Test
	public void testIsStudent() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		assert(!a.isStudent());
	}

	@Test
	public void testIsFaculty() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		assert(!a.isFaculty());
	}

	@Test
	public void testIsAdministrator() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		assert(!a.isAdministrator());
	}

	@Test
	public void testCorrectPassword() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		a.setPassword("Test Pass");
		assert(a.correctPassword("Test Pass"));
	}

	@Test
	public void testGetFirstName() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		a.setFirstName("Test First");
		assert(a.getFirstName().equals("Test First"));
	}

	@Test
	public void testSetFirstName() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		a.setFirstName("Test First");
		assert(a.getFirstName().equals("Test First"));
	}

	@Test
	public void testGetLastName() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		a.setLastName("Test Last");
		assert(a.getLastName().equals("Test Last"));
	}

	@Test
	public void testSetLastName() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		a.setLastName("Test Last");
		assert(a.getLastName().equals("Test Last"));
	}

	@Test
	public void testGetPassword() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		a.setPassword("Test Pass");
		assert(a.getPassword().equals("Test Pass"));
	}

	@Test
	public void testSetPassword() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		a.setPassword("Test Pass");
		assert(a.correctPassword("Test Pass"));
	}

	@Test
	public void testGetUsername() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		a.setUsername("TestUser");
		assert(a.getUsername().equals("Test User"));
	}

	@Test
	public void testSetUsername() {
		Administrator a = new Administrator(5,"AdminUser", "AdminPass", "AdminFirst", "AdminLast");
		a.setUsername("TestUser");
		assert(a.getUsername().equals("Test User"));
	}

}
