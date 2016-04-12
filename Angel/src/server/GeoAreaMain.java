package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Packets.Packet;


public class GeoAreaMain {

	ArrayList<GeographicalArea> norway;
	Map<String,GeographicalArea> norwayDict;
	Listener listener;
	GeographicalArea k;

	public static void main(String[] args) {
		InformationHandler informationHandler = new InformationHandler();
		System.out.println(informationHandler.parseMessage("63.415763,10.406500"));
		
		GeoAreaMain gam = new GeoAreaMain();
		gam.init();
		gam.run();
		
	}
	
	public void init()
	{
		listener = new Listener();
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
		addTestData();
		
		while(true)
		{
			getNewCar(listener.getCarData());
			Packet packet= createCarPackage();
			listener.sendCarData(packet);
			listener.close();
		}
	}

	private String getKommune(double latitude, double longditude)
	{
		CoordinateHandler city = new CoordinateHandler(latitude, longditude); 
		return city.getCityName();
	}
	
	private void printAreasWithCars()
	{
		for(GeographicalArea a:norwayDict.values())
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
		for(GeographicalArea ga:norway)
		{
			norwayDict.put(ga.getName(), ga);
		}
	}
	
	private void getNewCar(Packet packet)
	{
		String kommune = getKommune(packet.getLatitude(),packet.getLongitude());
		k = norwayDict.get(kommune);
		k.updateWithCarData(new CarData("bla bla", 1, packet.getLicensePlate()));
		printAreasWithCars();
		
	}
	
	private Packet createCarPackage()
	{
		int numAirbag = 0;
		int icy = 0;
		
		for(CarData c : k.cars)
		{
			if(c.isAirbag())
			{
				numAirbag++;
			}
			icy+=c.getSlippage();
		}

		return new Packet(new ArrayList<String>(), numAirbag, icy, "Road is fine");
	}
	

}
