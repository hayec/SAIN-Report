package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

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
import eventHandlers.RemoveStaffEventObject;
import eventHandlers.RemoveStaffListener;
import eventHandlers.ReportEventObject;
import eventHandlers.SQLListener;
import eventHandlers.SearchEventObject;
import eventHandlers.SearchListener;
import eventHandlers.StaffEditEventObject;
import eventHandlers.StaffEditListener;
import eventHandlers.DeleteCourseEventObject;
import eventHandlers.AddMajorListener;
import eventHandlers.AdminEditEventObject;
import eventHandlers.AdminEditListener;
import eventHandlers.AddCourseEventObject;
import eventHandlers.AddCourseListener;
import eventHandlers.AddMajorEventObject;
import eventHandlers.SQLEventObject;
import javafx.geometry.Insets;
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
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Administrator;
import user.MajorBag;
import user.Student;
import user.User;
import user.Major;
import report.Course;
/*
 * Displays the main administrative control panel and associated creation and edit windows.  All windows are accessible from this window.
 */
public class AdminView {
    Stage primaryStage;
    LogoutListener listenerLogout;
    PasswordListener listenerPassword;
    CreateAccountListener listenerAccount;
    AddMajorListener listenerMajorAdd;
    AddCourseListener listenerCourseAdd;
    DeleteMajorListener listenerMajorDelete;
    DeleteCourseListener listenerCourseDelete;
    BackListener listenerStudentSearch;
    StaffEditListener listenerEdit;
    SearchListener listenerSearch;
    AdminEditListener listenerAdmin;
    RemoveStaffListener listenerDelete;
    SQLListener listenerSQL;
    Major[] majors;
    Course[] courses;
    public AdminView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    /*
     * Displays the main administrative control panel.  All windows are accessible from this window.
     * @param user Administrator which is currently logged in
     * @param majorsIn An array of the currently defined majors
     * @param coursesIN An array of the currently defined courses
     */
    public void adminView(Administrator user, Major[] majorsIn, Course[] coursesIn) {
        majors = majorsIn;
        courses = coursesIn;
        Button btnSearchStudents = new Button("Search Students");
        Button btnSearchAdmin = new Button("Search Staff");
        Button btnCreateAccount = new Button("Create Account");
        Button btnManageCourses = new Button("Manage Courses");
        Button btnManageMajors = new Button("Manage Majors");
        Button btnSetSQL = new Button("Set Data Saving Method");
        btnSetSQL.setOnAction(e-> {
        	Stage sqlStage = new Stage();
        	Button btnBin = new Button("Load from Binary");
        	Button btnSQL = new Button("Load from SQL");
        	HBox pane = new HBox();
        	pane.getChildren().addAll(btnBin, btnSQL);
        	pane.setSpacing(20);
        	pane.setAlignment(Pos.CENTER);
        	btnBin.setOnAction(ea->{
        		SQLEventObject ev = new SQLEventObject(btnBin, false);
        		if(listenerSQL != null) {
        			listenerSQL.setSQL(ev);
        		}
        		sqlStage.close();
        	});
        	btnSQL.setOnAction(ea->{
        		SQLEventObject ev = new SQLEventObject(btnSQL, true);
        		if(listenerSQL != null) {
        			listenerSQL.setSQL(ev);
        		}
        		sqlStage.close();
        	});
        	Scene scene = new Scene(pane, 400, 100);
        	sqlStage.setScene(scene);
        	sqlStage.setTitle("Data Type");
        	sqlStage.showAndWait();
        });
        btnCreateAccount.setOnAction(e -> {
            Stage acctStage = new Stage();
            acctStage.setTitle("New Account");
            Button btnCancel = new Button("Cancel");
            Button btnStudent = new Button("New Student");
            Button btnFaculty = new Button("New Faculty");
            Button btnAdministrator = new Button("New Administrator");
            btnCancel.setOnAction(ea -> {
                acctStage.close();
            });
            btnStudent.setOnAction(ea -> {
                createAccount(user, false, true, majors, courses);
                acctStage.close();
            });
            btnFaculty.setOnAction(ea -> {
                createAccount(user, false, false, majors, courses);
                acctStage.close();
            });
            btnAdministrator.setOnAction(ea -> {
                createAccount(user, true, false, majors, courses);
                acctStage.close();
            });
            HBox hbxButtons = new HBox();
            hbxButtons.getChildren().addAll(btnCancel, btnStudent, btnFaculty, btnAdministrator);
            hbxButtons.setAlignment(Pos.CENTER);
            hbxButtons.setSpacing(20);
            Scene acctScene = new Scene(hbxButtons, 650, 100);
            acctStage.setScene(acctScene);
            acctStage.setTitle("Add New Account");
            acctStage.showAndWait();
        });
        btnSearchAdmin.setOnAction(e -> {
            Stage editStage = new Stage();
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
            Label lblSSN = new Label("Social Security # : ");
            grid.add(lblSSN, 0, 7);
            TextField txtSSN = new TextField();
            grid.add(txtSSN, 1, 7);
            Label lblUsername = new Label("Username : ");
            grid.add(lblUsername, 0, 8);
            TextField txtUsername = new TextField();
            grid.add(txtUsername, 1, 8);
            Label lblUsers = new Label("Results : ");
            grid.add(lblUsers, 0, 9);
            ListView < User > lstUsers = new ListView < User > ();
            grid.add(lstUsers, 0, 10);
            lstUsers.setMaxHeight(300);
            lstUsers.setMaxWidth(600);
            grid.setAlignment(Pos.CENTER);
            Button btnSearch = new Button("Search");
            Button btnClear = new Button("Clear");
            Button btnEdit = new Button("Edit User Data");
            Button btnBack = new Button("Back");
            HBox hbxButtons = new HBox();
            hbxButtons.getChildren().addAll(btnBack, btnClear, btnSearch, btnEdit);
            hbxButtons.setAlignment(Pos.CENTER);
            hbxButtons.setSpacing(20);
            btnBack.setOnAction(ea -> {
                editStage.close();
            });
            btnSearch.setOnAction(ea -> {
                lstUsers.getItems().clear();
                SearchEventObject ev = new SearchEventObject(btnSearch, txtID.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtCity.getText(), txtState.getText(), txtZipCode.getText(), txtSSN.getText(), txtUsername.getText());
                if (listenerSearch != null) {
                    listenerSearch.search(ev);
                    if (ev.isInputValid()) {
                        if (ev.getUserResults().length == 0) {
                            Alert alert = new Alert(AlertType.INFORMATION, "No users were found with the specified search terms.", ButtonType.OK);
                            alert.showAndWait();
                        } else {
                            for (int i = 0; i < ev.getUserResults().length; i++) {
                                lstUsers.getItems().add(ev.getUserResults()[i]);
                            }
                        }
                    } else {
                        Alert alert = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            });
            lstUsers.setOnMouseClicked(ea -> {
                if (lstUsers.getSelectionModel().getSelectedItem() != null) {
                    User tempUser = lstUsers.getSelectionModel().getSelectedItem();
                    txtID.setText(parseId(tempUser.getId()));
                    txtFirstName.setText(tempUser.getFirstName());
                    txtLastName.setText(tempUser.getLastName());
                    txtAddress.setText(tempUser.getAddress());
                    txtCity.setText(tempUser.getCity());
                    txtZipCode.setText(Integer.toString(tempUser.getZipCode()));
                    txtState.setText(tempUser.getState());
                    txtSSN.setText(tempUser.getSocialSecNum());
                    txtUsername.setText(tempUser.getUsername());
                }
            });
            btnEdit.setOnAction(ea -> {
                AdminEditEventObject ev = new AdminEditEventObject(btnEdit, txtID.getText());
                if (listenerAdmin != null) {
                    listenerAdmin.verify(ev);
                }
                if (ev.isUserValid()) {
                    editFacultyView(ev.getUser(), user);
                    editStage.close();
                } else {
                    Alert alert = new Alert(AlertType.ERROR, "Error!  Invalid ID detected!  Please enter a valid ID, then try again.", ButtonType.OK);
                    alert.showAndWait();
                }
            });
            btnClear.setOnAction(ea -> {
                txtID.clear();
                txtFirstName.clear();
                txtLastName.clear();
                txtAddress.clear();
                txtCity.clear();
                txtZipCode.clear();
                txtState.clear();
                txtSSN.clear();
                lstUsers.getItems().clear();
                txtUsername.clear();
            });
            VBox pane = new VBox();
            pane.getChildren().addAll(logoutHBox(user), grid, hbxButtons);
            pane.setAlignment(Pos.CENTER);
            pane.setSpacing(20);
            Scene scene = new Scene(pane, 800, 1100);
            editStage.setScene(scene);
            editStage.setTitle("Edit User Data");
            editStage.show();
        });
        btnSearchStudents.setOnAction(e -> {
            BackEventObject ev = new BackEventObject(btnSearchStudents);
            if (listenerStudentSearch != null) {
                listenerStudentSearch.back(ev);
            }
        });
        btnManageCourses.setOnAction(e -> {
            MultipleSelectionModel < Course > courseSelect;
            Stage courseStage = new Stage();
            courseStage.setTitle("Manage Courses");
            Label lblCourses = new Label("Courses : ");
            ListView < Course > lstCourses = new ListView < Course > ();
            courseSelect = lstCourses.getSelectionModel();
            for (Course c: courses) {
                lstCourses.getItems().add(c);
            }
            Button btnCancel = new Button("Close");
            Button btnDelete = new Button("Delete Course");
            Button btnEdit = new Button("Edit Course");
            Button btnAdd = new Button("Add Course");
            btnCancel.setOnAction(ea -> {
                courseStage.close();
            });
            btnDelete.setOnAction(ea -> {
                try {
                    if (lstCourses.getSelectionModel().getSelectedItem().equals(courseSelect)) {
                        throw new Exception();
                    }
                    DeleteCourseEventObject ev = new DeleteCourseEventObject(btnDelete, lstCourses.getSelectionModel().getSelectedItem());
                    if (listenerCourseDelete != null) {
                        Alert alert = new Alert(AlertType.CONFIRMATION, "This will permanently delete the course and all students, majors, and courses who are associated.\nAre you sure you want to continue?", ButtonType.YES, ButtonType.NO); //Ensure that user wants to delete course
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.NO) {
                                throw new IllegalArgumentException();
                            }
                        });
                        listenerCourseDelete.delete(ev);
                        lstCourses.getItems().remove(lstCourses.getSelectionModel().getSelectedIndex());
                        courses = ev.getCourses();
                    }
                } catch (IllegalArgumentException ex) {} catch (Exception ex) {
                    ex.printStackTrace();
                    Alert alert2 = new Alert(AlertType.ERROR, "Error, no course selected!\nPlease select a course then try again.", ButtonType.OK);
                    alert2.showAndWait();
                }
            });
            btnAdd.setOnAction(ea -> {
                addCourseView(false, new Course());
                lstCourses.getItems().clear();
                for (Course c: courses) {
                    lstCourses.getItems().add(c);
                }
            });
            btnEdit.setOnAction(ea -> {
                try {
                    addCourseView(true, lstCourses.getSelectionModel().getSelectedItem());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(AlertType.ERROR, "Error, no course selected!\nPlease select a course then try again.", ButtonType.OK);
                    alert.showAndWait();
                }
                lstCourses.getItems().clear();
                for (Course c: courses) {
                    lstCourses.getItems().add(c);
                }
            });
            lstCourses.setMaxWidth(350);
            HBox hbxButtons = new HBox();
            hbxButtons.getChildren().addAll(btnCancel, btnDelete, btnEdit, btnAdd);
            hbxButtons.setAlignment(Pos.CENTER);
            hbxButtons.setSpacing(20);
            VBox pane = new VBox();
            pane.getChildren().addAll(lblCourses, lstCourses, hbxButtons);
            pane.setSpacing(30);
            pane.setAlignment(Pos.CENTER);
            Scene acctScene = new Scene(pane, 550, 650);
            courseStage.setScene(acctScene);
            courseStage.showAndWait();
        });
        btnManageMajors.setOnAction(e -> {
            MultipleSelectionModel < Major > majorSelect;
            Stage majorStage = new Stage();
            majorStage.setTitle("Manage Majors");
            Label lblMajors = new Label("Majors");
            ListView < Major > lstMajors = new ListView < Major > ();
            majorSelect = lstMajors.getSelectionModel();
            for (Major m: majors) {
                lstMajors.getItems().add(m);
            }
            lstMajors.setMaxWidth(250);
            Button btnCancel = new Button("Close");
            Button btnEdit = new Button("Edit Major");
            Button btnDelete = new Button("Delete Major");
            Button btnAdd = new Button("Add Major");
            btnCancel.setOnAction(ea -> {
                majorStage.close();
            });
            btnDelete.setOnAction(ea -> {
                try {
                    DeleteMajorEventObject ev = new DeleteMajorEventObject(btnDelete, lstMajors.getSelectionModel().getSelectedItem());
                    if (listenerMajorDelete != null) {
                        Alert alert = new Alert(AlertType.CONFIRMATION, "This will permanently delete the major and all students who are associated.\nAre you sure you want to continue?", ButtonType.YES, ButtonType.NO); //Ensure that user wants to delete major
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.NO) {
                                throw new IllegalArgumentException();
                            }
                        });
                        listenerMajorDelete.delete(ev);
                        lstMajors.getItems().remove(lstMajors.getSelectionModel().getSelectedIndex());
                        majors = ev.getMajors();
                    }
                } catch (IllegalArgumentException ex) {} catch (Exception ex) {
                    ex.printStackTrace();
                    Alert alert2 = new Alert(AlertType.ERROR, "Error, no major selected!\nPlease select a major then try again.", ButtonType.OK);
                    alert2.showAndWait();
                }
            });
            btnAdd.setOnAction(ea -> {
                addMajorView(false, new Major());
                lstMajors.getItems().clear();
                for (Major m: majors) {
                    lstMajors.getItems().add(m);
                }
            });
            btnEdit.setOnAction(ea -> {
                try {
                    addMajorView(true, lstMajors.getSelectionModel().getSelectedItem());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(AlertType.ERROR, "Error, no major selected!\nPlease select a major then try again.", ButtonType.OK);
                    alert.showAndWait();
                }
                lstMajors.getItems().clear();
                for (Major m: majors) {
                    lstMajors.getItems().add(m);
                }
            });
            HBox hbxButtons = new HBox();
            hbxButtons.getChildren().addAll(btnCancel, btnDelete, btnEdit, btnAdd);
            hbxButtons.setAlignment(Pos.CENTER);
            hbxButtons.setSpacing(20);
            VBox pane = new VBox();
            pane.getChildren().addAll(lblMajors, lstMajors, hbxButtons);
            pane.setAlignment(Pos.CENTER);
            pane.setSpacing(20);
            Scene acctScene = new Scene(pane, 400, 650);
            majorStage.setScene(acctScene);
            majorStage.showAndWait();
        });
        HBox hbxButtons = new HBox();
        VBox pane = new VBox();
        hbxButtons.getChildren().addAll(btnSearchStudents, btnSearchAdmin, btnCreateAccount, btnManageMajors, btnManageCourses, btnSetSQL);
        pane.getChildren().addAll(logoutHBox(user), hbxButtons);
        hbxButtons.setAlignment(Pos.CENTER);
        hbxButtons.setSpacing(20);

        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(50);
        Scene scene = new Scene(pane, 1000, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Administrative View");
        primaryStage.show();
    }
    /*
     * Creates a window which allows the user to create a new account of any desired type.
     * @param user Administrator which is currently logged in
     * @param admin User to be created is Administrator
     * @param student User to be created is Student
     * @param majorsIn An array of the currently defined majors
     * @param coursesIN An array of the currently defined courses
     */
    public void createAccount(Administrator user, boolean admin, boolean student, Major[] majorsIn, Course[] coursesIn) {
        majors = majorsIn;
        courses = coursesIn;
        int line = 0;
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        Label lblID = new Label("ID # : ");
        grid.add(lblID, 0, line);
        TextField txtID = new TextField();
        grid.add(txtID, 1, line);
        Label lblFirstName = new Label("First Name : ");
        grid.add(lblFirstName, 0, ++line);
        TextField txtFirstName = new TextField();
        grid.add(txtFirstName, 1, line);
        Label lblLastName = new Label("Last Name : ");
        grid.add(lblLastName, 0, ++line);
        TextField txtLastName = new TextField();
        grid.add(txtLastName, 1, line);
        Label lblAddress = new Label("Address : ");
        grid.add(lblAddress, 0, ++line);
        TextField txtAddress = new TextField();
        grid.add(txtAddress, 1, line);
        Label lblCity = new Label("City : ");
        grid.add(lblCity, 0, ++line);
        TextField txtCity = new TextField();
        grid.add(txtCity, 1, line);
        Label lblZipCode = new Label("Zip Code : ");
        grid.add(lblZipCode, 0, ++line);
        TextField txtZipCode = new TextField();
        grid.add(txtZipCode, 1, line);
        Label lblState = new Label("State : ");
        grid.add(lblState, 0, ++line);
        TextField txtState = new TextField();
        grid.add(txtState, 1, line);
        Label lblSSN = new Label("Social Security # : ");
        grid.add(lblSSN, 0, ++line);
        TextField txtSSN = new TextField();
        grid.add(txtSSN, 1, line);
        Label lblCampus = new Label("Campus : ");
        TextField txtCampus = new TextField();
        if (student) {
            grid.add(lblCampus, 0, ++line);
            grid.add(txtCampus, 1, line);
        }
        Label lblDateEnrolled = new Label("Date Enrolled : ");
        DatePicker dtpDateEnrolled = new DatePicker();
        dtpDateEnrolled.setValue(LocalDate.now()); //Set to now to prevent nullpointerexception
        if (student) {
            grid.add(lblDateEnrolled, 0, ++line);
            grid.add(dtpDateEnrolled, 1, line);
        }
        Label lblBirthDate = new Label("Birth Date : ");
        grid.add(lblBirthDate, 0, ++line);
        DatePicker dtpBirthDate = new DatePicker();
        grid.add(dtpBirthDate, 1, line);
        dtpBirthDate.setValue(LocalDate.now()); //Set to now to prevent nullpointerexception
        Label lblMajor = new Label("Major : ");
        ComboBox < Major > cmbMajor = new ComboBox < Major > ();
        if (student) {
            grid.add(lblMajor, 0, ++line);
            grid.add(cmbMajor, 1, line);
        }
        for (int i = 0; i < majors.length; i++)
            cmbMajor.getItems().add(majors[i]);
        cmbMajor.getItems().add(new Major());
        cmbMajor.setValue(new Major());
        Label lblUsername = new Label("Username : ");
        grid.add(lblUsername, 0, ++line);
        TextField txtUsername = new TextField();
        grid.add(txtUsername, 1, line);
        Label lblPassword = new Label("Password : ");
        grid.add(lblPassword, 0, ++line);
        PasswordField txtPassword = new PasswordField();
        grid.add(txtPassword, 1, line);
        grid.setAlignment(Pos.CENTER);
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
        Button btnBack = new Button("Cancel");
        Button btnCreate;
        if (student) {
            btnCreate = new Button("Create Student");
        } else if (admin) {
            btnCreate = new Button("Create Administrator");
        } else {
            btnCreate = new Button("Create Faculty");
        }
        Button btnClear = new Button("Clear");
        HBox hbxButtons = new HBox();
        btnBack.setOnAction(e -> {
            adminView(user, majors, courses);
        });
        hbxButtons.getChildren().addAll(btnBack, btnCreate, btnClear);
        hbxButtons.setAlignment(Pos.CENTER);
        hbxButtons.setSpacing(20);
        btnCreate.setOnAction(e -> {
            try {
                CreateAccountEventObject ev = new CreateAccountEventObject(btnCreate);
                if (student) {
                    ev = new CreateAccountEventObject(btnCreate, txtID.getText(), txtUsername.getText(), txtPassword.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(), txtState.getText(), txtSSN.getText(), txtCampus.getText(), dtpDateEnrolled.getValue(), dtpBirthDate.getValue(), cmbMajor.getValue());
                } else if (admin) {
                    ev = new CreateAccountEventObject(btnCreate, txtID.getText(), txtUsername.getText(), txtPassword.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(), txtState.getText(), txtSSN.getText(), dtpBirthDate.getValue(), true);
                } else {
                    ev = new CreateAccountEventObject(btnCreate, txtID.getText(), txtUsername.getText(), txtPassword.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(), txtState.getText(), txtSSN.getText(), dtpBirthDate.getValue(), false);
                }
                if (listenerAccount != null)
                    listenerAccount.createAccount(ev);
                if (!ev.isValidAccount()) {
                    Alert alert = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION, "Account successfully created. ID# : " + parseId(Integer.parseInt(ev.getId())), ButtonType.OK);
                    alert.showAndWait();
                    adminView(user, majors, courses);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK);
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
            txtSSN.clear();
            dtpDateEnrolled.setValue(LocalDate.now());
            dtpBirthDate.setValue(LocalDate.now());
            cmbMajor.setValue(null);
            txtUsername.clear();
            txtPassword.clear();
        });
        VBox pane = new VBox();
        grid.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(logoutHBox(user), grid, hbxButtons);
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        Scene scene = new Scene(pane, 800, 1100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SAIN Report");
        primaryStage.show();
    }
    /*
     * Creates a window which allows the user to create or edit a previously selected course.
     * @param edit Whether or not the course is to be edited
     * @param course Course to be edited, if necessary
     */
    public void addCourseView(boolean edit, Course course) {
        MultipleSelectionModel < Course > defaultSelect;
        Stage newCourseStage = new Stage();
        if (!edit) {
            newCourseStage.setTitle("Add Course");
        } else {
            newCourseStage.setTitle("Edit Course");
        }
        GridPane gridOut = new GridPane();
        gridOut.setHgap(10);
        gridOut.setVgap(10);
        gridOut.setPadding(new Insets(30, 0, 10, 30));
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
        ListView < Course > lstCourse = new ListView < Course > ();
        for (Course c: courses) {
            lstCourse.getItems().add(c);
        }
        Label lblRightArrow = new Label("-->");
        Button btnAddCourse = new Button("Add Course");
        Label lblLeftArrow = new Label("<--");
        Button btnRemoveCourse = new Button("Remove Course");
        Label lblCoursesReq = new Label("Prerequisite Courses");
        ListView < Course > lstCoursesReq = new ListView < Course > ();
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
        ListView < Course > lstCoursesA = new ListView < Course > ();
        defaultSelect = lstCoursesA.getSelectionModel();
        for (Course c: courses) {
            lstCoursesA.getItems().add(c);
        }
        Label lblRightArrowA = new Label("-->");
        Button btnAddCourseA = new Button("Add Course");
        Label lblLeftArrowA = new Label("<--");
        Button btnRemoveCourseA = new Button("Remove Course");
        Label lblCoursesReqA = new Label("Corequisite Courses");
        ListView < Course > lstCoursesReqA = new ListView < Course > ();
        VBox vbxCourseButtonsA = new VBox();
        vbxCourseButtonsA.getChildren().addAll(lblRightArrowA, btnAddCourseA, lblLeftArrowA, btnRemoveCourseA);
        VBox vbxCoursesA = new VBox();
        vbxCoursesA.getChildren().addAll(lblCoursesA, lstCoursesA);
        VBox vbxCoursesReqA = new VBox();
        vbxCoursesReqA.getChildren().addAll(lblCoursesReqA, lstCoursesReqA);
        HBox hbxCoursesA = new HBox();
        hbxCoursesA.getChildren().addAll(vbxCoursesA, vbxCourseButtonsA, vbxCoursesReqA);
        hbxCoursesA.setAlignment(Pos.CENTER);
        hbxCoursesA.setSpacing(30);
        VBox vbxReqs = new VBox();
        vbxReqs.getChildren().addAll(hbxCourses, hbxCoursesA);
        vbxReqs.setSpacing(30);
        vbxReqs.setAlignment(Pos.CENTER);
        Button btnCancelThis = new Button("Cancel");
        Button btnAddThis;
        if(!edit){
        	btnAddThis = new Button("Add Course");
        } else {
        	btnAddThis = new Button("Save Course");
        }
        HBox hbxButtons = new HBox();
        hbxButtons.getChildren().addAll(btnCancelThis, btnAddThis);
        hbxButtons.setAlignment(Pos.CENTER);
        hbxButtons.setSpacing(20);
        if (edit) {
            txtCourseCode.setText(course.getCourseCode());
            txtCourseTitle.setText(course.getCourseTitle());
            txtCourseDescription.setText(course.getCourseDescription());
            txtCredits.setText(Integer.toString(course.getCredits()));
            chkAmmerman.setSelected(course.isAmmerman());
            chkGrant.setSelected(course.isGrant());
            chkEastern.setSelected(course.isEastern());
            chkPhysEd.setSelected(course.CAttributes.isPhysEd());
            chkHistory.setSelected(course.CAttributes.isHistory());
            chkLabScience.setSelected(course.CAttributes.isLabScience());
            chkMath.setSelected(course.CAttributes.isMath());
            chkHum.setSelected(course.CAttributes.isHumanities());
            chkBus.setSelected(course.CAttributes.isBusiness());
            chkEng.setSelected(course.CAttributes.isEnglish());
            chkCom.setSelected(course.CAttributes.isCommunications());
            chkAmerHis.setSelected(course.CAttributes.isAmerHis());
            chkSocSci.setSelected(course.CAttributes.isSocScience());
            chkLang.setSelected(course.CAttributes.isLanguage());
            chkPhl.setSelected(course.CAttributes.isPhilosophy());
            for (String s: course.getPrerequisites()) {
                lstCoursesReq.getItems().add(getCourse(s));
            }
            for (String s: course.getCorequisites()) {
                lstCoursesReqA.getItems().add(getCourse(s));
            }
        }
        btnCancelThis.setOnAction(eac -> {
            newCourseStage.close();
        });
        btnAddCourse.setOnAction(eac -> {
            try {
                if (lstCourse.getSelectionModel().getSelectedItem() == null) {
                    throw new IllegalArgumentException();
                } else if (lstCoursesReq.getItems().contains(lstCourse.getSelectionModel().getSelectedItem())) {
                    Alert alert = new Alert(AlertType.ERROR, "Error, course already required as a prerequisite.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    lstCoursesReq.getItems().add(lstCourse.getSelectionModel().getSelectedItem());
                    lstCoursesReq.setSelectionModel(defaultSelect);
                    lstCourse.setSelectionModel(defaultSelect);
                }
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
                lstCourse.setSelectionModel(defaultSelect);
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR, "Error, no course selected!\nPlease select a course then try again.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        btnAddCourseA.setOnAction(eac -> {
            try {
                if (lstCoursesA.getSelectionModel().getSelectedItem() == null) {
                    throw new IllegalArgumentException();
                } else if (lstCoursesReqA.getItems().contains(lstCoursesA.getSelectionModel().getSelectedItem())) {
                    Alert alert = new Alert(AlertType.ERROR, "Error, course already required as a corequisite.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    lstCoursesReqA.getItems().add(lstCoursesA.getSelectionModel().getSelectedItem());
                    lstCoursesReqA.setSelectionModel(defaultSelect);
                    lstCoursesA.setSelectionModel(defaultSelect);
                }
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR, "Error, no course selected!\nPlease select a course then try again.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        btnRemoveCourseA.setOnAction(eac -> {
            try {
                if (lstCoursesReqA.getSelectionModel().getSelectedItem() == null) {
                    throw new IllegalArgumentException();
                }
                lstCoursesReqA.getItems().remove(lstCoursesReqA.getSelectionModel().getSelectedIndex());
                lstCoursesReqA.setSelectionModel(defaultSelect);
                lstCoursesA.setSelectionModel(defaultSelect);
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR, "Error, no course selected!\nPlease select a course then try again.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        btnAddThis.setOnAction(eac -> {
            //Error thrown on 898 at runtime
            ArrayList < String > tempReq = new ArrayList < String > ();
            for (Course c: lstCoursesReq.getItems().toArray(new Course[lstCoursesReq.getItems().size()])) {
                tempReq.add(c.getCourseCode());
            }
            ArrayList < String > tempReqA = new ArrayList < String > ();
            for (Course c: lstCoursesReqA.getItems().toArray(new Course[lstCoursesReqA.getItems().size()])) {
                tempReqA.add(c.getCourseCode());
            }
            AddCourseEventObject ev = new AddCourseEventObject(btnAddThis, txtCourseCode.getText(), txtCourseTitle.getText(), txtCourseDescription.getText(),
                chkAmmerman.isSelected(), chkGrant.isSelected(), chkEastern.isSelected(), tempReq.toArray(new String[tempReq.size()]),
                tempReqA.toArray(new String[tempReqA.size()]), txtCredits.getText(), chkPhysEd.isSelected(), chkHistory.isSelected(),
                chkLabScience.isSelected(), chkMath.isSelected(), chkHum.isSelected(), chkBus.isSelected(), chkEng.isSelected(), chkCom.isSelected(),
                chkAmerHis.isSelected(), chkSocSci.isSelected(), chkLang.isSelected(), chkPhl.isSelected());
            if (listenerCourseAdd != null) {
                listenerCourseAdd.add(ev);
            }
            if (!ev.isValid()) {
                Alert alert = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
                alert.showAndWait();
            } else {
                courses = ev.getCourses();
                newCourseStage.close();
            }
        });
        VBox pane = new VBox();
        pane.getChildren().addAll(gridOut, vbxReqs, hbxButtons);
        Scene scene = new Scene(pane, 1080, 1500);
        newCourseStage.setScene(scene);
        newCourseStage.showAndWait();
    }
    /*
     * Creates a window which allows the user to create or edit a previously selected major.
     * @param edit Whether or not the major is to be edited
     * @param major Major to be edited, if necessary
     */
    public void addMajorView(boolean edit, Major major) {
        MultipleSelectionModel < Course > defaultSelect;
        Stage newMajorStage = new Stage();
        newMajorStage.setTitle("Add Major");
        GridPane gridOut = new GridPane();
        gridOut.setHgap(10);
        gridOut.setVgap(10);
        gridOut.setPadding(new Insets(30, 0, 10, 30));
        Label lblRequirements = new Label("Major Requirements : ");
        lblRequirements.setAlignment(Pos.CENTER);
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
        gridOut.add(txtCom, 1, 10);
        gridOut.add(txtAmerHis, 1, 11);
        gridOut.add(txtSocSci, 1, 12);
        gridOut.add(txtLang, 1, 13);
        gridOut.add(txtPhl, 1, 14);
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
        Button btnCancelThis = new Button("Cancel");
        Button btnAddThis;
        if (edit) {
            btnAddThis = new Button("Save Major");
        } else {
            btnAddThis = new Button("Add Major");
        }
        HBox hbxButtons = new HBox();
        hbxButtons.getChildren().addAll(btnCancelThis, btnAddThis);
        hbxButtons.setAlignment(Pos.CENTER);
        hbxButtons.setSpacing(20);
        if (edit) {
            txtName.setText(major.getName());
            txtMinGPA.setText(Double.toString(major.getMinGPA()));
            txtNumOfCredits.setText(Integer.toString(major.getNumOfCreditsReq()));
            txtPhysEd.setText(Integer.toString(major.getPhysEdReq()));
            txtHis.setText(Integer.toString(major.getHisReq()));
            txtLabSci.setText(Integer.toString(major.getLabSciReq()));
            txtMath.setText(Integer.toString(major.getMathReq()));
            txtHum.setText(Integer.toString(major.getHumReq()));
            txtBus.setText(Integer.toString(major.getBusReq()));
            txtEng.setText(Integer.toString(major.getEngReq()));
            txtCom.setText(Integer.toString(major.getComReq()));
            txtAmerHis.setText(Integer.toString(major.getAmerHisReq()));
            txtSocSci.setText(Integer.toString(major.getSocSciReq()));
            txtLang.setText(Integer.toString(major.getLangReq()));
            txtPhl.setText(Integer.toString(major.getPhlReq()));
            for (Course c: major.getReqCourses()) {
                lstCoursesReq.getItems().add(c);
            }
        }
        btnCancelThis.setOnAction(eac -> {
            newMajorStage.close();
        });
        btnAddThis.setOnAction(eac -> {
            AddMajorEventObject evm = new AddMajorEventObject(btnAddThis, txtName.getText(), txtMinGPA.getText(),
                txtNumOfCredits.getText(), txtPhysEd.getText(), txtHis.getText(), txtLabSci.getText(), txtMath.getText(),
                txtHum.getText(), txtBus.getText(), txtEng.getText(), txtCom.getText(), txtAmerHis.getText(), txtSocSci.getText(),
                txtLang.getText(), txtPhl.getText(), lstCoursesReq.getItems().toArray(new Course[lstCoursesReq.getItems().size()]), edit);
            if (listenerMajorAdd != null) {
                listenerMajorAdd.add(evm);
            }
            if (!evm.isValid()) {
                Alert alert = new Alert(AlertType.ERROR, evm.getErrorMessage(), ButtonType.OK);
                alert.showAndWait();
            } else {
                majors = evm.getMajors();
                newMajorStage.close();
            }
        });
        btnAddCourse.setOnAction(eac -> {
            try {
                if (lstCourses.getSelectionModel().getSelectedItem() == null) {
                    throw new IllegalArgumentException();
                } else if (lstCoursesReq.getItems().contains(lstCourses.getSelectionModel().getSelectedItem())) {
                    Alert alert = new Alert(AlertType.ERROR, "Error, course already required for the major.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    lstCoursesReq.getItems().add(lstCourses.getSelectionModel().getSelectedItem());
                    lstCoursesReq.setSelectionModel(defaultSelect);
                    lstCourses.setSelectionModel(defaultSelect);
                }
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
                lstCoursesReq.getItems().remove(lstCoursesReq.getSelectionModel().getSelectedItem());
                lstCoursesReq.setSelectionModel(defaultSelect);
                lstCourses.setSelectionModel(defaultSelect);
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR, "Error, no course selected!\nPlease select a course then try again.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        VBox pane = new VBox();
        pane.getChildren().addAll(lblRequirements, gridOut, hbxCourses, hbxButtons);
        Scene scene = new Scene(pane, 1280, 1080);
        newMajorStage.setScene(scene);
        if (!edit) {
            newMajorStage.setTitle("New Major");
        } else {
            newMajorStage.setTitle("Edit Major");
        }
        newMajorStage.showAndWait();
    }
    /*
     * Creates a window which allows the user to edit a previously selected faculty account.
     * @param user The user to be edited
     * @param admin The logged in user
     */
    public void editFacultyView(User user, Administrator admin) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        Label lblID = new Label("ID # : ");
        grid.add(lblID, 0, 0);
        TextField txtID = new TextField();
        grid.add(txtID, 1, 0);
        txtID.setText(parseId(user.getId()));
        txtID.setEditable(false); //Don't allow user to change id number
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
        Label lblUsername = new Label("Username : ");
        grid.add(lblUsername, 0, 9);
        TextField txtUsername = new TextField();
        grid.add(txtUsername, 1, 9);
        Label lblBirthDate = new Label("Birth Date : ");
        grid.add(lblBirthDate, 0, 10);
        DatePicker dtpBirthDate = new DatePicker();
        grid.add(dtpBirthDate, 1, 10);
        Label lblPassword = new Label("Password : ");
        grid.add(lblPassword, 0, 11);
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Leave this field blank to leave the password unchanged.");
        grid.add(txtPassword, 1, 11);
        grid.setAlignment(Pos.CENTER);
        Button btnBack = new Button("Cancel");
        Button btnEdit = new Button("Edit Faculty Data");
        Button btnClear = new Button("Clear");
        Button btnDelete = new Button("Delete User");
        HBox hbxButtons = new HBox();
        txtFirstName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        txtAddress.setText(user.getAddress());
        txtCity.setText(user.getCity());
        txtZipCode.setText(Integer.toString(user.getZipCode()));
        txtState.setText(user.getState());
        txtSSN.setText(user.getSocialSecNum());
        txtUsername.setText(user.getUsername());
        dtpBirthDate.setValue(user.getDateOfBirth());
        btnBack.setOnAction(e -> {
            adminView(admin, majors, courses);
        });
        hbxButtons.getChildren().addAll(btnBack, btnClear, btnEdit, btnDelete);
        hbxButtons.setAlignment(Pos.CENTER);
        hbxButtons.setSpacing(20);
        btnEdit.setOnAction(e -> {
            StaffEditEventObject ev = new StaffEditEventObject(btnEdit, txtUsername.getText(), user.getId(), txtFirstName.getText(), txtLastName.getText(), dtpBirthDate.getValue(), txtAddress.getText(), txtCity.getText(), txtState.getText(), txtZipCode.getText(), txtSSN.getText(), txtPassword.getText(), user.isAdministrator());
            if (listenerEdit != null)
                listenerEdit.editStaff(ev);
            if (!ev.isValid()) {
                Alert alert = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
                alert.showAndWait();
            } else {
                adminView(admin, majors, courses); //If valid, go back to default control panel
            }
        });
        btnDelete.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION, "This will permanently delete the user.  Are you sure you want to continue?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    RemoveStaffEventObject ev = new RemoveStaffEventObject(btnDelete, user.getId());
                    if (listenerDelete != null) {
                        listenerDelete.removeStaff(ev);
                    }
                    if (ev.isValid()) {
                        adminView(admin, majors, courses); //If successful, go back to default control panel
                    } else {
                        Alert alert2 = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
                        alert2.showAndWait();
                    }
                }
            });
        });
        btnClear.setOnAction(e -> {
            txtFirstName.clear();
            txtLastName.clear();
            txtAddress.clear();
            txtCity.clear();
            txtZipCode.clear();
            txtState.clear();
            txtSSN.clear();
            dtpBirthDate.setValue(LocalDate.now());
            txtPassword.clear();
            txtUsername.clear();
        });
        VBox pane = new VBox();
        pane.getChildren().addAll(logoutHBox(admin), grid, hbxButtons);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 800, 1100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Save Data");
        primaryStage.show();
    }
    /*
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
    public void setListenerPassword(PasswordListener listenerPassword) {
        this.listenerPassword = listenerPassword;
    }
    public void setListenerLogout(LogoutListener logoutListener) {
        this.listenerLogout = logoutListener;
    }
    public void setListenerNewAccount(CreateAccountListener listenerAccount) {
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
    public void setListenerEdit(StaffEditListener listenerEdit) {
        this.listenerEdit = listenerEdit;
    }
    public void setListenerSearch(SearchListener listenerSearch) {
        this.listenerSearch = listenerSearch;
    }
    public void setListenerAdmin(AdminEditListener listenerAdmin) {
        this.listenerAdmin = listenerAdmin;
    }
    public void setListenerDelete(RemoveStaffListener listenerDelete) {
        this.listenerDelete = listenerDelete;
    }
    public void setListenerSQL(SQLListener listenerSQL) {
    	this.listenerSQL = listenerSQL;
    }
    /*
     * Appends 0's to the beginning of the ID if necessary to create an 8-digit ID
     * @param id ID number to be parsed
     * @return Parsed ID number.  This may be equal to the original ID number, if that number was greater than 10000000
     */
    private String parseId(int id) {
        String returnString = Integer.toString(id);
        int index = returnString.length();
        if (index < 8) {
            for (int i = 0; i < 8 - index; i++) {
                returnString = "0" + returnString;
            }
        }
        return returnString;
    }
    private Course getCourse(String courseCode) {
        Course returnCourse = null;
        for (Course c: courses) {
            if (c.getCourseCode().equals(courseCode)) {
                returnCourse = c;
            }
        }
        return returnCourse;
    }
}