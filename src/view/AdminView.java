package view;

import java.time.LocalDate;

import eventHandlers.CreateAccountEventObject;
import eventHandlers.CreateAccountListener;
import eventHandlers.DeleteCourseListener;
import eventHandlers.DeleteMajorEventObject;
import eventHandlers.DeleteMajorListener;
import eventHandlers.EditEventObject;
import eventHandlers.LogoutEventObject;
import eventHandlers.LogoutListener;
import eventHandlers.NewAccountEventObject;
import eventHandlers.NewAccountListener;
import eventHandlers.PasswordEventObject;
import eventHandlers.PasswordListener;
import eventHandlers.SearchListener;
import eventHandlers.DeleteCourseEventObject;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import user.Major;
import report.Course;
public class AdminView 
{
	Stage primaryStage;
	LogoutListener listenerLogout;
	PasswordListener listenerPassword;
	CreateAccountListener listenerAccount;
	DeleteMajorListener listenerMajorDelete;
	DeleteCourseListener listenerCourseDelete;
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
		btnCreateAccount.setOnAction(e->
		{
			Stage acctStage = new Stage();
			acctStage.setTitle("New Account");
			Button btnCancel = new Button("Cancel");
			Button btnStudent = new Button("New Student");
			Button btnFaculty = new Button("New Faculty");
			Button btnAdministrator = new Button("New Administrator");
			btnCancel.setOnAction(ea->{
				acctStage.close();
			});
			btnStudent.setOnAction(ea->{
				createAccount(user, false, true);
			});
			btnFaculty.setOnAction(ea->{
				createAccount(user, false, false);
			});
			btnAdministrator.setOnAction(ea->{
				createAccount(user, true, false);
			});
			HBox hbxButtons = new HBox();
			hbxButtons.getChildren().addAll(btnCancel, btnStudent, btnFaculty, btnAdministrator);
			hbxButtons.setAlignment(Pos.CENTER);
			Scene acctScene = new Scene(hbxButtons, 300, 100);
			acctStage.setScene(acctScene);
			acctStage.showAndWait();
		});
		btnSearchAdmin.setOnAction(e->
		{
			
		});
		btnSearchStudents.setOnAction(e->
		{
			
		});
		btnManageCourses.setOnAction(e->
		{
			Stage courseStage = new Stage();
			courseStage.setTitle("Manage Courses");
			Label lblCourses = new Label("Courses");
			ListView<Course> lstCourses = new ListView<Course>();
			for(Major m : MajorBag.getMajors()) {
				lstCourses.getItems().add(m);
			}	
			Button btnCancel = new Button("Close");
			Button btnSave = new Button("Save");
			Button btnDelete = new Button("Delete Course");
			Button btnAdd = new Button("Add Course");
			btnCancel.setOnAction(ea->{
				courseStage.close();
			});
			btnSave.setOnAction(ea->{
				createAccount(user, false, true);
			});
			btnDelete.setOnAction(ea->{
				try {
					DeleteCourseEventObject ev = new DeleteCourseEventObject(btnDelete, lstCourses.getSelectionModel().getSelectedItem());
					if(listenerCourseDelete != null) {
						listenerCourseDelete.delete(ev);
					}
				} catch(Exception ex) {
					
				}
			});
			btnAdd.setOnAction(ea->{
				createAccount(user, true, false);
			});
			HBox hbxButtons = new HBox();
			hbxButtons.getChildren().addAll(btnCancel, btnSave, btnDelete, btnAdd);
			hbxButtons.setAlignment(Pos.CENTER);
			Scene acctScene = new Scene(hbxButtons, 300, 100);
			courseStage.setScene(acctScene);
			courseStage.showAndWait();
		});
		btnManageMajors.setOnAction(e->
		{
			Stage courseStage = new Stage();
			courseStage.setTitle("Manage Majors");
			Label lblMajors = new Label("Majors");
			ListView<Major> lstMajors = new ListView<Major>();
			for(Major m : MajorBag.getMajors()) {
				lstMajors.getItems().add(m);
			}	
			Button btnCancel = new Button("Close");
			Button btnSave = new Button("Save");
			Button btnDelete = new Button("Delete Major");
			Button btnAdd = new Button("Add Major");
			btnCancel.setOnAction(ea->{
				courseStage.close();
			});
			btnSave.setOnAction(ea->{
				createAccount(user, false, true);
			});
			btnDelete.setOnAction(ea->{
				try {
					DeleteMajorEventObject ev = new DeleteMajorEventObject(btnDelete, lstMajors.getSelectionModel().getSelectedItem());
					if(listenerMajorDelete != null) {
						listenerMajorDelete.delete(ev);
					}
				} catch(Exception ex) {
					
				}
			});
			btnAdd.setOnAction(ea->{
				createAccount(user, true, false);
			});
			HBox hbxButtons = new HBox();
			hbxButtons.getChildren().addAll(btnCancel, btnSave, btnDelete, btnAdd);
			hbxButtons.setAlignment(Pos.CENTER);
			Scene acctScene = new Scene(hbxButtons, 300, 100);
			courseStage.setScene(acctScene);
			courseStage.showAndWait();
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
	public void createAccount(Administrator user, boolean admin, boolean student)
	{
		int line = 0;
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
		grid.add(lblID,  0, line);
		TextField txtID = new TextField();
		grid.add(txtID,  1, line);
		//txtID.setText(Integer.toString(student.getId()));
		txtID.setEditable(false);
		Label lblFirstName = new Label("First Name : ");
		grid.add(lblFirstName,  0, ++line);
		TextField txtFirstName = new TextField();
		grid.add(txtFirstName,  1, line);
		Label lblLastName = new Label("Last Name : ");
		grid.add(lblLastName,  0, ++line);
		TextField txtLastName = new TextField();
		grid.add(txtLastName,  1, line);
		Label lblAddress = new Label("Address : ");
		grid.add(lblAddress,  0, ++line);
		TextField txtAddress = new TextField();
		grid.add(txtAddress,  1, line);
		Label lblCity = new Label("City : ");
		grid.add(lblCity,  0, ++line);
		TextField txtCity = new TextField();
		grid.add(txtCity,  1, line);
		Label lblZipCode = new Label("Zip Code : ");
		grid.add(lblZipCode,  0, ++line);
		TextField txtZipCode = new TextField();
		grid.add(txtZipCode,  1, line);
		Label lblState = new Label("State : ");
		grid.add(lblState,  0, ++line);
		TextField txtState = new TextField();
		grid.add(txtState,  1, line);
		Label lblSSN = new Label("Social Security # : ");
		grid.add(lblSSN,  0, ++line);
		TextField txtSSN = new TextField();
		grid.add(txtSSN,  1, line);
		Label lblCampus = new Label("Campus : ");
		TextField txtCampus = new TextField();
		if(student) {
			grid.add(lblCampus,  0, ++line);
			grid.add(txtCampus,  1, line);
		}
		Label lblDateEnrolled = new Label("Date Enrolled : ");
		DatePicker dtpDateEnrolled = new DatePicker();
		if(student) {
			grid.add(lblDateEnrolled,  0, ++line);
			grid.add(dtpDateEnrolled,  1, line);
		}
		Label lblBirthDate = new Label("Birth Date : ");
		grid.add(lblBirthDate,  0, ++line);
		DatePicker dtpBirthDate = new DatePicker();
		grid.add(dtpBirthDate,  1, line);
		Label lblMajor = new Label("Major : ");
		ComboBox<Major> cmbMajor = new ComboBox<Major>();
		if(student) {
			grid.add(lblMajor,  0, ++line);	
			grid.add(cmbMajor,  1, line);
		}
		for(int i = 0; i < MajorBag.getMajors().length; i++)
			cmbMajor.getItems().add(MajorBag.getMajors()[i]);
		Label lblUsername = new Label("Username : ");
		grid.add(lblUsername,  0, ++line);
		TextField txtUsername = new TextField();
		grid.add(txtUsername,  1, line);
		Label lblPassword = new Label("Password : ");
		grid.add(lblPassword, 0, ++line);
		PasswordField txtPassword = new PasswordField();
		grid.add(txtPassword, 1, line);
		grid.setAlignment(Pos.CENTER);
		Button btnBack = new Button("Cancel");
		Button btnCreate = new Button();
		if(student) {
			btnCreate = new Button("Create Student");
		} else if (admin) {
			btnCreate = new Button("Create Administrator");
		} else {
			btnCreate = new Button("Create Faculty");
		}
		Button btnClear = new Button("Clear");
		HBox hbxButtons = new HBox();
		btnBack.setOnAction(e->{
			adminView(user);
		});
		hbxButtons.getChildren().addAll(btnBack, btnCreate, btnClear);
		btnCreate.setOnAction(e->{
			try
			{
				CreateAccountEventObject ev = new CreateAccountEventObject(btnCreate);
				if(student) {
					ev = new CreateAccountEventObject(btnCreate, txtID.getText(), txtUsername.getText(), txtPassword.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(), txtState.getText(), txtSSN.getText(), txtCampus.getText(), dtpDateEnrolled.getValue(), dtpBirthDate.getValue(), cmbMajor.getValue());
				} else if (admin) {
					ev = new CreateAccountEventObject(btnCreate, txtID.getText(), txtUsername.getText(), txtPassword.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(), txtState.getText(), txtSSN.getText(), dtpBirthDate.getValue(), true);
				} else {
					ev = new CreateAccountEventObject(btnCreate, txtID.getText(), txtUsername.getText(), txtPassword.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(), txtState.getText(), txtSSN.getText(), dtpBirthDate.getValue(), false);
				}
				if(listenerAccount != null)
					listenerAccount.createAccount(ev);
				if(!ev.isValidAccount())
				{
					Alert alert = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
					alert.showAndWait();
				}
				else
				{
					Alert alert = new Alert(AlertType.INFORMATION, "Account successfully created. ID# : " + ev.getId(), ButtonType.OK);
					alert.showAndWait();
				}
			}
			catch(Exception ex)
			{
				
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
	public void setListenerNewAccount(CreateAccountListener listenerAccount)
	{
		this.listenerAccount = listenerAccount;
	}
	public void setListenerMajorDelete(DeleteMajorListener listenerMajorDelete) {
		this.listenerMajorDelete = listenerMajorDelete;
	}
	public void setListenerCourseDelete(DeleteCourseListener listenerCourseDelete) {
		this.listenerCourseDelete = listenerCourseDelete;
	}
	
}
