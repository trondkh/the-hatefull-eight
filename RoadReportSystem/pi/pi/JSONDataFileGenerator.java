package pi;

import java.io.FileWriter;
import java.io.IOException;
// download json-simple-1.1.1.jar from https://code.google.com/archive/p/json-simple/downloads 	 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONDataFileGenerator {
	 
		@SuppressWarnings("unchecked")
		public static void main(String[] args) throws IOException {
	 
			JSONObject obj = new JSONObject();
			obj.put("timestamp", 0.000101);
			obj.put("value", true);
			obj.put("name", "airbag");
			//add the information you want
	 
			// try-with-resources statement based on post comment below :)
			try (FileWriter file = new FileWriter("/Users/fredr/Documents/GitHub/the-hatefull-eight/RoadReportSystem/pi/pi/file1.txt")) {
				file.write(obj.toJSONString());
				System.out.println("Successfully Copied JSON Object to File...");
				System.out.println("\nJSON Object: " + obj);
			}
		}
	}