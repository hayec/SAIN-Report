package getWebsiteData;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class GetMajorData 
{
	public static void main(String[] args) throws IOException
	{
		String line = new String();
		//Scanner fileIn = new Scanner(new File("Courses.txt"));
		URL url;
		InputStream is = null;
		BufferedReader br;
		String temp;
		String name;
		ArrayList<String> courses = new ArrayList<String>();
		int startingIndex, atrCounter;
		atrCounter = startingIndex = 0;
		//while(fileIn.hasNextLine())
		//{
			
			try
			{
				/*String urlTemp = fileIn.nextLine();*/
		        url = new URL("http://www.sunysuffolk.edu/Students/DIET-AAS.asp");
		        is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));
		        while ((temp = br.readLine()) != null) 
		        {
		            line += temp;
		        }
		        line = line.substring(line.indexOf("<div class=\"hero\">"));
		        name = line.substring(line.indexOf("\\"));
		        name = name.replace(" ", "");
		        line = line.substring(line.indexOf("<table align=\"center\" border=\"0\""), line.indexOf("<\\table"));
		        for(int i = 0; i < line.length(); i++)
		        {
		        	if(Character.isUpperCase(line.charAt(i)))
		        		if(startingIndex >= 0 && atrCounter == 2)
		        		{
		        			if(isInts(line.substring(startingIndex + 3, startingIndex + 6)))
		        			{
		        				courses.add(line.substring(startingIndex, i + 1));
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
		        	System.out.println(name);
		        	for(String c : courses)
		        		System.out.println(c);
		        }
			}
			catch(Exception e)
			{
				
			}
		//}
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
}