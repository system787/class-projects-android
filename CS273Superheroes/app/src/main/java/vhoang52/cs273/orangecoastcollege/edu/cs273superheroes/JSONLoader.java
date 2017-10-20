package vhoang52.cs273.orangecoastcollege.edu.cs273superheroes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Loads JSON from file with json.org libraries
 *
 * @author vhoang52
 */
public class JSONLoader {
    private static final String TAG = "JSONLoader";

    /**
     * Attempts to load "cs273superheroes.json" from the assets directory, reconstructs Superhero objects,
     * and returns an ArrayList<Superhero> containing all object information
     * @param context context from the class calling this method (MainActivity in this case)
     * @return ArrayList<Superhero> containing all information held in "cs273superheroes.json"; returns null if unsuccessful load
     */
    public static ArrayList<Superhero> loadJSONFile(Context context) {
        ArrayList<Superhero> superheroArrayList = new ArrayList<>();
        try {
            InputStream in = context.getAssets().open("cs273superheroes.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            String json = new String(buffer, "UTF-8");

            JSONObject root = new JSONObject(json);
            JSONArray heroArray = root.getJSONArray("CS273Superheroes");

            for (int i = 0, n = heroArray.length(); i < n; i++) {
                JSONObject hero = heroArray.getJSONObject(i);
                Superhero selectedHero = new Superhero(hero.getString("Username"), hero.getString("Name"),
                        hero.getString("Superpower"), hero.getString("OneThing"));
                superheroArrayList.add(selectedHero);
            }

            return superheroArrayList;
        } catch (IOException e) {
            Log.d(TAG, "IOException while loading JSON file");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d(TAG, "JSONException while reading JSON file");
        }


        // sending a result code would be better but that's more in-depth than necessary here
        return null;
    }
}
