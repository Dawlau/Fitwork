package Models;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.perfmark.Link;

/*

In constructors, getters and setters:

Maybe create copies for collections instead of copying them by reference

*/

public class Workout {

    private String name;
    private String description;
    private double rating;
    private List<String> comments;

    // LinkedHashMap<exercise_id, list of sets>
    private Map<String, List <WorkoutSet> > exercises;

    public Workout(){
        this.name = "";
        this.description = "";
        this.rating = 0.0;
        this.comments = Collections.emptyList();
        this.exercises = new LinkedHashMap<>();
    }

    public Workout(String name, String description, double rating, List<String> comments, Map<String, List<WorkoutSet>> exercises) {
        this.name = name;
        this.description = description;
        this.rating = rating;

        this.comments = comments;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public Map<String, List<WorkoutSet>> getExercises() {
        return exercises;
    }

    public void setExercises(Map<String, List<WorkoutSet>> exercises) {
        this.exercises = exercises;
    }
}
