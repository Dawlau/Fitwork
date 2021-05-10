package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import Models.LowerBodyProportions;
import Models.Profile;
import Models.Proportions;
import Models.UpperBodyProportions;

public class UserDetailsThirdActivity extends Activity {
    private Button submitButton;
    private EditText thighInput;
    private EditText calfInput;
    private LowerBodyProportions lower = new LowerBodyProportions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_third);

        thighInput = (EditText) findViewById(R.id.thigh);
        calfInput = (EditText) findViewById(R.id.calf);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //TODO: Add validations
                String formThigh = thighInput.getText().toString();
                String formCalf = calfInput.getText().toString();

                Proportions userProportions = (Proportions) getIntent().getSerializableExtra("proportions");
                Profile userProfile = (Profile) getIntent().getSerializableExtra("profile");
                lower.setThighCirc(Double.parseDouble(formThigh));
                lower.setCalfCirc(Double.parseDouble(formCalf));

                userProportions.setLower(lower);

                String TAG = "TEST :";
                db.collection("profiles")
                        .document(userProfile.getUID())
                        .set(userProfile)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                CollectionReference userProfileRef = db.collection("profiles").document(userProfile.getUID()).collection("privateProfile");
                                        userProfileRef
                                        .add(new HashMap<>())
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference2) {
                                                documentReference2.collection("proportions")
                                                        .add(userProportions)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference3) {
                                                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference3.getId());
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w(TAG, "Error adding document", e);
                                                            }
                                                        });
                                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference2.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding document", e);
                                            }
                                        });

                                Log.d(TAG, "DocumentSnapshot added with ID: " + userProfile.getUID());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                Intent intent = new Intent(UserDetailsThirdActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
