package getWebsiteData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class WebChecker extends Thread
{
	String prefix;
	String line, cc, temp;
	URL url;
    InputStream is = null;
    BufferedReader br;
	public WebChecker(String prefix)
	{
		this.prefix = prefix;
	}
	public void start()
	{
		for(int k = 86; k <= 90; k++)
			for(int l = 0; l <= 2; l++)//There are no three hundred level courses or higher at Suffolk
				for(int m = 0; m < 10; m++)
					for(int n = 0; n < 10; n++)
					{
						cc = prefix + Character.toString((char) k) + l + m + n;
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
					        	//courses.add(cc);
					        }
						}
					   catch(Exception e){}
						line = "";
					}
	}
}
