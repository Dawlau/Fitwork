package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class CreateWorkoutActivity extends Activity {

    private Button addExerciseButton;
    private Button backTestButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_form);

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
                startActivity(new Intent(CreateWorkoutActivity.this, AddExerciseActivity.class));
            }
        });
    }
}
