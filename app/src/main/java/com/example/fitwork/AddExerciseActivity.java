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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static android.content.ContentValues.TAG;

public class AddExerciseActivity extends Activity {

    private Button GoBackToWorkoutButton;
    private ListView ChooseExercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);

        ChooseExercise = (ListView) findViewById(R.id.chooseExercise);
        ContextManager context = ContextManager.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("exercises")
            .get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    ArrayList<String> exercises = new ArrayList<>();
                    for(DocumentSnapshot exercise : queryDocumentSnapshots) {
                        if(exercise.exists()) {
                            String name = exercise.getString("name");
                            List<String> muscles = (List<String>) exercise.get("muscleGroups");
                            String result = name;
                            if (muscles != null) {
                                result += "\n" + muscles.stream()
                                        .map(i -> "" + i)
                                        .collect(Collectors.joining(", "));
                            }
                            exercises.add(result);
                        }
                    }
                    ArrayAdapter adaptExercises = new ArrayAdapter<String>(
                            context,
                            android.R.layout.simple_list_item_1,
                            exercises);
                    ChooseExercise.setAdapter(adaptExercises);
                }
            });

        GoBackToWorkoutButton = (Button) findViewById(R.id.goBackToWorkoutButton);
        GoBackToWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddExerciseActivity.this, CreateWorkoutActivity.class));
            }
        });
    }
}
