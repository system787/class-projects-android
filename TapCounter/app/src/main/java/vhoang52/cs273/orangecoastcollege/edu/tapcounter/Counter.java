package vhoang52.cs273.orangecoastcollege.edu.tapcounter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates a counter object that initializes at 0 or an input integer
 * @author Vincent Hoang
 */
class Counter {

    private int mCount;
    private final String TAG = "Counter";

    /**
     * Default constructor
     * Counter initializes at 0
     */
    Counter() {
        this.mCount = 0;
        Logger.getLogger(TAG).log(Level.INFO, "Counter class instantiated without parameter. mCount is 0");
    }

    /**
     * Paramatized constructor
     * Counter initializes at the input integer parameter
     * @param count initial count
     */
    Counter(int count) {
        this.mCount = count;
        Logger.getLogger(TAG).log(Level.INFO, "Counter class instantiated with parameter. mCount is " + count);
    }

    /**
     * Returns the current counter value
     * @return mCount; integer
     */
    int getCount() {
        Logger.getLogger(TAG).log(Level.INFO, "Counter.getCount() called, mCount is " + mCount);
        return mCount;
    }

    /**
     * Increases the current counter value by one
     */
    void increaseCount() {
        this.mCount++;
        Logger.getLogger(TAG).log(Level.INFO, "Counter.increaseCount() called. mCount is now " + mCount);
    }

}

