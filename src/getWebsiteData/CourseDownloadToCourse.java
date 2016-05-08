package getWebsiteData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import report.Course;

public class CourseDownloadToCourse {

	public static void main(String[] args) throws IOException {
		File fileO = new File("C:\\Users\\Christopher\\workspace\\SAIN Report\\Course Files\\Courses.bin");
		FileOutputStream fileOut = new FileOutputStream(fileO);
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		File[] files = new File("C:\\Users\\Christopher\\workspace\\SAIN Report\\Course Descriptions").listFiles();
		for(File f : files)
		{
			try {
				FileInputStream fileIn = new FileInputStream(f);
				ObjectInputStream objIn = new ObjectInputStream(fileIn);
				CourseDownload tempCourse = (CourseDownload) objIn.readObject();
				ArrayList<String> attr = new ArrayList<String>();
				for(String s : tempCourse.getAttributes()) {//Remove null attributes which cause errors
					if(s != null) {
						attr.add(s);
					}
				}
				tempCourse.setAttributes(attr.toArray(new String[attr.size()]));
				Course tempCourseNew = new Course(tempCourse.courseCode, tempCourse.getTitle(), tempCourse.getDescription(), tempCourse.isAmmerman(), tempCourse.isGrant(), tempCourse.isEastern(), tempCourse.getPrerequisites(), tempCourse.getCorequisites(), tempCourse.getCredits());
				for(int i = 0; i < tempCourse.getAttributes().length; i++)
				{
					if(tempCourse.getAttributes()[i] != null)
					{
						if(tempCourse.getAttributes()[i].equals("HUM"))
							tempCourseNew.CAttributes.setHumanities(true);
						else if(tempCourse.getAttributes()[i].equals("MAT"))
							tempCourseNew.CAttributes.setMath(true);
						else if(tempCourse.getAttributes()[i].equals("PED"))
							tempCourseNew.CAttributes.setPhysEd(true);
						else if(tempCourse.getAttributes()[i].equals("HIS"))
							tempCourseNew.CAttributes.setHistory(true);
						else if(tempCourse.getAttributes()[i].equals("LAS"))
							tempCourseNew.CAttributes.setLabScience(true);
						else if(tempCourse.getAttributes()[i].equals("BUS"))
							tempCourseNew.CAttributes.setBusiness(true);
						else if(tempCourse.getAttributes()[i].equals("ENG"))
							tempCourseNew.CAttributes.setEnglish(true);
						else if(tempCourse.getAttributes()[i].equals("COM"))
							tempCourseNew.CAttributes.setCommunications(true);
						else if(tempCourse.getAttributes()[i].equals("GAM"))
							tempCourseNew.CAttributes.setAmerHis(true);
						else if(tempCourse.getAttributes()[i].equals("FLA"))
							tempCourseNew.CAttributes.setLanguage(true);
						else if(tempCourse.getAttributes()[i].equals("PHL"))
							tempCourseNew.CAttributes.setPhilosophy(true);
					}
				}
				ArrayList<String> prereqs = new ArrayList<String>(Arrays.asList(tempCourseNew.getPrerequisites()));
				ArrayList<String> prereqsEdit = (ArrayList<String>) prereqs.clone();
				ArrayList<String> coreqs = new ArrayList<String>(Arrays.asList(tempCourseNew.getCorequisites()));
				ArrayList<String> coreqsEdit = (ArrayList<String>) coreqs.clone();
				for(String s : prereqs) {
					if(s == null) {
						prereqsEdit.remove(s);
					}
				}
				for(String s : coreqs) {
					if(s == null) {
						coreqsEdit.remove(s);
					}
				}
				tempCourseNew.setPrerequisites(prereqsEdit.toArray(new String[prereqsEdit.size()]));
				tempCourseNew.setCorequisites(coreqsEdit.toArray(new String[coreqsEdit.size()]));
				objOut.writeObject(tempCourseNew);
				System.out.println(tempCourse.getCourseCode() + " done.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		objOut.flush();
		objOut.close();
	}

}
