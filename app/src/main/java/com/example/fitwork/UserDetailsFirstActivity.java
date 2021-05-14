package com.example.fitwork;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

import Models.Profile;
import Models.Proportions;

public class UserDetailsFirstActivity extends Activity {
    private Button nextButton;
    private Button datePickerButton;
    private DatePickerDialog datePickerDialog;
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
        datePickerButton = (Button) findViewById(R.id.date_picker_button);
        //datePickerButton.setVisibility(View.GONE);

        if(getIntent().getSerializableExtra("firstTime") != null || false) {
            initDatePicker();
            datePickerButton.setVisibility(View.VISIBLE);
            //TODO: init with current date
            datePickerButton.setText(getDateString(1,1,2021));
            datePickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePickerDialog.show();
                }
            });
        }

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

                //Log.d("test", Integer.toString(datePickerDialog.getDatePicker().getDayOfMonth()));
                //Log.d("test", Integer.toString(datePickerDialog.getDatePicker().getMonth()));
                //Log.d("test", Integer.toString(datePickerDialog.getDatePicker().getYear()));

                Intent intent = new Intent(UserDetailsFirstActivity.this, UserDetailsSecondActivity.class);
                intent.putExtra("profile", userProfile);
                intent.putExtra("proportions", userProportions);

                if(getIntent().getSerializableExtra("firstTime") == null) {
                    Boolean firstTime = false;
                    intent.putExtra("firstTime", firstTime);
                }
                else
                    intent.putExtra("firstTime", getIntent().getSerializableExtra("firstTime"));

                startActivity(intent);
            }
        });
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = getDateString(day, month, year);
                datePickerButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    //Builds the strings used for the date picker
    //FORMAT: JAN 01 1970
    //        MONTH dd YYYY
    private String getDateString(int month, int day, int year) {

        Month monthEnum = Month.of(month);
        String monthString = monthEnum.getDisplayName(TextStyle.SHORT, Locale.getDefault());

        return monthString + " " + day + " " + year;
    }

}
