package Models;

import java.io.Serializable;

public class Proportions implements Serializable {

    private double height;
    private double width;
    private upperBodyProportions upper;
    private lowerBodyProportions lower;

    public Proportions() {
    }

    public Proportions(double height, double width, upperBodyProportions upper, lowerBodyProportions lower) {
        this.height = height;
        this.width = width;
        this.upper = upper;
        this.lower = lower;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public upperBodyProportions getUpper() {
        return upper;
    }

    public void setUpper(upperBodyProportions upper) {
        this.upper = upper;
    }

    public lowerBodyProportions getLower() {
        return lower;
    }

    public void setLower(lowerBodyProportions lower) {
        this.lower = lower;
    }
}
