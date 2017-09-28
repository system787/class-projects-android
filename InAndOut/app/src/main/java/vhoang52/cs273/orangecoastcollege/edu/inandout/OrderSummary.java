package vhoang52.cs273.orangecoastcollege.edu.inandout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Displays information such as price, tax, items ordered based on user input in OrderActivity
 * @author vincenthoang
 */
public class OrderSummary extends AppCompatActivity {
    private static final String TAG = "OrderSummary";

    TextView mOrderTotal;
    TextView mItemsOrdered;
    TextView mOrderSubtotal;
    TextView mOrderTax;
    Button mStartNewOrderButton;

    /**
     * initializeViews() assigns each reference its id defined in xml
     * then displays calculated information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        initializeViews();

        String[] quantities = getIntent().getStringArrayExtra("quantity");

        if (quantities != null) {
            calculateTotals(quantities);
        } else {
            Log.d(TAG, "String array from OrderActivity was null in OrderSummary");
            finish();
        }
    }

    private void initializeViews() {
        mOrderTotal = (TextView) findViewById(R.id.orderTotal);
        mItemsOrdered = (TextView) findViewById(R.id.itemsOrdered);
        mOrderSubtotal = (TextView) findViewById(R.id.orderSubtotal);
        mOrderTax = (TextView) findViewById(R.id.orderTax);
        mStartNewOrderButton = (Button) findViewById(R.id.startNewOrderButton);
        mStartNewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void calculateTotals(String[] quantities) {
        Order order = new Order(Integer.parseInt(quantities[0]), Integer.parseInt(quantities[1]),
                Integer.parseInt(quantities[2]), Integer.parseInt(quantities[3]),
                Integer.parseInt(quantities[4]), Integer.parseInt(quantities[5]),
                Integer.parseInt(quantities[6]));

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        NumberFormat pf = NumberFormat.getPercentInstance(Locale.getDefault());

        String orderTotal = String.format(mOrderTotal.getText().toString(), nf.format(order.calculateTotal()));
        mOrderTotal.setText(orderTotal);

        String itemsOrdered = String.format(mItemsOrdered.getText().toString(), String.valueOf(order.calculateItemsOrdered()));
        mItemsOrdered.setText(itemsOrdered);

        String subtotal = String.format(mOrderSubtotal.getText().toString(), nf.format(order.calculateSubtotal()));
        mOrderSubtotal.setText(subtotal);

        String tax = String.format(mOrderTax.getText().toString(), pf.format(Order.getTAX()), nf.format(order.calculateTax()));
        mOrderTax.setText(tax);
    }
}
