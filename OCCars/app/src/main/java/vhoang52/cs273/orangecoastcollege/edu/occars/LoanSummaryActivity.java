package vhoang52.cs273.orangecoastcollege.edu.occars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Controller class for LoanSummaryActivity
 * <p>
 * Contains TextViews displaying calculated information to the user.
 * The only action the user can take here within the program is one button to finish the activity
 */
public class LoanSummaryActivity extends AppCompatActivity {

    TextView mCalculatedMonthlyPayment;
    TextView mStickerPrice;
    TextView mDownPayment;
    TextView mCalculatedTaxAmount;
    TextView mCalculatedTotalAmount;
    TextView mCalculatedLoanAmount;
    TextView mCalculatedInterestAmount;
    TextView mLoanTerm;
    Button mReturnButton;


    /**
     * onCreate does in the following order:
     * <p>
     * - Associates view elements with their id values declared in the xml file
     * - Attempts to fill the views with the correct calculated values, calls finish() if unsuccessful, bringing the user back to the prior activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_summary);

        mCalculatedMonthlyPayment = (TextView) findViewById(R.id.calculatedMonthlyPayment);
        mStickerPrice = (TextView) findViewById(R.id.stickerPrice);
        mDownPayment = (TextView) findViewById(R.id.downPayment);
        mCalculatedTaxAmount = (TextView) findViewById(R.id.calculatedTaxAmount);
        mCalculatedTotalAmount = (TextView) findViewById(R.id.calculatedTotalAmount);
        mCalculatedLoanAmount = (TextView) findViewById(R.id.calculatedLoanAmount);
        mCalculatedInterestAmount = (TextView) findViewById(R.id.calculatedInterestAmount);
        mLoanTerm = (TextView) findViewById(R.id.loanTerm);

        try {
            int stickerPrice = getIntent().getExtras().getInt("carprice");
            int downPayment = getIntent().getExtras().getInt("downpayment");
            int loanTerm = getIntent().getExtras().getInt("loanterm");

            CarLoan cl = new CarLoan(stickerPrice, downPayment, loanTerm);
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());

            String calculatedMonthlyPayment = nf.format(cl.monthlyPayment());
            mCalculatedMonthlyPayment.setText(calculatedMonthlyPayment);

            mStickerPrice.setText(nf.format(stickerPrice));
            mDownPayment.setText(nf.format(downPayment));
            mCalculatedTaxAmount.setText(nf.format(cl.taxAmount()));
            mCalculatedTotalAmount.setText(nf.format(cl.totalAmount()));
            mCalculatedLoanAmount.setText(nf.format(cl.borrowedAmount()));
            mCalculatedInterestAmount.setText(nf.format(cl.interestAmount()));

            String loanYears = String.valueOf(loanTerm) + " years";
            mLoanTerm.setText(loanYears);

        } catch (Exception e) {
            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
            finish();
        }

        mReturnButton = (Button) findViewById(R.id.returnEntryButton);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
