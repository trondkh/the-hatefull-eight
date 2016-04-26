package Packets;

import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable{
	
	private String coordinates, licensePlate;
	private ArrayList<String> weatherData, roadData;
	private boolean isReport;
	private int numberOfAirbags, numberOfSlipperies;
	private double longitude, latitude, mmOfPercipitation; 
	private boolean airbagDeployed, hasSlipped;
	
	
	public Packet(boolean isReport, double latitude, double longitude, boolean hasAirbag, boolean hasSlipped, String licensePlate) {
		this.isReport = isReport;
		this.longitude = longitude;
		this.latitude = latitude;
		this.airbagDeployed = hasAirbag;
		this.hasSlipped = hasSlipped;
		roadData = null;
		coordinates = null;
		weatherData = null;
		this.licensePlate = licensePlate;
	}
	
	
	public Packet(ArrayList<String> weatherData, int numberOfAirbags, int numberOfSlipperies, ArrayList<String> roadData) {
		this.weatherData = weatherData;
		if(weatherData.size() > 0) try{mmOfPercipitation = Double.parseDouble(weatherData.get(0));} catch(NumberFormatException e) {e.printStackTrace();}
		this.roadData = roadData;
		this.numberOfAirbags = numberOfAirbags;
		this.numberOfSlipperies = numberOfSlipperies;
		isReport = false;
	}
	
	
	public String getCoordinates() {
		return coordinates;
	}
	
	public String toString() {
		if(isReport) return coordinates;
		String str = "WEATHER: \n";
		//str += weatherData + "\n";
		str += displayYr(weatherData) + " \n\n";
		
		str += "CAR DATA: \n";
		str += "Number of cars that has slipped: " + numberOfSlipperies + "\n";
		str += "Number of accidents: " + numberOfAirbags + "\n\n";
		
		str += "ROAD REPORTS: \n";
		
		str += displayVegvesen(roadData);
		
		return str;
	}
	
	public String displayYr(ArrayList<String> str) {
		if(str == null || str.size() == 0) return "No data for weather";
		String temp = "";
		/*temp += "Temperatur på " + str.get(0) + " " + str.get(1) + "\n";
		temp += str.get(2) + " på " + str.get(3) + " grader \n";
		temp += "Lufttrykk på " + str.get(4) + " " + str.get(5);*/
		
		temp = mmOfPercipitation + " mm percipitation";
		
		return temp;
	}
	
	public String displayVegvesen(ArrayList<String> str) {
		if(str == null || str.size() == 0) return "No road messages";
		
		String temp = "";
		for(int i = 0; i < str.size(); i++) temp += str.get(i) + " er stengt\n";
		
		return temp;
	}
	
	public boolean isReport() {return isReport;}
	public double getLatitude() {return latitude;}
	public double getLongitude() {return longitude;}
	public String getLicensePlate() { return this.licensePlate; }
	public int getAirbags() { return this.numberOfAirbags; }
	public int getSlips() { return this.numberOfSlipperies; }
	public ArrayList<String> getRoadCondition() { return this.roadData; }
	public boolean airbagDeployed() { return this.airbagDeployed; }
	public boolean hasSlipped() { return this.hasSlipped; }
	
	
}
