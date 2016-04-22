package controller;

import eventHandlers.AdminEditEventObject;
import eventHandlers.AdminEditListener;
import eventHandlers.BackEventObject;
import eventHandlers.BackListener;
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
import user.Major;
import user.MajorBag;
import user.Student;
import user.User;
import user.UserBag;
import view.LoginView;
import view.StaffView;
import view.StudentView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.*;
import java.util.ArrayList;

public class SAINController
{
	MessageDigest md;
	Stage primaryStage;
	LoginView loginView;
	StudentView studentView;
	StaffView staffView;
	User currentUser;
	UserBag users = new UserBag();
	CourseBag courses = new CourseBag();
	public SAINController(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		loginView = new LoginView(primaryStage);
		studentView = new StudentView(primaryStage);
		staffView = new StaffView(primaryStage);
		try{md = MessageDigest.getInstance("MD5");}catch(Exception e){}
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
					staffView.start(true, currentUser, MajorBag.getMajors());
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
							studentView.studentStart(currentUser, (Student) currentUser, MajorBag.getMajors());
						else
							staffView.start(currentUser.isAdministrator(), currentUser, MajorBag.getMajors());
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
		staffView.setListenerSearch(new SearchListener()
		{
			@Override
			public void search(SearchEventObject ev)
			{
				Major tempMajor = null;
				if(Major.getMajor(ev.getMajor()) != null)
					tempMajor = Major.getMajor(ev.getMajor());
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
		studentView.setListenerBack(new BackListener()
		{
			public void back(BackEventObject ev)
			{
				staffView.start(currentUser.isAdministrator(), currentUser, MajorBag.getMajors());
			}
		});
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
		try {
			loadData();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
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
	public void saveData()
	{
		
	}
}

