package Models;

import java.io.Serializable;

public class LowerBodyProportions implements Serializable {
    private double thighCirc;
    private double calfCirc;

    public LowerBodyProportions() {
    }

    public LowerBodyProportions(double thighCirc, double calfCirc) {
        this.thighCirc = thighCirc;
        this.calfCirc = calfCirc;
    }

    public double getThighCirc() {
        return thighCirc;
    }

    public void setThighCirc(double thighCirc) {
        this.thighCirc = thighCirc;
    }

    public double getCalfCirc() {
        return calfCirc;
    }

    public void setCalfCirc(double calfCirc) {
        this.calfCirc = calfCirc;
    }
}
