package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import report.Course;
import user.Major;
import user.Student;
import user.User;

import java.util.Optional;

import eventHandlers.*;

public class LoginView 
{
	LoginListener listenerLogin;
	Stage primaryStage;
	public LoginView(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
	}
	public void start()
	{
		Label lblUsername = new Label("User Name : ");
		TextField txtUsername = new TextField();
		Label lblPassword = new Label("Password : ");
		PasswordField txtPassword = new PasswordField();
		txtPassword.setPromptText("Password");
		Label lblLoginStatus = new Label("Username or Password not Found!\nPlease try again.");
		Button btnLogin = new Button("Login");
		Button btnExit = new Button("Exit");
		//Set warning message to hidden by default
		lblLoginStatus.setVisible(false);
		lblLoginStatus.setTextFill(Color.rgb(255, 50, 50));
		//Create and populate containers to align GUI
		VBox hbxLabel = new VBox();
		VBox hbxText = new VBox();
		HBox hbxButton = new HBox();
		hbxLabel.getChildren().addAll(lblUsername, lblPassword);
		hbxText.getChildren().addAll(txtUsername, txtPassword);
		hbxButton.getChildren().addAll(btnExit, btnLogin);
		//Set Spacings
		hbxLabel.setAlignment(Pos.CENTER);
		hbxText.setAlignment(Pos.CENTER);
		hbxButton.setAlignment(Pos.CENTER);
		hbxLabel.setSpacing(10);
		hbxText.setSpacing(10);
		hbxButton.setSpacing(30);
		HBox hbxInput = new HBox();
		hbxInput.getChildren().addAll(hbxLabel, hbxText);
		hbxInput.setAlignment(Pos.CENTER);
		VBox pane = new VBox();
		//Set Action Events
		btnLogin.setOnAction(e ->
		{
			LoginEventObject ev = new LoginEventObject(btnLogin, txtUsername.getText(), txtPassword.getText());
			if(!ev.isCredentialsValid())
			{
				if(lblLoginStatus.isVisible())
				{
					lblLoginStatus.setVisible(false);
					try
					{
						Thread.sleep(2000);
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
		pane.getChildren().addAll(hbxInput, lblLoginStatus, hbxButton);
		pane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane, 500, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void setListenerLogin(LoginListener listenerLogin)
	{
		this.listenerLogin = listenerLogin;
	}
}
