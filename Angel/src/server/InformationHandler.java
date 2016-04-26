package server;

import java.util.ArrayList;

public class InformationHandler implements ConnectionHandler {
	
	public static XMLFile vegvesen;
	
	public InformationHandler() {
		vegvesen = new XMLFile(true, "http://www.vegvesen.no/trafikk/xml/savedsearch.xml?id=600");
	}

	//63.4157977,10.4018315
	public String parseMessage(String str) {
		String temp = str;
		
		String[] subs = temp.trim().split(",");
		double lat = Double.parseDouble(subs[0].trim());
		double lon = Double.parseDouble(subs[1].trim());
		
		CoordinateHandler coordinates = new CoordinateHandler(lat, lon);
		
		XMLFile yr = new XMLFile(true, coordinates.generateYrURL());
		
		XMLHandler xmlHandler = new XMLHandler(yr, null, coordinates);
		//xmlHandler.exampleRequest();
		
		return generateResponse(xmlHandler);
	}
	
	
	public String generateResponse(XMLHandler xmlHandler) {
		String temp = "";//"Response....\n";
		
		ArrayList<String> tags = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		tags.add("windDirection");
		values.add("deg");
		
		tags.add("temperature");
		values.add("value");
		
		tags.add("temperature");
		values.add("unit");
		
		tags.add("windSpeed");
		values.add("name");
		
		tags.add("pressure");
		values.add("value");
		
		tags.add("pressure");
		values.add("unit");
		
		ArrayList<String> results = xmlHandler.requestYr(tags, values);
		
		for(int i = 0; i < tags.size(); i++) {
			temp += (tags.get(i) + " / " + values.get(i) + ": " + results.get(i)) + "\n";
		}
		
		
		return temp;
	}
	
	public ArrayList<String> getYrResults(String coor) {
		String[] subs = coor.trim().split(",");
		double lat = Double.parseDouble(subs[0].trim());
		double lon = Double.parseDouble(subs[1].trim());
		
		CoordinateHandler coordinates = new CoordinateHandler(lat, lon);
		
		XMLFile yr = new XMLFile(true, coordinates.generateYrURL());
		
		XMLHandler xmlHandler = new XMLHandler(yr, null, coordinates);
		
		ArrayList<String> tags = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		/*tags.add("temperature");
		values.add("value");
		
		tags.add("temperature");
		values.add("unit");
		
		tags.add("windSpeed");
		values.add("name");
		
		tags.add("windDirection");
		values.add("deg");
		
		tags.add("pressure");
		values.add("value");
		
		tags.add("pressure");
		values.add("unit");*/
		
		tags.add("precipitation");
		values.add("value");
		
		ArrayList<String> results = xmlHandler.requestYr(tags, values);
		
		return results;
	}
	
	public ArrayList<String> getRoadData(String coordinates) {
		CoordinateHandler coor = new CoordinateHandler(Double.parseDouble(coordinates.split(",")[0]), Double.parseDouble(coordinates.split(",")[1]));
		XMLHandler xmlHandler = new XMLHandler(null, vegvesen, coor);
		ArrayList<Integer> result = xmlHandler.getCloseWarnings(Double.parseDouble(coordinates.split(",")[0]), Double.parseDouble(coordinates.split(",")[1]), 1);
		ArrayList<String> temp = new ArrayList<String>();
		if(result == null || result.size() == 0) return temp;
		
		/*for(int i = 0; i < result.size(); i++) {
			System.out.println(xmlHandler.vegvesenExtractText(result.get(i), "heading"));
			System.out.println(xmlHandler.vegvesenExtractText(result.get(i), "urgency"));
			System.out.println(xmlHandler.vegvesenExtractText(result.get(i), "messageType"));
			System.out.println(xmlHandler.vegvesenExtractText(result.get(i), "roadType") + " " + xmlHandler.vegvesenExtractText(result.get(i), "roadNumber"));
			System.out.println();
		}*/
		
		for(int i = 0; i < result.size(); i++) temp.add(xmlHandler.vegvesenExtractText(result.get(i), "heading").split(",")[0]);
		
		return temp;
	}
	
	public int numberOfAirbags() {
		return 0;
	}
	
	public int numberOfSlipperies() {
		return 0;
	}
}
