package pi;

class NewCarData
{
	double time;
	boolean booleanValue;
	double numberValue;
	String variable;
	//to decide which print function
	boolean print;
	//constructor for boolean value
	public NewCarData(double time, boolean value, String variable)
	{
		this.time = time;
		booleanValue = value;
		this.variable = variable;
		print = true;
	}
	//constructor for gps
	public NewCarData(double time, double value, String variable)
	{
		this.time = time;
		numberValue = value;
		this.variable = variable;
		print = false;
	}
	public long getTimeStamp()
	{
		// Time is returned as milliseconds as input data is given in seconds as 
		// double value.
		time*=1000;
		return (long)time;
	}
	public String toString()
	{
		if (this.print == true){
			return Long.toString(getTimeStamp()) + ": " + this.variable + " = " + this.booleanValue;
		}
		else {
			return Long.toString(getTimeStamp()) + ": " + this.variable + " = " + this.numberValue;
		}
		
	}
}
