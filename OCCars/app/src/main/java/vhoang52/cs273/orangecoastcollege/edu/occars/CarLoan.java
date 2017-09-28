package vhoang52.cs273.orangecoastcollege.edu.occars;

/**
 * Simulates the calculation of interest on a car loan on set term intervals
 *
 * @author Vincent Hoang
 */

public class CarLoan {
    private static final double STATE_TAX = 0.08;
    private static final int MONTHS_YEAR = 12;
    private double mPrice;
    private double mDownPayment;
    private int mLoanTerm;

    /**
     * Parameterized constructor taking in the follwing:
     *
     * @param price       sticker price of the car
     * @param downPayment down payment made to the purchase price
     * @param loanTerm    how many years the buyer wants to finance their purchase over
     */
    public CarLoan(double price, double downPayment, int loanTerm) {
        mPrice = price;
        mDownPayment = downPayment;
        mLoanTerm = loanTerm;
    }

    /**
     * Default constructor with base initialized values
     * Not actually used
     */
    public CarLoan() {
        mPrice = 0.0;
        mDownPayment = 0.0;
        mLoanTerm = 3;
    }

    /**
     * Calculates the tax portion of the final price
     *
     * @return double value
     */
    public double taxAmount() {
        return mPrice * STATE_TAX;
    }

    /**
     * Calculates the total amount (final price) with tax included
     *
     * @return double value
     */
    public double totalAmount() {
        return mPrice + taxAmount();
    }

    /**
     * Calculates the amount borrowed by the borrower
     *
     * @return double value
     */
    public double borrowedAmount() {
        return totalAmount() - mDownPayment;
    }

    /**
     * Calculates the interest amount (compounded yearly)
     *
     * @return double value containing the interest value
     */
    public double interestAmount() {
        double interestRate;
        switch (mLoanTerm) {
            case 3:
                interestRate = 0.0462;
                break;
            case 4:
                interestRate = 0.0419;
                break;
            case 5:
                interestRate = 0.0416;
                break;
            default:
                interestRate = 3;
                break;
        }

        double interest = 0.0;
        for (int i = 0; i < mLoanTerm; i++) {
            if (interest > 0) {
                interest += (borrowedAmount() * interestRate);
            } else {
                interest = (borrowedAmount() * interestRate);
            }
        }

        return interest;
    }

    /**
     * Calculates the monthly payment from the information above
     *
     * @return double value
     */
    public double monthlyPayment() {
        return ((interestAmount() + borrowedAmount()) / (mLoanTerm * MONTHS_YEAR));
    }
}
