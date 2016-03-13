package server;

import java.util.ArrayList;

public class GeoAreaMain {

	ArrayList<GeographicalArea> norway;

	public static void main(String[] args) {
		GeoAreaMain gam = new GeoAreaMain();
		gam.init();
		gam.run();
	}
	
	public void init()
	{
		norway = new ArrayList<GeographicalArea>();
		Norway n = new Norway();
		for(int i=0;i<n.getNumberOfKommuner();i++)
		{
			norway.add(new GeographicalArea(n.getKommuneNumber(i)));
		}
		// Add cars to some random fylker
		CarData cd = new CarData("Car in a fylke...", 666);
		norway.get(0).updateWithCarData(cd);
		norway.get(10).updateWithCarData(cd);
		norway.get(20).updateWithCarData(cd);
		norway.get(32).updateWithCarData(cd);
		norway.get(66).updateWithCarData(cd);
		norway.get(50).updateWithCarData(cd);
	}
	
	public void run()
	{
		for(GeographicalArea a:norway)
		{
			System.out.println(a.toString());
		}
	}

}
