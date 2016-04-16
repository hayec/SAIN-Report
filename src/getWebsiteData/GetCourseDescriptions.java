package getWebsiteData;

import java.io.*;
import java.net.URL;
import java.util.Scanner;
public class GetCourseDescriptions {

	public static void main(String[] args) throws FileNotFoundException 
	{
		String courseCode = "";
		String description = "";
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line = new String();
		Scanner fileIn = new Scanner(new File("Courses.txt"));
		String temp;
		String[] prerequisites = new String[5];
		String[] attributes = new String[15];
		int prereqCount = 0;
		boolean ammerman;
		boolean grant;
		boolean eastern;
		boolean loop = true;
		int capIndex = 0;
		int credits = 0;
		int startingIndex = -1;
		int atrIndex = 0;
		String level;
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
		        line = line.substring(line.indexOf("</a></td> </tr> <tr> <TD CLASS=\"ntdefault\">") + "</a></td> </tr> <tr> <TD CLASS=\"ntdefault\">".length());
		        description = line.substring(0, line.indexOf("Offered on"));
		        if(line.contains("Prerequisite: "))
		        {
		        	line = line.substring(line.indexOf("Prerequisite"));
		        	while(loop)
		        	{
		        		for(int i = 0; i <= 50; i++)//50 is an arbitrarily long number which should encompass all prerequisistes
		        		{
		        			if(Character.isUpperCase(line.charAt(i)))
		        				capIndex++;
		        			else
		        				capIndex = 0;
		        			if(capIndex == 3)
		        			{
		        				capIndex = 0;
		        				prerequisites[prereqCount] = line.substring(i - 2, i + 3);
		        				i = i + 3;
		        			}
		        		}
		        	}
		        }
		        line = line.substring(line.indexOf("Offered on: ") + "Offered on: ".length());
		        if(line.substring(0, 4).contains("A"))
		        	ammerman = true;
		        if(line.substring(0, 4).contains("E"))
		        	eastern = true;
		        if(line.substring(0, 4).contains("G"))
		        	grant = true;
		        for(int i = 0; i <= 20; i++)
		        {
		        	if(Character.isDigit(line.charAt(i)))
		        	{
		        		credits = Character.getNumericValue(line.charAt(i));
		        	}
		        }
		        line = line.substring(line.indexOf("Levels: </SPAN>") + "Levels: </SPAN>".length());
		        level = line.substring(0, line.indexOf("<"));
		        level = level.replace(" ", "");
		        line = line.substring(line.indexOf("Course Attributes: </SPAN><br />") + "Course Attributes: </SPAN><br />".length());
		        for(int i = 0; i < line.indexOf("<"); i++)
		        {
		        	if(Character.isUpperCase(line.charAt(i)))
		        		if(startingIndex >= 0)
		        		{
		        			attributes[atrIndex] = line.substring(startingIndex, i);
		        			startingIndex = -1;
		        			atrIndex++;
		        		}
		        		else
		        			startingIndex = i;
		        }
			}
			catch(Exception e){}
			System.out.println(courseCode + " " + description + " " + prerequisites.toString());
			startingIndex = -1;
			credits = prereqCount = capIndex = atrIndex = 0;
			ammerman = eastern = grant = false;
			loop = true;
			prerequisites = new String[5];
			attributes = new String[15];
			line = level = "";
			description = "";
			
		}
	}

}
