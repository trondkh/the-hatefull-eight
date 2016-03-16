package server;

import java.util.ArrayList;

public class CoordinateHandler {

	private String cityName, municipaltyName, countyName, countryName;
	private static String[] parentNames = {"country", "administrative_area_level_1", "administrative_area_level_2", "postal_town"};
	private boolean complete;
	
	public CoordinateHandler(double lat, double lon) {
		XMLFile location = new XMLFile(true, "http://maps.googleapis.com/maps/api/geocode/xml?latlng=" + lat + "," + lon);
		
		ArrayList<Integer> addressComponents = location.getTagsNamed("address_component");
		
		int[] parents = {-1, -1, -1, -1};
		for(int i = 0; i < addressComponents.size(); i++) {
			for(int i2 = 0; i2 < location.getTagChildren(addressComponents.get(i)).size(); i2++) {
				if(location.getAttributeFromTag(location.getTagChildren(addressComponents.get(i)).get(i2), "tagname").equals("type")) {
					for(int i3 = 0; i3 < parentNames.length; i3++) {
						if(parents[i3] != -1) continue;
						if(location.getText(location.getTagChildren(addressComponents.get(i)).get(i2)).equals(parentNames[i3])) {
							parents[i3] = i;
						}
					}
				}
			}
		}
		
		for(int i = 0; i < parents.length; i++) {
			if(parents[i] == -1) {
				System.err.println("Error loading location file! Missing " + parentNames[i]);
				complete = false;
				return;
			}
		}
		
		countryName = location.getText(location.getTagChildren(addressComponents.get(parents[0])).get(0));
		countyName = location.getText(location.getTagChildren(addressComponents.get(parents[1])).get(0));
		municipaltyName = location.getText(location.getTagChildren(addressComponents.get(parents[2])).get(0));
		cityName = location.getText(location.getTagChildren(addressComponents.get(parents[3])).get(0));
		
		System.out.println(countryName + ", " + countyName + ", " + municipaltyName + ", " + cityName);
		complete = true;
	}
	
	public String generateYrURL() {
		if(!complete) {
			System.err.println("Not able to generate link");
			return "http://www.yr.no/sted/Norge/Sør-Trøndelag/Trondheim/Trondheim/varsel_time_for_time.xml";
		}
		return "http://www.yr.no/sted/" + countryName + "/" + countyName + "/" + municipaltyName + "/" + cityName + "/varsel_time_for_time.xml";
	}
	
	public String getCityName() {return cityName;}
	public String getMunicipaltyName() {return municipaltyName;}
	public String getCountyName() {return countyName;}
	public String getCountryName() {return countryName;}
}
