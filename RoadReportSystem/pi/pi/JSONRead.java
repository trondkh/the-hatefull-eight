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
 
public class JSONRead {
 

	
	ArrayList<NewCarData> carDataJsonObjects;
	
    public static void main(String[] args) {
       JSONRead f = new JSONRead();
       f.printFileAsCarData();
       System.out.println("Total number of JSON objects parsed:");
       System.out.println(f.carDataJsonObjects.size());
    }
    
    public void printFileAsCarData()
    {
    	ArrayList<String> jsonObjects = new ArrayList<String>();
    	readFile(jsonObjects);
    	carDataJsonObjects=json2CarData(jsonObjects);
    	for(NewCarData cd:carDataJsonObjects)
    	{
    		if(cd.variable.equals("airbag")||cd.variable.equals("anti_spin")){
	    		//checks if the data is airbag and if it was activated
	    		if(cd.variable.equals("airbag")){
	    			if(cd.booleanValue == true){
	    		    	System.out.println("airbag activated");
	    			}
	    			else {
	        			System.out.println("no activation");
	        		}
	       		}
	    		//checks if the data is anti-spin and if it was activated
	    		else {
	    			if(cd.booleanValue == true){
	    				System.out.println("antispin activated");
	    			}
	    			else {
	        			System.out.println("no activation");
	        		}
	    		}
	    		System.out.println(cd);
    		}
    		else {
    		System.out.println(cd);
    		}
    	}
    }
    
    public ArrayList<NewCarData> json2CarData(ArrayList<String> al)
    {
    	ArrayList<NewCarData> carDatas = new ArrayList<NewCarData>();
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
//			r = new FileReader("./pi/pi/uptown-west.json");
//			r = new FileReader("./pi/pi/uptown-west_TEST.json");
			r = new FileReader("./pi/pi/JSONTest.json");
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
    
    NewCarData jsonParser(String str)
    {
		NewCarData cd = null;
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
            // Checks what kind of variable value should be stored in
            	// here boolean
            if ((variableName.equals("airbag"))||(variableName.equals("anti_spin"))){
            	boolean value = (boolean)jsonObject.get("value");
            	double timestamp = (double) jsonObject.get("timestamp");
                cd = new NewCarData(timestamp,value,variableName);
            }
            	// here double
            else if ((variableName.equals( "longitude"))||(variableName.equals("latitude"))){
            	double value = (double)jsonObject.get("value");
            	double timestamp = (double) jsonObject.get("timestamp");
            	cd = new NewCarData(timestamp,value,variableName);
            }
            else {
           
            	String errorMsg = "String name variable is invalid for jsonParser. Str=";
				errorMsg+=str;

				throw new IllegalStateException(errorMsg);
            }         
 	
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
