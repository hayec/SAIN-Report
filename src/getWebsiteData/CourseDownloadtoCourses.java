package getWebsiteData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import report.Course;

public class CourseDownloadtoCourses {
	public static void main(String[] args) {//Convert CourseDownload objects to Course objects for importing majors
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
				Course tempCourseNew = new Course(tempCourse.getCourseCode(), tempCourse.getTitle(), tempCourse.getDescription(), tempCourse.isAmmerman(), tempCourse.isGrant(), tempCourse.isEastern(), tempCourse.getPrerequisites(), tempCourse.getCorequisites(), tempCourse.getCredits());
				for(int i = 0; i < tempCourse.getAttributes().length; i++)
				{
					if(tempCourse.getAttributes()[i].equals("HUM"))
							tempCourseNew.CAttributes.setHumanities(true);
					if(tempCourse.getAttributes()[i].equals("MAT"))
						tempCourseNew.CAttributes.setMath(true);
					if(tempCourse.getAttributes()[i].equals("PED"))
						tempCourseNew.CAttributes.setPhysEd(true);
					if(tempCourse.getAttributes()[i].equals("HIS"))
						tempCourseNew.CAttributes.setHistory(true);
					if(tempCourse.getAttributes()[i].equals("LAS"))
						tempCourseNew.CAttributes.setLabScience(true);
					if(tempCourse.getAttributes()[i].equals("BUS"))
						tempCourseNew.CAttributes.setBusiness(true);
					if(tempCourse.getAttributes()[i].equals("ENG"))
						tempCourseNew.CAttributes.setEnglish(true);
					if(tempCourse.getAttributes()[i].equals("COM"))
						tempCourseNew.CAttributes.setCommunications(true);
					if(tempCourse.getAttributes()[i].equals("GAM"))
						tempCourseNew.CAttributes.setAmerHis(true);
					if(tempCourse.getAttributes()[i].equals("FLA"))
						tempCourseNew.CAttributes.setLanguage(true);
					if(tempCourse.getAttributes()[i].equals("PHL"))
						tempCourseNew.CAttributes.setPhilosophy(true);
				}
				File fileO = new File("C:\\Users\\Christopher\\workspace\\SAIN Report\\Course Files\\" + tempCourse.getCourseCode() + ".bin");
				FileOutputStream fileOut = new FileOutputStream(fileO);
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
				objOut.writeObject(tempCourseNew);
				objOut.flush();
				objOut.close();
				System.out.println(tempCourse.getCourseCode() + " done.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
