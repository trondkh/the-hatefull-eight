package server;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class UNITTEST_GeographicalLocation {

	GeographicalArea trondheim;
	ArrayList<GeographicalArea> norway;
	ArrayList<CarData> cars;
	
	@Before
	public void setUp()
	{
		trondheim  = new GeographicalArea("Trondheim");
		norway = new ArrayList<GeographicalArea>();
		cars = new ArrayList<CarData>();
		addKommuner();
	}
	
	public void addKommuner()
	{
		Norway n = new Norway();
		for(int i=0;i<n.getNumberOfFylker();i++)
		{
			norway.add(new GeographicalArea(n.getKommuneNumber(i)));
		}
	}
	
	public void addCars()
	{
		cars.add(new CarData("Car 1", 10));
		cars.add(new CarData("Car 2", 100));
		cars.add(new CarData("Car 3", 5));
		cars.add(new CarData("Car 4", 534));
		cars.add(new CarData("Car 5", 9));
	}

	@Test
	public void constructGeo()
	{
		try
		{
			new GeographicalArea("Trondheim");
		}
		catch(Exception e)
		{
			fail("No exception should occur");
		}
	}
	
	@Test
	public void addCarData()
	{
		for(CarData cd:cars)
		{
			trondheim.updateWithCarData(cd);
		}
	}
}
