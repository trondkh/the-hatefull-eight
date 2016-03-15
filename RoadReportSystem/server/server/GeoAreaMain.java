package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeoAreaMain {

	ArrayList<GeographicalArea> norway;
	Map<String,GeographicalArea> norwayDict;

	public static void main(String[] args) {
		InformationHandler informationHandler = new InformationHandler();
		System.out.println(informationHandler.parseMessage("63.415763,10.406500"));
		
		GeoAreaMain gam = new GeoAreaMain();
		gam.init();
		gam.run();
		
	}
	
	public void init()
	{
		norway = new ArrayList<GeographicalArea>();
		norwayDict = new HashMap<String,GeographicalArea>();
		Norway n = new Norway();
		for(int i=0;i<n.getNumberOfKommuner();i++)
		{
			norway.add(new GeographicalArea(n.getKommuneNumber(i)));
			
			// Add to dictionary/ map
			GeographicalArea kommune = new GeographicalArea(n.getKommuneNumber(i));
			norwayDict.put(kommune.getName(), kommune);
		}
	}
	
	public void run()
	{

	}
	
	public void addCarSlippage(String licensePlate, double latitude, double longditude)
	{
		String kommune = getKommune(latitude,longditude);
		GeographicalArea k = norwayDict.get(kommune);
		k.updateWithCarData(new CarData(1,licensePlate));
	}

	private String getKommune(double latitude, double longditude)
	{
		// Some magical google api will give us a name of the kommune as string....
		return "Trondheim";
	}
	
	private void printAreasWithCars()
	{
		for(GeographicalArea a:norway)
		{
			if(a.numberOfCarsInArea()>0)
			{
				System.out.println(a.toString());
			}
		}
	}

	private void addTestData()
	{
		// Add cars to some random fylker
		CarData cd = new CarData("Car in a fylke...", 666, "PK9999");
		norway.get(0).updateWithCarData(cd);
		norway.get(0).updateWithCarData(cd);
		norway.get(0).updateWithCarData(cd);
		norway.get(0).updateWithCarData(cd);
		norway.get(10).updateWithCarData(cd);
		norway.get(20).updateWithCarData(cd);
		norway.get(32).updateWithCarData(cd);
		norway.get(66).updateWithCarData(cd);
		norway.get(50).updateWithCarData(cd);
	}

}
