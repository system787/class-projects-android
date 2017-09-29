package vhoang52.cs273.orangecoastcollege.edu.shippingcalculator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This application allows the user to input a weight (in ounces) and will display the calculated
 * costs to ship the item back to the user
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    private final String EDIT_TEXT_BUNDLE = "mEditText";
    private final String CALCULATOR_VISIBILITY = "calculatorVisibility";

    private EditText mEditText;
    private TextView mCalculatedAddedCost;
    private TextView mCalculatedTotalCost;
    private RelativeLayout mCalculatorRelativeLayout;
    private int mShortAnimationDuration;

    /**
     * onCreate sequence of events:
     * - Sets view to activity_main.xml
     * - Initializes layout IDs from activity_main.xml
     * - Formats base cost value to 3.00 units of currency in the system locale (e.g. $3.00)
     * - Checks to see if the savedInstanceState bundle is null, then attempts to unpack it and restore the last
     * instance state
     *
     * @param savedInstanceState bundle containing the last state of mEditText and the state of mCalculatorRelativeLayout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mCalculatorRelativeLayout = (RelativeLayout) findViewById(R.id.calculatorWidgetRelativeLayout);
        mEditText = (EditText) findViewById(R.id.editTextUser);
        mEditText.setOnClickListener(this);
        mEditText.requestFocus();
        mEditText.setInputType(InputType.TYPE_NULL);

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        TextView mCalculatedBaseCost = (TextView) findViewById(R.id.calculatedBaseCost);
        mCalculatedBaseCost.setText(nf.format(3.0));
        mCalculatedAddedCost = (TextView) findViewById(R.id.calculatedAddedCost);
        mCalculatedTotalCost = (TextView) findViewById(R.id.calculatedTotalCost);

        if (savedInstanceState != null) {
            mEditText.setText(savedInstanceState.getString("mEditText"));
            updateCost();
            if (!savedInstanceState.getBoolean("calculatorVisibility")) {
                mCalculatorRelativeLayout.setVisibility(View.GONE);
            }
        } else {
            mCalculatorRelativeLayout.setVisibility(View.VISIBLE);
        }

        initializeButtons();
    }

    /**
     * Handles the onClick events for buttons with an active View.OnClickListener attached
     *
     * @param view the View Ids assigned in onCreate
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editTextUser:
                //showAnimate();
                mCalculatorRelativeLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonOne:
                addInput("1");
                break;
            case R.id.buttonTwo:
                addInput("2");
                break;
            case R.id.buttonThree:
                addInput("3");
                break;
            case R.id.buttonFour:
                addInput("4");
                break;
            case R.id.buttonFive:
                addInput("5");
                break;
            case R.id.buttonSix:
                addInput("6");
                break;
            case R.id.buttonSeven:
                addInput("7");
                break;
            case R.id.buttonEight:
                addInput("8");
                break;
            case R.id.buttonNine:
                addInput("9");
                break;
            case R.id.buttonZero:
                addInput("0");
                break;
            case R.id.buttonBackspace:
                editTextBackSpace();
                break;
            case R.id.buttonDecimal:
                if (!mEditText.getText().toString().contains(".")) {
                    addInput(".");
                }
                break;
        }
    }

    /* These animations are not used in favor of android.animateLayoutChanges property in xml files */
    private void showAnimate() {
        mCalculatorRelativeLayout.setAlpha(0f);
        mCalculatorRelativeLayout.setVisibility(View.VISIBLE);
        mCalculatorRelativeLayout.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);
    }

    private void hideAnimate() {
        mCalculatorRelativeLayout.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCalculatorRelativeLayout.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * Overrides the default back button behavior. Removes the calculator widget before popping the activity off of the stack
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (mCalculatorRelativeLayout.getVisibility() == View.GONE) {
            finish();
        }
        if (mCalculatorRelativeLayout.getVisibility() == View.VISIBLE) {
            //hideAnimate();
            mCalculatorRelativeLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Gets the text from mEditText, checks to see if the length is less than 9 (to prevent overflow), appends the string
     * with the input value, then invokes updateCost()
     *
     * @param input the number
     */
    public void addInput(String input) {
        String currentText = mEditText.getText().toString();
        if (currentText.length() < 9) {
            currentText += input;
            mEditText.setText(currentText);
            updateCost();
        }
    }

    /**
     * Removes the last index from mEditText display if length > 0
     */
    public void editTextBackSpace() {
        if (mEditText.getText().toString().length() > 0) {
            String currentText = mEditText.getText().toString();
            currentText = currentText.substring(0, currentText.length() - 1);
            mEditText.setText(currentText);
        }
        updateCost();
    }

    /**
     * Extracts the double value in mEditText but first checks for invalid input (e.g. in "15.", the '.' without any trailing decimal points)
     * Logs a severe exception if a valid double is still not found.
     * <p>
     * Generates a new ShipItem with the extracted double, then generates a String[] containing the output values and updates the information
     * display
     */
    public void updateCost() {
        double weight = 0;

        if (mEditText.getText().toString().contains(".")) {
            String temp = mEditText.getText().toString();
            weight = Double.parseDouble(temp.substring(0, temp.indexOf(".")));
        }

        try {
            weight = Double.parseDouble(mEditText.getText().toString());
        } catch (Exception e) {
            Logger.getLogger(TAG).log(Level.SEVERE, "Exception while getting valid double");
            e.printStackTrace();
        }

        ShipItem shipItem = new ShipItem(weight);
        String[] costs = shipItem.calculateCost();
        mCalculatedAddedCost.setText(costs[0]);
        mCalculatedTotalCost.setText(costs[1]);
    }

    /**
     * Saves the current instance state by storing the current mEditText and mCalculatorRelativeLayout visibility values
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EDIT_TEXT_BUNDLE, mEditText.getText().toString());
        outState.putBoolean(CALCULATOR_VISIBILITY, mCalculatorRelativeLayout.getVisibility() == View.VISIBLE);
    }

    // Used to reduce the visual clutter in onCreate()
    private void initializeButtons() {
        Button mButtonOne = (Button) findViewById(R.id.buttonOne);
        mButtonOne.setOnClickListener(this);
        Button mButtonTwo = (Button) findViewById(R.id.buttonTwo);
        mButtonTwo.setOnClickListener(this);
        Button mButtonThree = (Button) findViewById(R.id.buttonThree);
        mButtonThree.setOnClickListener(this);
        Button mButtonFour = (Button) findViewById(R.id.buttonFour);
        mButtonFour.setOnClickListener(this);
        Button mButtonFive = (Button) findViewById(R.id.buttonFive);
        mButtonFive.setOnClickListener(this);
        Button mButtonSix = (Button) findViewById(R.id.buttonSix);
        mButtonSix.setOnClickListener(this);
        Button mButtonSeven = (Button) findViewById(R.id.buttonSeven);
        mButtonSeven.setOnClickListener(this);
        Button mButtonEight = (Button) findViewById(R.id.buttonEight);
        mButtonEight.setOnClickListener(this);
        Button mButtonNine = (Button) findViewById(R.id.buttonNine);
        mButtonNine.setOnClickListener(this);
        Button mButtonZero = (Button) findViewById(R.id.buttonZero);
        mButtonZero.setOnClickListener(this);
        ImageButton mButtonBackspace = (ImageButton) findViewById(R.id.buttonBackspace);
        mButtonBackspace.setOnClickListener(this);
        Button mButtonDecimal = (Button) findViewById(R.id.buttonDecimal);
        mButtonDecimal.setOnClickListener(this);
    }
}
