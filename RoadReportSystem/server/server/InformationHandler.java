package server;

public class InformationHandler {
	
	public InformationHandler() {
	
	}

	//63.4157977,10.4018315
	public void parseMessage(String str) {
		String temp = str;
		
		String[] subs = temp.split(",");
		double lon = Double.parseDouble(subs[0]);
		double lat = Double.parseDouble(subs[1]);
		
		CoordinateHandler coordinates = new CoordinateHandler(lat, lon);
		
		XMLFile yr = new XMLFile(true, coordinates.generateYrURL());
		
		XMLHandler xmlHandler = new XMLHandler(yr, null, coordinates);
		xmlHandler.exampleRequest();
	}
	
}
