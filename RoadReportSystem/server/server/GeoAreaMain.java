package server;

import java.util.ArrayList;

public class GeoAreaMain {

	ArrayList<GeographicalArea> norway;

	public static void main(String[] args) {
		GeoAreaMain gam = new GeoAreaMain();
//		gam.init();
//		gam.run();
	}
	
	public void init()
	{
		norway = new ArrayList<GeographicalArea>();
		Norway n = new Norway();
		for(int i=0;i<n.getNumberOfKommuner();i++)
		{
			norway.add(new GeographicalArea(n.getKommuneNumber(i)));
		}
	}
	
	public void run()
	{
		for(GeographicalArea a:norway)
		{
			System.out.println(a.toString());
		}
	}

}
