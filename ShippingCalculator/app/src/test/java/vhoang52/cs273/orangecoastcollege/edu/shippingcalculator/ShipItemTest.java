package vhoang52.cs273.orangecoastcollege.edu.shippingcalculator;

import org.junit.Test;

import java.text.NumberFormat;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Junit4 Test to check for calculation errors
 * @author vincenthoang
 */
public class ShipItemTest {

    /**
     * Generates 4 tests to check for various calculation errors
     * @throws Exception no need to catch anything here unless something goes wrong
     */
    @Test
    public void calculateCost() throws Exception {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());

        ShipItem mShipItem = new ShipItem(16.0);
        String[] test = { String.valueOf(nf.format(0)), String.valueOf(nf.format(3))};
        assertArrayEquals(test, mShipItem.calculateCost());

        mShipItem = new ShipItem(20.0);
        test = new String[]{ String.valueOf(nf.format(.50)), String.valueOf(nf.format(3.5))};
        assertArrayEquals(test, mShipItem.calculateCost());

        mShipItem = new ShipItem(23.99);
        test = new String[]{ String.valueOf(nf.format(1.0)), String.valueOf(nf.format(4))};
        assertArrayEquals(test, mShipItem.calculateCost());

        mShipItem = new ShipItem(15.);
        test = new String[]{String.valueOf(nf.format(0)), String.valueOf(nf.format(3.0))};
        assertArrayEquals(test, mShipItem.calculateCost());
    }

}