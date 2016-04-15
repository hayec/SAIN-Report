package getWebsiteData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

public class FindCourseCodes 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		char c1, c2, c3;//Three characters of course code
		int i1, i2, i3;//Three numbers of course code
		int credits;
		String cc;
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line = new String();
	    String temp;
	    ArrayList<String> courses = new ArrayList<String>();
	    WebChecker[] webCheck = new WebChecker[676];
		for(int i = 88; i <= 88; i++)//65 corresponds to ASCII 'A', 90 corresponds to ASCII 'Z'
			for(int j = 67; j <= 83; j++)
				for(int k = 65; k <= 90; k++)
					for(int l = 0; l <= 3; l++)//There are no three hundred level courses or higher at Suffolk
						for(int m = 0; m < 10; m++)
							for(int n = 0; n < 10; n++)
							{
								cc = Character.toString((char) i) + Character.toString((char) j) + Character.toString((char) k) + l + m + n;
								try
								{
							        url = new URL("http://www.sunysuffolk.edu/Courses/course.asp?catnum=" + cc);
							        is = url.openStream();  // throws an IOException
							        br = new BufferedReader(new InputStreamReader(is));
							        while ((temp = br.readLine()) != null) 
							        {
							            line += temp;
							        }
							        if(!line.contains("Course Description unavailable"))
							        {
							        	System.out.println(cc);
							        	courses.add(cc);
							        }
								}
							   catch(Exception e){}
								line = "";
							}
		PrintWriter fileOut = new PrintWriter("Courses.txt");
		for(String s : courses)
			fileOut.println(s);
		fileOut.flush();
		fileOut.close();
	}
}
/*
 *  URL url;
    InputStream is = null;
    BufferedReader br;
    String line;

    try {
        url = new URL("http://stackoverflow.com/");
        is = url.openStream();  // throws an IOException
        br = new BufferedReader(new InputStreamReader(is));

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    } catch (MalformedURLException mue) {
         mue.printStackTrace();
    } catch (IOException ioe) {
         ioe.printStackTrace();
    } finally {
        try {
            if (is != null) is.close();
        } catch (IOException ioe) {
            // nothing to see here
        }
    }
 */
/*
 * for(int k = 65; k <= 90; k++)
					for(int l = 0; l <= 2; l++)//There are no three hundred level courses or higher at Suffolk
						for(int m = 0; m < 10; m++)
							for(int n = 0; n < 10; n++)
							{
								cc = Character.toString((char) i) + Character.toString((char) j) + Character.toString((char) k) + l + m + n;
								try
								{
							        url = new URL("http://www.sunysuffolk.edu/Courses/course.asp?catnum=" + cc);
							        is = url.openStream();  // throws an IOException
							        br = new BufferedReader(new InputStreamReader(is));
							        while ((temp = br.readLine()) != null) 
							        {
							            line += temp;
							        }
							        if(!line.contains("Course Description unavailable"))
							        {
							        	System.out.println(cc);
							        	courses.add(cc);
							        }
								}
							   catch(Exception e){}
								line = "";
							}
 */
