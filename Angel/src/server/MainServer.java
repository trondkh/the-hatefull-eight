package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Packets.Packet;


public class MainServer {

	Map<String,GeographicalArea> norwayDict;
	Listener listener;
	GeographicalArea k;

	public static void main(String[] args) {
		InformationHandler informationHandler = new InformationHandler();
		System.out.println(informationHandler.parseMessage("63.415763,10.406500"));
		MainServer gam = new MainServer();
		gam.init();
		gam.run();
		
	}
	
	public void init(){
		listener = new Listener();
		norwayDict = new HashMap<String,GeographicalArea>();
		Norway n = new Norway();
		for(int i=0;i<n.getNumberOfKommuner();i++){
			// Add to dictionary/ map
			GeographicalArea kommune = new GeographicalArea(n.getKommuneNumber(i));
			norwayDict.put(kommune.getName(), kommune);
		}
	}
	
	public void run(){		
		while(true){
			getNewCar(listener.getCarData());
			Packet packet= createCarPackage();
			listener.sendCarData(packet);
			listener.close();
			deleteOldCars(15);
		}
	}
	
	private void deleteOldCars(int seconds)
	{
		for(GeographicalArea a:norwayDict.values()){
			a.removeOldCars(seconds);
		}
	}

	private String getKommune(double latitude, double longditude){
		CoordinateHandler city = new CoordinateHandler(latitude, longditude); 
		return city.getCityName();
	}
	
	private void printAreasWithCars(){
		for(GeographicalArea a:norwayDict.values()){
			if(a.numberOfCarsInArea()>0){
				System.out.println(a.toString());
			}
		}
	}
	
	private void getNewCar(Packet packet){
		String kommune = getKommune(packet.getLatitude(),packet.getLongitude());
		k = norwayDict.get(kommune);
		k.updateWithCarData(new CarData("bla bla", true, false, packet.getLicensePlate()));
		printAreasWithCars();
	}
	
	private Packet createCarPackage(){
		int numAirbag = 0;
		int icy = 0;
		
		for(CarData c : k.cars){
			if(c.isAirbag()){
				numAirbag++;
			}
			if(c.isSlippery()){
				icy++;
			}
		}

		return new Packet(new ArrayList<String>(), numAirbag, icy, "Road is fine");
	}
}