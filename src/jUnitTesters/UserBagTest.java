package jUnitTesters;

import static org.junit.Assert.*;

import org.junit.Test;

import user.Administrator;
import user.Student;
import user.User;
import user.UserBag;

public class UserBagTest {

	@Test
	public void testAddUserUser() {
		UserBag users = new UserBag();
		Student student = new Student();
		student.setId(12);
		users.addUser(student);
		assert(users.getUser(12) != null);
	}

	@Test
	public void testAddUserUserArray() {
		UserBag users = new UserBag();
		Student student = new Student();
		student.setId(12);
		Student student2 = new Student();
		student2.setId(15);
		Student[] students = new Student[2];
		students[0] = student;
		students[1] = student2; 
		users.addUser(students);
		assert(users.getUser(12) != null);
		assert(users.getUser(15) != null);
		assert(users.getUsers().length == 2);
	}

	@Test
	public void testGetUserInt() {
		UserBag users = new UserBag();
		Student student = new Student();
		student.setId(12);
		users.addUser(student);
		assert(users.getUser(12) != null);
	}

	@Test
	public void testGetUserString() {
		UserBag users = new UserBag();
		Student student = new Student();
		student.setId(12);
		student.setUsername("UserTest");
		users.addUser(student);
		assert(users.getUser("UserTest") != null);
	}

	@Test
	public void testRemoveUser() {
		UserBag users = new UserBag();
		Student student = new Student();
		student.setId(12);
		Student student2 = new Student();
		student2.setId(15);
		Student[] students = new Student[2];
		students[0] = student;
		students[1] = student2; 
		users.addUser(students);
		assert(users.getUser(12) != null);
		assert(users.getUser(15) != null);
		users.removeUser(12);
		assert(users.getUser(12) != null);
	}

	@Test
	public void testGetUsers() {
		UserBag users = new UserBag();
		Student student = new Student();
		student.setId(12);
		Student student2 = new Student();
		student2.setId(15);
		Student[] students = new Student[2];
		students[0] = student;
		students[1] = student2; 
		users.addUser(student);
		assert(users.getUser(12) != null);
		assert(users.getUser(15) != null);
		assert(users.getUsers().length == 2);
	}

	@Test
	public void testGetUsersStringStringStringStringStringIntStringInt() {
		UserBag users = new UserBag();
		Student student = new Student();
		student.setId(12);
		student.setFirstName("First Name");
		Administrator admin = new Administrator(1, null, null, null, null);
		admin.setId(15);
		admin.setFirstName("Not First Name");
		User[] userA = new User[2];
		userA[0] = student;
		userA[1] = admin; 
		users.addUser(userA);
		assert(users.getUsers(null, null, null, null, null, -1, null, -1).length == 2);
		assert(users.getUsers("First Name", null, null, null, null, -1, null, -1)[0].getId() == 12);
	}

	@Test
	public void testGetStudents() {
		UserBag users = new UserBag();
		Student student = new Student();
		student.setId(12);
		Administrator admin = new Administrator(1, null, null, null, null);
		admin.setId(15);
		User[] userA = new User[2];
		userA[0] = student;
		userA[1] = admin; 
		users.addUser(userA);
		assert(users.getStudents()[0].isStudent());
		assert(users.getStudents().length == 1);
	}

	@Test
	public void testGetAdministrators() {
		UserBag users = new UserBag();
		Student student = new Student();
		student.setId(12);
		Administrator admin = new Administrator(1, null, null, null, null);
		admin.setId(15);
		User[] userA = new User[2];
		userA[0] = student;
		userA[1] = admin; 
		users.addUser(userA);
		assert(users.getAdministrators()[0].isAdministrator());
		assert(users.getAdministrators().length == 1);
	}

}
