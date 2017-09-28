package vhoang52.cs273.orangecoastcollege.edu.inandout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This activity allows the user to input the number of items to order off of the menu through
 * a variety of EditText fields, and an order button
 *
 * @author vincenthoang
 */

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mDoubleDoubleTextView;
    TextView mCheeseburgerTextView;
    TextView mFrenchFriesTextView;
    TextView mShakesTextView;
    TextView mSmallDrinkTextView;
    TextView mMediumDrinkTextView;
    TextView mLargeDrinkTextView;

    EditText mDoubleDoubleEditText;
    EditText mCheeseburgerEditText;
    EditText mFrenchFriesEditText;
    EditText mShakesEditText;
    EditText mSmallDrinkEditText;
    EditText mMediumDrinkEditText;
    EditText mLargeDrinkEditText;

    Button mPlaceOrderButton;

    /**
     * initializeViews() assigns each reference with its id from the xml files
     * updatePrices() gets the current prices found in Order.class and applies them to the appropriate view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initializeViews();
        updatePrices();
    }

    private void initializeViews() {
        mDoubleDoubleTextView = (TextView) findViewById(R.id.doubleDoubleTextView);
        mCheeseburgerTextView = (TextView) findViewById(R.id.cheeseburgerTextView);
        mFrenchFriesTextView = (TextView) findViewById(R.id.frenchFriesTextView);
        mShakesTextView = (TextView) findViewById(R.id.shakesTextView);
        mSmallDrinkTextView = (TextView) findViewById(R.id.smallDrinkTextView);
        mMediumDrinkTextView = (TextView) findViewById(R.id.mediumDrinkTextView);
        mLargeDrinkTextView = (TextView) findViewById(R.id.largeDrinkTextView);

        mDoubleDoubleEditText = (EditText) findViewById(R.id.doubleDoubleEditText);
        mCheeseburgerEditText = (EditText) findViewById(R.id.cheeseburgerEditText);
        mFrenchFriesEditText = (EditText) findViewById(R.id.frenchFriesEditText);
        mShakesEditText = (EditText) findViewById(R.id.shakesEditText);
        mSmallDrinkEditText = (EditText) findViewById(R.id.smallDrinkEditText);
        mMediumDrinkEditText = (EditText) findViewById(R.id.mediumDrinkEditText);
        mLargeDrinkEditText = (EditText) findViewById(R.id.largeDrinkEditText);
        mPlaceOrderButton = (Button) findViewById(R.id.placeOrderButton);
        mPlaceOrderButton.setOnClickListener(this);
    }

    private void updatePrices() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());

        String doubleDouble = String.format(mDoubleDoubleTextView.getText().toString(),
                nf.format(Order.getPriceDoubledouble()));
        mDoubleDoubleTextView.setText(doubleDouble);

        String cheeseburger = String.format(mCheeseburgerTextView.getText().toString(),
                nf.format(Order.getPriceCheeseburger()));
        mCheeseburgerTextView.setText(cheeseburger);

        String frenchFries = String.format(mFrenchFriesTextView.getText().toString(),
                nf.format(Order.getPriceFrenchfries()));
        mFrenchFriesTextView.setText(frenchFries);

        String shakes = String.format(mShakesTextView.getText().toString(),
                nf.format(Order.getPriceShakes()));
        mShakesTextView.setText(shakes);

        String smallDrink = String.format(mSmallDrinkTextView.getText().toString(),
                nf.format(Order.getPriceSmalldrink()));
        mSmallDrinkTextView.setText(smallDrink);

        String mediumDrink = String.format(mMediumDrinkTextView.getText().toString(),
                nf.format(Order.getPriceMediumdrink()));
        mMediumDrinkTextView.setText(mediumDrink);

        String largeDrink = String.format(mLargeDrinkTextView.getText().toString(),
                nf.format(Order.getPriceLargedrink()));
        mLargeDrinkTextView.setText(largeDrink);
    }

    private String[] gatherUserInput() {
        String[] quantity = new String[7];
        try {
            quantity[0] = mDoubleDoubleEditText.getText().toString();
            quantity[1] = mCheeseburgerEditText.getText().toString();
            quantity[2] = mFrenchFriesEditText.getText().toString();
            quantity[3] = mShakesEditText.getText().toString();
            quantity[4] = mSmallDrinkEditText.getText().toString();
            quantity[5] = mMediumDrinkEditText.getText().toString();
            quantity[6] = mLargeDrinkEditText.getText().toString();

            for (int i = 0; i < quantity.length; i++) {
                if (quantity[i].length() == 0) {
                    quantity[i] = "0";
                }
            }

            return quantity;

        } catch (Exception e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /**
     * Starts a new activity with an intent containing a string[] with the recorded values of user input
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.placeOrderButton:
                Intent intent = new Intent(this, OrderSummary.class);
                intent.putExtra("quantity", gatherUserInput());
                startActivity(intent);
                break;
        }
    }
}
