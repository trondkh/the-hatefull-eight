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
       f.jsonParser();
       System.out.println(f.al.size());
        
    }
    
    public void jsonParser()
    {
    	JSONParser parser = new JSONParser();
        al = new ArrayList<CarData>();
        ArrayList<String> allTheLines = new ArrayList<String>();
		Reader r;
		try
		{
			r = new FileReader("./pi/pi/uptown-west.json");
			BufferedReader br = new BufferedReader(r);
			String str;
			while((str = br.readLine())!=null)
			{
				allTheLines.add(str);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
        try {
 
            Object obj = parser.parse(new FileReader(
                    "./pi/pi/uptown-west_TEST.json"));
 
            JSONObject jsonObject = (JSONObject) obj;
 
            String variableName = (String) jsonObject.get("name");
            long value = (long) jsonObject.get("value");
            double timestamp = (double) jsonObject.get("timestamp");
            al.add(new CarData(timestamp,value,variableName));

 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}