package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import Models.LowerBodyProportions;
import Models.Profile;
import Models.Proportions;
import Models.UpperBodyProportions;

import static android.content.ContentValues.TAG;

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
                Boolean firstTime = (Boolean) getIntent().getSerializableExtra("firstTime");
                lower.setThighCirc(Double.parseDouble(formThigh));
                lower.setCalfCirc(Double.parseDouble(formCalf));

                userProportions.setLower(lower);
                userProportions.setRecordedDate(new Date());

                String TAG = "TEST :";
                //If first time user
                //Create documents and collections
                if(firstTime) {
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

                                                    documentReference2.collection("proportionsHistory")
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
                }
                //If not first time user
                //Update proportions
                //Add new proportions to proportions history
                else {
                    db.collection("profiles")
                            .document(userProfile.getUID())
                            .collection("privateProfile")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()) {
                                        //Update user proportions
                                        Objects.requireNonNull(task.getResult())
                                                .getDocuments()
                                                .get(0)
                                                .getReference()
                                                .collection("proportions")
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task.isSuccessful()) {
                                                            Objects.requireNonNull(task.getResult())
                                                                    .getDocuments()
                                                                    .get(0)
                                                                    .getReference()
                                                                    .set(userProportions)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Log.w(TAG, "Error writing document", e);
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                        //Add new user proportions to proportionsHistory
                                        Objects.requireNonNull(task.getResult())
                                                .getDocuments()
                                                .get(0)
                                                .getReference()
                                                .collection("proportionsHistory")
                                                .add(userProportions)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error writing document", e);
                                                    }
                                                });
                                    }
                                }
                            });
                }

                Intent intent = new Intent(UserDetailsThirdActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
