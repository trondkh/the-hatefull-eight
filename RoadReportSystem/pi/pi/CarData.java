package pi;

class CarData
{
	double time;
	CarDataValueHolder value;
	String variable;
	public CarData(double time, CarDataValueHolder value, String variable)
	{
		this.time = time;
		this.value = new CarDataValueHolder(value.data);
		this.variable = variable;
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
		return Long.toString(getTimeStamp()) + ": " + this.variable + " = " + this.value.data;
	}
}
