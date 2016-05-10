package view;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;

import eventHandlers.AdminBackEventObject;
import eventHandlers.AdminBackListener;
import eventHandlers.AdminEditEventObject;
import eventHandlers.AdminEditListener;
import eventHandlers.EditEventObject;
import eventHandlers.EditListener;
import eventHandlers.LogoutEventObject;
import eventHandlers.LogoutListener;
import eventHandlers.PasswordEventObject;
import eventHandlers.PasswordListener;
import eventHandlers.RemoveStudentEventObject;
import eventHandlers.RemoveStudentListener;
import eventHandlers.ReportEventObject;
import eventHandlers.ReportListener;
import eventHandlers.SAINListener;
import eventHandlers.SearchEventObject;
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
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import report.Course;
import user.Administrator;
import user.Major;
import user.MajorBag;
import user.Student;
import user.User;
/**
 * Displays the staff control panel and associated search windows.  This window allows staff to search for a student and generate the corresponding SAIN Report.
 * Administrators accessing this view have the added ability of editing a selected student.
 */
public class StaffView {
    Stage primaryStage;
    LogoutListener listenerLogout;
    PasswordListener listenerPassword;
    SearchListener listenerSearch;
    AdminEditListener listenerAdmin;
    EditListener listenerEdit;
    ReportListener listenerReport;
    RemoveStudentListener listenerDelete;
    AdminBackListener listenerBack;
    Course[] courses;
    private DecimalFormat format = new DecimalFormat("#.00");
    public StaffView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    /**
     * Overloaded method which allows the courses object to be set before executing start()
     * @param courses Array of currently declared courses
     * @param admin Whether or not logged in user is an Administrator
     * @param user Logged in user
     * @param majors Array of currently declared majors
     */
    public void start(boolean admin, User user, Major[] majors, Course[] courses) {
        this.courses = courses;
        start(admin, user, majors);
    }
    /**
     * Displays the staff control panel and associated search windows.  This window allows staff to search for a student and generate the corresponding SAIN Report.
     * Administrators accessing this view have the added ability of editing a selected student.
     * @param admin Whether or not logged in user is an Administrator
     * @param user Logged in user
     * @param majors Array of currently declared majors
     */
    public void start(boolean admin, User user, Major[] majors) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        Label lblID = new Label("ID # : ");
        grid.add(lblID, 0, 0);
        TextField txtID = new TextField();
        grid.add(txtID, 1, 0);
        Label lblFirstName = new Label("First Name : ");
        grid.add(lblFirstName, 0, 1);
        TextField txtFirstName = new TextField();
        grid.add(txtFirstName, 1, 1);
        Label lblLastName = new Label("Last Name : ");
        grid.add(lblLastName, 0, 2);
        TextField txtLastName = new TextField();
        grid.add(txtLastName, 1, 2);
        Label lblAddress = new Label("Address : ");
        grid.add(lblAddress, 0, 3);
        TextField txtAddress = new TextField();
        grid.add(txtAddress, 1, 3);
        Label lblCity = new Label("City : ");
        grid.add(lblCity, 0, 4);
        TextField txtCity = new TextField();
        grid.add(txtCity, 1, 4);
        Label lblZipCode = new Label("Zip Code : ");
        grid.add(lblZipCode, 0, 5);
        TextField txtZipCode = new TextField();
        grid.add(txtZipCode, 1, 5);
        Label lblState = new Label("State : ");
        grid.add(lblState, 0, 6);
        TextField txtState = new TextField();
        grid.add(txtState, 1, 6);
        Label lblGPA = new Label("GPA : ");
        grid.add(lblGPA, 0, 7);
        TextField txtGPA = new TextField();
        grid.add(txtGPA, 1, 7);
        Label lblSSN = new Label("Social Security # : ");
        grid.add(lblSSN, 0, 8);
        TextField txtSSN = new TextField();
        grid.add(txtSSN, 1, 8);
        Label lblUsername = new Label("Username : ");
        grid.add(lblUsername, 0, 9);
        TextField txtUsername = new TextField();
        grid.add(txtUsername, 1, 9);
        Label lblMajor = new Label("Major : ");
        grid.add(lblMajor, 0, 10);
        ComboBox < Major > cmbMajor = new ComboBox < Major > ();
        grid.add(cmbMajor, 1, 10);
        for (int i = 0; i < majors.length; i++) {
            cmbMajor.getItems().add(majors[i]);
        }
        cmbMajor.getItems().add(new Major());
        cmbMajor.setValue(new Major());
        Label lblStudents = new Label("Results : ");
        grid.add(lblStudents, 0, 11);
        ListView < Student > lstStudents = new ListView < Student > ();
        grid.add(lstStudents, 0, 12);
        lstStudents.setMaxHeight(300);
        lstStudents.setMaxWidth(600);
        grid.setAlignment(Pos.CENTER);
        Button btnSearch = new Button("Search");
        Button btnSelect = new Button("Select Student");
        Button btnClear = new Button("Clear");
        Button btnEdit = new Button("Edit Student Data");
        Button btnBack = new Button("Back");
        HBox hbxButtons = new HBox();
        if (admin)
            hbxButtons.getChildren().addAll(btnBack, btnClear, btnSearch, btnSelect, btnEdit);
        else
            hbxButtons.getChildren().addAll(btnClear, btnSearch, btnSelect);
        if (!admin) //Don't allow faculty to edit student data
            btnEdit.setVisible(false);
        hbxButtons.setAlignment(Pos.CENTER);
        hbxButtons.setSpacing(20);
        btnBack.setOnAction(e -> {
            if (listenerBack != null) {
                listenerBack.back(new AdminBackEventObject(btnBack, (Administrator) user)); //Safe to cast user to Admin because button is only displayed if user is admin
            }
        });
        btnSearch.setOnAction(e -> {
            lstStudents.getItems().clear();
            SearchEventObject ev = new SearchEventObject(btnSearch, txtID.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtCity.getText(), txtState.getText(), txtZipCode.getText(), txtSSN.getText(), cmbMajor.getValue().getName(), txtGPA.getText(), txtUsername.getText());
            if (listenerSearch != null) {
                listenerSearch.search(ev);
                if (ev.isInputValid()) {
                    if (ev.getStudentResults().length == 0) {
                        Alert alert = new Alert(AlertType.INFORMATION, "No students were found with the specified search terms.", ButtonType.OK);
                        alert.showAndWait();
                    } else {
                        for (int i = 0; i < ev.getStudentResults().length; i++) {
                            lstStudents.getItems().add(ev.getStudentResults()[i]);
                        }
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR, "Error!  Invalid input detected!  Please check input validity, then try again.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
        cmbMajor.setOnAction(e -> {
            if (cmbMajor.getValue() == null) {
                cmbMajor.setValue(new Major());
            }
        });
        lstStudents.setOnMouseClicked(e -> {
            if (lstStudents.getSelectionModel().getSelectedItem() != null) {
                Student tempStudent = lstStudents.getSelectionModel().getSelectedItem();
                txtID.setText(parseId(tempStudent.getId()));
                txtFirstName.setText(tempStudent.getFirstName());
                txtLastName.setText(tempStudent.getLastName());
                txtAddress.setText(tempStudent.getAddress());
                txtCity.setText(tempStudent.getCity());
                txtZipCode.setText(Integer.toString(tempStudent.getZipCode()));
                txtState.setText(tempStudent.getState());
                if (tempStudent.getGpa() >= 0 && tempStudent.getGpa() <= 4) {
                    txtGPA.setText(format.format(tempStudent.getGpa())); //Only display GPA if it is set
                } else {
                    txtGPA.setText(""); //Otherwise set to blank
                }
                txtSSN.setText(tempStudent.getSocialSecNum());
                cmbMajor.setValue(tempStudent.getMajor());
                txtUsername.setText(tempStudent.getUsername());
            }
        });
        btnEdit.setOnAction(e -> {
            AdminEditEventObject ev = new AdminEditEventObject(btnEdit, txtID.getText());
            if (listenerAdmin != null)
                listenerAdmin.verify(ev);
            if (ev.isValid())
                adminEdit(user, ev.getStudent(), majors);
            else {
                Alert alert = new Alert(AlertType.ERROR, "Error!  Invalid ID detected!  Please enter a valid ID, then try again.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        btnSelect.setOnAction(e -> {
            if (lstStudents.getSelectionModel().getSelectedItem() != null) {
                ReportEventObject ev = new ReportEventObject(btnSelect, user, lstStudents.getSelectionModel().getSelectedItem());
                if (listenerReport != null) {
                    listenerReport.report(ev);
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR, "Error!  No student selected!.\nPlease select a student then try again.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        btnClear.setOnAction(e -> {
            txtID.clear();
            txtFirstName.clear();
            txtLastName.clear();
            txtAddress.clear();
            txtCity.clear();
            txtZipCode.clear();
            txtState.clear();
            txtGPA.clear();
            txtSSN.clear();
            txtUsername.clear();
            cmbMajor.setValue(null);
            lstStudents.getItems().clear();
        });
        VBox pane = new VBox();
        pane.getChildren().addAll(logoutHBox(user), grid, hbxButtons);
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        Scene scene = new Scene(pane, 800, 1100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SAIN Report");
        primaryStage.show();
        for (int i = 0; i < grid.getChildren().size(); i++) {
            try {
                if ((TextField) grid.getChildren().get(i) != null) { //If selected control is a textfield
                    ((TextField) grid.getChildren().get(i)).setMaxWidth(250);
                }
            } catch (Exception e) {
                //Control is not a textField
            }
            try {
                if ((PasswordField) grid.getChildren().get(i) != null) { //If selected control is a passwordField
                    ((PasswordField) grid.getChildren().get(i)).setMaxWidth(250);
                }
            } catch (Exception e) {
                //Control is not a passwordField
            }
            try {
                if ((ComboBox) grid.getChildren().get(i) != null) { //If selected control is a passwordField
                    ((ComboBox) grid.getChildren().get(i)).setMaxWidth(250);
                }
            } catch (Exception e) {
                //Control is not a passwordField
            }
        }
    }
    /**
     * Allows the user to edit the previously selected student if the user is an Administrator/
     * @param user Logged in user
     * @param student Student to be edited
     * @param majors Array of currently declared majors
     */
    public void adminEdit(User user, Student student, Major[] majors) {
        MultipleSelectionModel < Course > defaultSelect;
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        Label lblID = new Label("ID # : ");
        grid.add(lblID, 0, 0);
        TextField txtID = new TextField();
        grid.add(txtID, 1, 0);
        txtID.setText(parseId(student.getId()));
        txtID.setEditable(false);
        Label lblFirstName = new Label("First Name : ");
        grid.add(lblFirstName, 0, 1);
        TextField txtFirstName = new TextField();
        grid.add(txtFirstName, 1, 1);
        Label lblLastName = new Label("Last Name : ");
        grid.add(lblLastName, 0, 2);
        TextField txtLastName = new TextField();
        grid.add(txtLastName, 1, 2);
        Label lblAddress = new Label("Address : ");
        grid.add(lblAddress, 0, 3);
        TextField txtAddress = new TextField();
        grid.add(txtAddress, 1, 3);
        Label lblCity = new Label("City : ");
        grid.add(lblCity, 0, 4);
        TextField txtCity = new TextField();
        grid.add(txtCity, 1, 4);
        Label lblZipCode = new Label("Zip Code : ");
        grid.add(lblZipCode, 0, 5);
        TextField txtZipCode = new TextField();
        grid.add(txtZipCode, 1, 5);
        Label lblState = new Label("State : ");
        grid.add(lblState, 0, 6);
        TextField txtState = new TextField();
        grid.add(txtState, 1, 6);
        Label lblSSN = new Label("Social Security # : ");
        grid.add(lblSSN, 0, 8);
        TextField txtSSN = new TextField();
        grid.add(txtSSN, 1, 8);
        Label lblCampus = new Label("Campus : ");
        grid.add(lblCampus, 0, 9);
        TextField txtCampus = new TextField();
        grid.add(txtCampus, 1, 9);
        Label lblDateEnrolled = new Label("Date Enrolled : ");
        grid.add(lblDateEnrolled, 0, 10);
        DatePicker dtpDateEnrolled = new DatePicker();
        grid.add(dtpDateEnrolled, 1, 10);
        Label lblBirthDate = new Label("Birth Date : ");
        grid.add(lblBirthDate, 0, 11);
        DatePicker dtpBirthDate = new DatePicker();
        grid.add(dtpBirthDate, 1, 11);
        Label lblUsername = new Label("Username : ");
        grid.add(lblUsername, 0, 12);
        TextField txtUsername = new TextField();
        grid.add(txtUsername, 1, 12);
        Label lblMajor = new Label("Major : ");
        grid.add(lblMajor, 0, 13);
        ComboBox < Major > cmbMajor = new ComboBox < Major > ();
        grid.add(cmbMajor, 1, 13);
        for (int i = 0; i < majors.length; i++) {
            cmbMajor.getItems().add(majors[i]);
        }
        cmbMajor.getItems().add(new Major());
        cmbMajor.setValue(new Major());
        Label lblPassword = new Label("Password : ");
        grid.add(lblPassword, 0, 14);
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Leave this field blank to leave the password unchanged.");
        grid.add(txtPassword, 1, 14);
        grid.setAlignment(Pos.CENTER);
        txtFirstName.setText(student.getFirstName());
        txtLastName.setText(student.getLastName());
        txtAddress.setText(student.getAddress());
        txtCity.setText(student.getCity());
        txtZipCode.setText(Integer.toString(student.getZipCode()));
        txtState.setText(student.getState());
        txtSSN.setText(student.getSocialSecNum());
        dtpBirthDate.setValue(student.getDateOfBirth());
        dtpDateEnrolled.setValue(student.getDateEnrolled());
        txtCampus.setText(student.getCampus());
        txtUsername.setText(student.getUsername());
        cmbMajor.setValue(student.getMajor());
        Label lblCourses = new Label("Courses");
        ListView < Course > lstCourses = new ListView < Course > ();
        defaultSelect = lstCourses.getSelectionModel();
        for (Course c: courses) {
            lstCourses.getItems().add(c);
        }
        Label lblRightArrow = new Label("-->");
        Button btnAddCourse = new Button("Add Course");
        Label lblLeftArrow = new Label("<--");
        Button btnRemoveCourse = new Button("Remove Course");
        Label lblCoursesReq = new Label("Courses Required");
        ListView < Course > lstCoursesReq = new ListView < Course > ();
        for (Course c: student.getCourseWork()) {
            lstCoursesReq.getItems().add(c);
        }
        lstCourses.setMaxHeight(200);
        lstCoursesReq.setMaxHeight(200);
        VBox vbxCourseButtons = new VBox();
        vbxCourseButtons.getChildren().addAll(lblRightArrow, btnAddCourse, lblLeftArrow, btnRemoveCourse);
        vbxCourseButtons.setAlignment(Pos.CENTER);
        VBox vbxCourses = new VBox();
        vbxCourses.getChildren().addAll(lblCourses, lstCourses);
        VBox vbxCoursesReq = new VBox();
        vbxCoursesReq.getChildren().addAll(lblCoursesReq, lstCoursesReq);
        HBox hbxCourses = new HBox();
        hbxCourses.getChildren().addAll(vbxCourses, vbxCourseButtons, vbxCoursesReq);
        hbxCourses.setAlignment(Pos.CENTER);
        hbxCourses.setSpacing(30);
        Button btnBack = new Button("Cancel");
        Button btnEdit = new Button("Save Data");
        Button btnClear = new Button("Clear");
        Button btnDelete = new Button("Delete Student");
        HBox hbxButtons = new HBox();
        btnBack.setOnAction(e -> {
            start(true, user, majors);
        });
        hbxButtons.getChildren().addAll(btnBack, btnEdit, btnClear, btnDelete);
        btnEdit.setOnAction(e -> {
            try {
                EditEventObject ev;
                if (lstCoursesReq.getItems().size() == 0) {
                    ev = new EditEventObject(btnEdit, Integer.parseInt(txtID.getText()), txtFirstName.getText(), txtLastName.getText(), dtpDateEnrolled.getValue(), dtpBirthDate.getValue(), txtSSN.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(), txtState.getText(), txtCampus.getText(), cmbMajor.getValue(), txtUsername.getText(), txtPassword.getText(), new Course[0]);
                } else {
                    ev = new EditEventObject(btnEdit, Integer.parseInt(txtID.getText()), txtFirstName.getText(), txtLastName.getText(), dtpDateEnrolled.getValue(), dtpBirthDate.getValue(), txtSSN.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(), txtState.getText(), txtCampus.getText(), cmbMajor.getValue(), txtUsername.getText(), txtPassword.getText(), lstCoursesReq.getItems().toArray(new Course[lstCoursesReq.getItems().size()]));
                }
                if (listenerEdit != null)
                    listenerEdit.edit(ev);
                if (!ev.isValid()) {
                    Alert alert = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
                    alert.showAndWait();
                } else {
                    start(true, user, majors); //If valid, go back to student view
                }
            } catch (Exception ie) {
                Alert alert = new Alert(AlertType.ERROR, "Error, an exception occurred.  Please contact the developer for assistance.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        cmbMajor.setOnAction(e -> {
            if (cmbMajor.getValue() == null) {
                cmbMajor.setValue(new Major());
            }
        });
        btnClear.setOnAction(e -> {
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
            txtPassword.clear();
            txtUsername.clear();
        });
        btnAddCourse.setOnAction(eac -> {
            try {
                if (lstCourses.getSelectionModel().getSelectedItem() == null) {
                    throw new IllegalArgumentException();
                } else if (lstCoursesReq.getItems().contains(lstCourses.getSelectionModel().getSelectedItem())) {
                    Alert alert = new Alert(AlertType.ERROR, "Error, course already taken by student.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    Stage gradeStage = new Stage();
                    Label lblGrade = new Label("Please choose the course grade : ");
                    ComboBox < String > cmbGrade = new ComboBox < String > ();
                    String[] grades = {
                        "A",
                        "B+",
                        "B",
                        "C+",
                        "C",
                        "D+",
                        "D",
                        "F"
                    };
                    cmbGrade.getItems().addAll(grades);
                    cmbGrade.setValue("A"); //Default to A
                    Button btnOk = new Button("Continue");
                    btnOk.setOnAction(e -> {
                        Course tempCourse = lstCourses.getSelectionModel().getSelectedItem();
                        tempCourse.setCourseGrade(cmbGrade.getValue());
                        gradeStage.close();
                    });
                    gradeStage.setOnCloseRequest(e -> {
                        throw new IllegalArgumentException();
                    });
                    HBox gradePane = new HBox();
                    gradePane.getChildren().addAll(lblGrade, cmbGrade, btnOk);
                    gradePane.setAlignment(Pos.CENTER);
                    gradePane.setSpacing(20);
                    Scene gradeScene = new Scene(gradePane, 500, 100);
                    gradeStage.setTitle("Choose a course grade");
                    gradeStage.setScene(gradeScene);
                    gradeStage.showAndWait();
                    lstCoursesReq.getItems().add(lstCourses.getSelectionModel().getSelectedItem());
                    lstCoursesReq.setSelectionModel(defaultSelect);
                    lstCourses.setSelectionModel(defaultSelect);
                }
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(AlertType.ERROR, "You must select a course grade.", ButtonType.OK);
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR, "Error, no course selected!\nPlease select a course then try again.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        btnRemoveCourse.setOnAction(eac -> {
            try {
                if (lstCoursesReq.getSelectionModel().getSelectedItem() == null) {
                    throw new IllegalArgumentException();
                }
                lstCoursesReq.getItems().remove(lstCoursesReq.getSelectionModel().getSelectedIndex());
                lstCoursesReq.setSelectionModel(defaultSelect);
                lstCourses.setSelectionModel(defaultSelect);
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR, "Error, no course selected!\nPlease select a course then try again.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        btnDelete.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION, "This will permanently delete the student.\nAre you sure you want to continue?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.NO) {
                    return;
                }
            });
            RemoveStudentEventObject ev = new RemoveStudentEventObject(btnDelete, student.getId());
            if (listenerDelete != null) {
                listenerDelete.removeStudent(ev);
            }
            if (!ev.isValid()) {
                Alert alert2 = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
                alert2.showAndWait();
            } else {
                start(true, user, majors);
            }
        });
        hbxButtons.setAlignment(Pos.CENTER);
        hbxButtons.setSpacing(20);
        VBox pane = new VBox();
        pane.getChildren().addAll(logoutHBox(user), grid, hbxCourses, hbxButtons);
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        Scene scene = new Scene(pane, 800, 1100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SAIN Report");
        primaryStage.show();
        for (int i = 0; i < grid.getChildren().size(); i++) {
            try {
                if ((TextField) grid.getChildren().get(i) != null) { //If selected control is a textfield
                    ((TextField) grid.getChildren().get(i)).setMaxWidth(250);
                }
            } catch (Exception e) {
                //Control is not a textField
            }
            try {
                if ((PasswordField) grid.getChildren().get(i) != null) { //If selected control is a passwordField
                    ((PasswordField) grid.getChildren().get(i)).setMaxWidth(250);
                }
            } catch (Exception e) {
                //Control is not a passwordField
            }
            try {
                if ((ComboBox) grid.getChildren().get(i) != null) { //If selected control is a passwordField
                    ((ComboBox) grid.getChildren().get(i)).setMaxWidth(250);
                }
            } catch (Exception e) {
                //Control is not a passwordField
            }
        }
    }
    /**
     * Creates a window which allows the user to logout.
     * @param user The logged in user
     * @return An HBox containing the necessary event handlers and controls to allow the user to logout
     */
    public HBox logoutHBox(User user) {
        Label lblLogoutName = new Label("You are currently signed in as " + user.getName() + " : ");
        Hyperlink hplChangePass = new Hyperlink("Change Password");
        hplChangePass.setOnAction(e -> {
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
            btnCancel.setOnAction(ea -> {
                passStage.close();
            });
            Button btnChange = new Button("Change Password");
            btnChange.setOnAction(ea -> {
                PasswordEventObject ev = new PasswordEventObject(btnChange, oldPassword.getText(), newPassword.getText(), newPasswordConf.getText());
                if (listenerPassword != null) {
                    listenerPassword.changePassword(ev);
                    if (ev.isSuccessful()) {
                        Alert alert = new Alert(AlertType.INFORMATION, "Password Successfully Changed.", ButtonType.OK);
                        alert.showAndWait();
                        passStage.close();
                    } else {
                        Alert alert = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
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
        hplLogout.setOnAction(e -> {
            LogoutEventObject ev = new LogoutEventObject(hplLogout);
            if (listenerLogout != null)
                listenerLogout.logout(ev);
        });
        HBox hbxLogout = new HBox();
        hbxLogout.getChildren().addAll(lblLogoutName, hplChangePass, hplLogout);
        hbxLogout.setAlignment(Pos.TOP_RIGHT);
        hbxLogout.setSpacing(20);
        return hbxLogout;
    }
    public void setListenerReport(ReportListener listenerReport) {
        this.listenerReport = listenerReport;
    }
    public void setListenerPassword(PasswordListener listenerPassword) {
        this.listenerPassword = listenerPassword;
    }
    public void setListenerSearch(SearchListener listenerSearch) {
        this.listenerSearch = listenerSearch;
    }
    public void setListenerLogout(LogoutListener logoutListener) {
        this.listenerLogout = logoutListener;
    }
    public void setListenerAdmin(AdminEditListener listenerAdmin) {
        this.listenerAdmin = listenerAdmin;
    }
    public void setListenerEdit(EditListener listenerEdit) {
        this.listenerEdit = listenerEdit;
    }
    public void setListenerDelete(RemoveStudentListener listenerDelete) {
        this.listenerDelete = listenerDelete;
    }
    public void setListenerBack(AdminBackListener listenerBack) {
        this.listenerBack = listenerBack;
    }
    /**
     * Appends 0's to the beginning of the ID if necessary to create an 8-digit ID
     * @param id ID number to be parsed
     * @return Parsed ID number.  This may be equal to the original ID number, if that number was greater than 10000000
     */
    public String parseId(int id) {
        String returnString = Integer.toString(id);
        int index = returnString.length();
        if (index < 8) {
            for (int i = 0; i < 8 - index; i++) {
                returnString = "0" + returnString;
            }
        }
        return returnString;
    }
}