package server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GeographicalArea {
	
	private String countyName;
	private WeatherData weatherData;
	private RoadData roadData;
	public ArrayList<CarData> cars;
	private int upperLimit;
	
	public GeographicalArea(String countyName)
	{
		this.countyName = countyName;
		this.cars = new ArrayList<CarData>();
		this.upperLimit = 1000;
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
		for(CarData c : cars){
			if(c.isThisLicensePlate(cd.getLicensePlate())){
				cars.remove(c);
				break;
			}
		}
		cars.add(cd);
		if(cars.size()>this.upperLimit)
		{
			cars.remove(cars.size()-1);
		}
	}
	
	public void removeOldCars(int seconds)
	{
		Iterator<CarData> it = cars.iterator();
		while(it.hasNext())
		{
			CarData cd = it.next();
			if(cd.getSeconds()>=seconds)
			{
				it.remove();
			}
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
		retVal+= this.countyName;
		for(CarData cd:cars)
		{
			retVal += "\n" + cd.toString();
		}
		return retVal;
	}
}
