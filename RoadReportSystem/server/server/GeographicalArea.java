package server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GeographicalArea {
	
	private String countyName;
	private WeatherData weatherData;
	private RoadData roadData;
	private Queue<CarData> cars;
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
		cars.add(cd);
		if(cars.size()>this.upperLimit)
		{
			cars.remove();
		}
	}
	
	public void oneHourHasPassed()
	{
		carIt = getCarDataIterator();
		while(carIt.hasNext())
		{
			carIt.next().incrementHours();
		}
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
