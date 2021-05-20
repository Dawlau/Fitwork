package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import Models.Exercise;

import static android.content.ContentValues.TAG;

// TODO: check if exercise with same name already exists

public class CreateExerciseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_exercise);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Button goBackButton = (Button) findViewById(R.id.goBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateExerciseActivity.this, DashboardActivity.class));
            }
        });


        Button addButton = (Button) findViewById(R.id.addExerciseButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText exerciseNameBox = (EditText) findViewById(R.id.exerciseName);
                EditText exerciseDescriptionBox = (EditText) findViewById(R.id.exerciseDescription);
                EditText exerciseCategoryBox = (EditText) findViewById(R.id.exerciseCategory);
                EditText exerciseMuscleGroupsBox = (EditText) findViewById(R.id.exerciseMuscleGroups);

                String exerciseName = exerciseNameBox.getText().toString();
                String exerciseDescription = exerciseDescriptionBox.getText().toString();
                String exerciseCategory = exerciseCategoryBox.getText().toString();
                String[] exerciseMuscleGroups = exerciseMuscleGroupsBox.getText().toString().split("\\n");

                Exercise exercise = new Exercise(exerciseName, exerciseDescription, exerciseCategory, new ArrayList<>(Arrays.asList(exerciseMuscleGroups)));

                assert user != null;
                String uid = user.getUid();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                                                                .add(exercise)
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                                                        if(!task.isSuccessful()){
                                                                            Log.d(TAG, "Could not add exercise");
                                                                        }
                                                                        else{
                                                                            startActivity(new Intent(CreateExerciseActivity.this, MainActivity.class));
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
        });
    }
}
