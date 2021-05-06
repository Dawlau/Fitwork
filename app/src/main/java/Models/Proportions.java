package Models;

import java.io.Serializable;

public class Proportions implements Serializable {

    private double height;
    private double weight;
    private UpperBodyProportions upper;
    private LowerBodyProportions lower;

    public Proportions() {
    }

    public Proportions(double height, double weight, UpperBodyProportions upper, LowerBodyProportions lower) {
        this.height = height;
        this.weight = weight;
        this.upper = upper;
        this.lower = lower;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public UpperBodyProportions getUpper() {
        return upper;
    }

    public void setUpper(UpperBodyProportions upper) {
        this.upper = upper;
    }

    public LowerBodyProportions getLower() {
        return lower;
    }

    public void setLower(LowerBodyProportions lower) {
        this.lower = lower;
    }
}
