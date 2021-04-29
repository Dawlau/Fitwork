package Models;

/*

In constructors, getters and setters:

Maybe make a copy of the workout instead of copying it by reference

*/

public class FinishedWorkout {

    private String Date;
    private String Duration;
    private Workout workout;


    public FinishedWorkout(){
        this.Date = "";
        this.Duration = "";
        this.workout = new Workout();
    }

    public FinishedWorkout(String date, String duration, Workout workout) {
        Date = date;
        Duration = duration;
        this.workout = workout;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}
