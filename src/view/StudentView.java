package view;

import eventHandlers.BackEventObject;
import eventHandlers.BackListener;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import report.Course;
import report.CourseBag;
import user.Major;
import user.MajorBag;
import user.Student;
import user.User;

public class StudentView 
{
	private Stage primaryStage;
	PasswordListener listenerPassword;
	LogoutListener listenerLogout;
	BackListener listenerBack;
	String[] majorCache;
	public StudentView(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
	}
	public void studentStart(User user, Student student, String[] majors, CourseBag allCourses)
	{
		majorCache = majors;
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
		});
		Label lblMajor = new Label("Please select a major to continue : ");
		ComboBox<String> cmbMajor = new ComboBox<String>();
		for(int i = 0; i < majors.length; i++)
			cmbMajor.getItems().add(majors[i]);
		cmbMajor.setValue(student.getMajor().getName());
		HBox hbxInput = new HBox();
		hbxInput.getChildren().addAll(lblMajor, cmbMajor);
		hbxInput.setAlignment(Pos.CENTER);
		hbxInput.setSpacing(15);
		Button btnContinue = new Button("Continue");
		btnContinue.setOnAction(e->{
			Student tempStudent = student.clone();
			tempStudent.setMajor(MajorBag.getMajor(cmbMajor.getValue()));
			studentView(user, tempStudent, allCourses);
		});
		Button btnViewSAIN = new Button("View SAIN Report");
		btnViewSAIN.setOnAction(e->{
			studentView(user, student, allCourses);
		});
		HBox hbxButtons = new HBox();
		if(!user.isStudent())
		{
			Button btnBack = new Button("Back");
			hbxButtons.getChildren().addAll(btnBack);
			btnBack.setOnAction(e ->{
				BackEventObject ev = new BackEventObject(btnBack);
				if(listenerBack != null)
					listenerBack.back(ev);
			});
		}
		hbxButtons.getChildren().addAll(btnContinue, btnViewSAIN);
		hbxButtons.setAlignment(Pos.CENTER);
		hbxButtons.setSpacing(15);
		VBox pane = new VBox();
		pane.getChildren().addAll(hbxInput, hbxButtons);
		pane.setAlignment(Pos.CENTER);
		pane.setSpacing(15);
		Scene scene = new Scene(pane, 400, 400);
		primaryStage.setScene(scene);
		
	}
	public void studentView(User user, Student student, CourseBag allCourses)
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
		});
		Label lblSelectedMajor = new Label("Currently Selected Major is : " + student.getMajor());
		Label lblNumOfCredits = new Label("Number of Credits Attempted : " + student.numOfCredits());
		Label lblNumOfCreditsPass = new Label("Number of Credits Successfully Completed : " + student.numOfCreditsPassed());
		Label lblCoursePass = new Label("Courses Successfully Completed : ");
		ListView<Course> lstCoursesPassed = new ListView<Course>();
		Label lblMajorCoursesPass = new Label("Major Requirements Successfully Completed : ");
		ListView<Course> lstMajorCoursesPassed = new ListView<Course>();
		Label lblCourseFail = new Label("Courses Unsuccessfully Completed : ");
		ListView<Course> lstCoursesFailed = new ListView<Course>();
		Label lblCourseNeed = new Label("Courses Necessary for Graduation : ");
		ListView<Course> lstCoursesNeeded = new ListView<Course>();
		for(int i = 0; i <= student.getPassedCourses().length; i++)
			lstCoursesPassed.getItems().add(student.getPassedCourses()[i]);
		for(int i = 0; i <= student.getMajor().getMajorCoursesDone(student).length; i++)
			lstMajorCoursesPassed.getItems().add(student.getMajor().getMajorCoursesDone(student)[i]);
		for(int i = 0; i <= student.getFailedCourses().length; i++)
			lstCoursesFailed.getItems().add(student.getFailedCourses()[i]);
		for(int i = 0; i <= student.getMajor().getCoursesReq(student).length; i++)
			lstCoursesNeeded.getItems().add(student.getMajor().getCoursesReq(student)[i]);
		for(int i = 0; i < student.getCourseWork().length; i++)
		{
			if(student.getCourseWork()[i].isSuccessful())
				lstCoursesPassed.getItems().add(student.getCourseWork()[i]);
			else
				lstCoursesFailed.getItems().add(student.getCourseWork()[i]);
		}
		for(Course c : lstCoursesNeeded.getItems())
		{
			//Color code items
		}
		Label lblSemestersNeeded = new Label("A minimum of " + student.semestersNeeded(allCourses) + " semesters are necessary to complete this degree");
		Button btnBack = new Button("Back");
		btnBack.setOnAction(e->
		{
			if(user.isStudent())
				studentStart(user, student, majorCache, allCourses);
			else
			{
				BackEventObject ev = new BackEventObject(btnBack);
				if(listenerBack != null)
					listenerBack.back(ev);
			}
		});
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(lblSelectedMajor, 0, 0);
		grid.add(lblNumOfCredits, 0, 1);
		grid.add(lblNumOfCreditsPass, 0, 2);
		grid.add(lblCoursePass, 0, 3);
		grid.add(lstCoursesPassed, 0, 4);
		grid.add(lblMajorCoursesPass, 1, 3);
		grid.add(lstMajorCoursesPassed, 1, 4);
		grid.add(lblCourseFail, 2, 3);
		grid.add(lstCoursesFailed, 2, 4);
		grid.add(lblCourseNeed, 3, 3);
		grid.add(lstCoursesNeeded, 3, 4);
		grid.add(lblSemestersNeeded, 0, 5);
		grid.add(btnBack, 0, 6);
		Scene scene = new Scene(grid, 1920, 1080);
		primaryStage.setScene(scene);
		primaryStage.show();			
	}
	public void setListenerPassword(PasswordListener listenerPassword) 
	{
		this.listenerPassword = listenerPassword;
	}
	public void setListenerLogout(LogoutListener listenerLogout) 
	{
		this.listenerLogout = listenerLogout;
	}
	public void setListenerBack(BackListener listenerBack)
	{
		this.listenerBack = listenerBack;
	}
}
