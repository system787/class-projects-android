package edu.orangecoastcollege.cs273.vhoang52.yarrworld;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected Button mToastButton;
    private ImageView mOCCPirate;
    protected static boolean toggleRave;
    private Timer timer;
    private static String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Default value for the color switching background. Activity defaults to no strobing background
        toggleRave = false;

        // Assign references to view ids
        mToastButton = (Button) findViewById(R.id.occ_button);
        mOCCPirate = (ImageView) findViewById(R.id.occ_pirate);

        // Check the saved instance state to see if background was previously strobing
        // Set the background to the prior color (currently minor bug because calling
        //  the method randomizes the color again
        if (savedInstanceState != null && savedInstanceState.getString("backgroundcolor") != null) {
            mOCCPirate.setBackgroundColor(Color.parseColor(savedInstanceState.getString("backgroundcolor")));
        }

        // Attach listeners to the buttons/actionable objects
        mToastButton.setOnClickListener(this);
        mOCCPirate.setOnClickListener(this);

    }

    /**
     * Responds to clicks on view objects with an active onClick listener
     * @param view the view id that was clicked
     */
    @Override
    public void onClick(View view) {
       switch (view.getId()) {
           case R.id.occ_button:
               Toast.makeText(this, getString(R.string.toast_button), Toast.LENGTH_SHORT).show();
               if (timer != null) {
                   timer.cancel();
               }
               break;
           case R.id.occ_pirate:
                   try {
                    BackgroundTimer raveTimer = new BackgroundTimer();
                       timer = new Timer();
                       timer.schedule(raveTimer, 100, 100);
                   } catch (Exception e) {
                       Logger.getLogger("MainActivity").log(Level.SEVERE, "Exception when setting background color");
                       e.printStackTrace();
                   }
               break;
       }

    }

    /**
     * Generates a random hexadecimal color
     * @param r Random class object
     * @return string format hexadecimal color (e.g. #FFFFFF)
     */
    public static String randomHexColor(Random r) {
        final char [] hex = { '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char [] s = new char[7];
        int     n = r.nextInt(0x1000000);

        s[0] = '#';
        for (int i=1;i<7;i++) {
            s[i] = hex[n & 0xf];
            n >>= 4;
        }
        return new String(s);
    }

    /**
     * Runs updateBackground using Runnable on the UI thread
     */
    private class BackgroundTimer extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateBackground();
                }
            });
        }
    }

    /**
     * Cancels the timer used to space out background changing intervals when the activity is stopped
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Generates a new Random to retrieve a new background color, then sets the background of Pete the Pirate
     * to that randomly generated color
     */
    private void updateBackground() {
        Random r = new Random();
        mOCCPirate.setBackgroundColor(Color.parseColor(randomHexColor(r)));
        color = randomHexColor(r);
    }

    /**
     * Saves the state of the instance when it is either stopped or destroyed so that the information can persist
     * into the next instance of MainActivity
     * @param outState the savedInstanceState bundle
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (color != null) {
            outState.putString("backgroundcolor", color);
        }
    }
}
