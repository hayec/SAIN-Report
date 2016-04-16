package user;

import java.util.ArrayList;


public class UserBag
{
	ArrayList<User> users = new ArrayList<User>();
	public void addPoint(User user)
	{
		users.add(user);
	}
	public User getUser(int id)
	{
		User returnUser = null;
		for(User u : users)
		{
			if(u.getId() == id)
				returnUser = (User) u.getUser();//Safe to cast to type User, since this type can only return Administrator, Student, or Faculty
		}
		return returnUser;
	}
	@SuppressWarnings("unchecked")
	public User[] getUsers(String firstName, String lastName, String socSecNum, String address, String city, int zipCode, String state, int birthYear)//Any undesired requirements should be designated as negative or null
	{
		User[] returnUser = null;
		ArrayList<User> returnUsersFN = new ArrayList<User>();//All users with matching first name
		ArrayList<User> returnUsersLN = new ArrayList<User>();//All users with matching last name
		ArrayList<User> returnUsersSSN = new ArrayList<User>();//All users with matching social security number
		ArrayList<User> returnUsersA = new ArrayList<User>();//All users with matching address
		ArrayList<User> returnUsersC = new ArrayList<User>();//All users with matching city
		ArrayList<User> returnUsersZC = new ArrayList<User>();//All users with matching zip code
		ArrayList<User> returnUsersST = new ArrayList<User>();//All users with matching state
		ArrayList<User> returnUsersBY = new ArrayList<User>();//All users with matching state
		ArrayList<User> tempUsers = new ArrayList<User>();//Contains the union of all previous arraylists
		if(!firstName.equals("") && !firstName.equals(null))
		{
			for(User u : users)
			{
				if(u.getFirstName().equals(firstName))
					returnUsersFN.add((User) u.getUser());//Safe to cast to type User, since this type can only return Administrator, Student, or Faculty
			}
		}
		else
			returnUsersFN = (ArrayList<User>) users.clone();
		if(!lastName.equals("") && !lastName.equals(null))
		{
			for(User u : users)
			{
				if(u.getLastName().equals(lastName))
					returnUsersLN.add((User) u.getUser());//Safe to cast to type User, since this type can only return Administrator, Student, or Faculty
			}
		}
		else
			returnUsersFN = (ArrayList<User>) users.clone();
		if(!socSecNum.equals("") && !socSecNum.equals(null))
		{
			for(User u : users)
			{
				if(u.getSocialSecNum().equals(socSecNum))
					returnUsersSSN.add((User) u.getUser());//Safe to cast to type User, since this type can only return Administrator, Student, or Faculty
			}
		}
		else
			returnUsersFN = (ArrayList<User>) users.clone();
		if(!address.equals("") && !address.equals(null))
		{
			for(User u : users)
			{
				if(u.getAddress().equals(address))
					returnUsersA.add((User) u.getUser());//Safe to cast to type User, since this type can only return Administrator, Student, or Faculty
			}
		}
		else
			returnUsersFN = (ArrayList<User>) users.clone();
		if(!city.equals("") && !city.equals(null))
		{
			for(User u : users)
			{
				if(u.getCity().equals(city))
					returnUsersC.add((User) u.getUser());//Safe to cast to type User, since this type can only return Administrator, Student, or Faculty
			}
		}
		else
			returnUsersFN = (ArrayList<User>) users.clone();
		if(zipCode >= 0)
		{
			for(User u : users)
			{
				if(u.getZipCode() == zipCode)
					returnUsersZC.add((User) u.getUser());//Safe to cast to type User, since this type can only return Administrator, Student, or Faculty
			}
		}
		else
			returnUsersFN = (ArrayList<User>) users.clone();
		if(!state.equals("") && !state.equals(null))
		{
			for(User u : users)
			{
				if(u.getState().equals(state))
					returnUsersST.add((User) u.getUser());//Safe to cast to type User, since this type can only return Administrator, Student, or Faculty
			}
		}
		else
			returnUsersFN = (ArrayList<User>) users.clone();
		if(birthYear >= 0)
		{
			for(User u : users)
			{
				if(u.getBirthYear() == birthYear)
					returnUsersBY.add((User) u.getUser());//Safe to cast to type User, since this type can only return Administrator, Student, or Faculty
			}
		}
		else
			returnUsersBY = (ArrayList<User>) users.clone();
		for(User u : users)
			if(returnUsersFN.contains(u) && returnUsersLN.contains(u) && returnUsersSSN.contains(u) && returnUsersA.contains(u) && returnUsersC.contains(u) && returnUsersZC.contains(u) && returnUsersST.contains(u) && returnUsersBY.contains(u))
				tempUsers.add(u);
		if(tempUsers.size() != 0)
		{
			returnUser = new User[tempUsers.size()];
			for(User u : tempUsers)
				returnUser[tempUsers.indexOf(u)] = u;
		}
		return returnUser;
	}
	@SuppressWarnings("unchecked")
	public Student[] getStudents(String firstName, String lastName, String socSecNum, String address, String city, int zipCode, String state, int birthYear, double gpa, Major major, int yearEnrolled)//Any undesired requirements should be designated as negative or null
	{
		Student[] returnStudent = null;
		ArrayList<Student> students = new ArrayList<Student>();//All students in the bag
		ArrayList<Student> returnStudentsFN = new ArrayList<Student>();//All students with matching first name
		ArrayList<Student> returnStudentsLN = new ArrayList<Student>();//All students with matching last name
		ArrayList<Student> returnStudentsSSN = new ArrayList<Student>();//All students with matching social security number
		ArrayList<Student> returnStudentsA = new ArrayList<Student>();//All students with matching address
		ArrayList<Student> returnStudentsC = new ArrayList<Student>();//All students with matching city
		ArrayList<Student> returnStudentsZC = new ArrayList<Student>();//All students with matching zip code
		ArrayList<Student> returnStudentsST = new ArrayList<Student>();//All students with matching state
		ArrayList<Student> returnStudentsBY = new ArrayList<Student>();//All students with matching birth year
		ArrayList<Student> returnStudentsGPA = new ArrayList<Student>();//All students with matching gpa
		ArrayList<Student> returnStudentsM = new ArrayList<Student>();//All students with matching major
		ArrayList<Student> returnStudentsYE = new ArrayList<Student>();//All students with matching year enrolled
		ArrayList<Student> tempStudents = new ArrayList<Student>();//Contains the union of all previous arraylists
		for(User u : users)
		{
			if(u.isStudent())
			{
				students.add((Student) u.getUser());
			}
		}
		if(!firstName.equals("") && !firstName.equals(null))
		{
			for(Student u : students)
			{
				if(u.getFirstName().equals(firstName))
					returnStudentsFN.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsFN = (ArrayList<Student>) students.clone();
		if(!lastName.equals("") && !lastName.equals(null))
		{
			for(Student u : students)
			{
				if(u.getLastName().equals(lastName))
					returnStudentsLN.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsFN = (ArrayList<Student>) students.clone();
		if(!socSecNum.equals("") && !socSecNum.equals(null))
		{
			for(Student u : students)
			{
				if(u.getSocialSecNum().equals(socSecNum))
					returnStudentsSSN.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsFN = (ArrayList<Student>) students.clone();
		if(!address.equals("") && !address.equals(null))
		{
			for(Student u : students)
			{
				if(u.getAddress().equals(address))
					returnStudentsA.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsFN = (ArrayList<Student>) students.clone();
		if(!city.equals("") && !city.equals(null))
		{
			for(Student u : students)
			{
				if(u.getCity().equals(city))
					returnStudentsC.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsFN = (ArrayList<Student>) students.clone();
		if(zipCode >= 0)
		{
			for(Student u : students)
			{
				if(u.getZipCode() == zipCode)
					returnStudentsZC.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsFN = (ArrayList<Student>) students.clone();
		if(!state.equals("") && !state.equals(null))
		{
			for(Student u : students)
			{
				if(u.getState().equals(state))
					returnStudentsST.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsFN = (ArrayList<Student>) students.clone();
		if(birthYear >= 0)
		{
			for(Student u : students)
			{
				if(u.getBirthYear() == birthYear)
					returnStudentsBY.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsBY = (ArrayList<Student>) students.clone();
		if(gpa >= 0)
		{
			for(Student u : students)
			{
				if(u.getGpa() == gpa)
					returnStudentsGPA.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsGPA = (ArrayList<Student>) students.clone();
		if(major != null)
		{
			for(Student u : students)
			{
				if(u.getMajor().equals(major))
					returnStudentsM.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsM = (ArrayList<Student>) students.clone();
		if(yearEnrolled >= 0)
		{
			for(Student u : students)
			{
				if(u.getYearEnrolled() == yearEnrolled)
					returnStudentsYE.add(u);//Safe to cast to type Student, since this type can only return Student
			}
		}
		else
			returnStudentsYE = (ArrayList<Student>) students.clone();
		for(Student u : students)
			if(returnStudentsFN.contains(u) && returnStudentsLN.contains(u) && returnStudentsSSN.contains(u) && returnStudentsA.contains(u) && returnStudentsC.contains(u) && returnStudentsZC.contains(u) && returnStudentsST.contains(u) 
					&& returnStudentsBY.contains(u) && returnStudentsGPA.contains(u) && returnStudentsM.contains(u) && returnStudentsYE.contains(u))
				tempStudents.add(u);
		if(tempStudents.size() != 0)
		{
			returnStudent = new Student[tempStudents.size()];
			for(Student u : tempStudents)
				returnStudent[tempStudents.indexOf(u)] = u;
		}
		return returnStudent;
	}
	
}
