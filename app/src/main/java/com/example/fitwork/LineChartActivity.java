package com.example.fitwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import Models.Proportions;
import Models.ProportionsSorter;

public class LineChartActivity extends Activity {
    private ArrayList<Proportions> userProportionHistory = new ArrayList<>();
    //private ArrayList<ILineDataSet> userProportionsGraphData = new ArrayList<>();
    private ArrayList<LineData> userProportionsLineData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Spinner spinner = (Spinner) findViewById(R.id.muscles_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(LineChartActivity.this, R.array.muscles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        LineChart chart = (LineChart) findViewById(R.id.chart);
        //chart.setVisibility(View.GONE);
        chart.setNoDataText("Select an option to see your progress.");
        //chart.setDrawGridBackground(false);
        //chart.set


        Log.d("tag", "inainte");

        db.collection("profiles")
                .document(user.getUid())
                .collection("privateProfile")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Objects.requireNonNull(task.getResult())
                                    .getDocuments()
                                    .get(0)
                                    .getReference()
                                    .collection("proportionsHistory")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                //Add proportions in the proportions array list
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Proportions userProportions = document.toObject(Proportions.class);
                                                    userProportionHistory.add(userProportions);
                                                    Log.d("tag", "number");
                                                }

                                                //Sort the proportions by recorded date
                                                userProportionHistory.sort(new ProportionsSorter());

                                                initializeGraphData();

                                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                        if(position != 0) {
                                                            chart.setData(userProportionsLineData.get(position - 1));
                                                            //chart.setVisibility(View.VISIBLE);
                                                            chart.invalidate();
                                                        }
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parentView) {
                                                        // your code here
                                                    }

                                                });

                                                //chart.setData(data);

                                            } else {
                                                Log.d("tag", "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d("tag", "dupa");

    }

    public void initializeGraphData() {
        //Indexes:
        //0 - weight data
        //1 - biceps data
        //2 - triceps data
        //3 - chest data
        //4 - back data
        //5 - shoulders data
        //6 - thigh data
        //7 - calf data

        ArrayList<ArrayList<Entry>> entriesList = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            ArrayList<Entry> entries = new ArrayList<>();
            entriesList.add(entries);
        }

        int i = 0;

        //Add the data to the entries list
        for(Proportions p : userProportionHistory) {
            //Add weight data
            entriesList.get(0).add(new Entry(i, (float) p.getWeight()));
            //Add biceps data
            entriesList.get(1).add(new Entry(i, (float) p.getUpper().getBicepsCirc()));
            //Add triceps data
            entriesList.get(2).add(new Entry(i, (float) p.getUpper().getTricepsCirc()));
            //Add chest data
            entriesList.get(3).add(new Entry(i, (float) p.getUpper().getChestWidth()));
            //Add back data
            entriesList.get(4).add(new Entry(i, (float) p.getUpper().getBackWidth()));
            //Add shoulders data
            entriesList.get(5).add(new Entry(i, (float) p.getUpper().getShoulderWidth()));
            //Add thigh data
            entriesList.get(6).add(new Entry(i, (float) p.getLower().getThighCirc()));
            //Add calf data
            entriesList.get(7).add(new Entry(i, (float) p.getLower().getCalfCirc()));

            i += 1;
        }

        //Create the line data set and populate the graph data array
        for(i = 0; i < 8; i++) {
            LineDataSet lineDataSet = new LineDataSet(entriesList.get(i), Integer.toString(i));
            userProportionsLineData.add(new LineData(lineDataSet));
        }
    }
}
