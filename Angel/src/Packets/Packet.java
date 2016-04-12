﻿package Packets;

import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable{
	
	private String roadData, coordinates;
	private ArrayList<String> weatherData;
	private boolean isRequest, isReport;
	private int numberOfAirbags, numberOfSlipperies;
	private double longitude, latitude;
	String licensePlate;
	
	
	public Packet(ArrayList<String> weatherData, int numberOfAirbags, int numberOfSlipperies, String roadData) {
		this.weatherData = weatherData;
		this.roadData = roadData;
		this.numberOfAirbags = numberOfAirbags;
		this.numberOfSlipperies = numberOfSlipperies;
		isRequest = false;
		isReport = false;
	}
	
	public Packet(boolean isRequest, String coordinates) {
		isReport = false;
		this.isRequest = isRequest;
		this.coordinates = coordinates;
	}
	
	public Packet(boolean isReport, double latitude, double longitude, boolean hasAirbag, boolean hasSlipped, String licensePlate) {
		this.isReport = isReport;
		this.longitude = longitude;
		this.latitude = latitude;
		if(hasAirbag) numberOfAirbags = 1;
		if(hasSlipped) numberOfAirbags = 1;
		roadData = null;
		coordinates = null;
		weatherData = null;
		this.licensePlate = licensePlate;
	}
	
	
	public String getCoordinates() {
		return coordinates;
	}
	
	public String toString() {
		if(isRequest) return coordinates;
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
		if(str == null) return "No data for weather";
		String temp = "";
		temp += "Temperatur på " + str.get(0) + " " + str.get(1) + "\n";
		temp += str.get(2) + " på " + str.get(3) + " grader \n";
		temp += "Lufttrykk på " + str.get(4) + " " + str.get(5);
		
		return temp;
	}
	
	public String displayVegvesen(String str) {
		String temp = str;
		return temp;
	}
	
	public boolean isReport() {return isReport;}
	public double getLatitude() {return latitude;}
	public double getLongitude() {return longitude;}
	public String getLicensePlate() { return this.licensePlate; }
	public int getAirbags() { return this.numberOfAirbags; }
	public int getSlips() { return this.numberOfSlipperies; }
	public String getRoadCondition() { return this.roadData; }
	
	
}
