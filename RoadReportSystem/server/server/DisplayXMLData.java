package server;

import java.util.ArrayList;

/**
 * Created by Galina on 16.03.2016.
 */
public class DisplayXMLData extends InformationHandler {

    @Override
    public String generateResponse(XMLHandler xmlHandler) {
        String temp = "Response....\n";

        ArrayList<String> tags = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        tags.add("temperature");
        values.add("value");

        tags.add("temperature");
        values.add("unit");

        tags.add("rain");
        values.add("value");

        ArrayList<String> results = xmlHandler.requestYr(tags, values);
        //problem: do only 2 results instead of 3, need tag to get "rain"
        for(int i = 0; i < tags.size(); i++) {
            temp += (tags.get(i) + " / " + values.get(i) + ": " + results.get(i)) + "\n";
        }
        return temp;
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
        DisplayXMLData dxd = new DisplayXMLData();
        System.out.println(dxd.parseXMLMessage("63.415763,10.406500"));
    }
}
