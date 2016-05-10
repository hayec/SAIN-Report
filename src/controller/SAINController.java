package controller;

import eventHandlers.AddCourseEventObject;
import eventHandlers.AddCourseListener;
import eventHandlers.AddMajorEventObject;
import eventHandlers.AddMajorListener;
import eventHandlers.AdminBackEventObject;
import eventHandlers.AdminBackListener;
import eventHandlers.AdminEditEventObject;
import eventHandlers.AdminEditListener;
import eventHandlers.BackEventObject;
import eventHandlers.BackListener;
import eventHandlers.CreateAccountEventObject;
import eventHandlers.CreateAccountListener;
import eventHandlers.DeleteCourseEventObject;
import eventHandlers.DeleteCourseListener;
import eventHandlers.DeleteMajorEventObject;
import eventHandlers.DeleteMajorListener;
import eventHandlers.EditEventObject;
import eventHandlers.EditListener;
import eventHandlers.LoginEventObject;
import eventHandlers.LoginListener;
import eventHandlers.LogoutEventObject;
import eventHandlers.LogoutListener;
import eventHandlers.ModelChangedEventObject;
import eventHandlers.ModelListener;
import eventHandlers.NewAccountEventObject;
import eventHandlers.NewAccountListener;
import eventHandlers.PasswordEventObject;
import eventHandlers.PasswordListener;
import eventHandlers.RemoveStaffEventObject;
import eventHandlers.RemoveStaffListener;
import eventHandlers.RemoveStudentEventObject;
import eventHandlers.RemoveStudentListener;
import eventHandlers.ReportEventObject;
import eventHandlers.ReportListener;
import eventHandlers.SAINEventObject;
import eventHandlers.SAINListener;
import eventHandlers.SQLEventObject;
import eventHandlers.SQLListener;
import eventHandlers.SearchEventObject;
import eventHandlers.SearchListener;
import eventHandlers.StaffEditEventObject;
import eventHandlers.StaffEditListener;
import javafx.stage.Stage;
import report.Course;
import report.CourseAttributes;
import report.CourseBag;
import user.Administrator;
import user.Faculty;
import user.Major;
import user.MajorBag;
import user.Student;
import user.User;
import user.UserBag;
import view.AdminView;
import view.LoginView;
import view.StaffView;
import view.StudentView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * The sole controller class for the SAIN Report application.  Handles and validates input from 
 * classes in the view package, and adds or retrieves data from classes in the report and user packages as necessary.
 */    
public class SAINController {
    final int USERNAME_LENGTH = 64;
    MessageDigest md;
    Stage primaryStage;
    LoginView loginView;
    StudentView studentView;
    StaffView staffView;
    AdminView adminView;
    User currentUser;
    UserBag users = new UserBag();
    CourseBag courses = new CourseBag();
    MajorBag majors = new MajorBag();
    private boolean loading;
    private boolean sql = false;//Default to binary file
    public SAINController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loginView = new LoginView(primaryStage);
        studentView = new StudentView(primaryStage);
        staffView = new StaffView(primaryStage);
        adminView = new AdminView(primaryStage);
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {}
        setLoginListeners();
        setAdminListeners();
        setStaffListeners();
        setStudentListeners();
        setModelListeners();
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!primaryStage.isShowing()) {
            loginView.start();
        }
    }
    /**
     * Sets listeners to wait for thrown event objects from the classes in the view package
     */
    public void setLoginListeners() {
        loginView.setListenerAccount(new NewAccountListener() {
            @Override
            public void newAccount(NewAccountEventObject ev) {
                if (ev.getPassword().equals(ev.getPassword().toLowerCase()) || ev.getPassword().equals(ev.getPassword().toUpperCase()) || 
                		ev.getPassword().length() < 8 || ev.getPassword().length() > 32 || !ev.getPassword().matches(".*\\d.*")) {
                    ev.setValidPassword(false);
                    ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
                } else if (ev.getUsername().length() > USERNAME_LENGTH) {
                    ev.setValidPassword(false);
                    ev.setErrorMessage("Error, username must be less than" + USERNAME_LENGTH + " characters.");
                } else {
                    ev.setValidPassword(true);
                    int id = generateId(false);
                    users.addUser(new Administrator(id, ev.getUsername(), new String(md.digest(ev.getPassword().getBytes())), "Default", ""));
                    currentUser = users.getUser(id);
                    adminView.adminView((Administrator) currentUser.getUser(), majors.getMajors(), courses.getCourses());
                    saveData();
                }
            }
        });
        loginView.setListenerLogin(new LoginListener() {
            @Override
            public void login(LoginEventObject ev) {
                try {
                    if (users.getUser(ev.getUsername()).correctPassword(new String(md.digest(ev.getPassword().getBytes())))) {
                        ev.setCredentialsValid(true);
                        currentUser = users.getUser(ev.getUsername());
                        if (currentUser.isStudent()) {
                            studentView.studentStart(currentUser, (Student) currentUser, majors.getMajors(), courses.getCourses());
                        } else if (currentUser.isFaculty()) {
                            staffView.start(currentUser.isAdministrator(), currentUser, majors.getMajors());
                        } else {
                            adminView.adminView((Administrator) currentUser.getUser(), majors.getMajors(), courses.getCourses());
                        }
                    } else
                        ev.setCredentialsValid(false);
                } catch (Exception e) {
                    ev.setCredentialsValid(false);
                }
            }
        });
    }
    /**
     * Sets listeners to wait for thrown event objects from the AdminView class in the view package
     */    
    public void setAdminListeners() {
        adminView.setListenerLogout(new LogoutListener() {
            @Override
            public void logout(LogoutEventObject ev) {
                currentUser = null;
                loginView.start();
            }
        });
        adminView.setListenerSQL(new SQLListener(){
			@Override
			public void setSQL(SQLEventObject ev) {
				if(ev.isSQL()) {
					sql = true;
				} else {
					sql = false;
				}
				saveSQL();
			}
        });
        adminView.setListenerStudentSearch(new BackListener() {
            @Override
            public void back(BackEventObject ev) {
                staffView.start(currentUser.isAdministrator(), currentUser, majors.getMajors(), courses.getCourses());
            }
        });
        adminView.setListenerPassword(new PasswordListener() {
            @Override
            public void changePassword(PasswordEventObject ev) {
                if (currentUser.correctPassword(new String(md.digest(ev.getOldPassword().getBytes())))) {
                    if (ev.getNewPassword().equals(ev.getNewPassword().toLowerCase()) || ev.getNewPassword().equals(ev.getNewPassword().toUpperCase()) 
                    		|| ev.getNewPassword().length() < 8 || ev.getNewPassword().length() > 32 || !ev.getNewPassword().matches(".*\\d.*")) {
                        ev.setSuccessful(false);
                        ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
                    } else {
                        if (ev.getNewPassword().equals(ev.getNewPasswordConf())) {
                            ev.setSuccessful(true);
                            currentUser.setPassword(new String(md.digest(ev.getNewPassword().getBytes())));
                            saveData();
                        } else {
                            ev.setSuccessful(false);
                            ev.setErrorMessage("Error, new passwords do not match!");
                        }
                    }
                } else {
                    ev.setSuccessful(false);
                    ev.setErrorMessage("Error, old password is incorrect!");
                }
            }
        });
        adminView.setListenerNewAccount(new CreateAccountListener() {
            public void createAccount(CreateAccountEventObject ev) {
                int id;
                try {
                    id = Integer.parseInt(ev.getId());
                } catch (Exception e) {
                    id = -1;
                    if (!ev.getId().equals("")) {
                        ev.setErrorMessage("ID# is formatted incorrectly.");
                    }
                }
                if (ev.getPassword().equals(ev.getPassword().toLowerCase()) || ev.getPassword().equals(ev.getPassword().toUpperCase()) 
                		|| ev.getPassword().length() < 8 || ev.getPassword().length() > 32 || !ev.getPassword().matches(".*\\d.*")) {
                    ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
                }
                if (ev.getUsername().length() > USERNAME_LENGTH) {
                    ev.setErrorMessage("Error, username must be less than" + USERNAME_LENGTH + " characters.");
                }
                if (ev.getId().equals("") || ev.getId() == null) {
                    if (ev.getMajor() != null) { //If there is a declared major, then this must be a student
                        id = generateId(true); //Generate student ID
                        ev.setId(id);
                        if (id < 0) {
                            ev.setErrorMessage("All ID numbers are currently in use.  Please delete an account of the same type to continue.  "
                        + "Alternatively, contact the software developer to extend the number of allowed users.");
                        }
                    } else {
                        id = generateId(false); //Generate staff ID
                        ev.setId(id);
                        if (id < 0) {
                            ev.setErrorMessage("All ID numbers are currently in use.  Please delete an account of the same type to continue. " + 
                        " Alternatively, contact the software developer to extend the number of allowed users.");
                        }
                    }
                }
                if (id >= 0) {
                    if (users.getUser(Integer.parseInt(ev.getId())) != null) {
                        ev.setErrorMessage("User ID is already in use.  Please choose another ID number.  Alternatively, leave the field blank " + 
                    "and an ID number will be automatically generated.");
                    }
                }
                if (ev.getMajor() != null) {
                    if (id > 79999999) {
                        ev.setErrorMessage("User ID is too large.  Maximum Size is 79999999.  Please choose another ID number.  Alternatively, " + 
                    "leave the field blank and an ID number will be automatically generated.");
                    }
                } else {
                    if (id > 99999999 || id < 80000000) {
                        ev.setErrorMessage("User ID is invalid.  ID number must be in the range of 80000000-99999999.  Please choose another " + 
                    "ID number.  Alternatively, leave the field blank and an ID number will be automatically generated.");
                    }
                }
                if (ev.getFirstName().equals("") || ev.getFirstName() == null) {
                    ev.setErrorMessage("First Name cannot be left blank");
                }
                if (ev.getLastName().equals("") || ev.getLastName() == null) {
                    ev.setErrorMessage("Last Name cannot be left blank");
                }
                int zipCode;
                try {
                    zipCode = Integer.parseInt(ev.getZipCode());
                } catch (Exception e) {
                    zipCode = -1;
                    ev.setErrorMessage("Zip code is formatted incorrectly.");
                }
                if (zipCode < 0 || zipCode > 99999) {
                    ev.setErrorMessage("Zip code must be a number between 00000 and 99999");
                }
                if (ev.getDateOfBirth().isAfter(LocalDate.now().minusYears(13))) {
                    ev.setErrorMessage("New user must be at least 13 years old.");
                }
                if (!ev.getDateOfBirth().isAfter(LocalDate.now().minusYears(120))) {
                    ev.setErrorMessage("New user must not be more than 120 years old.");
                }
                if (ev.getMajor() != null) {
                    if (ev.getDateEnrolled().isAfter(LocalDate.now())) {
                        ev.setErrorMessage("Date of enrollment cannot be in the future.");
                    }
                    if (!ev.getDateEnrolled().isAfter(LocalDate.parse("1959-12-01"))) {
                        ev.setErrorMessage("Date of enrollment cannot be prior to college founding.");
                    }
                }
                if (users.getUser(ev.getUsername()) != null) {
                    ev.setErrorMessage("Username is already in use.  Please choose another username.");
                }
                if (ev.getUsername().equals("") || ev.getUsername() == null) {
                    ev.setErrorMessage("Username cannot be left blank");
                }
                if (ev.getErrorMessage().length() == 0) {
                    if (ev.getMajor() == null) {
                        if (ev.isAdmin()) {
                            users.addUser(new Administrator(id, ev.getFirstName(), ev.getLastName(), ev.getDateOfBirth(), ev.getAddress(), ev.getCity(), ev.getState(), 
                            		zipCode, ev.getSocSecNum(), ev.getUsername(), new String(md.digest(ev.getPassword().getBytes()))));
                        } else {
                            users.addUser(new Faculty(id, ev.getFirstName(), ev.getLastName(), ev.getDateOfBirth(), ev.getAddress(), ev.getCity(), ev.getState(), 
                            		zipCode, ev.getSocSecNum(), ev.getUsername(), new String(md.digest(ev.getPassword().getBytes()))));
                        }
                    } else {
                        users.addUser(new Student(id, ev.getFirstName(), ev.getLastName(), ev.getDateEnrolled(), ev.getDateOfBirth(), ev.getSocSecNum(), ev.getAddress(),
                        		ev.getCity(), zipCode, ev.getState(), ev.getCampus(), ev.getMajor(), ev.getUsername(), new String(md.digest(ev.getPassword().getBytes()))));
                    }
                    ev.setId(id);
                    ev.setValidAccount(true);
                }
            }
        });
        adminView.setListenerMajorAdd(new AddMajorListener() {
            @Override
            public void add(AddMajorEventObject ev) {
                if (ev.getName().equals("") || ev.getName() == null) {
                    ev.setErrorMessage("Name cannot be left blank!");
                }
                if (majors.getMajor(ev.getName()) != null && !ev.isEdit()) {
                    ev.setErrorMessage("Major already exists!");
                }
                if (ev.getAmerHisReq().equals("")) {
                    ev.setAmerHisReq("0");
                }
                if (ev.getBusReq().equals("")) {
                    ev.setBusReq("0");
                }
                if (ev.getComReq().equals("")) {
                    ev.setComReq("0");
                }
                if (ev.getEngReq().equals("")) {
                    ev.setEngReq("0");
                }
                if (ev.getHisReq().equals("")) {
                    ev.setHisReq("0");
                }
                if (ev.getHumReq().equals("")) {
                    ev.setHumReq("0");
                }
                if (ev.getLabSciReq().equals("")) {
                    ev.setLabSciReq("0");
                }
                if (ev.getLangReq().equals("")) {
                    ev.setLangReq("0");
                }
                if (ev.getMathReq().equals("")) {
                    ev.setMathReq("0");
                }
                if (ev.getPhlReq().equals("")) {
                    ev.setPhlReq("0");
                }
                if (ev.getSocSciReq().equals("")) {
                    ev.setSocSciReq("0");
                }
                if (ev.getMinGPA().equals("")) {
                    ev.setMinGPA("0");
                }
                try {
                    if (Integer.parseInt(ev.getAmerHisReq()) < 0 || Integer.parseInt(ev.getBusReq()) < 0 || Integer.parseInt(ev.getComReq()) < 0 ||
                        Integer.parseInt(ev.getEngReq()) < 0 || Integer.parseInt(ev.getHisReq()) < 0 || Integer.parseInt(ev.getHumReq()) < 0 ||
                        Integer.parseInt(ev.getLabSciReq()) < 0 || Integer.parseInt(ev.getLangReq()) < 0 || Integer.parseInt(ev.getMathReq()) < 0 ||
                        Integer.parseInt(ev.getPhlReq()) < 0 || Integer.parseInt(ev.getSocSciReq()) < 0) {
                        ev.setErrorMessage("All course requirements must be positive.");
                    } else if (Integer.parseInt(ev.getAmerHisReq()) > 10 || Integer.parseInt(ev.getBusReq()) > 10 || Integer.parseInt(ev.getComReq()) > 10 ||
                        Integer.parseInt(ev.getEngReq()) > 10 || Integer.parseInt(ev.getHisReq()) > 10 || Integer.parseInt(ev.getHumReq()) > 10 ||
                        Integer.parseInt(ev.getLabSciReq()) > 10 || Integer.parseInt(ev.getLangReq()) > 10 || Integer.parseInt(ev.getMathReq()) > 10 ||
                        Integer.parseInt(ev.getPhlReq()) > 10 || Integer.parseInt(ev.getSocSciReq()) > 10) {
                        ev.setErrorMessage("All course requirements have a maximum value of 10.");
                    }
                } catch (Exception e) {
                    ev.setErrorMessage("All course requirements must be integers.");
                }
                try {
                    if (Double.parseDouble(ev.getMinGPA()) < 0) {
                        ev.setErrorMessage("Min GPA must be positive.");
                    } else if (Double.parseDouble(ev.getMinGPA()) > 4.0) {
                        ev.setErrorMessage("Min GPA cannot be greater than 4.0.");
                    }
                } catch (Exception e) {
                    ev.setErrorMessage("Min GPA must be a number.");
                }
                try {
                    if (Integer.parseInt(ev.getNumOfCreditsReq()) < 0) {
                        ev.setErrorMessage("Required number of credits must be a positive integer.");
                    } else if (Integer.parseInt(ev.getNumOfCreditsReq()) >= 100) {
                        ev.setErrorMessage("Required number of credits must be less than 100.");
                    }
                } catch (Exception e) {
                    ev.setErrorMessage("Required number of credits must be an integer");
                }
                if (ev.getErrorMessage().equals("") || ev.getErrorMessage() == null) {
                    ev.setValid(true);
                } else {
                    ev.setValid(false);
                }
                if (ev.isValid()) {
                    if (ev.isEdit() && majors.getMajor(ev.getName()) != null) { //If user is editing a major, then delete it before adding the edited major
                        majors.removeMajor(ev.getName());
                    }
                    majors.addMajor(new Major(ev.getName(), Integer.parseInt(ev.getPhysEdReq()), Integer.parseInt(ev.getHisReq()), Integer.parseInt(ev.getLabSciReq()),
                        Integer.parseInt(ev.getMathReq()), Integer.parseInt(ev.getHumReq()), Integer.parseInt(ev.getBusReq()), Integer.parseInt(ev.getEngReq()),
                        Integer.parseInt(ev.getComReq()), Integer.parseInt(ev.getAmerHisReq()), Integer.parseInt(ev.getSocSciReq()), Integer.parseInt(ev.getLangReq()),
                        Integer.parseInt(ev.getPhlReq()), Integer.parseInt(ev.getNumOfCreditsReq()), Double.parseDouble(ev.getMinGPA()), ev.getReqCourses()));
                    ev.setMajors(majors.getMajors());
                }
            }
        });
        adminView.setListenerCourseAdd(new AddCourseListener() {
            @Override
            public void add(AddCourseEventObject ev) {
                int credits = 0;
                if (ev.getCourseCode() == null || ev.getCourseCode().equals("")) {
                    ev.setErrorMessage("Course code cannot be left blank");
                }
                if (ev.getCourseTitle() == null || ev.getCourseTitle().equals("")) {
                    ev.setErrorMessage("Course title cannot be left blank");
                }
                try {
                    if (Integer.parseInt(ev.getCredits()) < 0) {
                        ev.setErrorMessage("Number of credits must be positive.");
                    } else if (Integer.parseInt(ev.getCredits()) > 6) {
                        ev.setErrorMessage("The maximum number of credits allowed is 6.");
                    } else {
                        credits = Integer.parseInt(ev.getCredits());
                    }
                } catch (Exception e) {
                    ev.setErrorMessage("Number of credits must be an integer.");
                }
                if (ev.getErrorMessage().equals("") || ev.getErrorMessage() == null) {
                    Course c = new Course(ev.getCourseCode(), ev.getCourseTitle(), ev.getCourseDescription(), ev.isAmmerman(), ev.isGrant(), ev.isEastern(), 
                    		ev.getPrerequisites(), ev.getCorequisites(), credits);
                    c.CAttributes = new CourseAttributes(ev.isPhysEd(), ev.isHistory(), ev.isLabScience(), ev.isMath(), ev.isHumanities(), ev.isBusiness(), 
                    		ev.isEnglish(), ev.isCommunications(), ev.isAmerHis(), ev.isSocScience(), ev.isLanguage(), ev.isPhilosophy());
                    courses.addCourse(c);
                    ev.setCourses(courses.getCourses());
                    ev.setValid(true);
                }
            }
        });
        adminView.setListenerMajorDelete(new DeleteMajorListener() {
            @Override
            public void delete(DeleteMajorEventObject ev) {
                majors.removeMajor(ev.getTarget().getName());
                for (Student s: users.getStudents()) //Set all students with deleted major as declared major to undeclared majors
                {
                    if (s.getMajor().equals(ev.getTarget())) {
                        users.removeUser(s.getId());
                        Student temp = s.clone();
                        temp.setMajor(new Major());
                        users.addUser(temp);
                    }
                }
                ev.setMajors(majors.getMajors());
            }
        });
        adminView.setListenerCourseDelete(new DeleteCourseListener() {
            @Override
            public void delete(DeleteCourseEventObject ev) {
                courses.removeCourse(ev.getTarget().getCourseCode());
                for (Course c: courses.getCourses()) {
                    Course courseTemp = c.clone();
                    for (String s: c.getPrerequisites()) { //Loop through and remove all prereqs which correspond to course being removed
                        if (s.equals(ev.getTarget().getCourseCode())) {
                            List < String > tempPre1 = (List < String > ) Arrays.asList(courseTemp.getPrerequisites()); //Create temporary arraylist to hold course prereqs
                            ArrayList < String > tempPre = new ArrayList < String > ();
                            for (int i = 0; i < tempPre1.size(); i++) {
                                tempPre.add(tempPre1.get(i));
                            }
                            tempPre.remove(s); //Remove offending course
                            if (tempPre.size() == 0) {
                                courseTemp.setPrerequisites(new String[0]); //Set to String[0] instead of null if no prereqs are left
                            } else {
                                courseTemp.setPrerequisites(tempPre.toArray(new String[tempPre.size()])); //Reset prereqs
                            }
                        }
                    }
                    for (String s: c.getCorequisites()) { //Loop through and remove all coreqs which correspond to course being removed
                        if (s.equals(ev.getTarget().getCourseCode())) {
                            List < String > tempCo1 = (List < String > ) Arrays.asList(courseTemp.getCorequisites()); //Create temporary arraylist to hold course coreqs
                            ArrayList < String > tempCo = new ArrayList < String > ();
                            for (int i = 0; i < tempCo1.size(); i++) {
                                tempCo.add(tempCo1.get(i));
                            }
                            tempCo.remove(s); //Remove offending course
                            if (tempCo.size() == 0) {
                                courseTemp.setCorequisites(new String[0]); //Set to String[0] instead of null if no coreqs are left
                            } else {
                                courseTemp.setCorequisites(tempCo.toArray(new String[tempCo.size()])); //Reset coreqs
                            }
                        }
                    }
                    if (!courseTemp.equals(courseTemp)) { //Only change if modifications were made
                        courses.removeCourse(c.getCourseCode());
                        courses.addCourse(courseTemp);
                    }
                }
                for (Major m: majors.getMajors()) {
                    for (Course c: m.getReqCourses()) {
                        try {
                            if (c.getCourseCode().equals(ev.getTarget().getCourseCode())) {
                                List < Course > tempCourse1 = (List < Course > ) Arrays.asList(m.getReqCourses()); //Create temporary arraylist to hold course prereqs
                                ArrayList < Course > tempCourse = new ArrayList < Course > ();
                                for (int i = 0; i < tempCourse1.size(); i++) {
                                    tempCourse.add(tempCourse1.get(i));
                                }
                                tempCourse.remove(c); //Remove offending course
                                m.setCoursesReq(tempCourse.toArray(new Course[tempCourse.size()])); //Reset major requirements
                            }
                        } catch (Exception e) {} //Catch if null in major reqs
                    }
                }
                for (Student s: users.getStudents()) {
                    for (Course c: s.getCourseWork()) {
                        if (c.getCourseCode().equals(ev.getTarget().getCourseCode())) {
                            s.removeCourse(c); //Remove nonexistant course from record
                            users.removeUser(s.getId()); //Remove old student from model
                            users.addUser(s); //Update model
                        }
                    }
                }
                ev.setCourses(courses.getCourses());
            }
        });
        adminView.setListenerEdit(new StaffEditListener() {
            @Override
            public void editStaff(StaffEditEventObject ev) {
                if (!(ev.getPassword() == null || ev.getPassword().equals(""))) {
                    if (ev.getPassword().equals(ev.getPassword().toLowerCase()) || ev.getPassword().equals(ev.getPassword().toUpperCase()) 
                    		|| ev.getPassword().length() < 8 || ev.getPassword().length() > 32 || !ev.getPassword().matches(".*\\d.*")) {
                        ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
                    } else {
                        ev.setPassword(new String(md.digest(ev.getPassword().getBytes()))); //If password is valid, set password to hashed password
                    }
                } else {
                    ev.setPassword(users.getUser(ev.getId()).getPassword()); //If password is blank, set password to current password
                }
                if (ev.getUsername().length() > USERNAME_LENGTH) {
                    ev.setErrorMessage("Error, username must be less than" + USERNAME_LENGTH + " characters.");
                }
                if (ev.getFirstName().equals("") || ev.getFirstName() == null) {
                    ev.setErrorMessage("First Name cannot be left blank");
                }
                if (ev.getLastName().equals("") || ev.getLastName() == null) {
                    ev.setErrorMessage("Last Name cannot be left blank");
                }
                int zipCode;
                try {
                    zipCode = Integer.parseInt(ev.getZipCode());
                } catch (Exception e) {
                    zipCode = -1;
                    ev.setErrorMessage("Zip code is formatted incorrectly.");
                }
                if (zipCode < 0 || zipCode > 99999) {
                    ev.setErrorMessage("Zip code must be a number between 00000 and 99999");
                }
                if (ev.getDateOfBirth() == null) {
                    ev.setErrorMessage("Please choose a birth date for the user.");
                } else {
                    if (ev.getDateOfBirth().isAfter(LocalDate.now().minusYears(13))) {
                        ev.setErrorMessage("New user must be at least 13 years old.");
                    }
                    if (!ev.getDateOfBirth().isAfter(LocalDate.now().minusYears(120))) {
                        ev.setErrorMessage("New user must not be more than 120 years old.");
                    }
                }
                if (users.getUser(ev.getUsername()) != null && users.getUser(ev.getUsername()).getId() != ev.getId()) {
                    ev.setErrorMessage("Username is already in use.  Please choose another username.");
                }
                if (ev.getUsername().equals("") || ev.getUsername() == null) {
                    ev.setErrorMessage("Username cannot be left blank");
                }
                if (ev.getErrorMessage().length() == 0) {
                    users.removeUser(ev.getId());
                    if (ev.isAdmin()) {
                        users.addUser(new Administrator(ev.getId(), ev.getFirstName(), ev.getLastName(), ev.getDateOfBirth(), ev.getAddress(), 
                        		ev.getCity(), ev.getState(), zipCode, ev.getSocialSecNum(), ev.getUsername(), ev.getPassword()));
                    } else {
                        users.addUser(new Faculty(ev.getId(), ev.getFirstName(), ev.getLastName(), ev.getDateOfBirth(), ev.getAddress(),
                        		ev.getCity(), ev.getState(), zipCode, ev.getSocialSecNum(), ev.getUsername(), ev.getPassword()));
                    }
                    ev.setValid(true);
                }
            }
        });
        adminView.setListenerSearch(new SearchListener() {
            @Override
            public void search(SearchEventObject ev) {
                if (!(ev.getId() == null || ev.getId().equals(""))) {
                    try {
                        User[] user = new User[1];
                        user[0] = (User) users.getUser(Integer.parseInt(ev.getId()));
                        if (user[0] != null && !user[0].isStudent()) { //If an admin or faculty user was found, then continue
                            ev.setUserResults(user);
                            ev.setInputValid(true);
                            return;
                        }
                    } catch (Exception e) {
                        ev.setInputValid(false); //Should occur at runtime if id is not an integer
                    }
                } else {
                    if (!(ev.getUsername() == null || ev.getUsername().equals(""))) {
                        User[] user = new User[1];
                        user[0] = (User) users.getUser(ev.getUsername());
                        if (user[0] != null && !user[0].isStudent()) { //If an admin or faculty user was found, then continue
                            ev.setUserResults(user);
                            ev.setInputValid(true);
                            return;
                        }
                    } else {
                        if (!ev.isInputValid()) { //Only try to find based on other variables if username and id were invalid
                            try {
                                if (ev.getZipCode() == null || ev.getZipCode().equals("")) {
                                    List < User > tempReturnUsers1 = (List < User > ) Arrays.asList(users.getUsers(ev.getFirstName(), ev.getLastName(), 
                                    		ev.getSocSecNum(), ev.getAddress(), ev.getCity(), -1, ev.getState(), -1)); //Birth year left in for future expansion, it was decided to not be a useful feature
                                    ArrayList < User > tempReturnUsers = new ArrayList < User > ();
                                    for (int i = 0; i < tempReturnUsers1.size(); i++) {
                                        tempReturnUsers.add(tempReturnUsers1.get(i));
                                    }
                                    for (User u: (ArrayList < User > ) tempReturnUsers.clone()) {
                                        if (u.isStudent()) {
                                            tempReturnUsers.remove(u);
                                        }
                                    }
                                    ev.setUserResults(tempReturnUsers.toArray(new User[tempReturnUsers.size()]));
                                    ev.setInputValid(true);
                                } else {
                                    List < User > tempReturnUsers1 = (List < User > ) Arrays.asList(users.getUsers(ev.getFirstName(), ev.getLastName(),
                                    		ev.getSocSecNum(), ev.getAddress(), ev.getCity(), Integer.parseInt(ev.getZipCode()), ev.getState(), -1)); //Birth year left in for future expansion, it was decided to not be a useful feature
                                    ArrayList < User > tempReturnUsers = new ArrayList < User > ();
                                    for (int i = 0; i < tempReturnUsers1.size(); i++) {
                                        tempReturnUsers.add(tempReturnUsers1.get(i));
                                    }
                                    ArrayList < User > tempReturnUsersClone = (ArrayList < User > ) tempReturnUsers.clone(); //Cannot edit an arraylist while looping through it
                                    for (User u: tempReturnUsers) {
                                        if (u.isStudent()) {
                                            tempReturnUsersClone.remove(u);
                                        }
                                    }
                                    ev.setUserResults(tempReturnUsersClone.toArray(new User[tempReturnUsersClone.size()]));
                                    ev.setInputValid(true);
                                }
                            } catch (Exception e) {
                                ev.setInputValid(false); //Should occur at runtime if zip code is not an int
                            }
                            if (ev.isInputValid() && ev.getUserResults() == null) {
                                ev.setUserResults(new User[0]);
                            }
                        }
                    }
                }
            }
        });
        adminView.setListenerAdmin(new AdminEditListener() {
            @Override
            public void verify(AdminEditEventObject ev) {
                try {
                    int id = Integer.parseInt(ev.getId());
                    User user = users.getUser(id);
                    if (user == null) { //If null, then no user found
                        ev.setValid(false);
                    } else if (user.isStudent()) { //Do not return students
                        ev.setValid(false);
                    } else if (user.isAdministrator() || user.isFaculty()) {
                        ev.setUser(user);
                        ev.setUserValid(true);
                    }
                } catch (Exception e) {
                    ev.setValid(false);
                }
            }
        });
        adminView.setListenerDelete(new RemoveStaffListener() {
            @Override
            public void removeStaff(RemoveStaffEventObject ev) {
                if (users.getUser(ev.getId()) == null) {
                    ev.setValid(false);
                    ev.setErrorMessage("Error, invalid ID number."); //Should not occur at runtime
                } else {
                    if (users.getUser(ev.getId()).isStudent()) {
                        ev.setValid(false);
                        ev.setErrorMessage("Error, a student is selected."); //Should not occur at runtime
                    } else {
                        List < User > tempUsers1 = (List < User > ) Arrays.asList(users.getUsers());
                        ArrayList < User > tempUsers = new ArrayList < User > ();
                        for (int i = 0; i < tempUsers1.size(); i++) {
                            tempUsers.add(tempUsers1.get(i));
                        }
                        for (User u: (ArrayList < User > ) tempUsers.clone()) {
                            if (u.isStudent()) { //Remove Students
                                tempUsers.remove(u);
                            } else if (u.isFaculty()) { //Remove faculty
                                tempUsers.remove(u);
                            } else if (u.getId() == ev.getId()) { //Remove user to be deleted
                                tempUsers.remove(u);
                            }
                        }
                        if (tempUsers.size() == 0) {
                            ev.setValid(false);
                            ev.setErrorMessage("Error, you must keep at least one administrative account at all times.");
                        } else {
                            try {
                                users.removeUser(ev.getId());
                                ev.setValid(true);
                                if (ev.getId() == currentUser.getId()) { //If current user was deleted, then logout
                                    currentUser = null;
                                    loginView.start();
                                    return;
                                }
                            } catch (Exception e) {
                                ev.setValid(false);
                                ev.setErrorMessage("An unknown error occurred.  Please contact the developer for assistance."); //Should not occur at runtime
                            }
                        }
                    }
                }
            }
        });
    }
    /**
     * Sets listeners to wait for thrown event objects from the StaffView class in the view package
     */    
    public void setStaffListeners() {
        staffView.setListenerBack(new AdminBackListener() {
            @Override
            public void back(AdminBackEventObject ev) {
                adminView.adminView(ev.getUser(), majors.getMajors(), courses.getCourses());
            }
        });
        staffView.setListenerLogout(new LogoutListener() {
            @Override
            public void logout(LogoutEventObject ev) {
                currentUser = null;
                loginView.start();
            }
        });
        staffView.setListenerPassword(new PasswordListener() {
            @Override
            public void changePassword(PasswordEventObject ev) {
                if (currentUser.correctPassword(new String(md.digest(ev.getOldPassword().getBytes())))) {
                    if (ev.getNewPassword().equals(ev.getNewPassword().toLowerCase()) || ev.getNewPassword().equals(ev.getNewPassword().toUpperCase()) 
                    		|| ev.getNewPassword().length() < 8 || ev.getNewPassword().length() > 32 || !ev.getNewPassword().matches(".*\\d.*")) {
                        ev.setSuccessful(false);
                        ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
                    } else {
                        if (ev.getNewPassword().equals(ev.getNewPasswordConf())) {
                            ev.setSuccessful(true);
                            currentUser.setPassword(new String(md.digest(ev.getNewPassword().getBytes())));
                            saveData();
                        } else {
                            ev.setSuccessful(false);
                            ev.setErrorMessage("Error, new passwords do not match!");
                        }
                    }
                } else {
                    ev.setSuccessful(false);
                    ev.setErrorMessage("Error, old password is incorrect!");
                }
            }
        });
        staffView.setListenerSearch(new SearchListener() {
            @Override
            public void search(SearchEventObject ev) {
                if (!ev.getId().equals("") && ev.getId() != null) {
                    try {
                        Student[] student = new Student[1];
                        student[0] = (Student) users.getUser(Integer.parseInt(ev.getId()));
                        if (student[0] != null) {
                            ev.setStudentResults(student);
                            ev.setInputValid(true);
                            return;
                        }
                    } catch (Exception e) {

                    }
                } else {
                    if (!ev.getUsername().equals("") && ev.getUsername() != null) {
                        try {
                            Student[] student = new Student[1];
                            student[0] = (Student) users.getUser(ev.getUsername());
                            if (student[0] != null) {
                                ev.setStudentResults(student);
                                ev.setInputValid(true);
                                return;
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        Major tempMajor = null;
                        if (majors.getMajor(ev.getMajor()) != null)
                            tempMajor = majors.getMajor(ev.getMajor());
                        else if (ev.getMajor() != null && !ev.getMajor().equals("") && !ev.getMajor().equals("Undeclared"))
                            ev.setInputValid(false);
                        if (ev.getGpa() == null || ev.getGpa().equals("")) {
                            ev.setGpa("-1"); //Set to ignore during search
                        }
                        try {
                            if (Double.parseDouble(ev.getGpa()) <= 4.0) {
                                if (ev.getZipCode().equals("") || ev.getZipCode() == null) {
                                    if (ev.getGpa().equals("") || ev.getGpa() == null) {
                                        ev.setStudentResults(users.getStudents(ev.getFirstName(), ev.getLastName(), ev.getSocSecNum(), ev.getAddress(), 
                                        		ev.getCity(), -1, ev.getState(), -1, -1, tempMajor, -1));
                                        ev.setInputValid(true);
                                    } else {
                                        ev.setStudentResults(users.getStudents(ev.getFirstName(), ev.getLastName(), ev.getSocSecNum(), ev.getAddress(),
                                        		ev.getCity(), -1, ev.getState(), -1, Double.parseDouble(ev.getGpa()), tempMajor, -1));
                                        ev.setInputValid(true);
                                    }
                                } else if (ev.getGpa().equals("") || ev.getGpa() == null) {
                                    ev.setStudentResults(users.getStudents(ev.getFirstName(), ev.getLastName(), ev.getSocSecNum(), ev.getAddress(), 
                                    		ev.getCity(), Integer.parseInt(ev.getZipCode()), ev.getState(), -1, -1, tempMajor, -1));
                                    ev.setInputValid(true);
                                } else {
                                    ev.setStudentResults(users.getStudents(ev.getFirstName(), ev.getLastName(), ev.getSocSecNum(), ev.getAddress(),
                                    		ev.getCity(), Integer.parseInt(ev.getZipCode()), ev.getState(), -1, Double.parseDouble(ev.getGpa()), tempMajor, -1));
                                    ev.setInputValid(true);
                                }
                            } else
                                ev.setInputValid(false);
                        } catch (Exception e) {
                            ev.setInputValid(false);
                        }
                        if (ev.isInputValid() && ev.getStudentResults() == null) {
                            ev.setStudentResults(new Student[0]);
                        }
                    }
                }
            }
        });
        staffView.setListenerReport(new ReportListener() {
            @Override
            public void report(ReportEventObject ev) {
                studentView.studentStart(ev.getUser(), ev.getStudent(), majors.getMajors(), courses.getCourses());
            }
        });
        staffView.setListenerDelete(new RemoveStudentListener() {
            @Override
            public void removeStudent(RemoveStudentEventObject ev) {
                try {
                    users.removeUser(ev.getId());
                    ev.setValid(true);
                } catch (Exception e) {
                    ev.setErrorMessage("Error, student doesn't exist.");
                    ev.setValid(false);
                }
            }
        });
        staffView.setListenerAdmin(new AdminEditListener() {
            @Override
            public void verify(AdminEditEventObject ev) {
                try {
                    if (users.getUser(Integer.parseInt(ev.getId())).isStudent()) {
                        ev.setValid(true);
                        ev.setStudent((Student) users.getUser(Integer.parseInt(ev.getId())));
                    } else
                        ev.setValid(false);
                } catch (Exception e) {
                    ev.setValid(false);
                }

            }
        });
        staffView.setListenerEdit(new EditListener() {
            @Override
            public void edit(EditEventObject ev) {
                if (!(ev.getPassword() == null || ev.getPassword().equals(""))) //If password field is blank, then leave password alone
                {
                    if (ev.getPassword().equals(ev.getPassword().toLowerCase()) || ev.getPassword().equals(ev.getPassword().toUpperCase()) 
                    		|| ev.getPassword().length() < 8 || ev.getPassword().length() > 32 || !ev.getPassword().matches(".*\\d.*")) {
                        ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
                    } else {
                        ev.setPassword(new String(md.digest(ev.getPassword().getBytes())));
                    }
                } else {
                    ev.setPassword(users.getUser(ev.getId()).getPassword());
                }
                if (ev.getFirstName() == null || ev.getFirstName().equals("")) {
                    ev.setErrorMessage("First Name cannot be left blank");
                }
                if (ev.getLastName() == null || ev.getLastName().equals("")) {
                    ev.setErrorMessage("Last Name cannot be left blank");
                }
                try {
                    if (Integer.parseInt(ev.getZipCode()) < 0 || Integer.parseInt(ev.getZipCode()) > 99999) {
                        ev.setErrorMessage("Zip code must be a number between 00000 and 99999");
                    }
                } catch (Exception e) {
                    ev.setErrorMessage("Zip code must be an integer");
                }
                if (ev.getDateOfBirth().isAfter(LocalDate.now().minusYears(13))) {
                    ev.setErrorMessage("New user must be at least 13 years old.");
                }
                if (ev.getDateEnrolled().isAfter(LocalDate.now())) {
                    ev.setErrorMessage("Date of Enrollment cannot be in the future.");
                }
                if (!ev.getDateOfBirth().isAfter(LocalDate.now().minusYears(120))) {
                    ev.setErrorMessage("New user must not be more than 120 years old.");
                }
                if (!ev.getDateEnrolled().isAfter(LocalDate.parse("1959-12-01"))) {
                    ev.setErrorMessage("Date of enrollment cannot be prior to college founding.");
                }
                if (users.getUser(ev.getUsername()) != null && users.getUser(ev.getUsername()).getId() != ev.getId()) {
                    ev.setErrorMessage("Username is already in use.  Please choose another username.");
                }
                if (ev.getUsername() == null || ev.getUsername().equals("")) {
                    ev.setErrorMessage("Username cannot be left blank");
                }
                if (ev.getErrorMessage().length() == 0) {
                    users.removeUser(ev.getId());
                    Student tempStudent = new Student(ev.getId(), ev.getFirstName(), ev.getLastName(), ev.getDateEnrolled(), ev.getDateOfBirth(), 
                    		ev.getSocialSecNum(), ev.getAddress(), ev.getCity(), Integer.parseInt(ev.getZipCode()), ev.getState(), ev.getCampus(), 
                    		ev.getMajor(), ev.getUsername(), ev.getPassword());
                    tempStudent.setCourseWork(ev.getCoursework());
                    users.addUser(tempStudent);
                    ev.setValid(true);
                    saveData();
                }
            }
        });
    }
    /**
     * Sets listeners to wait for thrown event objects from the StudentView class in the view package
     */    
    public void setStudentListeners() {
        studentView.setListenerLogout(new LogoutListener() {
            @Override
            public void logout(LogoutEventObject ev) {
                currentUser = null;
                loginView.start();
            }
        });
        studentView.setListenerPassword(new PasswordListener() {
            @Override
            public void changePassword(PasswordEventObject ev) {
                if (currentUser.correctPassword(new String(md.digest(ev.getOldPassword().getBytes())))) {
                    if (ev.getNewPassword().equals(ev.getNewPassword().toLowerCase()) || ev.getNewPassword().equals(ev.getNewPassword().toUpperCase())
                    		|| ev.getNewPassword().length() < 8 || ev.getNewPassword().length() > 32 || !ev.getNewPassword().matches(".*\\d.*")) {
                        ev.setSuccessful(false);
                        ev.setErrorMessage("Error, password must be between 8 and 32 characters long, and must\n contain a lowercase letter, an uppercase letter, and a number.");
                    } else {
                        if (ev.getNewPassword().equals(ev.getNewPasswordConf())) {
                            ev.setSuccessful(true);
                            currentUser.setPassword(new String(md.digest(ev.getNewPassword().getBytes())));
                            saveData();
                        } else {
                            ev.setSuccessful(false);
                            ev.setErrorMessage("Error, new passwords do not match!");
                        }
                    }
                } else {
                    ev.setSuccessful(false);
                    ev.setErrorMessage("Error, old password is incorrect!");
                }
            }
        });
        studentView.setListenerBack(new BackListener() {
            public void back(BackEventObject ev) {
                staffView.start(currentUser.isAdministrator(), currentUser, majors.getMajors());
            }
        });
        studentView.setListenerSAIN(new SAINListener() {
            @Override
            public void getReport(SAINEventObject ev) {
                if (ev.getMajor().getName().equals("") || ev.getMajor().getName() == null) {
                    ev.setErrorMessage("Error no major is selected.  Please select a major then try again.");
                } else {
                    ev.setValid(true);
                    Student temp = ev.getStudent(); //Change major if what-if analysis is selected
                    temp.setMajor(ev.getMajor());
                    studentView.studentView(ev.getUser(), temp, courses.getCourses());
                }
            }
        });
    }
    /**
     * Sets listeners to wait for a model(CourseBag, UserBag, or MajorBag) to indicate that data has been changed.
     * Then proceeds to save data in a user specified manner. 
     */    
    public void setModelListeners() {
        users.setModelListener(new ModelListener() {
            @Override
            public void modelChanged(ModelChangedEventObject ev) {
                saveData();
            }
        });
        courses.setModelListener(new ModelListener() {
            @Override
            public void modelChanged(ModelChangedEventObject ev) {
                saveData();
            }
        });
        majors.setModelListener(new ModelListener() {
            @Override
            public void modelChanged(ModelChangedEventObject ev) {
                saveData();
            }
        });
    }
    /**
     * Saves the user preference of data type to load from on startup.
     */
    public void saveSQL() {
    	try {
			FileOutputStream fileOut = new FileOutputStream(new File("SQL.bin"));
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeBoolean(sql);
			objOut.flush();
			objOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * Loads the user preference of data type to load from on startup.
     * Default is loading from the binary data file.
     */
    public void loadSQL() {
    	try {
			FileInputStream fileIn = new FileInputStream(new File("SQL.bin"));
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			sql = objIn.readBoolean();
		} catch (Exception e) {
			sql = false;
			e.printStackTrace();
		}
    }
    /**
     * Loads the program data from the user specified method.
     */
    public void loadData() throws ClassNotFoundException, IOException {
    	loadSQL();
    	if(!sql) {
    		loadBinData();
    	} else {
    		loadSqlData();
    	}
    }
    /**
     * Loads major, course, and user data from an SQL database.
     */
    public void loadSqlData() {
    	loading = true;
        Connection conn = null;
        try {
            String userName = "SAINReport";
            String password = "";
            String url = "jdbc:mysql://localhost/SAINReport";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            Statement stmt = conn.createStatement();
            ResultSet rset;
            byte[] buffer;
            ObjectInputStream objectIn = null;
            rset = stmt.executeQuery("SELECT * from courses");
            while (rset.next()) {
                buffer = rset.getBytes("serialObj");
                if (buffer != null) {
                    objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                    courses.addCourse((Course) objectIn.readObject());
                }
                objectIn.close();
            }
            rset = stmt.executeQuery("SELECT * from majors");
            while (rset.next()) {
                buffer = rset.getBytes("serialObj");
                if (buffer != null) {
                    objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                    majors.addMajor((Major) objectIn.readObject());
                }
                objectIn.close();
            }
            rset = stmt.executeQuery("SELECT * from users");
            while (rset.next()) {
                buffer = rset.getBytes("serialObj");
                if (buffer != null) {
                    objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                    users.addUser((User) objectIn.readObject());
                }
                objectIn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {

                }
            }
        }
        loading = false;
    }
    /**
     * Loads major, course, and user data from a binary file.
     */
    public void loadBinData() throws ClassNotFoundException, IOException {
        loading = true;
        FileInputStream fileCourseIn;
        FileInputStream fileUserIn;
        FileInputStream fileMajorIn;
        boolean loop = true;
        ArrayList < User > tempUsers = new ArrayList < User > ();
        ArrayList < Major > tempMajors = new ArrayList < Major > ();
        ArrayList < Course > tempCourses = new ArrayList < Course > ();
        if (new File("Courses.bin").isFile()) {
            fileCourseIn = new FileInputStream(new File("Courses.bin"));
            ObjectInputStream objCourseIn = new ObjectInputStream(fileCourseIn);
            while (loop) {
                try {
                    courses.addCourse((Course) objCourseIn.readObject());
                } catch (Exception e) {
                    loop = false;
                }
            }
            loop = true;
        } else
            fileCourseIn = null;
        if (new File("Users.bin").isFile()) {
            fileUserIn = new FileInputStream(new File("Users.bin"));
            ObjectInputStream objUserIn = new ObjectInputStream(fileUserIn);
            while (loop) {
                try {
                    users.addUser((User) objUserIn.readObject());
                } catch (Exception e) {
                    loop = false;
                }
            }
            loop = true;
        } else
            fileUserIn = null;
        if (new File("Majors.bin").isFile()) {
            fileMajorIn = new FileInputStream(new File("Majors.bin"));
            ObjectInputStream objMajorIn = new ObjectInputStream(fileMajorIn);
            while (loop) {
                try {
                    Major tempMajor = (Major) objMajorIn.readObject();
                    if (majors.getMajor(tempMajor.getName()) == null) { //Remove doubled majors(these may arise from the importing script)
                        majors.addMajor(tempMajor);
                    }
                } catch (Exception e) {
                    loop = false;
                }
            }
        } else
            fileMajorIn = null;
        if (fileUserIn == null)
            loginView.newUser();
        loading = false;
    }
    /**
     * Saves the program data in both binary and SQL formats
     * @return Whether or not the write was successful
     */
    public boolean saveData() {
        if (!loading) {
            return saveBinData() && saveSqlData();
        } else
            return false;
    }
    /**
     * Saves the program data in both binary and SQL formats
     * @return Whether or not the write was successful
     */
    public boolean saveSqlData() {
        boolean success = true;
        Connection conn = null;
        try {
            String userName = "SAINReport";
            String password = "";
            String url = "jdbc:mysql://localhost/SAINReport";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            Statement stmt = conn.createStatement();
            PreparedStatement ps = null;
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            stmt.executeUpdate("drop table if exists courses");
            stmt.executeUpdate("CREATE TABLE `courses` (`id` int(11) unsigned NOT NULL AUTO_INCREMENT,  `serialObj` longblob,  PRIMARY KEY (`id`))");
            for (Course c: courses.getCourses()) {
            	byteOut = new ByteArrayOutputStream();
            	objOut = new ObjectOutputStream(byteOut);
                objOut.writeObject(c);
                objOut.flush();
                objOut.close();
                byteOut.close();
                byte[] data = byteOut.toByteArray();
                String str = "INSERT INTO courses (serialObj) VALUES(?);";
                ps = conn.prepareStatement(str);
                ps.setObject(1, data);
                ps.executeUpdate();
            }
            stmt.executeUpdate("drop table if exists majors");
            stmt.executeUpdate("CREATE TABLE `majors` (`id` int(11) unsigned NOT NULL AUTO_INCREMENT,  `serialObj` longblob,  PRIMARY KEY (`id`))");
            for (Major m: majors.getMajors()) {
            	byteOut = new ByteArrayOutputStream();
            	objOut = new ObjectOutputStream(byteOut);
                objOut.writeObject(m);
                objOut.flush();
                objOut.close();
                byteOut.close();
                byte[] data = byteOut.toByteArray();
                String str = "INSERT INTO majors (serialObj) VALUES(?);";
                ps = conn.prepareStatement(str);
                ps.setObject(1, data);
                ps.executeUpdate();
            }
            stmt.executeUpdate("drop table if exists users");
            stmt.executeUpdate("CREATE TABLE `users` (`id` int(11) unsigned NOT NULL AUTO_INCREMENT,  `serialObj` longblob,  PRIMARY KEY (`id`))");
            for (User u: users.getUsers()) {
            	byteOut = new ByteArrayOutputStream();
            	objOut = new ObjectOutputStream(byteOut);
                objOut.writeObject(u);
                objOut.flush();
                objOut.close();
                byteOut.close();
                byte[] data = byteOut.toByteArray();
                String str = "INSERT INTO users (serialObj) VALUES(?);";
                ps = conn.prepareStatement(str);
                ps.setObject(1, data);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {

                }
            }
        }
        return success;
    }
    /**
     * Saves major, course, and user data to a binary file.
     * @return Success of the operation
     */
    public boolean saveBinData() {
        try {
            File fileCourse = new File("Courses.bin");
            File fileUser = new File("Users.bin");
            File fileMajor = new File("Majors.bin");
            fileCourse.createNewFile();
            fileUser.createNewFile();
            fileMajor.createNewFile();
            ObjectOutputStream courseOut = new ObjectOutputStream(new FileOutputStream(fileCourse));
            ObjectOutputStream userOut = new ObjectOutputStream(new FileOutputStream(fileUser));
            ObjectOutputStream majorOut = new ObjectOutputStream(new FileOutputStream(fileMajor));
            for (Course c: courses.getCourses()) {
                courseOut.writeObject(c);
            }
            for (User u: users.getUsers()) {
                userOut.writeObject(u);
            }
            for (Major m: majors.getMajors()) {
                majorOut.writeObject(m);
            }
            courseOut.close();
            userOut.close();
            majorOut.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Generates an ID of the lowest possible unused value.
     * Value is dependant on the type of User
     * @param student Whether or not a student ID is desired
     * @return A new, unused, valid ID number of the desired type
     */
    public int generateId(boolean student) {
        int returnInt = -1;
        if (!student) {
            for (int i = 80000000; i <= 99999999; i++) {
                if (users.getUser(i) == null) {
                    returnInt = i;
                    break;
                }
            }
        } else {
            for (int i = 00000000; i <= 79999999; i++) {
                if (users.getUser(i) == null) {
                    returnInt = i;
                    break;
                }
            }
        }
        return returnInt;
    }
}