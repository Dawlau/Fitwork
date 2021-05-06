package Models;

import java.io.Serializable;

public class upperBodyProportions implements Serializable {

    private double chestWidth;
    private double backWidth;
    private double shoulderWidth;
    private double bicepsCirc;
    private double tricepsCirc;

    public upperBodyProportions() {
    }

    public upperBodyProportions(double chestWidth, double backWidth, double shoulderWidth, double bicepsCirc, double tricepsCirc) {
        this.chestWidth = chestWidth;
        this.backWidth = backWidth;
        this.shoulderWidth = shoulderWidth;
        this.bicepsCirc = bicepsCirc;
        this.tricepsCirc = tricepsCirc;
    }

    public double getChestWidth() {
        return chestWidth;
    }

    public void setChestWidth(double chestWidth) {
        this.chestWidth = chestWidth;
    }

    public double getBackWidth() {
        return backWidth;
    }

    public void setBackWidth(double backWidth) {
        this.backWidth = backWidth;
    }

    public double getShoulderWidth() {
        return shoulderWidth;
    }

    public void setShoulderWidth(double shoulderWidth) {
        this.shoulderWidth = shoulderWidth;
    }

    public double getBicepsCirc() {
        return bicepsCirc;
    }

    public void setBicepsCirc(double bicepsCirc) {
        this.bicepsCirc = bicepsCirc;
    }

    public double getTricepsCirc() {
        return tricepsCirc;
    }

    public void setTricepsCirc(double tricepsCirc) {
        this.tricepsCirc = tricepsCirc;
    }
}
