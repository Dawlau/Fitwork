package Models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise implements Serializable, Parcelable {

    private static Creator creator;
    private String name;
    private String description;
    private String category;
    private ArrayList<String> muscleGroups; // does not need a copy in constructor/getter since it is only used for CRUD

    public Exercise(){
        this.name = "";
        this.description = "";
        this.category = "";
        this.muscleGroups = new ArrayList<>();
    }

    public Exercise(String name, String description, String category, ArrayList<String> muscleGroups) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.muscleGroups = muscleGroups;
    }

    public Exercise(Exercise exercise){
        this.name = exercise.name;
        this.description = exercise.description;
        this.category = exercise.category;
        this.muscleGroups = new ArrayList<>(exercise.muscleGroups);
    }

    protected Exercise(Parcel in) {
        name = in.readString();
        description = in.readString();
        category = in.readString();
        muscleGroups = in.createStringArrayList();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

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

    public ArrayList<String> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(ArrayList<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeStringList(muscleGroups);
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
