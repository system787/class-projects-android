package vhoang52.cs273.orangecoastcollege.edu.shippingcalculator;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Model for shipping items in Shipping Calculator
 * @author Vincent Hoang
 */

public class ShipItem {
    private static final double BASE_COST = 3.0;
    private static final double ADDED_CHARGE_RATE = 0.5;
    private static final int ADDITIONAL_OUNCES = 4;
    private static final int FLAT_CHARGE = 16;

    private double weight;

    /**
     * Parameterized constructor with a single parameter
     * @param input weight of the item the customer is shipping
     */
    public ShipItem(double input) {
        this.weight = input;
    }

    /**
     * Calculates the cost of shipping based on the ShipItem object's weight
     * @return String[] containing formatted currency values in the system locale's currency
     *      formatting
     */
    public String[] calculateCost() {
        String[] output = new String[2];

        double additionalMultiple = 0;
        if (weight > FLAT_CHARGE) {
            additionalMultiple = (weight - FLAT_CHARGE) / ADDITIONAL_OUNCES;
        }

        double additionalOutput = ((int) additionalMultiple) * ADDED_CHARGE_RATE;
        if (additionalMultiple > (int) additionalMultiple) {
            additionalOutput += ADDED_CHARGE_RATE;
        }

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());


        output[0] = String.valueOf(nf.format(additionalOutput));
        output[1] = String.valueOf(nf.format(BASE_COST + additionalOutput));

        return output;
    }
}
