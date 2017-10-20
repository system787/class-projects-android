package vhoang52.cs273.orangecoastcollege.edu.cs273superheroes;

import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Settings fragment activity for the user to select the type of quiz
 *
 * @author vhoang52
 */
public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

    /**
     * Overrides the default support actionbar with the one we wish to inflate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException when trying to call getSupportActionBar().setDisplayHomeAsUpEnabled(true");
            e.printStackTrace();
        } finally {
            getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
        }
    }

    /**
     * The fragment activity to invoke
     */
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
