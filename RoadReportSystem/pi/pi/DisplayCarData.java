package pi;

// Class to store JSON display data according to time
public class DisplayCarData {
	
	double time;
	boolean airbag,antispin;
	double latitude,longitude;
	
	
	public DisplayCarData(double time, double latitude, double longitude, boolean airbag, boolean antispin){
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;		
		this.airbag = airbag;
		this.antispin = antispin;
	}
	

}
