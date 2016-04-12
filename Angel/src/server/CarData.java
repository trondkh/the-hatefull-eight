package server;

public class CarData {
	
	private int numberOfHoursInServer;
	private String message;
	private boolean roadSlippage;
	private String licensePlate;
	private boolean airbag;
	
	public CarData(CarData cd)
	{
		this.init(cd.licensePlate);
		this.roadSlippage = cd.roadSlippage;
		this.message = cd.message;
	}
	
	public CarData(String message, boolean roadSlippage, boolean airbag, String licensePlate)
	{
		this.init(licensePlate);
		this.roadSlippage = roadSlippage;
		this.airbag = airbag;
		this.message = message;
	}

	private void init(String licensePlate)
	{
		this.licensePlate = licensePlate;
		this.numberOfHoursInServer = 0;
		this.message = "";
		this.roadSlippage = false;
		this.airbag = false;
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
	
	public boolean isAirbag()
	{
		return this.airbag;
	}
	
	public String getLicensePlate(){
		return licensePlate;
	}
	
	public boolean isSlippery()
	{
		return this.roadSlippage;
	}
	
	@Override
	public String toString()
	{
		String retVal = new String("");
		retVal += "Car hours: " + Integer.toString(this.numberOfHoursInServer);
		retVal += " RoadSlippage: " + this.roadSlippage;
		retVal += " Msg: " + this.message + "\n";
		return retVal;
	}
}
