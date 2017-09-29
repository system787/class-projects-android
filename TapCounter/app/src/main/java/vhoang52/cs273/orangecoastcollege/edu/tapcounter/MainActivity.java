package vhoang52.cs273.orangecoastcollege.edu.tapcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This application features a single button to increment a counter by one for each button press
 * @author Vincent Hoang
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().toString();
    private int mCurrentCounter;
    private Counter mCounter;
    private TextView mCounterTextView;

    /**
     * Automatically run on creation of the activity
     *
     * First checks to see if savedInstanceState contains a stored counter value. If a counter
     *  value is found. If present, counter is initialized at the stored value, otherwise a new
     *  counter is created and initialized at 0
     *
     * Then assigns a TextView the duty of displaying the counter and assigns a button the job of
     *  updating the counter.
     *
     * @param savedInstanceState Contains the last known value of the counter in between activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            int savedCounter = savedInstanceState.getInt("counter", 0);
                mCounter = new Counter(savedCounter);
                Logger.getLogger(TAG).log(Level.INFO, "Instance state restored in onCreate");
        } else {
            mCounter = new Counter();
            Logger.getLogger(TAG).log(Level.INFO, "Instance state was not restored. saveInstanceState is null");
        }

        mCounterTextView = (TextView) findViewById(R.id.counterNumber);
        updateCounter();

        Button mTapButton = (Button) findViewById(R.id.tapButton);
        mTapButton.setOnClickListener(this);
    }

    /**
     * onClick handler for buttons assigned in onCreate
     * @param view the resource layout id of the Views with an onClickListener attached
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tapButton:
                Logger.getLogger(TAG).log(Level.INFO, "R.id.tapButton pressed");
                tapHandler();
                break;
        }
    }

    /**
     * Increases the counter by one and updates the view accordingly
     */
    public void tapHandler() {
        mCounter.increaseCount();
        updateCounter();
    }

    /**
     * Updates the view by retrieving the current counter value and updating the TextView
     */
    public void updateCounter() {
        mCurrentCounter = mCounter.getCount();
        mCounterTextView.setText(String.valueOf(mCurrentCounter));
        Logger.getLogger(TAG).log(Level.INFO, "Counter updated");
    }

    /**
     * Saves the current counter value for changes in the activity lifecycle (e.g. view orientation
     *  change)
     * @param outState the savedInstanceState bundle to be returned to onCreate
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", mCurrentCounter);
        Logger.getLogger(TAG).log(Level.INFO, "onSaveInstanceState() called. Instance state recorded");
    }
}
