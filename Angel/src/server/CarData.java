package server;

import java.util.Calendar;
import java.util.Date;

public class CarData {
	
	private String message;
	private boolean roadSlippage;
	private String licensePlate;
	private boolean airbag;
	private Calendar timestamp;
	
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
		this.timestamp = Calendar.getInstance();
		this.message = "";
		this.roadSlippage = false;
		this.airbag = false;
	}
	
	public boolean isThisLicensePlate(String licensePlate)
	{
		return this.licensePlate.equals(licensePlate);
	}
	
	public int getSeconds()
	{
		return ((int)(Calendar.getInstance().getTimeInMillis() - timestamp.getTimeInMillis()))/1000;
//		LocalDateTime.now();
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
		retVal += "Car in system: " + this.getSeconds() + " seconds";
		retVal += " Icy road: " + (this.roadSlippage ? "YES" : "NO");
		retVal += " Airbag deployed: " + (this.airbag ? "YES" : "NO");
		retVal += " Licenseplate: " + this.licensePlate + "\n";
		return retVal;
	}
}
