package demo;

import javafx.application.Application;
import javafx.stage.Stage;
import user.Administrator;
import user.User;
import view.LoginView;

public class Demo extends Application 
{
	@Override
	public void start(Stage primaryStage)
	{
		LoginView view = new LoginView(primaryStage);
		view.staffView(true, new Administrator("Christopher", "Hayes"));
	}
}
