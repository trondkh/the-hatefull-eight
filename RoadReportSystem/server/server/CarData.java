package server;

public class CarData {
	
	private int numberOfHoursInServer;
	private String message;
	private int roadSlippage;
	private String licensePlate;
	
	public CarData(CarData cd)
	{
		this.init(cd.licensePlate);
		this.roadSlippage = cd.roadSlippage;
		this.message = cd.message;
	}
	
	public CarData(String licensePlate)
	{
		this.init(licensePlate);
	}
	
	public CarData(String message, String licensePlate)
	{
		this.init(licensePlate);
		this.message = message;
	}
	
	public CarData(int roadSlippage, String licensePlate)
	{
		this.init(licensePlate);
		this.roadSlippage = roadSlippage;
	}
	
	public CarData(String message, int roadSlippage, String licensePlate)
	{
		this.init(licensePlate);
		this.roadSlippage = roadSlippage;
		this.message = message;
	}

	private void init(String licensePlate)
	{
		this.licensePlate = licensePlate;
		this.numberOfHoursInServer = 0;
		this.message = "";
		this.roadSlippage = 0;
	}
	
	public boolean isThisLicensePlate(String licensePlate)
	{
		return this.licensePlate.equals(licensePlate);
	}

	public void incrementHours()
	{
		this.numberOfHoursInServer++;
	}
	
	public int getNumberOfHoursInServer()
	{
		return this.numberOfHoursInServer;
	}
	
	@Override
	public String toString()
	{
		String retVal = new String("");
		retVal += "Car hours: " + Integer.toString(this.numberOfHoursInServer);
		retVal += " RoadSlippage: " + Integer.toString(this.roadSlippage);
		retVal += " Msg: " + this.message + "\n";
		return retVal;
	}
}
