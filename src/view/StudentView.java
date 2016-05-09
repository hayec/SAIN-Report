package view;

import eventHandlers.BackEventObject;
import eventHandlers.BackListener;
import eventHandlers.LogoutEventObject;
import eventHandlers.LogoutListener;
import eventHandlers.PasswordEventObject;
import eventHandlers.PasswordListener;
import eventHandlers.SAINEventObject;
import eventHandlers.SAINListener;
import eventHandlers.SearchListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import report.Course;
import report.CourseBag;
import user.Major;
import user.MajorBag;
import user.Student;
import user.User;

public class StudentView {
    private Stage primaryStage;
    PasswordListener listenerPassword;
    LogoutListener listenerLogout;
    BackListener listenerBack;
    SAINListener listenerSAIN;
    Major[] majorCache;
    Major studentMajor;
    public StudentView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    /*
     * Creates a window which allows the user to select a major, then view a What-If Analysis or SAIN Report 
     */
    public void studentStart(User user, Student student, Major[] majors, Course[] allCourses) {
        studentMajor = student.getMajor(); //Save so it can be reset after What-If Analysis
        majorCache = majors;
        Label lblCurrentMajor = new Label("Your currently declared major is : " + student.getMajor());
        Label lblMajor = new Label("Please select a major to continue : ");
        ComboBox < Major > cmbMajor = new ComboBox < Major > ();
        for (int i = 0; i < majors.length; i++)
            cmbMajor.getItems().add(majors[i]);
        cmbMajor.setValue(student.getMajor());
        HBox hbxInput = new HBox();
        hbxInput.getChildren().addAll(lblMajor, cmbMajor);
        hbxInput.setAlignment(Pos.CENTER);
        hbxInput.setSpacing(15);
        Button btnContinue = new Button("Continue(What-If Analysis)");
        btnContinue.setOnAction(e -> {
            SAINEventObject ev = new SAINEventObject(btnContinue, cmbMajor.getSelectionModel().getSelectedItem(), user, student);
            if (listenerSAIN != null) {
                listenerSAIN.getReport(ev);
                if (!ev.isValid()) {
                    Alert alert = new Alert(AlertType.ERROR, ev.getErrorMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
        Button btnViewSAIN = new Button("View SAIN Report");
        btnViewSAIN.setOnAction(e -> {
            studentView(user, student, allCourses);
        });
        HBox hbxButtons = new HBox();
        if (!user.isStudent()) {
            Button btnBack = new Button("Back");
            hbxButtons.getChildren().addAll(btnBack);
            btnBack.setOnAction(e -> {
                BackEventObject ev = new BackEventObject(btnBack);
                if (listenerBack != null)
                    listenerBack.back(ev);
            });
        }
        hbxButtons.getChildren().addAll(btnContinue, btnViewSAIN);
        hbxButtons.setAlignment(Pos.CENTER);
        hbxButtons.setSpacing(15);
        VBox pane = new VBox();
        pane.getChildren().addAll(logoutHBox(user), lblCurrentMajor, hbxInput, hbxButtons);
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(15);
        Scene scene = new Scene(pane, 1200, 400);
        primaryStage.setScene(scene);
    }
    /*
     * Creates a window which displays a What-If Analysis or SAIN Report
     * @param user Logged in user
     * @param student Current target
     * @param allCourses Array of all defined courses 
     */
    public void studentView(User user, Student student, Course[] allCourses) {
        Label lblSelectedMajor = new Label("Currently Selected Major is : " + student.getMajor());
        Label lblNumOfCredits = new Label("Number of Credits Attempted : " + student.numOfCredits());
        Label lblNumOfCreditsPass = new Label("Number of Credits Successfully Completed : " + student.numOfCreditsPassed());
        Label lblCoursePass = new Label("Courses Successfully Completed : ");
        ListView <Course> lstCoursesPassed = new ListView < Course > ();
        Label lblMajorCoursesPass = new Label("Major Requirements Successfully Completed : ");
        ListView <Course> lstMajorCoursesPassed = new ListView < Course > ();
        Label lblCourseFail = new Label("Courses Unsuccessfully Completed : ");
        ListView < Course > lstCoursesFailed = new ListView < Course > ();
        Label lblCourseNeed = new Label("Courses Necessary for Graduation : ");
        ListView < Course > lstCoursesNeeded = new ListView < Course > ();
        for (int i = 0; i < student.getPassedCourses().length; i++) {
            lstCoursesPassed.getItems().add(student.getPassedCourses()[i]);
        }
        for (int i = 0; i < student.getMajor().getMajorCoursesDone(student).length; i++) {
            lstMajorCoursesPassed.getItems().add(student.getMajor().getMajorCoursesDone(student)[i]);
        }
        for (int i = 0; i < student.getFailedCourses().length; i++) {
            lstCoursesFailed.getItems().add(student.getFailedCourses()[i]);
        }
        Course[] coursesReq = student.getMajor().getCoursesReq(student.clone());
        for (int i = 0; i < coursesReq.length; i++) {
            if (coursesReq[i].toString() != null && !coursesReq[i].equals("null"))
                lstCoursesNeeded.getItems().add(coursesReq[i]);
        }
        Label lblSemestersNeeded = new Label("A minimum of " + student.clone().semestersNeeded(allCourses) + " semesters are necessary to complete this degree.");
        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> {
            student.setMajor(studentMajor);
            studentStart(user, student, majorCache, allCourses);
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
        grid.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        pane.getChildren().addAll(logoutHBox(user), grid);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 1920, 1080);
        primaryStage.setScene(scene);
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
    public void setListenerLogout(LogoutListener listenerLogout) {
        this.listenerLogout = listenerLogout;
    }
    public void setListenerBack(BackListener listenerBack) {
        this.listenerBack = listenerBack;
    }
    public void setListenerSAIN(SAINListener listenerSAIN) {
        this.listenerSAIN = listenerSAIN;
    } 
}