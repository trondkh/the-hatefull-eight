package pi;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Crunchify.com
 */
 
public class JSONReadFromFile {
 
	class CarDataValueHolder
	{
		Object test;
		public CarDataValueHolder(Object o) {
			this.test = o;
		}
	}

	class CarData
    {
    	double time;
    	CarDataValueHolder value;
    	String variable;
    	public CarData(double time, CarDataValueHolder value, String variable)
    	{
    		this.time = time;
    		this.value = new CarDataValueHolder(value.test);
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
    		return Long.toString(getTimeStamp()) + ": " + this.variable + " = " + this.value.test;
    	}
    }
	
	 ArrayList<CarData> carDataJsonObjects;
	
    public static void main(String[] args) {
       JSONReadFromFile f = new JSONReadFromFile();
       f.printFileAsCarData();
       System.out.println("Total number of JSON objects parsed:");
       System.out.println(f.carDataJsonObjects.size());
    }
    
    public void printFileAsCarData()
    {
    	ArrayList<String> jsonObjects = new ArrayList<String>();
    	readFile(jsonObjects);
    	carDataJsonObjects=json2CarData(jsonObjects);
    	for(CarData cd:carDataJsonObjects)
    	{
    		System.out.println(cd);
    	}
    }
    
    public ArrayList<CarData> json2CarData(ArrayList<String> al)
    {
    	ArrayList<CarData> carDatas = new ArrayList<CarData>();
    	for(String s:al)
    	{

    		carDatas.add(jsonParser(s));
    	}
    	return carDatas;
    }
    
    public void readFile(ArrayList<String> al)
    {
		Reader r;
		try
		{
			r = new FileReader("./pi/pi/uptown-west.json");
			BufferedReader br = new BufferedReader(r);
			String str;
			while((str = br.readLine())!=null)
			{
				if(str.length()!=0)
				{
					al.add(str);
				}
			}
			br.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    	
    }
    
    private CarData jsonParser(String str)
    {
		CarData cd = null;
        try {
			if(str==null || str.length()==0)
			{
				String errorMsg = "String variable is invalid for jsonParser. Str=";
				errorMsg+=str;

				throw new IllegalStateException(errorMsg);
			}
			JSONParser parser = new JSONParser();
        	Object obj = parser.parse(str);
            JSONObject jsonObject = (JSONObject) obj;
 
            String variableName = (String) jsonObject.get("name");
            CarDataValueHolder value = new CarDataValueHolder((Object)jsonObject.get("value"));
            double timestamp = (double) jsonObject.get("timestamp");
            cd = new CarData(timestamp,value,variableName);

 
        } 
        catch (IllegalStateException e)
        {
        	System.out.println(e);
        }
        catch (Exception e) {
      		System.out.println("Error parsing: " + str);
            e.printStackTrace();
        }
        return cd;
    }
}