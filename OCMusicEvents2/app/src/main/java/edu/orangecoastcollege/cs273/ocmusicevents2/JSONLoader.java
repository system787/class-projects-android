package edu.orangecoastcollege.cs273.ocmusicevents2;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class loads MusicEvent data from a formatted JSON (JavaScript Object Notation) file.
 * Populates data model (MusicEvent) with data.
 */

public class JSONLoader {

    /**
     * Loads JSON data from a file in the assets directory.
     * @param context The activity from which the data is loaded.
     * @throws IOException If there is an error reading from the JSON file.
     */
    public static List<MusicEvent> loadJSONFromAsset(Context context) throws IOException {
        ArrayList<MusicEvent> allEventsList = new ArrayList<>();
        String json = null;
            InputStream is = context.getAssets().open("MusicEvents.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        //TODO: Now that the JSON string has been retrieved, parse it for each individual
        //TODO: MusicEvent object and add each object to the ArrayList (allEventsList)

        return allEventsList;
    }
}
