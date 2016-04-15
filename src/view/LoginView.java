package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginView 
{
	public void start(Stage primaryStage)
	{
		Label lblUsername = new Label("User Name : ");
		TextField txtUsername = new TextField();
		Label lblPassword = new Label("Password : ");
		PasswordField txtPassword = new PasswordField();
		Label lblLoginStatus = new Label("Username or Password not Found!\nPlease try again.");
		Button btnLogin = new Button("Login");
		Button btnExit = new Button("Exit");
		//Set warning message to hidden by default
		lblLoginStatus.setVisible(false);
		lblLoginStatus.setTextFill(Color.rgb(255, 50, 50));
		//Create and populate containers to align GUI
		HBox hbxUser = new HBox();
		HBox hbxPassword = new HBox();
		HBox hbxButton = new HBox();
		hbxUser.getChildren().addAll(lblUsername, txtUsername);
		hbxPassword.getChildren().addAll(lblPassword, txtPassword);
		hbxButton.getChildren().addAll(btnExit, btnLogin);
		//Set Spacings
		hbxUser.setAlignment(Pos.CENTER);
		hbxPassword.setAlignment(Pos.CENTER);
		hbxButton.setAlignment(Pos.CENTER);
		hbxUser.setSpacing(10);
		hbxPassword.setSpacing(10);
		hbxButton.setSpacing(30);
		VBox pane = new VBox();
		//Set Action Events
		btnLogin.setOnAction(e ->
		{
			if(false/*Authenticate*/)
			{
				
			}
			else
			{
				if(lblLoginStatus.isVisible())
				{
					lblLoginStatus.setVisible(false);
					try
					{
						Thread.sleep(500);
					}
					catch(InterruptedException ex)
					{
						ex.printStackTrace();
					}
				}
				lblLoginStatus.setVisible(true);
			}
		});
		btnExit.setOnAction(e ->{
			primaryStage.close();
		});
		//Add horizontal panes in descending order of username, password, error message, and buttons
		pane.getChildren().addAll(hbxUser, hbxPassword, lblLoginStatus, hbxButton);
		Scene scene = new Scene(pane, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void staffView(Stage stage, boolean admin)
	{
		
	}
}
