package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import Models.Proportions;
import Models.UpperBodyProportions;

public class UserDetailsSecondActivity extends Activity {
    private Button nextButton;
    private EditText chestInput;
    private EditText backInput;
    private EditText shouldersInput;
    private EditText bicepsInput;
    private EditText tricepsInput;
    private UpperBodyProportions upper = new UpperBodyProportions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_second);

        chestInput = (EditText) findViewById(R.id.chest);
        backInput = (EditText) findViewById(R.id.back);
        shouldersInput = (EditText) findViewById(R.id.shoulders);
        bicepsInput = (EditText) findViewById(R.id.biceps);
        tricepsInput = (EditText) findViewById(R.id.triceps);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //TODO: Add validations
                String formChest = chestInput.getText().toString();
                String formBack = backInput.getText().toString();
                String formShoulders = shouldersInput.getText().toString();
                String formBiceps = bicepsInput.getText().toString();
                String formTriceps = tricepsInput.getText().toString();

                Proportions userProportions= (Proportions) getIntent().getSerializableExtra("proportions");

                upper.setChestWidth(Double.parseDouble(formChest));
                upper.setBackWidth(Double.parseDouble(formBack));
                upper.setShoulderWidth(Double.parseDouble(formShoulders));
                upper.setBicepsCirc(Double.parseDouble(formBiceps));
                upper.setTricepsCirc(Double.parseDouble(formTriceps));
                userProportions.setUpper(upper);

                Intent intent = new Intent(UserDetailsSecondActivity.this, UserDetailsThirdActivity.class);
                intent.putExtra("profile", getIntent().getSerializableExtra("profile"));
                intent.putExtra("proportions", userProportions);
                startActivity(intent);
            }
        });
    }
}
