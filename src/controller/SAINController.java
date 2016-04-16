package controller;

import eventHandlers.LogoutEventObject;
import eventHandlers.LogoutListener;
import eventHandlers.SearchEventObject;
import eventHandlers.SearchListener;
import javafx.stage.Stage;
import user.Major;
import user.User;
import user.UserBag;
import view.LoginView;

public class SAINController
{
	Stage primaryStage = new Stage();
	LoginView view = new LoginView(primaryStage);
	User currentUser;
	UserBag users = new UserBag();
	public SAINController()
	{
		view.setListenerSearch(new SearchListener()
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
		view.setListenerLogout(new LogoutListener()
		{
			@Override
			public void logout(LogoutEventObject ev)
			{
				currentUser = null;
				view.start();
			}
		});
	}
	
}
