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

        ArrayList<String> results = xmlHandler.requestYr(tags, values);

        for(int i = 0; i < tags.size(); i++) {
            temp += (tags.get(i) + " / " + values.get(i) + ": " + results.get(i)) + "\n";
        }


        return temp;
    }
}
