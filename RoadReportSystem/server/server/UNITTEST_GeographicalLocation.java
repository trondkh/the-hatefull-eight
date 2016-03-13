package server;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class UNITTEST_GeographicalLocation {

	GeographicalArea trondheim;
	ArrayList<GeographicalArea> norway;
	
	@Before
	public void setUp()
	{
		trondheim  = new GeographicalArea("Trondheim");
		norway = new ArrayList<GeographicalArea>();
		addKommuner();
	}
	
	public void addKommuner()
	{
		norway.add(new GeographicalArea("Hallingdal"));
		norway.add(new GeographicalArea(""));
		norway.add(new GeographicalArea("Hallingdal"));
		norway.add(new GeographicalArea("Hallingdal"));
		norway.add(new GeographicalArea("Hallingdal"));
		norway.add(new GeographicalArea("Hallingdal"));
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
		
	}
}
