package pi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import json.*;

public class Pi {
	
	void readShit()
	{
		ArrayList<String> allTheLines = new ArrayList<String>();
		Reader r;
		JsonArray items = new JsonArray();
		try
		{
			r = new FileReader("./pi/pi/uptown-west.json");
			BufferedReader br = new BufferedReader(r);
			String str;
			while((str = br.readLine())!=null)
			{
				allTheLines.add(str);
			}
//			items = Json.parse(str).asArray();

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String[] strings = new String[3];
		String str;
		for(int i=0;i<10;i++)
		{
			str = allTheLines.get(i);
			strings = str.split(","); 
			str = strings[0].split(":")[1];
			str += " at "; 
			str += strings[1].split(":")[1];
			System.out.println(str);
		}
	}
	
	public static void main(String[] args) {
		Pi p = new Pi();
		p.readShit();
	}

}
