package Models;

import java.util.Collections;
import java.util.List;

public class Exercise {

    private String name;
    private String description;
    private String category;
    private List<String> muscleGroups; // does not need a copy in constructor/getter since it is only used for CRUD

    public Exercise(){
        this.name = "";
        this.description = "";
        this.muscleGroups = Collections.emptyList();
    }

    public Exercise(String name, String description, String category, List<String> muscleGroups) {
        this.name = name;
        this.description = description;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() { // for testing purposes
        StringBuilder representation = new StringBuilder();

        representation.append("Name: ").append(name).append("; Description: ").append(description).append("; Muscle groups ");
        for(String muscleGroup : muscleGroups)
            representation.append(muscleGroup).append(" ");

        return representation.toString();
    }
}
