Default Username - Created upon initial startup
Default Password - Created upon initial startup

Administrators can choose whether to load SQL or binary data file upon startup
All data is saved as both SQL and binary files.
SQL Information - 
	Database Address - mysql://localhost/SAINReport
	Username - "SAINReport"
	Password - None
	Tables are automatically created and removed as necessary
Majors and courses were imported from the college website via scripts in the getWebsiteData package
	CourseDownload.java is the equivalent of the Course object for temporarily storing the downloaded files
	CourseDownloadtoCourse.java takes all of the downloaded CourseDownload objects, converts them to Course objects, then saves them to a binary file called "Courses.bin" which is in a format compatible with the main application
	CourseDownloadtoCourses.java takes all of the downloaded CourseDownload objects, and converts them to Course objects.  Each object is then saved in it's own binary file, called "<CourseCode>.bin" which is necessary for getMajorData.java to run
	FindCourseCodes.java detects all currently available course codes and saves them to a text file called Courses.txt
	GetCourseDescriptions.java uses the Courses.txt file and gets all applicable data about the courses from Suffolk's live website, such as name, description, number of credits, campuses offered on, attributes, prerequisites, and corequisites
	GetMajorData.java uses the html files in the Major Links(saved from http://sunysuffolk.edu/Students/Programs.asp) to get all of the information on the major such as courses required, electives required, and total number of credits.  It then places this data into a Major object, and saves all generated objects to a file called Majors.bin  This file is in a format which can be read by the application
	WebChecker.java Remanant from the development process - should be ignored

Javadoc webpages are available in the doc folder

UML diagrams are available in the UML Diagrams folder

Runnable.jar is a compiled executable containing the default binary files and source code

JUnit testers are available in the jUnitTesters package

Features - 
	If a major is deleted, all students with that declared as their major will be set to 'Undeclared'
	If a course is deleted, all students, majors, and courses associated will have the associations deleted
	All passwords are put through an MD5 hash before saving and when comparing passwords
	Leave any portion of a search page blank to ignore that parameter when searching - clicking search with all fields empty will display all users
	To add courses to a student, click edit student data on the student search page, then select a course and click add course
	When creating a user, if no ID is entered, one will be automatically generated(Students 00000000 - 79999999, Staff and Admin 80000000 - 99999999)

	Views : 

	Administrators
		Click Search Students to go to Staff View
		Click Create account to create a new account
		Click Manage Majors to add/edit/delete majors
		Click Manage Courses to add/edit/delete courses
		Click Search staff to search all Administrators and Faculty
		Click Logout at any time to return to the login screen
		Click Change Password to change current users password at any time
		Click Set Data Saving Method to choose whether data is loaded from SQL or Binary on next startup
	Staff
		Click Select Student to generate SAIN reports or What-If Analyses
		Click Search to search for students based on currently entered data
		Click Clear to clear all fields
		Click Edit Student to edit selected student data or add Courses
		Click Back to return to Administrative View(Admins only)
	Student
		Click View SAIN Report to view the student's SAIN Report
		Select a major, then click Continue to generate a What-If Analysis
		Click back to return to Staff View(Staff and Admin only)
		
	SAIN Report View
		Click back to return to Student View
		