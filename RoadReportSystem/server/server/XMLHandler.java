package server;

import java.util.ArrayList;
import java.util.Date;

public class XMLHandler {
	
	@SuppressWarnings("unused")
	private XMLFile yr, vegvesen;
	@SuppressWarnings("unused")
	private CoordinateHandler location;
	
	public XMLHandler(XMLFile yr, XMLFile vegvesen, CoordinateHandler location) {
		this.yr = yr;
		this.vegvesen = vegvesen;
		this.location = location;
	}
	
	///////////////UNIT TEST///////////////////////
	public void exampleRequest() {
		ArrayList<Integer> time = yr.getTagsNamed("time");
		
		Date d = new Date();
		
		//int[] times = {2016, 3, 3, 5, 0, 0}; //Format year, month, day, hour, minute, second
		int[] times = {d.getYear() + 1900, d.getMonth() + 1, d.getDate(), d.getHours() + 1, 0, 0};
		
		String search = XMLHandler.generateTime(times);
		int indexOfTime = 0;
		for(int i = 0; i < time.size(); i++) {
			if(yr.getAttributeFromTag(time.get(i), "from").equals(search)) {
				indexOfTime = time.get(i);
			}
		}
		
		ArrayList<Integer> timeSubParts = yr.getTagChildren(indexOfTime);
		int indexOfTemperature = -1;
		int indexOfSymbol = -1;
		int indexOfPrecipitation = -1;
		
		for(int i = 0; i < timeSubParts.size(); i++) {
			if(yr.getAttributeFromTag(timeSubParts.get(i), "tagname").equals("temperature")) {
				indexOfTemperature = timeSubParts.get(i);
			}
			
			if(yr.getAttributeFromTag(timeSubParts.get(i), "tagname").equals("symbol")) {
				indexOfSymbol = timeSubParts.get(i);
			}
			
			if(yr.getAttributeFromTag(timeSubParts.get(i), "tagname").equals("precipitation")) {
				indexOfPrecipitation = timeSubParts.get(i);
			}
		}
		
		if(indexOfTemperature == -1 || indexOfSymbol == -1 || indexOfPrecipitation == -1) {
			System.err.println("Couldn't load file");
			return;
		}
		
		System.out.println("At " + search + " the temperature will be " + yr.getAttributeFromTag(indexOfTemperature, "value") + " " + yr.getAttributeFromTag(indexOfTemperature, "unit"));
		System.out.println("	- Vær: 	\t" + yr.getAttributeFromTag(indexOfSymbol, "name"));
		System.out.println("	- Nedbør: \t" + yr.getAttributeFromTag(indexOfPrecipitation, "value"));
		
		int sunRise = yr.getTagsNamed("sun").get(0);
		System.out.println("Sun will rise at " + extractYrTime(yr.getAttributeFromTag(sunRise, "rise")) + " and set at " + extractYrTime(yr.getAttributeFromTag(sunRise, "set")));
	}
	
	public ArrayList<String> requestYr(int[] times, ArrayList<String> tags, ArrayList<String> values) {
		System.out.println("Requesting...");
		ArrayList<String> temp = new ArrayList<String>();
		int requestLength = Math.min(tags.size(), values.size());
		
		
		String search = XMLHandler.generateTime(times);
		int indexOfTime = 0;
		
		ArrayList<Integer> time = yr.getTagsNamed("time");
		
		for(int i = 0; i < time.size(); i++) {
			if(yr.getAttributeFromTag(time.get(i), "from").equals(search)) {
				indexOfTime = time.get(i);
			}
		}
		
		ArrayList<Integer> timeSubParts = yr.getTagChildren(indexOfTime);
		
		for(int i = 0; i < timeSubParts.size(); i++) {
			for(int i2 = 0; i2 < requestLength; i2++) {
				if(yr.getAttributeFromTag(timeSubParts.get(i), "tagname").equals(tags.get(i2).trim())) {
					temp.add(yr.getAttributeFromTag(timeSubParts.get(i), values.get(i2)));
					continue;
				}
			}
		}
		
		System.out.println("------Recieved " + temp.size() + " results--------");
		return temp;
	}
	
	public String requestYr(int[] times, String tag, String tagvalue) {
		String search = XMLHandler.generateTime(times);
		int indexOfTime = 0;
		ArrayList<Integer> time = yr.getTagsNamed("time");
		for(int i = 0; i < time.size(); i++) {
			if(yr.getAttributeFromTag(time.get(i), "from").equals(search)) {
				indexOfTime = time.get(i);
			}
		}
		
		ArrayList<Integer> timeSubParts = yr.getTagChildren(indexOfTime);
		
		for(int i = 0; i < timeSubParts.size(); i++) {
			if(yr.getAttributeFromTag(timeSubParts.get(i), "tagname").equals(tag)) {
				return yr.getAttributeFromTag(timeSubParts.get(i), tagvalue);
			}
		}
		
		return "ERROR for request";
	}
	
	public String requestYr(String tag, String tagvalue) {
		Date d = new Date();
		int[] times = {d.getYear() + 1900, d.getMonth() + 1, d.getDate(), d.getHours() + 1, 0, 0};
		return requestYr(times, tag, tagvalue);
	}
	
	public ArrayList<String> requestYr(ArrayList<String> tags, ArrayList<String> values) {
		Date d = new Date();
		int[] times = {d.getYear() + 1900, d.getMonth() + 1, d.getDate(), d.getHours() + 1, 0, 0};

		return requestYr(times, tags, values);
	}
	/////////////////////////////////////////////////
	
	public static String generateTime(int[] times) {
		//Format year, month, day, hour, minute, second
		String[] timeString = new String[times.length];
		
		for(int i = 0; i < timeString.length; i++) {
			timeString[i] = String.valueOf(times[i]);
			int minLength = 2;
			if(i == 0) minLength = 4;
			while(timeString[i].length() < minLength) {
				timeString[i] = "0" + timeString[i];
			}
		}
		
		return timeString[0] + "-" + timeString[1] + "-" + timeString[2] + "T" + timeString[3] + ":" + timeString[4] + ":00";
	}
	
	public String extractYrTime(String time) {
		String[] parts = time.split("T");
		if(parts.length < 2) return "00:00:00";
		return parts[1];
	}
}
