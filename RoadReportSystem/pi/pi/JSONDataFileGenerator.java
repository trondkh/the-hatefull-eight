package pi;

import java.io.FileWriter;
import java.io.IOException;
	 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONDataFileGenerator {
	 
		@SuppressWarnings("unchecked")
		public static void main(String[] args) throws IOException {
	 
			JSONObject obj = new JSONObject();
			obj.put("Name", "airbag");
			obj.put("Value", true);
	 
	 
			// try-with-resources statement based on post comment below :)
			try (FileWriter file = new FileWriter("/Users/fredr/Documents/GitHub/the-hatefull-eight/RoadReportSystem/pi/pi/file1.txt")) {
				file.write(obj.toJSONString());
				System.out.println("Successfully Copied JSON Object to File...");
				System.out.println("\nJSON Object: " + obj);
			}
		}
	}