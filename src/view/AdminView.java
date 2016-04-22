package view;

import java.time.LocalDate;

import eventHandlers.EditEventObject;
import eventHandlers.LogoutEventObject;
import eventHandlers.LogoutListener;
import eventHandlers.PasswordEventObject;
import eventHandlers.PasswordListener;
import eventHandlers.SearchListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Administrator;
import user.MajorBag;
import user.Student;

public class AdminView 
{
	Stage primaryStage;
	LogoutListener listenerLogout;
	PasswordListener listenerPassword;
	public AdminView(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
	}
	public void adminView(Administrator user)
	{
		Label lblLogoutName = new Label("You are currently signed in as " + user.getName() + " : ");
		Hyperlink hplChangePass = new Hyperlink("Change Password");
		hplChangePass.setOnAction(e->
		{
			Stage passStage = new Stage();
			passStage.setTitle("Change Password");
			GridPane gridOut = new GridPane();
			gridOut.setHgap(10);
			gridOut.setVgap(10);
			PasswordField oldPassword = new PasswordField();
			oldPassword.setPromptText("Old Password");
			PasswordField newPassword = new PasswordField();
			newPassword.setPromptText("New Password");
			PasswordField newPasswordConf = new PasswordField();
			newPasswordConf.setPromptText("Confim New Password");
			gridOut.add(new Label("Old Password: "), 0, 0);
			gridOut.add(oldPassword, 1, 0); 
			gridOut.add(new Label("New Password: "), 0, 1);
			gridOut.add(newPassword, 1, 1);
			gridOut.add(new Label("Confirm New Password: "), 0, 2);
			gridOut.add(newPasswordConf, 1, 2);
			gridOut.setAlignment(Pos.CENTER);
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
			passPane.getChildren().addAll(gridOut, hbxPassButtons);
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
		Button btnSearchStudents = new Button("Search Students");
		Button btnSearchAdmin = new Button("Search Staff");
		Button btnCreateAccount = new Button("Create Account");
		Button btnManageCourses = new Button("Manage Courses");
		Button btnManageMajors = new Button("Manage Majors");
		btnSearchStudents.setOnAction(e->
		{
			
		});
		btnSearchAdmin.setOnAction(e->
		{
			
		});
		btnCreateAccount.setOnAction(e->
		{
			
		});
		btnManageCourses.setOnAction(e->
		{
			
		});
		btnManageMajors.setOnAction(e->
		{
			
		});
		HBox hbxButtons = new HBox();
		VBox pane = new VBox();
		HBox hbxLogout = new HBox();
		hbxLogout.getChildren().addAll(lblLogoutName, hplChangePass, hplLogout);
		pane.getChildren().addAll(hbxLogout, hbxButtons);
		hbxButtons.setAlignment(Pos.CENTER);
		hbxButtons.setSpacing(20);
		hbxLogout.setAlignment(Pos.TOP_RIGHT);
		hbxLogout.setSpacing(20);
		pane.setAlignment(Pos.CENTER);
		pane.setSpacing(50);
		Scene scene = new Scene(pane, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void createAccount(Administrator user)
	{
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		Label lblLogoutName = new Label("You are currently signed in as " + user.getName() + " : ");
		Hyperlink hplChangePass = new Hyperlink("Change Password");
		hplChangePass.setOnAction(e->
		{
			Stage passStage = new Stage();
			passStage.setTitle("Change Password");
			GridPane gridOut = new GridPane();
			gridOut.setHgap(10);
			gridOut.setVgap(10);
			//grid.setPadding(new Insets(20, 150, 10, 10));
			PasswordField oldPassword = new PasswordField();
			oldPassword.setPromptText("Old Password");
			PasswordField newPassword = new PasswordField();
			newPassword.setPromptText("New Password");
			PasswordField newPasswordConf = new PasswordField();
			newPasswordConf.setPromptText("Confim New Password");
			gridOut.add(new Label("Old Password: "), 0, 0);
			gridOut.add(oldPassword, 1, 0); 
			gridOut.add(new Label("New Password: "), 0, 1);
			gridOut.add(newPassword, 1, 1);
			gridOut.add(new Label("Confirm New Password: "), 0, 2);
			gridOut.add(newPasswordConf, 1, 2);
			gridOut.setAlignment(Pos.CENTER);
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
			passPane.getChildren().addAll(gridOut, hbxPassButtons);
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
		grid.add(lblID,  0, 0);
		TextField txtID = new TextField();
		grid.add(txtID,  1, 0);
		txtID.setText(Integer.toString(student.getId()));
		txtID.setEditable(false);
		Label lblFirstName = new Label("First Name : ");
		grid.add(lblFirstName,  0, 1);
		TextField txtFirstName = new TextField();
		grid.add(txtFirstName,  1, 1);
		Label lblLastName = new Label("Last Name : ");
		grid.add(lblLastName,  0, 2);
		TextField txtLastName = new TextField();
		grid.add(txtLastName,  1, 2);
		Label lblAddress = new Label("Address : ");
		grid.add(lblAddress,  0, 3);
		TextField txtAddress = new TextField();
		grid.add(txtAddress,  1, 3);
		Label lblCity = new Label("City : ");
		grid.add(lblCity,  0, 4);
		TextField txtCity = new TextField();
		grid.add(txtCity,  1, 4);
		Label lblZipCode = new Label("Zip Code : ");
		grid.add(lblZipCode,  0, 5);
		TextField txtZipCode = new TextField();
		grid.add(txtZipCode,  1, 5);
		Label lblState = new Label("State : ");
		grid.add(lblState,  0, 6);
		TextField txtState = new TextField();
		grid.add(txtState,  1, 6);
		Label lblSSN = new Label("Social Security # : ");
		grid.add(lblSSN,  0, 8);
		TextField txtSSN = new TextField();
		grid.add(txtSSN,  1, 8);
		Label lblCampus = new Label("Campus : ");
		grid.add(lblCampus,  0, 9);
		TextField txtCampus = new TextField();
		grid.add(txtCampus,  1, 9);
		Label lblDateEnrolled = new Label("Date Enrolled : ");
		grid.add(lblDateEnrolled,  0, 10);
		DatePicker dtpDateEnrolled = new DatePicker();
		grid.add(dtpDateEnrolled,  1, 10);
		Label lblBirthDate = new Label("Birth Date : ");
		grid.add(lblBirthDate,  0, 11);
		DatePicker dtpBirthDate = new DatePicker();
		grid.add(dtpBirthDate,  1, 11);
		Label lblMajor = new Label("Major : ");
		grid.add(lblMajor,  0, 12);
		ComboBox<String> cmbMajor = new ComboBox<String>();
		grid.add(cmbMajor,  1, 12);
		for(int i = 0; i < majors.length; i++)
			cmbMajor.getItems().add(majors[i]);
		Label lblPassword = new Label("Password : ");
		grid.add(lblPassword, 0, 13);
		PasswordField txtPassword = new PasswordField();
		grid.add(txtPassword, 1, 13);
		grid.setAlignment(Pos.CENTER);
		Button btnBack = new Button("Cancel");
		Button btnEdit = new Button("Edit Student Data");
		Button btnClear = new Button("Clear");
		HBox hbxButtons = new HBox();
		btnBack.setOnAction(e->{
			start(true, user, majors);
		});
		hbxButtons.getChildren().addAll(btnBack, btnEdit, btnClear);
		btnEdit.setOnAction(e->{
			try
			{
				if(Integer.parseInt(txtZipCode.getText()) > 99999 || Integer.parseInt(txtZipCode.getText()) < 0)
					throw new IllegalArgumentException();
				EditEventObject ev = new EditEventObject(btnEdit, new Student(Integer.parseInt(txtID.getText()), txtFirstName.getText(), txtLastName.getText(), dtpDateEnrolled.getValue(), dtpBirthDate.getValue(), txtSSN.getText(), txtAddress.getText(), txtCity.getText(), Integer.parseInt(txtZipCode.getText()), txtCampus.getText(), MajorBag.getMajor(cmbMajor.getValue())));
				if(listenerEdit != null)
					listenerEdit.edit(ev);
			}
			catch(IllegalArgumentException ie)
			{
				Alert alert = new Alert(AlertType.ERROR, "Error, zip code is invalid.", ButtonType.OK);
				alert.showAndWait();
			}
		});
		btnClear.setOnAction(e->{
			txtFirstName.clear();
			txtLastName.clear();
			txtAddress.clear();
			txtCity.clear();
			txtZipCode.clear();
			txtState.clear();
			txtSSN.clear();
			dtpDateEnrolled.setValue(LocalDate.now());
			dtpBirthDate.setValue(LocalDate.now());
			cmbMajor.setValue(null);
		});
		VBox pane = new VBox();
		pane.getChildren().addAll(hbxAccount, grid, hbxButtons);
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
	public void setListenerLogout(LogoutListener logoutListener) 
	{
		this.listenerLogout = logoutListener;
	}
}
