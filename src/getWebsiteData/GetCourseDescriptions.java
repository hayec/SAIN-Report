package getWebsiteData;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
public class GetCourseDescriptions {

	public static void main(String[] args) throws IOException 
	{
		String courseCode = "";
		String description = "";
		String title = "";
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line = new String();
		Scanner fileIn = new Scanner(new File("Courses.txt"));
		String temp;
		ArrayList<String> prereqArray = new ArrayList<String>();
		ArrayList<String> coreqArray = new ArrayList<String>();
		ArrayList<String> attrArray = new ArrayList<String>();
		String[] prerequisites = null;
		String[] corequisites = null;
		String[] attributes = null;
		boolean ammerman = false;
		boolean grant = false;
		boolean eastern = false;
		int capIndex = 0;
		int credits = 0;
		int startingIndex = -1;
		int atrIndex = 0;
		String level = new String();
		int atrCounter = 0;
		int prereqIndex = 0;
		while(fileIn.hasNextLine())
		{
			courseCode = fileIn.nextLine();
			try
			{
				@SuppressWarnings("unused")
				String debug = courseCode.substring(3);
		        url = new URL("https://lighthouse.sunysuffolk.edu/pls/prod/bwckctlg.p_display_courses?term_in=201602&one_subj=" + courseCode.substring(0, 3) + "&sel_crse_strt=" + courseCode.substring(3) + "&sel_crse_end=" + 
		        		courseCode.substring(3) + "&sel_subj=&sel_levl=&sel_schd=&sel_coll=&sel_divs=&sel_dept=&sel_attr=");
		        is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));
		        while ((temp = br.readLine()) != null) 
		        {
		            line += temp;
		        }
		        line = line.substring(line.indexOf("\">" + courseCode.substring(0, 3)) + ("\">" + courseCode + "  - ").length());
		        title = line.substring(0, line.indexOf("</a>"));
		        line = line.substring(line.indexOf("</a></td></tr><tr><TD CLASS=\"ntdefault\">") + "</a></td></tr><tr><TD CLASS=\"ntdefault\">".length());
		        description = line.substring(0, line.indexOf("Offered on"));
		        if(line.contains("Prerequisite: "))
		        {
		        	line = line.substring(line.indexOf("Prerequisite"));
		        	if(!line.contains("Corequisite"))
		        	{
		        		prereqIndex = 50;//50 is an arbitrarily long number which should encompass all prerequisistes
		        	}
		        	else
		        	{
		        		prereqIndex = line.indexOf("Corequisite");
		        	}
		        	for(int i = 0; i <= 50; i++)
	        		{
	        			if(Character.isUpperCase(line.charAt(i)))
	        				capIndex++;
	        			else
	        				capIndex = 0;
	        			if(capIndex == 3)
	        			{
	        				capIndex = 0;
	        				prereqArray.add(line.substring(i - 2, i + 4));
	        				i = i + 3;
	        			}
	        		}
		        	capIndex = 0;
		        }
		        if(line.contains("Corequisite: "))
		        {
		        	line = line.substring(line.indexOf("Corequisite"));
		        	for(int i = 0; i <= 50; i++)//50 is an arbitrarily long number which should encompass all corequisistes
	        		{
	        			if(Character.isUpperCase(line.charAt(i)))
	        				capIndex++;
	        			else
	        				capIndex = 0;
	        			if(capIndex == 3)
	        			{
	        				capIndex = 0;
	        				coreqArray.add(line.substring(i - 2, i + 4));
	        				i = i + 3;
	        			}
	        		} 	
		        }
		        line = line.substring(line.indexOf("Offered on: ") + "Offered on: ".length());
		        if(line.substring(0, 5).contains("A"))
		        	ammerman = true;
		        if(line.substring(0, 5).contains("E"))
		        	eastern = true;
		        if(line.substring(0, 5).contains("G"))
		        	grant = true;
		        for(int i = 0; i <= 20; i++)
		        {
		        	if(Character.isDigit(line.charAt(i)))
		        	{
		        		credits = Character.getNumericValue(line.charAt(i));
		        		break;
		        	}
		        }
		        line = line.substring(line.indexOf("Levels: </SPAN>") + "Levels: </SPAN>".length());
		        level = line.substring(0, line.indexOf("<"));
		        level = level.replace(" ", "");
		        line = line.substring(line.indexOf("Course Attributes: </SPAN><br />") + "Course Attributes: </SPAN><br />".length());
		        for(int i = 0; i < line.indexOf("<"); i++)
		        {
		        	if(Character.isUpperCase(line.charAt(i)))
		        		if(startingIndex >= 0 && atrCounter == 2)
		        		{
		        			attrArray.add(line.substring(startingIndex, i + 1));
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
			}
			catch(Exception e){}
			prerequisites = prereqArray.toArray(new String[prereqArray.size()]);
			corequisites = coreqArray.toArray(new String[prereqArray.size()]);
			attributes = attrArray.toArray(new String[prereqArray.size()]);
			if(description != null && description != "")
			{
				File file = new File("C:\\Users\\Christopher\\workspace\\SAIN Report\\Course Descriptions\\" + courseCode + ".bin");
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
				objOut.writeObject(new CourseDownload(courseCode, title, description, ammerman, grant, eastern, prerequisites, corequisites, attributes, credits, level));
				objOut.close();
				fileOut.close();
			}
			System.out.println(courseCode + " " + title + " " + description);
			startingIndex = -1;
			credits = capIndex = atrIndex = atrCounter = 0;
			ammerman = eastern = grant = false;
			prereqArray = new ArrayList<String>();
			coreqArray = new ArrayList<String>();
			attrArray = new ArrayList<String>();
			line = level = "";
			description = "";	
		}
		fileIn.close();
	}

}
