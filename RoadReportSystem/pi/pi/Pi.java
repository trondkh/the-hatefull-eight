package pi;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import json.*;

public class Pi {
	
	void readShit()
	{
		Reader r;
		JsonArray items = new JsonArray();
		try
		{
			r = new FileReader("uptown-west.json");
			items = Json.parse(r).asArray();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		for(JsonValue v : items)
		{
			System.out.println(v.toString());
		}
	}
	
	public static void main(String[] args) {
		Pi p = new Pi();
		p.readShit();
	}

}
