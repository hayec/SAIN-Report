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
import eventHandlers.PasswordEventObject;
import eventHandlers.PasswordListener;
import eventHandlers.SearchEventObject;
import eventHandlers.SearchListener;
import javafx.stage.Stage;
import user.Major;
import user.Student;
import user.User;
import user.UserBag;
import view.LoginView;
import view.StaffView;
import view.StudentView;

import java.security.*;

public class SAINController
{
	MessageDigest md;
	Stage primaryStage = new Stage();
	LoginView loginView = new LoginView(primaryStage);
	StudentView studentView = new StudentView(primaryStage);
	StaffView staffView = new StaffView(primaryStage);
	User currentUser;
	UserBag users = new UserBag();
	public SAINController()
	{
		try{md = MessageDigest.getInstance("MD5");}catch(Exception e){}
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
							studentView.studentStart(currentUser, (Student) currentUser);
						else
							staffView.start(currentUser.isAdministrator(), currentUser);
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
				staffView.start(currentUser.isAdministrator(), currentUser);
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
	}
	
}
