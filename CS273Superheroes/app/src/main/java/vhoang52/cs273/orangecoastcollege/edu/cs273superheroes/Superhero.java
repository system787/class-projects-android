package vhoang52.cs273.orangecoastcollege.edu.cs273superheroes;

/**
 * Object class for Superhero objects
 *
 * @author vhoang52
 */
class Superhero {
    String mUsername;
    String mName;
    String mSuperpower;
    String mOneThing;

    /**
     *
     * @param username The school id login
     * @param name Their name
     * @param superpower Their superpower
     * @param oneThing Their one quirk
     */
    public Superhero(String username, String name, String superpower, String oneThing) {
        mUsername = username;
        mName = name;
        mSuperpower = superpower;
        mOneThing = oneThing;
    }

    /**
     * Gets username
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * Gets name
     */
    public String getName() {
        return mName;
    }

    /**
     * Gets power
     */
    public String getSuperpower() {
        return mSuperpower;
    }

    /**
     * Gets quirk
     */
    public String getOneThing() {
        return mOneThing;
    }
}
