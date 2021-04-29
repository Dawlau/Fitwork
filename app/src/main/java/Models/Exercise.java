package Models;

import java.util.Collections;
import java.util.List;

public class Exercise {

    private String name;
    private String description;
    private List<String> muscleGroups; // does not need a copy in constructor/getter since it is only used for CRUD

    public Exercise(){
        this.name = "";
        this.description = "";
        this.muscleGroups = Collections.emptyList();
    }

    public Exercise(String name, String description, List<String> muscleGroups) {
        this.name = name;
        this.description = description;
        this.muscleGroups = muscleGroups;
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

    public List<String> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(List<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }
}