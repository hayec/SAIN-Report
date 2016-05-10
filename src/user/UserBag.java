package user;

import java.util.ArrayList;
import eventHandlers.ModelChangedEventObject;
import eventHandlers.ModelListener;
/**
 * Contains an ArrayList of Users.  Implements a variety algorithms for conveniently adding, removing, and searching for users.
 */

public class UserBag
{
	private ArrayList<User> users = new ArrayList<User>();
	private ModelListener listenerModel;
	  /**
     * Adds the specified user to the ArrayList.
     * @param user User to add.
     */
	public void addUser(User user)
	{
		if(user != null) {
			users.add(user);
			if(listenerModel != null) {
				listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
			}
		}
	}
	/**
     * Adds all users in the specified array to the ArrayList.
     * @param newUsers Array of users to add.
     */
	public void addUser(User[] newUsers)
	{
		for(int i = 0; i < newUsers.length; i++) {
			if(newUsers[i] != null) {
				users.add(newUsers[i]);
			}
		}
		if(listenerModel != null) {
			listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
		}
	}
	 /**
     * Returns the specified user from the ArrayList.  If the user is not found, null is returned instead.
     * @param id ID number of the user to be searched for
     * @return Search results
     */
	public User getUser(int id)
	{
		User returnUser = null;
		for(User u : users)
		{
			if(u.getId() == id) {
				returnUser = u;
				break;
			}
		}
		return returnUser;
	}
	 /**
     * Returns the specified user from the ArrayList.  If the user is not found, null is returned instead.
     * @param username Username of the user to be searched for
     * @return Search results
     */
	public User getUser(String username)
	{
		User returnUser = null;
		for(User u : users)
		{
			if(u.getUsername().equals(username)) {
				returnUser = u;
				break;
			}
		}
		return returnUser;
	}
	 /**
     * Removes the specified user from the ArrayList
     * @param id ID number of the user to be removed
     */
	public void removeUser(int id)
	{
		for(User u : (ArrayList<User>) users.clone())
		{
			if(u.getId() == id)
			{
				users.remove(u);
				break;
			}
		}
		if(listenerModel != null) {
			listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
		}
	}
	/**
     * Returns all users in the ArrayList
     * @return The array of all users in the ArrayList
     */
	public User[] getUsers()
	{
		return users.toArray(new User[users.size()]);
	}
	/**
     * Returns all users in the ArrayList with the specified values.  Blank or null strings are ignored as search terms, as are integers with negative values.
     * @return The array of all users in the ArrayList which meet the criteria.
     */
	@SuppressWarnings("unchecked")
	public User[] getUsers(String firstName, String lastName, String socSecNum, String address, String city, int zipCode, String state, int birthYear)//Any undesired requirements should be designated as negative or null
	{
		User[] returnUser = null;
		ArrayList<User> userResults = new ArrayList<User>();//All students which currently match given attributes
		ArrayList<User> tempUser = new ArrayList<User>();
		for(User u : users)
			userResults.add(u);	
		if(!firstName.equals("") && !firstName.equals(null))
			for(User u : users)
				if(!u.getFirstName().equals(firstName))
					userResults.remove(u);
		if(!lastName.equals("") && !lastName.equals(null))
		{
			tempUser = (ArrayList<User>) userResults.clone();
			for(User u : tempUser)
				if(!u.getLastName().equals(lastName))
					userResults.remove(u);
		}
		if(!socSecNum.equals("") && !socSecNum.equals(null))
		{
			tempUser = (ArrayList<User>) userResults.clone();
			for(User u : tempUser)
				if(!u.getSocialSecNum().equals(socSecNum))
					userResults.remove(u);
		}
		if(!address.equals("") && !address.equals(null))
		{
			tempUser = (ArrayList<User>) userResults.clone();
			for(User u : tempUser)
				if(!u.getAddress().equals(address))
					userResults.remove(u);
		}
		if(!city.equals("") && !city.equals(null))
		{
			tempUser = (ArrayList<User>) userResults.clone();
			for(User u : tempUser)
				if(!u.getCity().equals(city))
					userResults.remove(u);
		}
		if(zipCode >= 0)
		{
			tempUser = (ArrayList<User>) userResults.clone();
			for(User u : tempUser)
				if(u.getZipCode() != zipCode)
					userResults.remove(u);
		}
		if(!state.equals("") && !state.equals(null))
		{
			tempUser = (ArrayList<User>) userResults.clone();
			for(User u : tempUser)
				if(!u.getState().equals(state))
					userResults.remove(u);
		}
		if(birthYear >= 0)
		{
			tempUser = (ArrayList<User>) userResults.clone();
			for(User u : tempUser)
				if(u.getBirthYear() != birthYear)
					userResults.remove(u);
		}
		if(userResults.size() != 0)
			returnUser = userResults.toArray(new User[userResults.size()]);
		return returnUser;
	}
	/**
     * Returns all students in the ArrayList with the specified values.  Blank or null strings are ignored as search terms, as are integers with negative values.
     * @return The array of all students in the ArrayList which meet the criteria.
     */
	@SuppressWarnings("unchecked")
	public Student[] getStudents(String firstName, String lastName, String socSecNum, String address, String city, int zipCode, String state, int birthYear, double gpa, Major major, int yearEnrolled)//Any undesired requirements should be designated as negative or null
	{
		Student[] returnStudent = null;
		ArrayList<Student> studentResults = new ArrayList<Student>();//All students which currently match given attributes
		ArrayList<Student> tempStudent = new ArrayList<Student>();
		for(User u : users)
			if(u.isStudent())
				studentResults.add((Student) u);	
		if(!firstName.equals("") && !firstName.equals(null))
			for(Student u : studentResults)
				if(!u.getFirstName().equals(firstName))
					studentResults.remove(u);
		if(!lastName.equals("") && !lastName.equals(null))
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(!u.getLastName().equals(lastName))
					studentResults.remove(u);
		}
		if(!socSecNum.equals("") && !socSecNum.equals(null))
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(!u.getSocialSecNum().equals(socSecNum))
					studentResults.remove(u);
		}
		if(!address.equals("") && !address.equals(null))
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(!u.getAddress().equals(address))
					studentResults.remove(u);
		}
		if(!city.equals("") && !city.equals(null))
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(!u.getCity().equals(city))
					studentResults.remove(u);
		}
		if(zipCode >= 0)
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(u.getZipCode() != zipCode)
					studentResults.remove(u);
		}
		if(!state.equals("") && !state.equals(null))
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(!u.getState().equals(state))
					studentResults.remove(u);
		}
		if(birthYear >= 0)
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(u.getBirthYear() != birthYear)
					studentResults.remove(u);
		}
		if(gpa >= 0)
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(u.getGpa() != gpa)
					studentResults.remove(u);
		}
		if(major != null && !major.getName().equals("") && major.getName() != null)
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(!u.getMajor().equals(major))
					studentResults.remove(u);
		}
		if(yearEnrolled >= 0)
		{
			tempStudent = (ArrayList<Student>) studentResults.clone();
			for(Student u : tempStudent)
				if(u.getYearEnrolled() != yearEnrolled)
					studentResults.remove(u);
		}
		if(studentResults.size() != 0)
			returnStudent = studentResults.toArray(new Student[studentResults.size()]);
		return returnStudent;
	}
	/**
     * Returns all students in the ArrayList
     * @return The array of all students in the ArrayList
     */
	public Student[] getStudents()
	{
		ArrayList<Student> returnStudents = new ArrayList<Student>();
		for(User u : users)
		{
			if(u.isStudent()) {
				returnStudents.add((Student) u);//Safe to cast User to Student, because boolean value is hard coded in Student Class 
			}
		}
		return returnStudents.toArray(new Student[returnStudents.size()]);
	}
	/**
     * Returns all administrators in the ArrayList
     * @return The array of all administrators in the ArrayList
     */
	public Administrator[] getAdministrators()
	{
		ArrayList<Administrator> returnAdmins = new ArrayList<Administrator>();
		for(User u : users)
		{
			if(u.isStudent()) {
				returnAdmins.add((Administrator) u);//Safe to cast User to Student, because boolean value is hard coded in Student Class 
			}
		}
		return returnAdmins.toArray(new Administrator[returnAdmins.size()]);
	}
	public void setModelListener(ModelListener listenerModel) {
		this.listenerModel = listenerModel;
	}
}
