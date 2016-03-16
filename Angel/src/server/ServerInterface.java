package server;

public class ServerInterface {

	GeoAreaMain main = new GeoAreaMain();
	
	public void carReportsSlippage(String licencePlate, double latitude, double longditude)
	{
		main.addCarSlippage(licencePlate, latitude, longditude);
	}
	
}
