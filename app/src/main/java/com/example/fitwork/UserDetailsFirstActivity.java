package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import Models.Profile;
import Models.Proportions;

public class UserDetailsFirstActivity extends Activity {
    private Button nextButton;
    private Profile userProfile = new Profile();
    private EditText heightInput;
    private EditText weightInput;
    private Proportions userProportions = new Proportions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_first);

        heightInput = (EditText) findViewById(R.id.height);
        weightInput = (EditText) findViewById(R.id.weight);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //TODO: Get birthday from form
        userProfile.setUID(user.getUid());
        userProfile.setBirthday("test");

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //TODO: Add validations
                String formHeight = heightInput.getText().toString();
                String formWeight = weightInput.getText().toString();
                userProportions.setHeight(Double.parseDouble(formHeight));
                userProportions.setWeight(Double.parseDouble(formWeight));

                Intent intent = new Intent(UserDetailsFirstActivity.this, UserDetailsSecondActivity.class);
                intent.putExtra("profile", userProfile);
                intent.putExtra("proportions", userProportions);
                startActivity(intent);
            }
        });
    }

}
