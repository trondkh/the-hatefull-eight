package pi;

import java.util.ArrayList;

public class DemoCoordinateGenerator {
	
	Location mandal; 
	Location Evje;
	Location Skien;
	Location Tønsberg;
	Location Kongsberg;
	Location Oslo;
	Location Trondheim;
	
	ArrayList<Location> fixedPoints = new ArrayList<Location>();

	class Location
	{
		double latiture; double longditude;
		public Location(double latitude, double longditude)
		{
			this.latiture = latitude; this.longditude = longditude;
		}
	}

	ArrayList<Location> coordinates;
	
	public static void main(String[] args) {
		DemoCoordinateGenerator dcg = new DemoCoordinateGenerator();
		dcg.test();
	}
	
	public DemoCoordinateGenerator()
	{
		init();
	}
	
	public void test()
	{
		ArrayList<Double> t1,t2;
		t1 = this.getLatitude();
		t2 = this.getLongditude();
		System.out.println("The fixed locations are:");
		for(int i=0;i<t1.size();i++)
		{
			System.out.println(t1.get(i) + " " + t2.get(i));
		}
		System.out.println(" The interpolated locations are:");
		t1 = new ArrayList<Double>();
		t2 = new ArrayList<Double>();
		this.calculateCoordinates(2, t1, t2);
		for(int i=0;i<t1.size();i++)
		{
			System.out.println(t1.get(i) + " " + t2.get(i));
		}

	}
	
	public void init()
	{
		mandal = new Location(58.026684,7.451513);
		fixedPoints.add(mandal);
		Evje = new Location(58.588834,7.788852);
		fixedPoints.add(Evje);
		Skien = new Location(59.204758,9.605145);
		fixedPoints.add(Skien);
		Tønsberg = new Location(59.267108,10.412387);
		fixedPoints.add(Tønsberg);
		Kongsberg = new Location(59.664713,9.644523);
		fixedPoints.add(Kongsberg);
		Oslo = new Location(59.908576,10.74012);
		fixedPoints.add(Oslo);
		Trondheim = new Location(63.424418,10.41918);
		fixedPoints.add(Trondheim);
	}
	
	public void run()
	{
		
	}

	public ArrayList<Double> getLatitude()
	{
		ArrayList<Double> retVal = new ArrayList<Double>();
		for(Location l:fixedPoints)
		{
			retVal.add(new Double(l.latiture));
		}
		return retVal;
	}
	
	public ArrayList<Double> getLongditude()
	{
		ArrayList<Double> retVal = new ArrayList<Double>();
		for(Location l:fixedPoints)
		{
			retVal.add(new Double(l.longditude));
		}
		return retVal;
	}

	private void calculateCoordinates(int points, ArrayList<Double> latitudes, ArrayList<Double> longditudes)
	{
		int indexUpper = fixedPoints.size()-1;
		double latFrom,latTo,lonFrom,lonTo;
		for(int i=0;i<indexUpper;i++)
		{
			latFrom = fixedPoints.get(i).latiture;
			latTo = fixedPoints.get(i+1).latiture;
			lonFrom = fixedPoints.get(i).longditude;
			lonTo = fixedPoints.get(i+1).longditude;
			insertCoordinates(latitudes, latFrom, latTo, points);
			insertCoordinates(longditudes, lonFrom, lonTo, points);
		}

		latitudes.add(fixedPoints.get(indexUpper).latiture);
		longditudes.add(fixedPoints.get(indexUpper).longditude);
		
	}
	
	private void insertCoordinates(ArrayList<Double> coordinates, double from, double to, int points)
	{
		double delta = (to-from)/((double)points);
		double newVal = from;
		coordinates.add(new Double(newVal));
		for(int i=0;i<(points-1);i++)
		{
			newVal+=delta;
			coordinates.add(new Double(newVal));
		}
	}
}
