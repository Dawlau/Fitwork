package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class AddExerciseActivity extends Activity {

    private Button GoBackToWorkoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);

        GoBackToWorkoutButton = (Button) findViewById(R.id.goBackToWorkoutButton);
        GoBackToWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddExerciseActivity.this, CreateWorkoutActivity.class));
            }
        });
    }
}
