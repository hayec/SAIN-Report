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
import user.User;

import java.util.Optional;

import eventHandlers.*;

public class LoginView 
{
	SearchListener listenerSearch;
	LogoutListener listenerLogout;
	PasswordListener listenerPassword;
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
	@SuppressWarnings("unchecked")
	public void staffView(boolean admin, User user)
	{
		Label lblLogoutName = new Label("You are currently signed in as " + user.getName() + " : ");
		Hyperlink hplChangePass = new Hyperlink("Change Password");
		hplChangePass.setOnAction(e->
		{
			Stage passStage = new Stage();
			passStage.setTitle("Change Password");
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			//grid.setPadding(new Insets(20, 150, 10, 10));
			PasswordField oldPassword = new PasswordField();
			oldPassword.setPromptText("Old Password");
			PasswordField newPassword = new PasswordField();
			newPassword.setPromptText("New Password");
			PasswordField newPasswordConf = new PasswordField();
			newPasswordConf.setPromptText("Confim New Password");
			grid.add(new Label("Old Password: "), 0, 0);
			grid.add(oldPassword, 1, 0); 
			grid.add(new Label("New Password: "), 0, 1);
			grid.add(newPassword, 1, 1);
			grid.add(new Label("Confirm New Password: "), 0, 2);
			grid.add(newPasswordConf, 1, 2);
			grid.setAlignment(Pos.CENTER);
			Button btnCancel = new Button("Cancel");
			btnCancel.setOnAction(ea->
			{
				passStage.close();
			});
			Button btnChange = new Button("Change Password");
			btnChange.setOnAction(ea->
			{
				PasswordEventObject ev = new PasswordEventObject(btnChange, oldPassword.getText(), newPassword.getText(), newPasswordConf.getText());
				if(listenerPassword != null)
				{
					listenerPassword.changePassword(ev);
					if(ev.isOldPassSuccessful() && ev.isPassMatch())
					{
						Alert alert = new Alert(AlertType.INFORMATION, "Password Successfully Changed.", ButtonType.OK);
						alert.showAndWait();
						passStage.close();
					}
					else if(!ev.isOldPassSuccessful())
					{
						Alert alert = new Alert(AlertType.ERROR, "Error, old password is incorrect.", ButtonType.OK);
						alert.showAndWait();
					}
					else
					{
						Alert alert = new Alert(AlertType.ERROR, "Error, new passwords do not match.", ButtonType.OK);
						alert.showAndWait();
					}
				}
			});
			HBox hbxPassButtons = new HBox();
			hbxPassButtons.getChildren().addAll(btnCancel, btnChange);
			hbxPassButtons.setSpacing(20);
			hbxPassButtons.setAlignment(Pos.CENTER);
			VBox passPane = new VBox();
			passPane.getChildren().addAll(grid, hbxPassButtons);
			passPane.setSpacing(20);
			passPane.setAlignment(Pos.CENTER);
			Scene passScene = new Scene(passPane, 550, 300);	
			passStage.setScene(passScene);
			passStage.showAndWait();
		});
		Hyperlink hplLogout = new Hyperlink("Logout");
		hplLogout.setOnAction(e->{
			LogoutEventObject ev = new LogoutEventObject(hplLogout);
			if(listenerLogout != null)
				listenerLogout.logout(ev);
		});
		HBox hbxAccount = new HBox();
		hbxAccount.getChildren().addAll(lblLogoutName, hplChangePass, hplLogout);
		hbxAccount.setAlignment(Pos.TOP_RIGHT);
		Label lblID = new Label("ID # : ");
		TextField txtID = new TextField();
		Label lblFirstName = new Label("First Name : ");
		TextField txtFirstName = new TextField();
		Label lblLastName = new Label("Last Name : ");
		TextField txtLastName = new TextField();
		Label lblAddress = new Label("Address : ");
		TextField txtAddress = new TextField();
		Label lblCity = new Label("City : ");
		TextField txtCity = new TextField();
		Label lblZipCode = new Label("Zip Code : ");
		TextField txtZipCode = new TextField();
		Label lblState = new Label("State : ");
		TextField txtState = new TextField();
		Label lblGPA = new Label("GPA : ");
		TextField txtGPA = new TextField();
		Label lblSSN = new Label("Social Security # : ");
		TextField txtSSN = new TextField();
		Label lblYearEnrolled = new Label("Year Enrolled : ");
		TextField txtYearEnrolled = new TextField();
		Label lblBirthYear = new Label("Year of Birth : ");
		TextField txtBirthYear = new TextField();
		Label lblMajor = new Label("Major : ");
		ComboBox<String> cmbMajor = new ComboBox<String>();
		Label lblStudents = new Label("Results : ");
		ListView<String> lstStudents = new ListView<String>();
		lstStudents.setMaxHeight(300);
		lstStudents.setMaxWidth(600);
		VBox vbxResults = new VBox();
		vbxResults.getChildren().addAll(lblStudents, lstStudents);
		vbxResults.setAlignment(Pos.CENTER);
		Button btnSearch = new Button("Search");
		Button btnSelect = new Button("Select Student");
		Button btnClear = new Button("Clear");
		Button btnEdit = new Button("Edit Student Data");
		HBox hbxButtons = new HBox();
		if(admin)
			hbxButtons.getChildren().addAll(btnClear, btnSearch, btnSelect, btnEdit);
		else
			hbxButtons.getChildren().addAll(btnClear, btnSearch, btnSelect);
		VBox vbxLabel = new VBox();
		vbxLabel.getChildren().addAll(lblID, lblFirstName, lblLastName, lblAddress, lblCity, lblZipCode, lblState, lblGPA, lblSSN, lblYearEnrolled, lblBirthYear, lblMajor);
		vbxLabel.setAlignment(Pos.CENTER);
		vbxLabel.setSpacing(30);
		VBox vbxText = new VBox();
		vbxText.getChildren().addAll(txtID, txtFirstName, txtLastName, txtAddress, txtCity, txtZipCode, txtState, txtGPA, txtSSN, txtYearEnrolled, txtBirthYear, cmbMajor);
		vbxText.setAlignment(Pos.CENTER);
		vbxText.setSpacing(15);
		HBox hbxInput = new HBox();
		hbxInput.getChildren().addAll(vbxLabel, vbxText);
		if(!admin)//Don't allow faculty to edit student data
			btnEdit.setVisible(false);
		btnSearch.setOnAction(e->{
			SearchEventObject ev = new SearchEventObject(btnSearch, Integer.parseInt(txtID.getText()), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtCity.getText(), txtState.getText(), Integer.parseInt(txtZipCode.getText()), txtSSN.getText(), Integer.parseInt(txtYearEnrolled.getText()), Integer.parseInt(txtBirthYear.getText()), cmbMajor.getValue(), Double.parseDouble(txtGPA.getText()));
			if(listenerSearch != null)
			{
				listenerSearch.search(ev);
				if(ev.isInputValid())
				{
					if(ev.getStudentResults().length == 0)
					{
						Alert alert = new Alert(AlertType.INFORMATION, "No students were found with the specified search terms.", ButtonType.OK);
						alert.showAndWait();
					}
					else
					{
						for(int i = 0; i < ev.getStudentResults().length; i++)
							lstStudents.getItems().add(ev.getStudentResults()[i].getLastName() + ", " + ev.getStudentResults()[i].getFirstName() + " " + ev.getStudentResults()[i].getId());
					}
				}
				else
				{
					Alert alert = new Alert(AlertType.ERROR, "Error!  Invalid input detected!  Please check input validity, then try again.", ButtonType.OK);
					alert.showAndWait();
				}
			}
		});
		btnClear.setOnAction(e->{
			txtFirstName.clear();
			txtLastName.clear();
			txtAddress.clear();
			txtCity.clear();
			txtZipCode.clear();
			txtState.clear();
			txtGPA.clear();
			txtSSN.clear();
			txtYearEnrolled.clear();
			txtBirthYear.clear();
			cmbMajor.setValue(null);
			lstStudents.getItems().removeAll();
		});
		VBox pane = new VBox();
		pane.getChildren().addAll(hbxAccount, hbxInput, vbxResults);
		pane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane, 800, 1100);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SAIN Report");
		primaryStage.show();
	}
	public void setListenerPassword(PasswordListener listenerPassword) 
	{
		this.listenerPassword = listenerPassword;
	}
	public void studentView()
	{
		
	}
	public void setListenerSearch(SearchListener listenerSearch) 
	{
		this.listenerSearch = listenerSearch;
	}
	public void setListenerLogout(LogoutListener logoutListener) 
	{
		this.listenerLogout = logoutListener;
	}
}
