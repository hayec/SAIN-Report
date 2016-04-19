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
				returnUser = u;
		}
		return returnUser;
	}
	@SuppressWarnings("unchecked")
	public User[] getUsers(String firstName, String lastName, String socSecNum, String address, String city, int zipCode, String state, int birthYear)//Any undesired requirements should be designated as negative or null
	{
		User[] returnUser = null;
		ArrayList<User> userResults = new ArrayList<Student>();//All students which currently match given attributes
		ArrayList<User>> tempUser = new ArrayList<Student>();
		if(!firstName.equals("") && !firstName.equals(null))
			for(User u : users)
				if(u.getFirstName().equals(firstName))
					userResults.add(u);
		if(!lastName.equals("") && !lastName.equals(null))
		{
			tempUser = userResults.clone();
			userResults.clear();
			for(User u : users)
				if(u.getLastName().equals(lastName))
					userResults.add(u);
		}
		if(!socSecNum.equals("") && !socSecNum.equals(null))
		{
			tempUser = userResults.clone();
			userResults.clear();
			for(User u : tempUser)
				if(u.getSocialSecNum().equals(socSecNum))
					userResults.add(u);
		}
		if(!address.equals("") && !address.equals(null))
		{
			tempUser = userResults.clone();
			userResults.clear();
			for(User u : tempUser)
				if(u.getAddress().equals(address))
					userResults.add(u);
		}
		if(!city.equals("") && !city.equals(null))
		{
			tempUser = userResults.clone();
			userResults.clear();
			for(User u : tempUser)
				if(u.getCity().equals(city))
					userResults.add(u);
		}
		if(zipCode >= 0)
		{
			tempUser = userResults.clone();
			userResults.clear();
			for(User u : tempUser)
				if(u.getZipCode() == zipCode)
					userResults.add(u);
		}
		if(!state.equals("") && !state.equals(null))
		{
			tempUser = userResults.clone();
			userResults.clear();
			for(User u : tempUser)
				if(u.getState().equals(state))
					userResults.add(u);
		}
		if(birthYear >= 0)
		{
			tempUser = userResults.clone();
			userResults.clear();
			for(User u : tempUser)
				if(u.getBirthYear() == birthYear)
					userResults.add(u);
		}
		if(userResults.size() != 0)
		{
			returnUser = new User[userResults.size()];
			for(User u : userResults)
				returnUser[userResults.indexOf(u)] = u;
		}
		return returnUser;
	}
	@SuppressWarnings("unchecked")
	public Student[] getStudents(String firstName, String lastName, String socSecNum, String address, String city, int zipCode, String state, int birthYear, double gpa, Major major, int yearEnrolled)//Any undesired requirements should be designated as negative or null
	{
		Student[] returnStudent = null;
		ArrayList<Student> students = new ArrayList<Student>();//All students in the bag
		ArrayList<Student> studentResults = new ArrayList<Student>();//All students which currently match given attributes
		ArrayList<Student> tempStudent = new ArrayList<Student>();
		for(User u : users)
			if(u.isStudent())
				students.add((Student) u.getUser());
		if(!firstName.equals("") && !firstName.equals(null))
			for(Student u : students)
				if(u.getFirstName().equals(firstName))
					studentResults.add(u);
		if(!lastName.equals("") && !lastName.equals(null))
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getLastName().equals(lastName))
					studentResults.add(u);
		}
		if(!socSecNum.equals("") && !socSecNum.equals(null))
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getSocialSecNum().equals(socSecNum))
					studentResults.add(u);
		}
		if(!address.equals("") && !address.equals(null))
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getAddress().equals(address))
					studentResults.add(u);
		}
		if(!city.equals("") && !city.equals(null))
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getCity().equals(city))
					studentResults.add(u);
		}
		if(zipCode >= 0)
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getZipCode() == zipCode)
					studentResults.add(u);
		}
		if(!state.equals("") && !state.equals(null))
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getState().equals(state))
					studentResults.add(u);
		}
		if(birthYear >= 0)
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getBirthYear() == birthYear)
					studentResults.add(u);
		}
		if(gpa >= 0)
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getGpa() == gpa)
					studentResults.add(u);
		}
		if(major != null)
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getMajor().equals(major))
					studentResults.add(u);
		}
		if(yearEnrolled >= 0)
		{
			tempStudent = studentResults.clone();
			studentResults.clear();
			for(Student u : tempStudent)
				if(u.getYearEnrolled() == yearEnrolled)
					studentResults.add(u);
		}
		if(studentResults.size() != 0)
		{
			returnStudent = new Student[studentResults.size()];
			for(Student u : studentResults)
				returnStudent[studentResults.indexOf(u)] = u;
		}
		return returnStudent;
	}
	
}
