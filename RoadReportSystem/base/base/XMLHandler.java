package base;

import java.util.ArrayList;

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
		
		int[] times = {2016, 3, 3, 5, 0, 0}; //Format year, month, day, hour, minute, second
		String search = XMLHandler.generateTime(times);
		int indexOfTime = 0;
		for(int i = 0; i < time.size(); i++) {
			if(yr.getAttributeFromTag(time.get(i), "from").equals(search)) {
				indexOfTime = time.get(i);
			}
		}
		
		ArrayList<Integer> timeSubParts = yr.getTagChildren(indexOfTime);
		int indexOfTemperature = -1;
		for(int i = 0; i < timeSubParts.size(); i++) {
			if(yr.getAttributeFromTag(timeSubParts.get(i), "tagname").equals("temperature")) {
				indexOfTemperature = timeSubParts.get(i);
			}
		}
		
		if(indexOfTemperature == -1) return;
		System.out.println("At " + search + " the temperature will be " + yr.getAttributeFromTag(indexOfTemperature, "value") + " " + yr.getAttributeFromTag(indexOfTemperature, "unit"));
		
		int sunRise = yr.getTagsNamed("sun").get(0);
		System.out.println("Sun will rise at " + extractYrTime(yr.getAttributeFromTag(sunRise, "rise")) + " and set at " + extractYrTime(yr.getAttributeFromTag(sunRise, "set")));
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
