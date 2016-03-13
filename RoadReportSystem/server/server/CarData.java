package server;

public class CarData {
	
	private int numberOfHoursInServer;
	private String message;
	private int roadSlippage;
	
	public CarData()
	{
		this.init();
	}
	
	public CarData(String message)
	{
		this.init();
		this.message = message;
	}
	
	public CarData(int roadSlippage)
	{
		this.init();
		this.roadSlippage = roadSlippage;
	}
	
	public CarData(String message, int roadSlippage)
	{
		this.init();
		this.roadSlippage = roadSlippage;
		this.message = message;
	}

	private void init()
	{
		this.numberOfHoursInServer = 0;
		this.message = "";
		this.roadSlippage = 0;
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
