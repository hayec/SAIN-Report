package view;

import java.time.LocalDate;
import eventHandlers.BackEventObject;
import eventHandlers.BackListener;
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
import eventHandlers.AddMajorListener;
import eventHandlers.AddCourseEventObject;
import eventHandlers.AddCourseListener;
import eventHandlers.AddMajorEventObject;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
	AddMajorListener listenerMajorAdd;
	AddCourseListener listenerCourseAdd;
	DeleteMajorListener listenerMajorDelete;
	DeleteCourseListener listenerCourseDelete;
	BackListener listenerStudentSearch;
	Major[] majors;
	Course[] courses;
	public AdminView(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
	}
	public void adminView(Administrator user, Major[] majorsIn, Course[] coursesIn)
	{
		majors = majorsIn;
		courses = coursesIn;
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
				createAccount(user, false, true, majors, courses);
			});
			btnFaculty.setOnAction(ea->{
				createAccount(user, false, false, majors, courses);
			});
			btnAdministrator.setOnAction(ea->{
				createAccount(user, true, false, majors, courses);
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
			BackEventObject ev = new BackEventObject(btnSearchStudents);
			if(listenerStudentSearch != null) {
				listenerStudentSearch.back(ev);
			}
		});
		btnManageCourses.setOnAction(e->
		{
			Stage courseStage = new Stage();
			courseStage.setTitle("Manage Courses");
			Label lblCourses = new Label("Courses");
			ListView<Course> lstCourses = new ListView<Course>();
			for(Course c : courses) {
				lstCourses.getItems().add(c);
			}	
			Button btnCancel = new Button("Close");
			Button btnDelete = new Button("Delete Course");
			Button btnAdd = new Button("Add Course");
			btnCancel.setOnAction(ea->{
				courseStage.close();
			});
			btnDelete.setOnAction(ea->{
				DeleteCourseEventObject ev = new DeleteCourseEventObject(btnDelete, lstCourses.getSelectionModel().getSelectedItem());
				if(listenerCourseDelete != null) {
					listenerCourseDelete.delete(ev);
					lstCourses.getItems().remove(lstCourses.getSelectionModel().getSelectedIndex());
					courses = ev.getCourses();
				}
			});
			btnAdd.setOnAction(ea->{
				Stage newCourseStage = new Stage();
				newCourseStage.setTitle("Add Course");
				GridPane gridOut = new GridPane();
				gridOut.setHgap(10);
				gridOut.setVgap(10);
				Label lblCourseCode = new Label("Course Code : ");
				Label lblCourseTitle = new Label("Course Title : ");
				Label lblCourseDescription = new Label("Course Description : ");
				Label lblCredits = new Label("Credits : ");
				TextField txtCourseCode = new TextField();
				TextField txtCourseTitle = new TextField();
				TextField txtCourseDescription = new TextField();
				TextField txtCredits = new TextField();
				gridOut.add(lblCourseCode, 0, 0);
				gridOut.add(lblCourseTitle, 0, 1);
				gridOut.add(lblCourseDescription, 0, 2);
				gridOut.add(lblCredits, 0, 3);
				gridOut.add(txtCourseCode, 1, 0);
				gridOut.add(txtCourseTitle, 1, 1);
				gridOut.add(txtCourseDescription, 1, 2);
				gridOut.add(txtCredits, 1, 3);		
				Label lblCampus = new Label("Campus : \n");
				CheckBox chkAmmerman = new CheckBox("Ammerman : ");
				CheckBox chkGrant = new CheckBox("Grant : ");
				CheckBox chkEastern = new CheckBox("Eastern : ");
				gridOut.add(lblCampus, 0, 4);
				gridOut.add(chkAmmerman, 0, 5);
				gridOut.add(chkGrant, 0, 6);
				gridOut.add(chkEastern, 0, 7);			
				Label lblAttr = new Label("Attributes : ");
				CheckBox chkPhysEd = new CheckBox("Physical Education : ");
				CheckBox chkHistory = new CheckBox("History : ");
				CheckBox chkLabScience = new CheckBox("Laboratory Science : ");
				CheckBox chkMath = new CheckBox("Mathematics : ");
				CheckBox chkHum = new CheckBox("Humanities : ");
				CheckBox chkBus = new CheckBox("Business : ");
				CheckBox chkEng = new CheckBox("English : ");
				CheckBox chkCom = new CheckBox("Communications : ");
				CheckBox chkAmerHis = new CheckBox("American History : ");
				CheckBox chkSocSci = new CheckBox("Social Science : ");
				CheckBox chkLang = new CheckBox("Language : ");
				CheckBox chkPhl = new CheckBox("Philosophy : ");
				gridOut.add(lblAttr, 0, 8);
				gridOut.add(chkPhysEd, 0, 9);
				gridOut.add(chkHistory, 0, 10);
				gridOut.add(chkLabScience, 0, 11);
				gridOut.add(chkMath, 0, 12);
				gridOut.add(chkHum, 0, 13);
				gridOut.add(chkBus, 0, 14);
				gridOut.add(chkEng, 0, 15);
				gridOut.add(chkCom, 0, 16);
				gridOut.add(chkAmerHis, 0, 17);
				gridOut.add(chkSocSci, 0, 18);
				gridOut.add(chkLang, 0, 19);
				gridOut.add(chkPhl, 0, 20);				
				Label lblCourse = new Label("Courses : ");
				ListView<Course> lstCourse = new ListView<Course>();
				Label lblRightArrow = new Label("-->");
				Button btnAddCourse = new Button("Add Course");
				Label lblLeftArrow = new Label("<--");
				Button btnRemoveCourse = new Button("Remove Course");
				Label lblCoursesReq = new Label("Corequisite Courses");
				ListView<Course> lstCoursesReq = new ListView<Course>();
				VBox vbxCourseButtons = new VBox();
				vbxCourseButtons.getChildren().addAll(lblRightArrow, btnAddCourse, lblLeftArrow, btnRemoveCourse);
				VBox vbxCourses = new VBox();
				vbxCourses.getChildren().addAll(lblCourse, lstCourse);
				VBox vbxCoursesReq = new VBox();
				vbxCoursesReq.getChildren().addAll(lblCoursesReq, lstCoursesReq);
				HBox hbxCourses = new HBox();
				hbxCourses.getChildren().addAll(vbxCourses, vbxCourseButtons, vbxCoursesReq);
				hbxCourses.setAlignment(Pos.CENTER);
				hbxCourses.setSpacing(30);
				Label lblCoursesA = new Label("Courses");
				ListView<Course> lstCoursesA = new ListView<Course>();
				Label lblRightArrowA = new Label("-->");
				Button btnAddCourseA = new Button("Add Course");
				Label lblLeftArrowA = new Label("<--");
				Button btnRemoveCourseA = new Button("Remove Course");
				Label lblCoursesReqA = new Label("Prerequisite Courses");
				ListView<Course> lstCoursesReqA = new ListView<Course>();
				VBox vbxCourseButtonsA = new VBox();
				vbxCourseButtons.getChildren().addAll(lblRightArrowA, btnAddCourseA, lblLeftArrowA, btnRemoveCourseA);
				VBox vbxCoursesA = new VBox();
				vbxCourses.getChildren().addAll(lblCoursesA, lstCoursesA);
				VBox vbxCoursesReqA = new VBox();
				vbxCoursesReq.getChildren().addAll(lblCoursesReqA, lstCoursesReqA);
				HBox hbxCoursesA = new HBox();
				hbxCoursesA.getChildren().addAll(vbxCoursesA, vbxCourseButtonsA, vbxCoursesReqA);
				hbxCoursesA.setAlignment(Pos.CENTER);
				hbxCoursesA.setSpacing(30);
				VBox vbxReqs = new VBox();
				vbxReqs.getChildren().addAll(hbxCourses, hbxCoursesA);
				vbxReqs.setSpacing(30);
				vbxReqs.setAlignment(Pos.CENTER);
				Button btnCancelThis = new Button("Cancel");
				Button btnAddThis = new Button("Add Course");
				HBox hbxButtons = new HBox();
				hbxButtons.getChildren().addAll(btnCancelThis, btnAddThis);
				btnCancelThis.setOnAction(eac->{
					newCourseStage.close();
				});
				btnAddThis.setOnAction(eac->{
					AddCourseEventObject ev = new AddCourseEventObject(btnAddThis, txtCourseCode.getText(), txtCourseTitle.getText(), txtCourseDescription.getText(),
							chkAmmerman.isSelected(), chkGrant.isSelected(), chkEastern.isSelected(), lstCourse.getItems().toArray(new String[lstCourse.getItems().size()]), 
							lstCoursesA.getItems().toArray(new String[lstCoursesA.getItems().size()]), txtCredits.getText(), chkPhysEd.isSelected(), chkHistory.isSelected(), 
							chkLabScience.isSelected(), chkMath.isSelected(), chkHum.isSelected(), chkBus.isSelected(), chkEng.isSelected(), chkCom.isSelected(), 
							chkAmerHis.isSelected(), chkSocSci.isSelected(), chkLang.isSelected(), chkPhl.isSelected());
					if(listenerCourseAdd != null) {
						listenerCourseAdd.add(ev);
					}
					if(!ev.isValid()) {
						Alert alert = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
						alert.showAndWait();
					} else {
						courses = ev.getCourses();
						courseStage.close();
					}
				});
				VBox pane = new VBox();
				pane.getChildren().addAll(gridOut, vbxReqs, hbxButtons);
				Scene scene = new Scene(pane, 600, 800);
				newCourseStage.setScene(scene);
				newCourseStage.showAndWait();
			});
			HBox hbxButtons = new HBox();
			hbxButtons.getChildren().addAll(btnCancel, btnDelete, btnAdd);
			hbxButtons.setAlignment(Pos.CENTER);
			Scene acctScene = new Scene(hbxButtons, 300, 100);
			courseStage.setScene(acctScene);
			courseStage.showAndWait();
		});
		btnManageMajors.setOnAction(e->
		{
			Stage majorStage = new Stage();
			majorStage.setTitle("Manage Majors");
			Label lblMajors = new Label("Majors");
			ListView<Major> lstMajors = new ListView<Major>();
			for(Major m : MajorBag.getMajors()) {
				lstMajors.getItems().add(m);
			}	
			Button btnCancel = new Button("Close");
			Button btnDelete = new Button("Delete Major");
			Button btnAdd = new Button("Add Major");
			btnCancel.setOnAction(ea->{
				majorStage.close();
			});
			btnDelete.setOnAction(ea->{
				DeleteMajorEventObject ev = new DeleteMajorEventObject(btnDelete, lstMajors.getSelectionModel().getSelectedItem());
				if(listenerMajorDelete != null) {
					listenerMajorDelete.delete(ev);
					lstMajors.getItems().remove(lstMajors.getSelectionModel().getSelectedIndex());
					majors = ev.getMajors();					
				}
			});
			btnAdd.setOnAction(ea->{
				Stage newMajorStage = new Stage();
				newMajorStage.setTitle("Add Major");
				GridPane gridOut = new GridPane();
				gridOut.setHgap(10);
				gridOut.setVgap(10);
				Label lblRequirements = new Label("Requirements : ");
				Label lblName = new Label("Name : ");
				Label lblMinGPA = new Label("Minimum GPA : ");
				Label lblNumOfCredits = new Label("Number of Credits : ");
				Label lblPhysEd = new Label("Physical Education Courses : ");
				Label lblHis = new Label("History Courses : ");
				Label lblLabSci = new Label("Laboratory Science Courses : ");
				Label lblMath = new Label("Mathematics Courses : ");
				Label lblHum = new Label("Humanities Courses : ");
				Label lblBus = new Label("Business Courses : ");
				Label lblEng = new Label("English Courses : ");
				Label lblCom = new Label("Communications Courses : ");
				Label lblAmerHis = new Label("American History Courses : ");
				Label lblSocSci = new Label("Social Science Courses : ");
				Label lblLang = new Label("Foreign Language Courses : ");
				Label lblPhl = new Label("Philsophy Courses : ");
				TextField txtName = new TextField();
				TextField txtMinGPA = new TextField();
				TextField txtNumOfCredits = new TextField();
				TextField txtPhysEd = new TextField();
				TextField txtHis = new TextField();
				TextField txtLabSci = new TextField();
				TextField txtMath = new TextField();
				TextField txtHum = new TextField();
				TextField txtBus = new TextField();
				TextField txtEng = new TextField();
				TextField txtCom = new TextField();
				TextField txtAmerHis = new TextField();
				TextField txtSocSci = new TextField();
				TextField txtLang = new TextField();
				TextField txtPhl = new TextField();
				gridOut.add(lblName, 0, 0);
				gridOut.add(lblMinGPA, 0, 1);
				gridOut.add(lblNumOfCredits, 0, 2);
				gridOut.add(lblPhysEd, 0, 3);
				gridOut.add(lblHis, 0, 4);
				gridOut.add(lblLabSci, 0, 5);
				gridOut.add(lblMath, 0, 6);
				gridOut.add(lblHum, 0, 7);
				gridOut.add(lblBus, 0, 8);
				gridOut.add(lblEng, 0, 9);
				gridOut.add(lblCom, 0, 10);
				gridOut.add(lblAmerHis, 0, 11);
				gridOut.add(lblSocSci, 0, 12);
				gridOut.add(lblLang, 0, 13);
				gridOut.add(lblPhl, 0, 14);
				gridOut.add(txtName, 1, 0);
				gridOut.add(txtMinGPA, 1, 1);
				gridOut.add(txtNumOfCredits, 1, 2);
				gridOut.add(txtPhysEd, 1, 3);
				gridOut.add(txtHis, 1, 4);
				gridOut.add(txtLabSci, 1, 5);
				gridOut.add(txtMath, 1, 6);
				gridOut.add(txtHum, 1, 7);
				gridOut.add(txtBus, 1, 8);
				gridOut.add(txtEng, 1, 9);
				gridOut.add(txtCom, 1, 11);
				gridOut.add(txtAmerHis, 1, 11);
				gridOut.add(txtSocSci, 1, 12);
				gridOut.add(txtLang, 1, 13);
				gridOut.add(txtPhl, 1, 14);
				Label lblCourses = new Label("Courses");
				ListView<Course> lstCourses = new ListView<Course>();
				Label lblRightArrow = new Label("-->");
				Button btnAddCourse = new Button("Add Course");
				Label lblLeftArrow = new Label("<--");
				Button btnRemoveCourse = new Button("Remove Course");
				Label lblCoursesReq = new Label("Courses Required");
				ListView<Course> lstCoursesReq = new ListView<Course>();
				VBox vbxCourseButtons = new VBox();
				vbxCourseButtons.getChildren().addAll(lblRightArrow, btnAddCourse, lblLeftArrow, btnRemoveCourse);
				VBox vbxCourses = new VBox();
				vbxCourses.getChildren().addAll(lblCourses, lstCourses);
				VBox vbxCoursesReq = new VBox();
				vbxCoursesReq.getChildren().addAll(lblCoursesReq, lstCoursesReq);
				HBox hbxCourses = new HBox();
				hbxCourses.getChildren().addAll(vbxCourses, vbxCourseButtons, vbxCoursesReq);
				hbxCourses.setAlignment(Pos.CENTER);
				hbxCourses.setSpacing(30);
				Button btnCancelThis = new Button("Cancel");
				Button btnAddThis = new Button("Add Major");
				HBox hbxButtons = new HBox();
				hbxButtons.getChildren().addAll(btnCancelThis, btnAddThis);
				btnCancelThis.setOnAction(eac->{
					newMajorStage.close();
				});
				btnAddThis.setOnAction(eac->{
					AddMajorEventObject evm = new AddMajorEventObject(btnAddThis, txtName.getText(), txtMinGPA.getText(), 
					txtNumOfCredits.getText(), txtPhysEd.getText(), txtHis.getText(), txtLabSci.getText(), txtMath.getText(), 
					txtHum.getText(), txtBus.getText(), txtEng.getText(), txtCom.getText(), txtAmerHis.getText(), txtSocSci.getText(),
					txtLang.getText(), txtPhl.getText(), lstCoursesReq.getItems().toArray(new Course[lstCoursesReq.getItems().size()]));
					if(listenerMajorAdd != null){
						listenerMajorAdd.add(evm);
					}
					if(!evm.isValid()) {
						Alert alert = new Alert(AlertType.ERROR, evm.getErrorMessage(), ButtonType.OK);
						alert.showAndWait();
					} else {
						majors = evm.getMajors();
						newMajorStage.close();
					}
				});
				btnAddCourse.setOnAction(eac->{
					lstCoursesReq.getItems().add(lstCourses.getSelectionModel().getSelectedItem());
				});
				btnRemoveCourse.setOnAction(eac->{
					lstCoursesReq.getItems().remove(lstCoursesReq.getSelectionModel().getSelectedItem());
				});
				VBox pane = new VBox();
				pane.getChildren().addAll(lblRequirements, gridOut, hbxCourses, hbxButtons);
				Scene scene = new Scene(pane, 1280, 720);
				newMajorStage.setScene(scene);
				newMajorStage.showAndWait();
			});
			HBox hbxButtons = new HBox();
			hbxButtons.getChildren().addAll(btnCancel, btnDelete, btnAdd);
			hbxButtons.setAlignment(Pos.CENTER);
			VBox pane = new VBox();
			pane.getChildren().addAll(lblMajors, lstMajors, hbxButtons);
			pane.setAlignment(Pos.CENTER);
			pane.setSpacing(20);
			Scene acctScene = new Scene(pane, 300, 100);
			majorStage.setScene(acctScene);
			majorStage.showAndWait();
		});
		HBox hbxButtons = new HBox();
		VBox pane = new VBox();
		HBox hbxLogout = new HBox();
		hbxButtons.getChildren().addAll(btnSearchStudents, btnSearchAdmin, btnCreateAccount, btnManageMajors, btnManageCourses);
		hbxLogout.getChildren().addAll(lblLogoutName, hplChangePass, hplLogout);
		pane.getChildren().addAll(hbxLogout, hbxButtons);
		hbxButtons.setAlignment(Pos.CENTER);
		hbxButtons.setSpacing(20);
		hbxLogout.setAlignment(Pos.TOP_RIGHT);
		hbxLogout.setSpacing(20);
		pane.setAlignment(Pos.CENTER);
		pane.setSpacing(50);
		Scene scene = new Scene(pane, 600, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void createAccount(Administrator user, boolean admin, boolean student, Major[] majorsIn, Course[] coursesIn)
	{
		majors = majorsIn;
		courses = coursesIn;
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
		Button btnCreate;
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
			adminView(user, majors, courses);
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
	public void setListenerCourseAdd(AddCourseListener listenerCourseAdd) {
		this.listenerCourseAdd = listenerCourseAdd;
	}
	public void setListenerMajorAdd(AddMajorListener listenerMajorAdd) {
		this.listenerMajorAdd = listenerMajorAdd;
	}
	public void setListenerCourseDelete(DeleteCourseListener listenerCourseDelete) {
		this.listenerCourseDelete = listenerCourseDelete;
	}
	public void setListenerStudentSearch(BackListener listenerStudentSearch) {
		this.listenerStudentSearch = listenerStudentSearch;
	}
}
