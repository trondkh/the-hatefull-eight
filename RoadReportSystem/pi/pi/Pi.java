package pi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;


public class Pi {
	
	void readShit()
	{
		ArrayList<String> allTheLines = new ArrayList<String>();
		Reader r;
		try
		{
			r = new FileReader("./pi/pi/uptown-west.json");
			BufferedReader br = new BufferedReader(r);
			String str;
			while((str = br.readLine())!=null)
			{
				allTheLines.add(str);
			}
			br.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String[] strings = new String[3];
		String str;
		for(int i=0;i<100;i++)
		{
			str = Integer.toString(i) + " ";
			str += allTheLines.get(i);
			System.out.println(str);
			strings = str.split(","); 
			str += strings[0].split(":")[1];
			str += " at "; 
			str += strings[1].split(":")[1];
//			System.out.println(str);
		}
	}
	
	public static void main(String[] args) {
		Pi p = new Pi();
		p.readShit();
	}

}
