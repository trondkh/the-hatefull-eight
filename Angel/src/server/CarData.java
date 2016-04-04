package server;

public class CarData {
	
	private int numberOfHoursInServer;
	private String message;
	private int roadSlippage;
	private String licensePlate;
	private boolean airbag;
	
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
	
	public void setAirbag()
	{
		this.airbag = true;
	}
	
	public void resetAirbag()
	{
		this.airbag = false;
	}
	
	public boolean isAirbag()
	{
		return this.airbag;
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
	
	public String getLicensePlate(){
		return licensePlate;
	}
	
	public int getSlippage()
	{
		return this.roadSlippage;
	}
}
