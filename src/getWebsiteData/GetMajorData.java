package getWebsiteData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import report.Course;
import user.Major;

public class GetMajorData 
{
	public static void main(String[] args) throws IOException
	{
		String line = new String();
		Scanner fileIn;
		File[] files = new File("C:\\Users\\Christopher\\workspace\\SAIN Report\\Major Links").listFiles();
		URL url;
		InputStream is = null;
		BufferedReader br;
		String temp;
		String name;
		ArrayList<String> courses = new ArrayList<String>();
		File file = new File("C:\\Users\\Christopher\\workspace\\SAIN Report\\Major Descriptions\\Majors.bin");
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		int startingIndex, atrCounter;
		atrCounter = startingIndex = 0;
		int physEdReq = 0;
		int hisReq = 0;
		int labSciReq = 0;
		int mathReq = 0;
		int humReq = 0;
		int busReq = 0;
		int engReq = 0;
		int comReq = 0;
		int amerHisReq = 0;
		int socSciReq = 0;
		int langReq = 0;
		int phlReq = 0;
		int numOfCredits;
		for(File f : files)
		{
			
			try
			{
				fileIn = new Scanner(f);
		        while (fileIn.hasNextLine()) 
		        {
		            line += fileIn.nextLine();
		        }
		        line = line.substring(line.indexOf("<div class=\"hero\">") + "<div class=\"hero\">".length());
		        line = line.substring(line.indexOf("<h2>") + "<h2>".length());
		        name = line.substring(0, line.indexOf("/"));
		        line = line.substring(line.indexOf("<tr bgcolor=\"#CCCCCC\">"));
		        line = line.substring(0, line.indexOf("</table>"));
		        for(int i = 0; i < line.length(); i++)
		        {
		        	if(Character.isUpperCase(line.charAt(i)))
		        		if(startingIndex >= 0 && atrCounter == 2)
		        		{
		        			if(isInts(line.substring(startingIndex + 3, startingIndex + 6)))
		        			{
		        				courses.add(line.substring(startingIndex, i + 4));
		        			}
		        			startingIndex = -1;
		        			atrCounter = 0;
		        		}	
		        		else if(startingIndex >= 0)
		        			atrCounter++;
		        		else
		        		{
		        			startingIndex = i;
		        			atrCounter++;
		        		}
		        	else
		        	{
		        		atrCounter = 0;
		        		startingIndex = -1;
		        	}
		        }
		        String lineTemp = line;
		        while(lineTemp.contains("Physical Education"))
		        {
		        	physEdReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Physical Education") + "Physical Education".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Laboratory Science Elective"))
		        {
		        	labSciReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Laboratory Science Elective") + "Laboratory Science Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Social Sciences Elective"))
		        {
		        	socSciReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Social Sciences Elective") + "Social Sciences Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Mathematics Elective"))
		        {
		        	mathReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Mathematics Elective") + "Mathematics Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("History Elective"))
		        {
		        	hisReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("History Elective") + "History Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Humanities Elective"))
		        {
		        	humReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Humanities Elective") + "Humanities Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Business Elective"))
		        {
		        	busReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Business Elective") + "Business Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("English Elective"))
		        {
		        	engReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("English Elective") + "English Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Communications Elective"))
		        {
		        	comReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Communications Elective") + "Communications Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Communication Elective"))
		        {
		        	comReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Communication Elective") + "Communication Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Philosophy Elective"))
		        {
		        	phlReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Philosophy Elective") + "Philosophy Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("American History Elective"))
		        {
		        	amerHisReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("American History Elective") + "American History Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Foreign Language"))
		        {
		        	langReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Foreign Language") + "Foreign Language".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Math Elective"))
		        {
		        	mathReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Math Elective") + "Math Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Lab Science Elective"))
		        {
		        	labSciReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Lab Science Elective") + "Lab Science Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Mathematics or Science Elective"))
		        {
		        	mathReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Mathematics or Science Elective") + "Mathematics or Science Elective".length());
		        }
		        lineTemp = line;
		        while(lineTemp.contains("Mathematics/Science Elective"))
		        {
		        	mathReq++;
		        	lineTemp = lineTemp.substring(lineTemp.indexOf("Mathematics/Science Elective") + "Mathematics/Science Elective".length());
		        }
		        lineTemp = line.substring(line.indexOf("TOTAL CREDITS REQUIRED") + "TOTAL CREDITS REQUIRED</b></td>".length());
		        numOfCredits = (int) Math.floor(Double.parseDouble(lineTemp.substring(lineTemp.indexOf("</td>") - 4, lineTemp.indexOf("</td>"))));
		        //Need to remove doubling
		        System.out.println(name);
		        Course[] reqCourses = new Course[courses.size()];
	        	for(String c : courses)
	        		reqCourses[courses.indexOf(c)] = getCourse(c);
	        	Major tempMajor = new Major(name, physEdReq, hisReq, labSciReq, mathReq, humReq, busReq, engReq, comReq, amerHisReq, socSciReq, langReq, phlReq, numOfCredits, reqCourses);
				objOut.writeObject(tempMajor);
			}
			catch(Exception e)
			{
				
			}
			
		}
		objOut.close();
		fileOut.close();
	}
	private static boolean isInts(String string)
	{
		boolean val = true;
		for(char c : string.toCharArray())
		{
			if(!Character.isDigit(c))
				val = false;
		}
		return val;
	}
	private static Course getCourse(String c)
	{
		File file = new File("C:\\Users\\Christopher\\workspace\\SAIN Report\\Course Files\\" + c + ".bin");
		Course returnCourse = new Course();
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			returnCourse = (Course) objIn.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnCourse;
	}
}
