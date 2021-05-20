package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    private ArrayList<Exercise> exercises = new ArrayList<>();

    private void showDefaultExercises() {
        ArrayList<Exercise> exercises = new ArrayList<>();
        db.collection("exercises")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Successfully fetched exercises from database");
                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                        if(queryDocumentSnapshots != null) {
                            for (DocumentSnapshot exercise : queryDocumentSnapshots) {
                                exercises.add(exercise.toObject(Exercise.class));
                            }
                        }
                        showCustomExercises(exercises); // propagate the list further to the next step
                    } else {
                        Log.d(TAG, "Failed to fetch exercises from database");
                    }
                }
            });
    }

    private void showCustomExercises(ArrayList<Exercise> exercises) {
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
                                                                    if(queryDocumentSnapshots != null) {
                                                                        for (DocumentSnapshot exercise : queryDocumentSnapshots) {
                                                                            exercises.add(exercise.toObject(Exercise.class));
                                                                        }
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
    }

    private ArrayList<String> formatExercises(ArrayList<Exercise> exercises) {
        ArrayList<String> formattedExercises = new ArrayList<>();
        this.exercises = new ArrayList<>();
        for(Exercise exercise : exercises) {
            String name = exercise.getName();
            String category = exercise.getCategory();
            List<String> muscles = exercise.getMuscleGroups();
            String result = name + "\n" + category;
            if (muscles != null) {
                result += "\n" + muscles.stream()
                        .map(i -> "" + i)
                        .collect(Collectors.joining(", "));
            }
            formattedExercises.add(result);
            this.exercises.add(exercise);
        }
        return formattedExercises;
    }

    private void showExercises(ArrayList<Exercise> exercises) {
        ArrayList<String> formattedExercises = formatExercises(exercises);
        ArrayAdapter<String> adaptExercises = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                formattedExercises);
        ChooseExercise.setAdapter(adaptExercises);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);

        ChooseExercise = (ListView) findViewById(R.id.chooseExercise);
        showDefaultExercises();

        Bundle bundle = getIntent().getExtras();
        ArrayList<Exercise> currentExercises = bundle.getParcelableArrayList("current exercises");


        ChooseExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Exercise number " + position + " clicked!!!");
                Intent intent = new Intent(AddExerciseActivity.this, CreateWorkoutActivity.class);

                currentExercises.add(new Exercise(exercises.get(position)));

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("current exercises", currentExercises);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        GoBackToWorkoutButton = (Button) findViewById(R.id.goBackToWorkoutButton);
        GoBackToWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddExerciseActivity.this, CreateWorkoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("current exercises", currentExercises);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
