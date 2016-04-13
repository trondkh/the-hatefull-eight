package server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GeographicalArea {
	
	private String countyName;
	private WeatherData weatherData;
	private RoadData roadData;
	public Queue<CarData> cars;
	private Iterator<CarData> carIt;
	private int upperLimit;
	private int expirationHours;
	
	public GeographicalArea(String countyName)
	{
		this.countyName = countyName;
		this.cars = new LinkedList<CarData>();
		this.upperLimit = 1000;
		this.expirationHours = (24*1);
	}
	
	public String getName()
	{
		return this.countyName;
	}
	
	public ArrayList<CarData> getListOfCars()
	{
		ArrayList<CarData> l = new ArrayList<CarData>();
		Iterator<CarData> it = getCarDataIterator();
		while(it.hasNext())
		{
			l.add(new CarData(it.next()));
		}
		return l;
	}
	
	public void setUpperLimitCars(int upperLimit)
	{
		this.upperLimit = upperLimit;
	}
	
	public void setExpirationHours(int expirationHours)
	{
		this.expirationHours = expirationHours;
	}
	
	public void updateWeatherData(WeatherData wd)
	{
		this.weatherData = wd;
	}
	
	public void updateRoadData(RoadData rd)
	{
		this.roadData = rd;
	}
	
	public void updateWithCarData(CarData cd)
	{
		System.out.println("Car in " + this.countyName + " with plate " + cd.getLicensePlate());
		for(CarData c : cars){
			if(c.isThisLicensePlate(cd.getLicensePlate())){
				cars.remove(c);
				break;
			}
		}
		cars.add(cd);
		if(cars.size()>this.upperLimit)
		{
			cars.remove();
		}
	}
	
	public int numberOfCarsInArea()
	{
		return cars.size();
	}
	
	private Iterator<CarData> getCarDataIterator()
	{
		return cars.iterator();
	}
	
	@Override
	public String toString()
	{
		String retVal = new String("");
		retVal+= this.countyName + " has the following cars:\n";
		for(CarData cd:cars)
		{
			retVal += cd.toString() + "\n";
		}
		retVal += "end of " + this.countyName + "\n-----\n\n";
		return retVal;
	}
}
