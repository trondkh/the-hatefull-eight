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
 
	class CarData
    {
    	double time;
    	long value;
    	String variable;
    	public CarData(double time, long value, String variable)
    	{
    		this.time = time;
    		this.value = value;
    		this.variable = variable;
    	}
    	public String toString()
    	{
    		return Double.toString(time) + ": " + this.variable + " = " + Long.toString(this.value);
    	}
    }
	
	 ArrayList<CarData> al;
	
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
       JSONReadFromFile f = new JSONReadFromFile();
       f.printFileAsCarData();
    }
    
    public void printFileAsCarData()
    {
    	ArrayList<String> jsonObjects = new ArrayList<String>();
    	ArrayList<CarData> carDataObjects;
    	readFile(jsonObjects);
    	carDataObjects=json2CarData(jsonObjects);
    	for(CarData cd:carDataObjects)
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
			r = new FileReader("./pi/pi/uptown-west_TEST.json");
			BufferedReader br = new BufferedReader(r);
			String str;
			while((str = br.readLine())!=null)
			{
				al.add(str);
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
    	JSONParser parser = new JSONParser();
    	CarData cd = null;
        try {
        	Object obj = parser.parse(str);
            JSONObject jsonObject = (JSONObject) obj;
 
            String variableName = (String) jsonObject.get("name");
            long value = (long) jsonObject.get("value");
            double timestamp = (double) jsonObject.get("timestamp");
            cd = new CarData(timestamp,value,variableName);

 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cd;
    }
}