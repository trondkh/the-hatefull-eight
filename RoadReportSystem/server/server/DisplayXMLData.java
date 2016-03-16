package server;

import java.util.ArrayList;

/**
 * Created by Galina on 16.03.2016.
 */
public class DisplayXMLData extends InformationHandler {

    public String condition;

    //gets condition for coordinates
    public DisplayXMLData(String coordinates){
        condition = parseXMLMessage(coordinates);
    }

    @Override
    public String generateResponse(XMLHandler xmlHandler) {
        String temp = "Response....\n";

        ArrayList<String> tags = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        /*can add more tags for both weather and data from vegvesenet that we
        want to alert the driver about
         */
        tags.add("precipitation"); //Nedbør
        values.add("value");

        ArrayList<String> results = xmlHandler.requestYr(tags, values);

        for(int i = 0; i < tags.size(); i++) {
            temp += (tags.get(i) + " / " + values.get(i) + ": " + results.get(i)) + "\n";
        }
        return temp;
    }

    public void displayCondition(){
        String temp = condition;
        String[] subs = temp.trim().split(":");
        int prec = Integer.parseInt(subs[1].trim());
        //checks for precipitation (rain, snow..)
        if (prec > 0){
            System.out.println("slippery road");
        }
        //test
        else{
            System.out.println("nothing");
        }

    }

    public String parseXMLMessage(String str) {
        String temp = str;

        String[] subs = temp.trim().split(",");
        double lat = Double.parseDouble(subs[0].trim());
        double lon = Double.parseDouble(subs[1].trim());

        CoordinateHandler coordinates = new CoordinateHandler(lat, lon);

        XMLFile yr = new XMLFile(true, coordinates.generateYrURL());

        XMLHandler xmlHandler = new XMLHandler(yr, null, coordinates);
        //xmlHandler.exampleRequest();

        return generateResponse(xmlHandler);
    }

    public static void main(String[] args) {
        DisplayXMLData dxd = new DisplayXMLData("63.415763,10.406500");
        dxd.displayCondition();
    }
}
