package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class XMLHandler {
	
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
		//System.out.println("Requesting...");
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
		if(timeSubParts == null) return null;
		
		for(int i2 = 0; i2 < requestLength; i2++) {
			for(int i = 0; i < timeSubParts.size(); i++) {
				if(yr.getAttributeFromTag(timeSubParts.get(i), "tagname").equals(tags.get(i2).trim())) {
					temp.add(yr.getAttributeFromTag(timeSubParts.get(i), values.get(i2)));
					continue;
				}
			}
		}
		
		
		//System.out.println("------Recieved " + temp.size() + " results--------");
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
	
	public ArrayList<Integer> getCloseWarnings(double lat, double lon, double threshold) {
		ArrayList<Integer> messages = vegvesen.getTagsNamed("message");
		ArrayList<Integer> startPositions = vegvesen.getTagsNamed("startPoint");
		ArrayList<Integer> relevantOnes = new ArrayList<Integer>();
		
		for(int i = 0; i < startPositions.size(); i++) {
			ArrayList<Integer> children = vegvesen.getTagChildren(startPositions.get(i));
			//vegvesen.printTag(children.get(0));
			
			double x = 0;
			double y = 0;
			
			try {
				x = Double.parseDouble(vegvesen.getText(children.get(0)));
				y = Double.parseDouble(vegvesen.getText(children.get(1)));
			} catch(Exception e) {
				continue;
			}
			
			
			double distance = Math.sqrt(Math.pow(x - lon, 2) + Math.pow(y - lat, 2));
			threshold = 4;
			if(distance > threshold) continue;
			
			for(int i2 = 0; i2 < messages.size(); i2++) {
				if(vegvesen.getTagTree(startPositions.get(i)).indexOf(messages.get(i2)) != -1) {
					int index = messages.get(i2);
					if(!vegvesenTimeIsValid(vegvesenExtractText(messages.get(i2), "validTo"))) continue;
					relevantOnes.add(index);
				}
			}
		}
		
		if(relevantOnes.size() < 1) {
			System.out.println("Vegvesen: No relevant warnings");
			return null;
		}
		
		return relevantOnes;
	}
	
	public String vegvesenExtractText(int parent, String name) {
		for(int i = 0; i < vegvesen.getTagChildren(parent).size(); i++) {
			if(vegvesen.getAttributeFromTag(vegvesen.getTagChildren(parent).get(i), "tagname").equals(name)) {
				return vegvesen.getText(vegvesen.getTagChildren(parent).get(i));
			}
		}
		
		return "N/A";
	}
	
	public boolean vegvesenTimeIsValid(String str) {
		if(str.equals("N/A")) return false;
		
		String[] parts = str.trim().split(" ");
		String[] date = parts[0].split("-");
		String[] time = parts[1].split(":");
		int[] dateNumbers = new int[6];
		
		for(int i = 0; i < parts.length; i++) dateNumbers[i] = Integer.parseInt(date[0]);
		for(int i = 0; i < 2; i++) dateNumbers[3 + i] = Integer.parseInt(time[i]);
		
		Date d = new Date();
		int[] localTimes = {d.getYear() + 1900, d.getMonth() + 1, d.getDate(), d.getHours(), d.getMinutes(), d.getSeconds()};
		
		for(int i = 0; i < dateNumbers.length; i++) {
			if(dateNumbers[i] < localTimes[i]) {
				return false;
			} else if(dateNumbers[i] > localTimes[i]) {
				return true;
			}
		}
		
		return true;
	}
	
	//////////////////VEGVESEN//////////////////////
	
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
