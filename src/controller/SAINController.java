package controller;

import eventHandlers.AddCourseEventObject;
import eventHandlers.AddCourseListener;
import eventHandlers.AddMajorEventObject;
import eventHandlers.AddMajorListener;
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
import eventHandlers.SAINEventObject;
import eventHandlers.SAINListener;
import eventHandlers.SearchEventObject;
import eventHandlers.SearchListener;
import javafx.stage.Stage;
import report.Course;
import report.CourseAttributes;
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
import java.nio.charset.StandardCharsets;
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
		try{md = MessageDigest.getInstance("MD5");} catch(Exception e) {}
		
		/**********************Login Listeners**********************************/
		loginView.setListenerAccount(new NewAccountListener()
		{
			@Override
			public void newAccount(NewAccountEventObject ev)
			{
				if(ev.getPassword().equals(ev.getPassword().toLowerCase()) || ev.getPassword().equals(ev.getPassword().toUpperCase()) || ev.getPassword().length() < 8 || ev.getPassword().length() > 32 || !ev.getPassword().matches(".*\\d.*"))
				{
					ev.setValidPassword(false);
					ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
				}
				else
				{
					ev.setValidPassword(true);
					int id = generateId(false);
					users.addUser(new Administrator(id, ev.getUsername(), new String(md.digest(ev.getPassword().getBytes())), "Default", ""));
					currentUser = users.getUser(id);
					adminView.adminView((Administrator) currentUser.getUser(), MajorBag.getMajors(), courses.getCourses());
					saveData();
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
					if(users.getUser(ev.getUsername()).correctPassword(new String(md.digest(ev.getPassword().getBytes()))))
					{
						ev.setCredentialsValid(true);
						currentUser = users.getUser(ev.getUsername());
						if(currentUser.isStudent())
							studentView.studentStart(currentUser, (Student) currentUser, MajorBag.getMajors(), courses.getCourses());
						else if(currentUser.isFacutly())
							staffView.start(currentUser.isAdministrator(), currentUser, MajorBag.getMajors());
						else
							adminView.adminView((Administrator) currentUser.getUser(), MajorBag.getMajors(), courses.getCourses());
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
				if(currentUser.correctPassword(new String(md.digest(ev.getOldPassword().getBytes()))))
				{
					if(ev.getNewPassword().equals(ev.getNewPassword().toLowerCase()) || ev.getNewPassword().equals(ev.getNewPassword().toUpperCase()) || ev.getNewPassword().length() < 8 || ev.getNewPassword().length() > 32 || !ev.getNewPassword().matches(".*\\d.*")) {
						ev.setSuccessful(false);
						ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
					}
					else {
						if(ev.getNewPassword().equals(ev.getNewPasswordConf())) {
							ev.setSuccessful(true);
							currentUser.setPassword(new String(md.digest(ev.getNewPassword().getBytes())));
							saveData();
						}
						else {
							ev.setSuccessful(false);
							ev.setErrorMessage("Error, new passwords do not match!");
						}
					}
				}
				else {
					ev.setSuccessful(false);
					ev.setErrorMessage("Error, old password is incorrect!");
				}
			}
		});
		studentView.setListenerPassword(new PasswordListener()
		{
			@Override
			public void changePassword(PasswordEventObject ev)
			{
				if(currentUser.correctPassword(new String(md.digest(ev.getOldPassword().getBytes()))))
				{
					if(ev.getNewPassword().equals(ev.getNewPassword().toLowerCase()) || ev.getNewPassword().equals(ev.getNewPassword().toUpperCase()) || ev.getNewPassword().length() < 8 || ev.getNewPassword().length() > 32 || !ev.getNewPassword().matches(".*\\d.*")) {
						ev.setSuccessful(false);
						ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
					}
					else {
						if(ev.getNewPassword().equals(ev.getNewPasswordConf())) {
							ev.setSuccessful(true);
							currentUser.setPassword(new String(md.digest(ev.getNewPassword().getBytes())));
							saveData();
						}
						else {
							ev.setSuccessful(false);
							ev.setErrorMessage("Error, new passwords do not match!");
						}
					}
				}
				else {
					ev.setSuccessful(false);
					ev.setErrorMessage("Error, old password is incorrect!");
				}
			}
		});
		adminView.setListenerPassword(new PasswordListener()
		{
			@Override
			public void changePassword(PasswordEventObject ev)
			{
				if(currentUser.correctPassword(new String(md.digest(ev.getOldPassword().getBytes()))))
				{
					if(ev.getNewPassword().equals(ev.getNewPassword().toLowerCase()) || ev.getNewPassword().equals(ev.getNewPassword().toUpperCase()) || ev.getNewPassword().length() < 8 || ev.getNewPassword().length() > 32 || !ev.getNewPassword().matches(".*\\d.*")) {
						ev.setSuccessful(false);
						ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
					}
					else {
						if(ev.getNewPassword().equals(ev.getNewPasswordConf())) {
							ev.setSuccessful(true);
							currentUser.setPassword(new String(md.digest(ev.getNewPassword().getBytes())));
							saveData();
						}
						else {
							ev.setSuccessful(false);
							ev.setErrorMessage("Error, new passwords do not match!");
						}
					}
				}
				else {
					ev.setSuccessful(false);
					ev.setErrorMessage("Error, old password is incorrect!");
				}
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
				staffView.start(currentUser.isAdministrator(), currentUser, MajorBag.getMajors());
			}
		});
		adminView.setListenerStudentSearch(new BackListener(){
			@Override
			public void back(BackEventObject ev)
			{
				staffView.start(currentUser.isAdministrator(), currentUser, MajorBag.getMajors());
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
				if(ev.getPassword().equals(ev.getPassword().toLowerCase()) || ev.getPassword().equals(ev.getPassword().toUpperCase()) || ev.getPassword().length() < 8 || ev.getPassword().length() > 32 || !ev.getPassword().matches(".*\\d.*"))
				{
					ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
				}
				if(ev.getId().equals("") || ev.getId() == null) {
					if(ev.getMajor() != null) {//If there is a declared major, then this must be a student
						id = generateId(true);//Generate student ID
						if(id < 0) {
							ev.setErrorMessage("All ID numbers are currently in use.  Please delete an account of the same type to continue.  Alternatively, contact the software developer to extend the number of allowed users.");
						}
					} else {
						id = generateId(false);//Generate staff ID
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
				if(ev.getMajor() != null) {
					if(id > 79999999) {
						ev.setErrorMessage("User ID is too large.  Maximum Size is 79999999.  Please choose another ID number.  Alternatively, leave the field blank and an ID number will be automatically generated.");
					}
				}
				else {
					if(id > 99999999 || id < 80000000) {
						ev.setErrorMessage("User ID is invalid.  ID number must be in the range of 80000000-99999999.  Please choose another ID number.  Alternatively, leave the field blank and an ID number will be automatically generated.");
					}
				}
				if(ev.getFirstName().equals("") || ev.getFirstName() == null) {
					ev.setErrorMessage("First Name cannot be left blank");
				}
				if(ev.getLastName().equals("") || ev.getLastName() == null) {
					ev.setErrorMessage("Last Name cannot be left blank");
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
				if(!ev.getDateOfBirth().isAfter(LocalDate.now().minusYears(120))) {
					ev.setErrorMessage("New user must not be more than 120 years old.");
				}
				if(ev.getMajor() != null) {
					if(ev.getDateEnrolled().isAfter(LocalDate.now())) {
						ev.setErrorMessage("Date of enrollment cannot be in the future.");
					}
					if(!ev.getDateEnrolled().isAfter(LocalDate.parse("1959-12-01"))) {
						ev.setErrorMessage("Date of enrollment cannot be prior to college founding.");
					}
				}
				if(users.getUser(ev.getUsername()) != null) {
					ev.setErrorMessage("Username is already in use.  Please choose another username.");
				}
				if(ev.getUsername().equals("") || ev.getUsername() == null) {
					ev.setErrorMessage("Username cannot be left blank");
				}
				if(ev.getErrorMessage().length() == 0) {
					if(ev.getMajor() == null) {
						if(ev.isAdmin()) {
							users.addUser(new Administrator(id, ev.getFirstName(), ev.getLastName(), ev.getDateOfBirth(), ev.getAddress(), ev.getCity(), ev.getState(), zipCode, ev.getSocSecNum(), ev.getUsername(), new String(md.digest(ev.getPassword().getBytes()))));
						} else {
							users.addUser(new Faculty(id, ev.getFirstName(), ev.getLastName(), ev.getDateOfBirth(), ev.getAddress(), ev.getCity(), ev.getState(), zipCode, ev.getSocSecNum(), ev.getUsername(), new String(md.digest(ev.getPassword().getBytes()))));
						}
					} else {
						users.addUser(new Student(id, ev.getFirstName(), ev.getLastName(), ev.getDateEnrolled(), ev.getDateOfBirth(), ev.getSocSecNum(), ev.getAddress(), ev.getCity(), zipCode, ev.getState(), ev.getCampus(), ev.getMajor(), ev.getUsername(), new String(md.digest(ev.getPassword().getBytes()))));
					}		
					ev.setId(id);
					ev.setValidAccount(true);
				}
				saveData();
			}
		});
		/****************New Major/Course Listeners********************************************/
		adminView.setListenerMajorAdd(new AddMajorListener(){
			@Override
			public void add(AddMajorEventObject ev)
			{
				if(ev.getName().equals("") || ev.getName() == null) {
					ev.setErrorMessage("Name cannot be left blank!");
				}
				if(MajorBag.getMajor(ev.getName()) != null)
				{
					ev.setErrorMessage("Major already exists!");
				}
				try {
					if(Integer.parseInt(ev.getAmerHisReq()) < 0 || Integer.parseInt(ev.getBusReq()) < 0 || Integer.parseInt(ev.getComReq()) < 0
							|| Integer.parseInt(ev.getEngReq()) < 0 || Integer.parseInt(ev.getHisReq()) < 0 || Integer.parseInt(ev.getHumReq()) < 0
							|| Integer.parseInt(ev.getLabSciReq()) < 0 || Integer.parseInt(ev.getLangReq()) < 0 || Integer.parseInt(ev.getMathReq()) < 0
							|| Integer.parseInt(ev.getPhlReq()) < 0 || Integer.parseInt(ev.getSocSciReq()) < 0) {
						ev.setErrorMessage("All course requirements must be positive.");
					} else if (Integer.parseInt(ev.getAmerHisReq()) > 10 || Integer.parseInt(ev.getBusReq()) > 10 || Integer.parseInt(ev.getComReq()) > 10
					|| Integer.parseInt(ev.getEngReq()) > 10 || Integer.parseInt(ev.getHisReq()) > 10 || Integer.parseInt(ev.getHumReq()) > 10
					|| Integer.parseInt(ev.getLabSciReq()) > 10 || Integer.parseInt(ev.getLangReq()) > 10 || Integer.parseInt(ev.getMathReq()) > 10
					|| Integer.parseInt(ev.getPhlReq()) > 10 || Integer.parseInt(ev.getSocSciReq()) > 10) {
						ev.setErrorMessage("All course requirements have a maximum value of 10.");
					}
				} catch(Exception e) {
					ev.setErrorMessage("All course requirements must be integers.");
				}
				try {
					if(Double.parseDouble(ev.getMinGPA()) < 0) {
						ev.setErrorMessage("Min GPA must be positive.");
					} else if(Double.parseDouble(ev.getMinGPA()) > 4.0) {
						ev.setErrorMessage("Min GPA cannot be greater than 4.0.");
					}
				} catch(Exception e) {
					ev.setErrorMessage("Min GPA must be a number.");
				}
				try {
					if(Integer.parseInt(ev.getNumOfCreditsReq()) < 0) {
						ev.setErrorMessage("Required number of credits must be a positive integer.");
					} else if(Integer.parseInt(ev.getNumOfCreditsReq()) >= 100) {
						ev.setErrorMessage("Required number of credits must be less than 100.");
					}
				} catch(Exception e) {
					ev.setErrorMessage("Required number of credits must be an integer");
				}
				if(ev.getErrorMessage().equals("") || ev.getErrorMessage() == null) {
					ev.setValid(true);
				} else {
					ev.setValid(false);
				}
				if(ev.isValid()) {
					MajorBag.addMajor(new Major(ev.getName(), Integer.parseInt(ev.getPhysEdReq()), Integer.parseInt(ev.getHisReq()), Integer.parseInt(ev.getLabSciReq()),
							Integer.parseInt(ev.getMathReq()), Integer.parseInt(ev.getHumReq()), Integer.parseInt(ev.getBusReq()), Integer.parseInt(ev.getEngReq()), 
							Integer.parseInt(ev.getComReq()), Integer.parseInt(ev.getAmerHisReq()), Integer.parseInt(ev.getSocSciReq()), Integer.parseInt(ev.getLangReq()), 
							Integer.parseInt(ev.getPhlReq()), Integer.parseInt(ev.getNumOfCreditsReq()), ev.getReqCourses()));
					ev.setMajors(MajorBag.getMajors());
				}
			}
		});
		studentView.setListenerSAIN(new SAINListener(){
			@Override
			public void getReport(SAINEventObject ev)
			{
				if(ev.getMajor().getName().equals("") || ev.getMajor().getName() == null)
				{
					ev.setErrorMessage("Error no major is selected.  Please select a major then try again.");
				}
				else
				{
					ev.setValid(true);
					Student temp = ev.getStudent();//Change major if what-if analysis is selected
					temp.setMajor(ev.getMajor());
					studentView.studentView(ev.getUser(), temp, courses.getCourses());
				}
			}
		});
		adminView.setListenerCourseAdd(new AddCourseListener(){
			@Override
			public void add(AddCourseEventObject ev) 
			{
				int credits = 0;
				try {
					if(Integer.parseInt(ev.getCredits()) < 0) {
						ev.setErrorMessage("Number of credits must be positive.");
					}
					else if(Integer.parseInt(ev.getCredits()) > 6) {
						ev.setErrorMessage("The maximum number of credits allowed is 6.");
					}
					else {
						credits = Integer.parseInt(ev.getCredits());
					}
				} catch(Exception e) {
					ev.setErrorMessage("Number of credits must be an integer.");
				}
				if(ev.getErrorMessage().equals("") || ev.getErrorMessage() == null) {
					Course c = new Course(ev.getCourseCode(), ev.getCourseTitle(), ev.getCourseDescription(), ev.isAmmerman(), ev.isGrant(), ev.isEastern(), ev.getPrerequisites(), ev.getCorequisites(), credits);
					c.CAttributes = new CourseAttributes(ev.isPhysEd(), ev.isHistory(), ev.isLabScience(), ev.isMath(), ev.isHumanities(), ev.isBusiness(), ev.isEnglish(), ev.isCommunications(), ev.isAmerHis(), ev.isSocScience(), ev.isLanguage(), ev.isPhilosophy());
					courses.addCourse(c);
					ev.setCourses(courses.getCourses());
					ev.setValid(true);
					saveData();
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
				if(!(ev.getStudent().getPassword().equals("") || ev.getStudent().getPassword() == null))//If password field is blank, then leave password alone
				{
					if(ev.getStudent().getPassword().equals(ev.getStudent().getPassword().toLowerCase()) || ev.getStudent().getPassword().equals(ev.getStudent().getPassword().toUpperCase()) || ev.getStudent().getPassword().length() < 8 || ev.getStudent().getPassword().length() > 32 || !ev.getStudent().getPassword().matches(".*\\d.*"))
					{
						ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
					}
					else
					{
						ev.getStudent().setPassword(md.digest(ev.getStudent().getPassword().getBytes()).toString());
					}
				}
				else
				{
					ev.getStudent().setPassword(((Student) users.getUser(ev.getStudent().getId()).getUser()).getPassword()); 
				}
				if(ev.getStudent().getFirstName().equals("") || ev.getStudent().getFirstName() == null) {
					ev.setErrorMessage("First Name cannot be left blank");
				}
				if(ev.getStudent().getLastName().equals("") || ev.getStudent().getLastName() == null) {
					ev.setErrorMessage("Last Name cannot be left blank");
				}
				if(ev.getStudent().getZipCode() < 0 || ev.getStudent().getZipCode() > 99999) {
					ev.setErrorMessage("Zip code must be a number between 00000 and 99999");
				}
				if(ev.getStudent().getDateOfBirth().isAfter(LocalDate.now().minusYears(13))) {
					ev.setErrorMessage("New user must be at least 13 years old.");
				}
				if(ev.getStudent().getDateEnrolled().isAfter(LocalDate.now())) {
					ev.setErrorMessage("Date of Enrollment cannot be in the future.");
				}
				if(!ev.getStudent().getDateOfBirth().isAfter(LocalDate.now().minusYears(120))) {
					ev.setErrorMessage("New user must not be more than 120 years old.");
				}
				if(!ev.getStudent().getDateEnrolled().isAfter(LocalDate.parse("1959-12-01"))) {
					ev.setErrorMessage("Date of enrollment cannot be prior to college founding.");
				}
				if(users.getUser(ev.getStudent().getUsername()) != null) {
					ev.setErrorMessage("Username is already in use.  Please choose another username.");
				}
				if(ev.getStudent().getUsername().equals("") || ev.getStudent().getUsername() == null) {
					ev.setErrorMessage("Username cannot be left blank");
				}
				if(ev.getErrorMessage().length() == 0) {
					if(ev.getStudent().getMajor() == null) {
						users.removeUser(ev.getStudent().getId());
						users.addUser(ev.getStudent());
						saveData();
					}
				}
			}
		});
		adminView.setListenerMajorDelete(new DeleteMajorListener() {
			@Override
			public void delete(DeleteMajorEventObject ev) {
				MajorBag.removeMajor(ev.getTarget().getName());
				saveData();
				ev.setMajors(MajorBag.getMajors());
			}
		});
		adminView.setListenerCourseDelete(new DeleteCourseListener() {
			@Override
			public void delete(DeleteCourseEventObject ev) {
				courses.removeCourse(ev.getTarget().getCourseCode());
				saveData();
				ev.setCourses(courses.getCourses());
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
				try {
					courses.addCourse((Course) objCourseIn.readObject());
				} catch(Exception e) {
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
				try {
					users.addUser((User) objUserIn.readObject());
				} catch(Exception e) {
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
				try {
				MajorBag.addMajor((Major) objMajorIn.readObject());
				} catch(Exception e) {
					loop = false;
				}
			}
		}
		else
			fileMajorIn = null;
		if(fileUserIn == null)
			loginView.newUser();
	}
	public boolean saveData()
	{
		try 
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
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
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

