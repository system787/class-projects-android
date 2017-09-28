package vhoang52.cs273.orangecoastcollege.edu.occars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


/**
 * Launcher activity
 * <p>
 * Here, the user can input the price of the car, down payments made, and number of years to finance
 *
 * @author VincentHoang
 */
public class PurchaseActivity extends AppCompatActivity implements View.OnClickListener {

    private CarLoan mCarLoan;

    private Button mLoanReportButton;
    private RadioButton mThreeYearRadio;
    private RadioButton mFourYearRadio;
    private RadioButton mFiveYearRadio;
    private EditText mPriceEditText;
    private EditText mDownEditText;

    /**
     * onCreate does in the following order:
     * <p>
     * - Associates view elements to xml defined id values
     * - Assigns onClickListener to mLoanReportButton
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        mCarLoan = new CarLoan();

        mLoanReportButton = (Button) findViewById(R.id.loanReportButton);
        mLoanReportButton.setOnClickListener(this);
        mThreeYearRadio = (RadioButton) findViewById(R.id.threeYearButton);
        mFourYearRadio = (RadioButton) findViewById(R.id.fourYearButton);
        mFiveYearRadio = (RadioButton) findViewById(R.id.fiveYearButton);
        mPriceEditText = (EditText) findViewById(R.id.priceEditText);
        mDownEditText = (EditText) findViewById(R.id.downEditText);

    }

    /**
     * Handles onClick behavior for mLoanReportButton
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loanReportButton:
                calculateHandler();
                break;
        }
    }

    /**
     * Checks to see if the radio values have been selected
     * @return value of radio button, or -1 for error
     */
    public int checkRadio() {
        try {
            getInputValues();
        } catch (Exception e) {
            return -1;
        }

        if (mThreeYearRadio.isChecked()) return 3;
        if (mFourYearRadio.isChecked()) return 4;
        if (mFiveYearRadio.isChecked()) return 5;

        return -1;
    }

    /**
     * Bundles user-inputted information into an intent and starts a new activity
     *
     * Plays a toast for errors instead of starting a new activity
     */
    public void calculateHandler() {
        int response = checkRadio();
        if (response == 3 || response == 4 || response == 5) {
            Intent intent = new Intent(this, LoanSummaryActivity.class);
            int[] inputValues = getInputValues();
            intent.putExtra("carprice", inputValues[0]);
            intent.putExtra("downpayment", inputValues[1]);
            intent.putExtra("loanterm", response);
            startActivity(intent);
        } else if (response == -1) {
            Toast.makeText(this, "Please enter a valid input for all fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something weird happened", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Gets the values in the EditText boxes
     * @return int[] with user-inputted price and down payment information
     */
    public int[] getInputValues() {
        return new int[]{Integer.parseInt(mPriceEditText.getText().toString()), Integer.parseInt(mDownEditText.getText().toString())};
    }
}
