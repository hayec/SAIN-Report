package controller;

import eventHandlers.AdminEditEventObject;
import eventHandlers.AdminEditListener;
import eventHandlers.BackEventObject;
import eventHandlers.BackListener;
import eventHandlers.CreateAccountEventObject;
import eventHandlers.CreateAccountListener;
import eventHandlers.DeleteCourseEventObject;
import eventHandlers.DeleteCourseListener;
import eventHandlers.DeleteMajorEventObject;
import eventHandlers.DeleteMajorListener;
import eventHandlers.EditEventObject;
import eventHandlers.EditListener;
import eventHandlers.LoginEventObject;
import eventHandlers.LoginListener;
import eventHandlers.LogoutEventObject;
import eventHandlers.LogoutListener;
import eventHandlers.NewAccountEventObject;
import eventHandlers.NewAccountListener;
import eventHandlers.PasswordEventObject;
import eventHandlers.PasswordListener;
import eventHandlers.SearchEventObject;
import eventHandlers.SearchListener;
import javafx.stage.Stage;
import report.Course;
import report.CourseBag;
import user.Administrator;
import user.Faculty;
import user.Major;
import user.MajorBag;
import user.Student;
import user.User;
import user.UserBag;
import view.AdminView;
import view.LoginView;
import view.StaffView;
import view.StudentView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SAINController
{
	MessageDigest md;
	Stage primaryStage;
	LoginView loginView;
	StudentView studentView;
	StaffView staffView;
	AdminView adminView;
	User currentUser;
	UserBag users = new UserBag();
	CourseBag courses = new CourseBag();
	public SAINController(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		loginView = new LoginView(primaryStage);
		studentView = new StudentView(primaryStage);
		staffView = new StaffView(primaryStage);
		adminView = new AdminView(primaryStage);
		try{md = MessageDigest.getInstance("MD5");}catch(Exception e){}
		/**********************Login Listeners**********************************/
		loginView.setListenerAccount(new NewAccountListener()
		{
			@Override
			public void newAccount(NewAccountEventObject ev)
			{
				if(Integer.parseInt(ev.getUsername()) > 99999999 || Integer.parseInt(ev.getUsername()) < 90000000)
				{
					ev.setValidPassword(false);
					ev.setErrorMessage("Error, administrative ID numbers must be between 90000000 and 99999999");
				}
				else if(ev.getPassword().equals(ev.getPassword().toLowerCase()) || ev.getPassword().equals(ev.getPassword().toUpperCase()) || ev.getPassword().length() < 8 || ev.getPassword().length() > 32 || !ev.getPassword().matches(".*\\d.*"))
				{
					ev.setValidPassword(false);
					ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
				}
				else
				{
					users.addUser(new Administrator(Integer.parseInt(ev.getUsername()), md.digest(ev.getPassword().getBytes()).toString()));
					currentUser = users.getUser(Integer.parseInt(ev.getUsername()));
					staffView.start(true, currentUser, MajorBag.getMajorNames());
				}
			}
		});
		loginView.setListenerLogin(new LoginListener()
		{
			@Override
			public void login(LoginEventObject ev)
			{
				try
				{
					if(users.getUser(Integer.parseInt(ev.getUsername())).correctPassword(md.digest(ev.getPassword().getBytes()).toString()))
					{
						ev.setCredentialsValid(true);
						currentUser = users.getUser(Integer.parseInt(ev.getUsername()));
						if(currentUser.isStudent())
							studentView.studentStart(currentUser, (Student) currentUser, MajorBag.getMajorNames(), courses);
						else
							staffView.start(currentUser.isAdministrator(), currentUser, MajorBag.getMajorNames());
					}
					else
						ev.setCredentialsValid(false);
				}
				catch(Exception e)
				{
					ev.setCredentialsValid(false);
				}
			}
		});
		/**************Logout Listeners******************************************/
		staffView.setListenerLogout(new LogoutListener()
		{
			@Override
			public void logout(LogoutEventObject ev)
			{
				currentUser = null;
				loginView.start();
			}
		});
		studentView.setListenerLogout(new LogoutListener()
		{
			@Override
			public void logout(LogoutEventObject ev)
			{
				currentUser = null;
				loginView.start();
			}
		});
		adminView.setListenerLogout(new LogoutListener()
		{
			@Override
			public void logout(LogoutEventObject ev)
			{
				currentUser = null;
				loginView.start();
			}
		});
		/*********************Password Listeners*********************************/
		staffView.setListenerPassword(new PasswordListener()
		{
			@Override
			public void changePassword(PasswordEventObject ev)
			{
				if(currentUser.correctPassword(md.digest(ev.getOldPassword().getBytes()).toString()))
					if(ev.getNewPassword().equals(ev.getNewPasswordConf()))
						currentUser.setPassword(md.digest(ev.getNewPassword().getBytes()).toString()); //Save user data!!!(Placeholder)
					else
						ev.setPassMatch(false);
				else
					ev.setOldPassSuccessful(false);
			}
		});
		studentView.setListenerPassword(new PasswordListener()
		{
			@Override
			public void changePassword(PasswordEventObject ev)
			{
				if(currentUser.correctPassword(md.digest(ev.getOldPassword().getBytes()).toString()))
					if(ev.getNewPassword().equals(ev.getNewPasswordConf()))
						currentUser.setPassword(md.digest(ev.getNewPassword().getBytes()).toString()); //Save user data!!!(Placeholder)
					else
						ev.setPassMatch(false);
				else
					ev.setOldPassSuccessful(false);
			}
		});
		adminView.setListenerPassword(new PasswordListener()
		{
			@Override
			public void changePassword(PasswordEventObject ev)
			{
				if(currentUser.correctPassword(md.digest(ev.getOldPassword().getBytes()).toString()))
					if(ev.getNewPassword().equals(ev.getNewPasswordConf()))
						currentUser.setPassword(md.digest(ev.getNewPassword().getBytes()).toString()); //Save user data!!!(Placeholder)
					else
						ev.setPassMatch(false);
				else
					ev.setOldPassSuccessful(false);
			}
		});
		/***************************Search Listeners*******************************************/
		staffView.setListenerSearch(new SearchListener()
		{
			@Override
			public void search(SearchEventObject ev)
			{
				Major tempMajor = null;
				if(MajorBag.getMajor(ev.getMajor()) != null)
					tempMajor = MajorBag.getMajor(ev.getMajor());
				else if (ev.getMajor() != "")
					ev.setInputValid(false);
				if(ev.getGpa() <= 4.0 && ev.getGpa() <= 0)
				{
					ev.setStudentResults(users.getStudents(ev.getFirstName(), ev.getLastName(), ev.getSocSecNum(), ev.getAddress(), ev.getCity(), ev.getZipCode(), ev.getState(), ev.getBirthYear(), ev.getGpa(), tempMajor, ev.getYearEnrolled()));
					ev.setInputValid(true);
				}
				else
					ev.setInputValid(false);
			}
		});
		studentView.setListenerBack(new BackListener()
		{
			public void back(BackEventObject ev)
			{
				staffView.start(currentUser.isAdministrator(), currentUser, MajorBag.getMajorNames());
			}
		});
		/***********************New Account Listeners*****************************************/
		adminView.setListenerNewAccount(new CreateAccountListener(){
			public void createAccount(CreateAccountEventObject ev)
			{
				int id;
				try {
					id = Integer.parseInt(ev.getId());
				} catch(Exception e) {
					id = -1;
					ev.setErrorMessage("ID# is formatted incorrectly.");
				}
				if(ev.getId().equals("") || ev.getId() == null) {
					if(ev.getMajor() != null) {//If there is a declared major, then this must be a student
						id = generateId(true);
						if(id < 0) {
							ev.setErrorMessage("All ID numbers are currently in use.  Please delete an account of the same type to continue.  Alternatively, contact the software developer to extend the number of allowed users.");
						}
					} else {
						id = generateId(false);
						if(id < 0) {
							ev.setErrorMessage("All ID numbers are currently in use.  Please delete an account of the same type to continue.  Alternatively, contact the software developer to extend the number of allowed users.");
						}
					}
						
				}
				if(id >= 0) {
					if(users.getUser(Integer.parseInt(ev.getId())) != null) {
						ev.setErrorMessage("User ID is already in use.  Please choose another ID number.  Alternatively, leave the field blank and an ID number will be automatically generated.");
					}
					
				}
				int zipCode;
				try {
					zipCode = Integer.parseInt(ev.getZipCode());
				} catch(Exception e) {
					zipCode = -1;
					ev.setErrorMessage("Zip code is formatted incorrectly.");
				}
				if(zipCode < 0 || zipCode > 99999) {
					ev.setErrorMessage("Zip code must be a number between 00000 and 99999");
				}
				if(ev.getDateOfBirth().isAfter(LocalDate.now().minusYears(13))) {
					ev.setErrorMessage("New user must be at least 13 years old.");
				}
				if(ev.getDateEnrolled().isAfter(LocalDate.now())) {
					ev.setErrorMessage("Date of Enrollment cannot be in the future.");
				}
				if(ev.getDateOfBirth().isAfter(LocalDate.now().minusYears(120))) {
					ev.setErrorMessage("New user must not be more than 120 years old.");
				}
				if(ev.getDateEnrolled().isAfter(LocalDate.parse("1959-12-01"))) {
					ev.setErrorMessage("Date of enrollment cannot be prior to college founding.");
				}
				if(ev.getErrorMessage().length() == 0) {
					if(ev.getMajor() == null) {
						if(ev.isAdmin()) {
							users.addUser(new Administrator(id, ev.getFirstName(), ev.getLastName(), ev.getDateOfBirth(), ev.getAddress(), ev.getCity(), ev.getState(), zipCode, ev.getSocSecNum(), ev.getUsername(), md.digest(ev.getPassword().getBytes()).toString()));
						} else {
							users.addUser(new Faculty(id, ev.getFirstName(), ev.getLastName(), ev.getDateOfBirth(), ev.getAddress(), ev.getCity(), ev.getState(), zipCode, ev.getSocSecNum(), ev.getUsername(), md.digest(ev.getPassword().getBytes()).toString()));
						}
					} else {
						users.addUser(new Student(id, ev.getFirstName(), ev.getLastName(), ev.getDateEnrolled(), ev.getDateOfBirth(), ev.getSocSecNum(), ev.getAddress(), ev.getCity(), zipCode, ev.getState(), ev.getCampus(), ev.getMajor(), ev.getUsername(), md.digest(ev.getPassword().getBytes()).toString()));
					}		
					ev.setId(id);
				}
					
			}
		});
		/****************Edit Listeners*******************************************************/
		staffView.setListenerAdmin(new AdminEditListener()
		{
			@Override
			public void verify(AdminEditEventObject ev)
			{
				try
				{
					if(users.getUser(Integer.parseInt(ev.getId())).isStudent())
					{
						ev.setValid(true);
						ev.setStudent((Student) users.getUser(Integer.parseInt(ev.getId())));
					}
					else
						ev.setValid(false);
				}
				catch(Exception e)
				{
					ev.setValid(false);
				}
					
			}
		});
		staffView.setListenerEdit(new EditListener()
		{
			@Override
			public void edit(EditEventObject ev)
			{
				users.removeUser(ev.getStudent().getId());
				users.addUser(ev.getStudent());
			}
		});
		adminView.setListenerMajorDelete(new DeleteMajorListener() {
			@Override
			public void delete(DeleteMajorEventObject ev) {
				MajorBag.removeMajor(ev.getTarget().getName());
			}
		});
		adminView.setListenerCourseDelete(new DeleteCourseListener() {
			@Override
			public void delete(DeleteCourseEventObject ev) {
				courses.removeCourse(ev.getTarget().getCourseCode());
			}
		});
		try {
			loadData();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if(!primaryStage.isShowing())
			loginView.start();
	}
	public void loadData() throws ClassNotFoundException, IOException
	{
		FileInputStream fileCourseIn;
		FileInputStream fileUserIn;
		FileInputStream fileMajorIn;
		boolean loop = true;
		ArrayList<User> tempUsers = new ArrayList<User>();
		ArrayList<Major> tempMajors = new ArrayList<Major>();
		ArrayList<Course> tempCourses = new ArrayList<Course>();
		if(new File("Courses.bin").isFile())
		{
			fileCourseIn = new FileInputStream(new File("Courses.bin"));
			ObjectInputStream objCourseIn = new ObjectInputStream(fileCourseIn);
			while(loop)
			{
				tempCourses.add((Course) objCourseIn.readObject());
				if(tempCourses.get(tempCourses.size() - 1) == null)
				{
					tempCourses.remove(tempCourses.size() - 1);
					loop = false;
				}
			}
			loop = true;
		}
		else
			fileCourseIn = null;
		if(new File("Users.bin").isFile())
		{
			fileUserIn = new FileInputStream(new File("Users.bin"));
			ObjectInputStream objUserIn = new ObjectInputStream(fileUserIn);
			while(loop)
			{
				tempUsers.add((User) objUserIn.readObject());
				if(tempUsers.get(tempUsers.size() - 1) == null)
				{
					tempUsers.remove(tempUsers.size() - 1);
					loop = false;
				}
			}
			loop = true;
		}
		else
			fileUserIn = null;
		if(new File("Majors.bin").isFile())
		{
			fileMajorIn = new FileInputStream(new File("Majors.bin"));
			ObjectInputStream objMajorIn = new ObjectInputStream(fileMajorIn);
			while(loop)
			{
				tempMajors.add((Major) objMajorIn.readObject());
				if(tempMajors.get(tempMajors.size() - 1) == null)
				{
					tempMajors.remove(tempMajors.size() - 1);
					loop = false;
				}
			}
		}
		else
			fileMajorIn = null;
		users.addUser(tempUsers.toArray(new User[tempUsers.size()]));
		MajorBag.addMajor(tempMajors.toArray(new Major[tempMajors.size()]));
		courses.addCourse(tempCourses.toArray(new Course[tempCourses.size()]));
		if(fileUserIn == null)
			loginView.newUser();
	}
	public void saveData() throws IOException
	{
		File fileCourse = new File("Courses.bin");
		File fileUser = new File("Users.bin");
		File fileMajor = new File("Majors.bin");
		fileCourse.createNewFile();
		fileUser.createNewFile();
		fileMajor.createNewFile();
		ObjectOutputStream courseOut = new ObjectOutputStream(new FileOutputStream(fileCourse));
		ObjectOutputStream userOut = new ObjectOutputStream(new FileOutputStream(fileUser));
		ObjectOutputStream majorOut = new ObjectOutputStream(new FileOutputStream(fileMajor));
		for(Course c : courses.getCourses()) {
			courseOut.writeObject(c);
		}
		for(User u : users.getUsers()) {
			userOut.writeObject(u);
		}
		for(Major m : MajorBag.getMajors()) {
			majorOut.writeObject(m);
		}		
		courseOut.close();
		userOut.close();
		majorOut.close();
	}
	public String validateAccount(Student student)
	{
		return null;
	}
	public String validateAccount(Faculty faculty)
	{
		return null;
	}
	public String validateAccount(Administrator admin)
	{
		return null;
	}
	public int generateId(boolean student)
	{
		int returnInt = -1;
		if(!student) {
			for(int i = 80000000; i <= 99999999; i++) {
				if(users.getUser(i) == null) {
					returnInt = i;
					break;
				}
			}
		}
		else {
			for(int i = 00000000; i <= 79999999; i++) {
				if(users.getUser(i) == null) {
					returnInt = i;
					break;
				}
			}
		}
		return returnInt;
	}
}

