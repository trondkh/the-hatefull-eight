package server;

import java.util.ArrayList;

public class InformationHandler {
	
	
	private double lat, lon;
	
	public InformationHandler() {
	
	}

	//63.415763, 10.406500
	public String parseMessage(String str) {
		String temp = str;
		
		String[] subs = temp.trim().split(",");
		this.lat = Double.parseDouble(subs[0].trim());
		this.lon = Double.parseDouble(subs[1].trim());
		
		CoordinateHandler coordinates = new CoordinateHandler(lat, lon);
		
		XMLFile yr = new XMLFile(true, coordinates.generateYrURL());
		XMLFile vegvesen = new XMLFile(true, "http://www.vegvesen.no/trafikk/xml/savedsearch.xml?id=600");
		
		XMLHandler xmlHandler = new XMLHandler(yr, vegvesen, coordinates);
		//xmlHandler.exampleRequest();
		
		return generateResponse(xmlHandler);
	}
	
	
	public String generateResponse(XMLHandler xmlHandler) {
		String temp = "Response....\n";
		
		ArrayList<String> tags = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		tags.add("precipitation"); //Nedbør
		values.add("value");
		
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
		
		ArrayList<Integer> messages = xmlHandler.getCloseWarnings(lat, lon, 1.0);
		
		for(int i = 0; i < messages.size(); i++) {
			temp += xmlHandler.vegvesenExtractText(messages.get(i), "heading") + "\n";
			temp += "\t" + xmlHandler.vegvesenExtractText(messages.get(i), "messageType") + "\n";
		}
		
		return temp;
	}
}
