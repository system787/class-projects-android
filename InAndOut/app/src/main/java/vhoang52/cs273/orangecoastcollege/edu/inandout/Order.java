package vhoang52.cs273.orangecoastcollege.edu.inandout;

/**
 * Model class for orders placed in OrderActivity
 * This is where prices would be updated
 *
 * Returns calculated values based on user input
 *
 * @author vincenthoang
 */

public class Order {
    private static final double PRICE_CHEESEBURGER = 2.15;
    private static final double PRICE_DOUBLEDOUBLE = 3.60;
    private static final double PRICE_FRENCHFRIES = 1.65;
    private static final double PRICE_LARGEDRINK = 1.75;
    private static final double PRICE_MEDIUMDRINK = 1.55;
    private static final double PRICE_SMALLDRINK = 1.45;
    private static final double PRICE_SHAKES = 2.20;
    private static final double TAX = .08;

    private int mCheeseburgers;
    private int mDoubleDoubles;
    private int mFrenchFries;
    private int mLargeDrinks;
    private int mMediumDrinks;
    private int mSmallDrinks;
    private int mShakes;

    /**
     * Default constructor
     */
    public Order() {
        mCheeseburgers = 0;
        mDoubleDoubles = 0;
        mFrenchFries = 0;
        mLargeDrinks = 0;
        mMediumDrinks = 0;
        mSmallDrinks = 0;
        mShakes = 0;
    }

    /**
     * Parameterized constructor
     * Takes in the number of each item specified as an integer. User input is restricted to 2 digits (0-99)
     */
    public Order(int doubleDoubles, int cheeseburgers, int frenchFries, int shakes, int smallDrinks, int mediumDrinks, int largeDrinks) {
        mCheeseburgers = cheeseburgers;
        mDoubleDoubles = doubleDoubles;
        mFrenchFries = frenchFries;
        mLargeDrinks = largeDrinks;
        mMediumDrinks = mediumDrinks;
        mSmallDrinks = smallDrinks;
        mShakes = shakes;
    }

    /**
     * Calculates the subtotal (sans tax)
     * @return subtotal as a double
     */
    public double calculateSubtotal() {
        double subtotal = 0;
        subtotal += mDoubleDoubles * PRICE_DOUBLEDOUBLE;
        subtotal += mCheeseburgers * PRICE_CHEESEBURGER;
        subtotal += mFrenchFries * PRICE_FRENCHFRIES;
        subtotal += mShakes * PRICE_SHAKES;
        subtotal += mSmallDrinks * PRICE_SMALLDRINK;
        subtotal += mMediumDrinks * PRICE_MEDIUMDRINK;
        subtotal += mLargeDrinks * PRICE_LARGEDRINK;

        return subtotal;
    }

    /**
     * Calculates the tax amount (on the subtotal)
     * @return tax as a double
     */
    public double calculateTax() {
        return calculateSubtotal() * TAX;
    }

    /**
     * Calculates the number of items ordered
     * @return items ordered as int
     */
    public int calculateItemsOrdered() {
        int total = mCheeseburgers + mDoubleDoubles + mFrenchFries + mShakes + mSmallDrinks + mMediumDrinks + mLargeDrinks;
        return total;
    }

    /**
     * Calculates the total with tax
     * @return total as double
     */
    public double calculateTotal() {
        return calculateSubtotal() + calculateTax();
    }

    /**
     * Sets the number of cheeseburgers
     */
    public void setCheeseburgers(int cheeseburgers) {
        mCheeseburgers = cheeseburgers;
    }

    /**
     * Sets the number of double doubles
     */
    public void setDoubleDoubles(int doubleDoubles) {
        mDoubleDoubles = doubleDoubles;
    }

    /**
     * Sets the number of french fries
     */
    public void setFrenchFries(int frenchFries) {
        mFrenchFries = frenchFries;
    }

    /**
     * Sets the number of large drinks
     */
    public void setLargeDrinks(int largeDrinks) {
        mLargeDrinks = largeDrinks;
    }

    /**
     * Sets the number of medium drinks
     */
    public void setMediumDrinks(int mediumDrinks) {
        mMediumDrinks = mediumDrinks;
    }

    /**
     * Sets the number of small drinks
     */
    public void setSmallDrinks(int smallDrinks) {
        mSmallDrinks = smallDrinks;
    }

    /**
     * Sets the number of shakes
     */
    public void setShakes(int shakes) {
        mShakes = shakes;
    }

    /**
     * Returns the price of cheeseburgers
     */
    public static double getPriceCheeseburger() {
        return PRICE_CHEESEBURGER;
    }

    /**
     * Returns the price of double doubles
     */
    public static double getPriceDoubledouble() {
        return PRICE_DOUBLEDOUBLE;
    }

    /**
     * Returns the price of french fries
     */
    public static double getPriceFrenchfries() {
        return PRICE_FRENCHFRIES;
    }

    /**
     * Returns the price of large drinks
     */
    public static double getPriceLargedrink() {
        return PRICE_LARGEDRINK;
    }

    /**
     * Returns the price of medium drinks
     */
    public static double getPriceMediumdrink() {
        return PRICE_MEDIUMDRINK;
    }

    /**
     * Returns the price of small drinks
     */
    public static double getPriceSmalldrink() {
        return PRICE_SMALLDRINK;
    }

    /**
     * Returns the price of shakes
     */
    public static double getPriceShakes() {
        return PRICE_SHAKES;
    }

    /**
     * Returns the tax rate
     */
    public static double getTAX() {
        return TAX;
    }
}


