package server;

import java.util.ArrayList;


/**
 * Created by Galina on 16.03.2016.
 */
public class DisplayXMLData extends InformationHandler {

    public String condition;
    public Double lat;
    public Double lon;

    //gets condition for coordinates
    public DisplayXMLData(String coordinates){

        condition = parseXMLMessage(coordinates);
    }


    public String getXML(XMLHandler xmlHandler) {
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

        ArrayList<Integer> messages = xmlHandler.getCloseWarnings(lat, lon, 1.0);

        for(int i = 0; i < messages.size(); i++) {
            temp += ":";
            temp += xmlHandler.vegvesenExtractText(messages.get(i), "heading") + "\n";
            temp += "\t" + xmlHandler.vegvesenExtractText(messages.get(i), "messageType") + "\n";
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
        }//test
        else {
            System.out.println("no rain");
        }
        if(subs.length > 2) {
            for (int i = 2; i < subs.length; i++) {
                System.out.println(subs[i]);
            }
        }
    }

    public String parseXMLMessage(String str) {
        String temp = str;

        String[] subs = temp.trim().split(",");
        lat = Double.parseDouble(subs[0].trim());
        lon = Double.parseDouble(subs[1].trim());

        CoordinateHandler coordinates = new CoordinateHandler(lat, lon);

        XMLFile yr = new XMLFile(true, coordinates.generateYrURL());
        XMLFile vegvesen = new XMLFile(true, "http://www.vegvesen.no/trafikk/xml/savedsearch.xml?id=600");

        XMLHandler xmlHandler = new XMLHandler(yr, vegvesen, coordinates);
        //xmlHandler.exampleRequest();

        return getXML(xmlHandler);
    }

    public static void main(String[] args) {
        DisplayXMLData dxd = new DisplayXMLData("63.415763,10.406500");
        dxd.displayCondition();
    }
}
