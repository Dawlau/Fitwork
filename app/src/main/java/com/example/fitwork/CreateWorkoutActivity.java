package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import Models.Exercise;

public class CreateWorkoutActivity extends Activity {

    private Button addExerciseButton;
    private Button backTestButton;
    private ArrayList<Exercise> currentExercises = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_form);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            currentExercises = bundle.getParcelableArrayList("current exercises");
        }

        for(int i = 0; i < currentExercises.size(); ++i)
            Log.d("Debug", currentExercises.get(i).toString());


        backTestButton = (Button) findViewById(R.id.backTestButton);
        backTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateWorkoutActivity.this, DashboardActivity.class));
            }
        });

        addExerciseButton = (Button) findViewById(R.id.addWorkoutExerciseButton);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateWorkoutActivity.this, AddExerciseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("current exercises", currentExercises);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
