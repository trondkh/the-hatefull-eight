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
 
public class Display {
 

	
	ArrayList<NewCarData> carDataJsonObjects;
	ArrayList<DisplayCarData> displayInfo;
	
    public static void main(String[] args) {
       Display f = new Display();
       f.storeData();
       f.displayData();
    }
    
    public void storeData(){
    	ArrayList<String> jsonObjects = new ArrayList<String>();
    	//Creates an array of strings and reads the file
    	readFile(jsonObjects);
    	//Converts the data into objects with all the information with the same timestamp
    	displayInfo = json2CarData(jsonObjects);
    	
    }
    public void displayData()
    {
    	//Itterate over the array
    	for (DisplayCarData info:displayInfo){
    		//If the both the antispin and the airbag activetade, notify driver about slippery and accident
    		if((info.airbag == true)&&(info.antispin == true) ) {
    			System.out.println("Accident,slippery");
    		}
    		//if only the airbag, notify driver about accident
    		else if (info.airbag == true){
    			System.out.println("Accident");
    		}
    		//if only antispin, notify driver about slippery road
    		else if (info.antispin == true){
    			System.out.println("Slippery");
    		}
    		//Test that it actually checks all objects
    		else{
    		//	System.out.println("Nothing");
    		}
    	}
    }
    
    public ArrayList<DisplayCarData> json2CarData(ArrayList<String> al)
    {
    	ArrayList<DisplayCarData> carDatas = new ArrayList<DisplayCarData>();
    	int length = al.size();
    	for (int i= 0; i < length; i+=4 ){
    		//Data form the car are 4 strings per timestamp, reads 4 and 4 lines and stores the data
    		NewCarData cardata1 = jsonParser(al.get(i));
    		NewCarData cardata2 = jsonParser(al.get(i+1));
    		NewCarData cardata3 = jsonParser(al.get(i+2));
    		NewCarData cardata4 = jsonParser(al.get(i+3));
    		carDatas.add(new DisplayCarData(cardata1.time,cardata1.numberValue,cardata2.numberValue,cardata3.booleanValue,cardata4.booleanValue));
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
