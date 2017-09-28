package vhoang52.cs273.orangecoastcollege.edu.paintestimator;

/**
 * Created by vincenthoang on 9/19/17.
 */

public class PaintModel {
    private static final double DOOR_AREA = 21.00;
    private static final double SQ_FEET_PER_GALLON = 275.00;
    private static final double WINDOW_AREA = 16.00;

    private int mDoors;
    private int mWindows;

    private String mHeight;
    private String mLength;
    private String mWidth;

    public PaintModel() {
        mDoors = 0;
        mWindows = 0;
        mHeight = "";
        mLength = "";
        mWidth = "";
    }

    public PaintModel(int doors, int windows, String height, String length, String width) {
        mDoors = doors;
        mWindows = windows;
        mHeight = height;
        mLength = length;
        mWidth = width;
    }

    public void setDoors(int doors) {
        mDoors = doors;
    }

    public void setWindows(int windows) {
        mWindows = windows;
    }

    public void setHeight(String height) {
        mHeight = height;
    }

    public void setLength(String length) {
        mLength = length;
    }

    public void setWidth(String width) {
        mWidth = width;
    }

    public double doorAndWindowArea() {
        double doorArea = mDoors * DOOR_AREA;
        double windowArea = mWindows * WINDOW_AREA;

        return doorArea + windowArea;
    }

    public double wallSurfaceArea() {
        double height = Double.parseDouble(mHeight);
        double width = Double.parseDouble(mWidth);
        double length = Double.parseDouble(mLength);

        double side = 2 * (height * width);
        double face = (length * width);
        double narrow = 2 * (length * height);

        return side + face + narrow;
    }

    public double totalSurfaceArea() {
        return wallSurfaceArea() - doorAndWindowArea();
    }

    public double gallonsOfPaintRequired() {
        return totalSurfaceArea() / SQ_FEET_PER_GALLON;
    }
}
