package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import Models.Exercise;

import static android.content.ContentValues.TAG;

public class AddExerciseActivity extends Activity {

    private Button GoBackToWorkoutButton;
    private ListView ChooseExercise;

    private final ContextManager context = ContextManager.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();

    private ArrayList<Exercise> getDefaultExercises() {
        ArrayList<Exercise> exercises = new ArrayList<>();
        db.collection("exercises")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Successfully fetched exercises from database");
                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                        for(DocumentSnapshot exercise : queryDocumentSnapshots) {
                            String name             = exercise.getString("name");
                            String description      = exercise.getString("description");
                            List<String> muscles    = (List<String>) exercise.get("muscleGroups");
                            exercises.add(new Exercise(name, description, muscles));
                        }
                        getCustomExercises(exercises); // propagate the list further to the next step
                    } else {
                        Log.d(TAG, "Failed to fetch exercises from database");
                    }
                }
            });
        return exercises;
    }

    private ArrayList<Exercise> getCustomExercises(ArrayList<Exercise> exercises) {
        assert user != null;
        String uid = user.getUid();
        db.collection("profiles")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Objects.requireNonNull(task.getResult())
                                    .getDocuments()
                                    .get(0)
                                    .getReference()
                                    .collection("privateProfile")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                Objects.requireNonNull(task.getResult())
                                                        .getDocuments()
                                                        .get(0)
                                                        .getReference()
                                                        .collection("exercises")
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d(TAG, "Successfully fetched exercises from database");
                                                                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                                                                    for(DocumentSnapshot exercise : queryDocumentSnapshots) {
                                                                        String name             = exercise.getString("name");
                                                                        String description      = exercise.getString("description");
                                                                        List<String> muscles    = (List<String>) exercise.get("muscleGroups");
                                                                        exercises.add(new Exercise(name, description, muscles));
                                                                    }
                                                                    showExercises(exercises); // propagate the answer forward to the display function
                                                                } else {
                                                                    Log.d(TAG, "Failed to fetch exercises from database");
                                                                }
                                                            }
                                                        });
                                            }
                                            else{
                                                Log.d(TAG, "No private profile for user with uid " + uid);
                                            }
                                        }
                                    });
                        }
                        else{
                            Log.d(TAG, "No user with uid " + uid);
                        }
                    }
                });
        return exercises;
    }

    private void showExercises(ArrayList<Exercise> exercises) {
        ArrayList<String> formattedExercises = formatExercises(exercises);
        ArrayAdapter<String> adaptExercises = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                formattedExercises);
        ChooseExercise.setAdapter(adaptExercises);
    }

    private ArrayList<String> formatExercises(ArrayList<Exercise> exercises) {
        ArrayList<String> formattedExercises = new ArrayList<>();
        for(Exercise exercise : exercises) {
            String name = exercise.getName();
            List<String> muscles = exercise.getMuscleGroups();
            String result = name;
            if (muscles != null) {
                result += "\n" + muscles.stream()
                        .map(i -> "" + i)
                        .collect(Collectors.joining(", "));
            }
            formattedExercises.add(result);
        }
        for(String e : formattedExercises) {
            Log.d(TAG, e + "\n");
        }
        return formattedExercises;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);

        ChooseExercise = (ListView) findViewById(R.id.chooseExercise);

        getDefaultExercises();

        GoBackToWorkoutButton = (Button) findViewById(R.id.goBackToWorkoutButton);
        GoBackToWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddExerciseActivity.this, CreateWorkoutActivity.class));
            }
        });
    }
}
