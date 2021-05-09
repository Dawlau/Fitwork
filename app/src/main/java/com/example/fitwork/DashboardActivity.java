package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends Activity {
    private Button signOutButton;
    private Button createExercisesFormButton;
    private Button createWorkoutFormButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activty);

        signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                signOut();
            }
        });

        createExercisesFormButton = (Button) findViewById(R.id.createExercise);
        createExercisesFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, CreateExerciseActivity.class));
            }
        });

        createWorkoutFormButton = (Button) findViewById((R.id.CreateWorkoutButton));
        createWorkoutFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, CreateWorkoutActivity.class));
            }
        });
    }


    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        //TODO: add signed out message
        Intent intent = new Intent(this, GoogleAuth.class);
        startActivity(intent);
    }
}
