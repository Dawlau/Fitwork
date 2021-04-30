package Models;

public class WorkoutSet {

    private double weight; // used weight
    private int repCount; // number of repetitions
    private String pauseDuration;

    public WorkoutSet(){

        this.weight = 0;
        this.repCount = 0;
        this.pauseDuration = "";
    }

    public WorkoutSet(double weight, int repCount, String pauseDuration) {
        this.weight = weight;
        this.repCount = repCount;
        this.pauseDuration = pauseDuration;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRepCount() {
        return repCount;
    }

    public void setRepCount(int repCount) {
        this.repCount = repCount;
    }

    public String getPauseDuration() {
        return pauseDuration;
    }

    public void setPauseDuration(String pauseDuration) {
        this.pauseDuration = pauseDuration;
    }
}
