package Services;

import android.renderscript.ScriptGroup;
import android.util.Log;


import androidx.annotation.NonNull;

import com.example.fitwork.ContextManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Models.Exercise;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class ExercisesService {

    private static ExercisesService singleton = null;

    private ExercisesService() {}

    private String readJSON() {

        String json = null;
        try{
            InputStream is = ContextManager.getContext().getAssets().open("exercises.json");

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        }catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    private ArrayList<Exercise> loadJSON()  {

        String contentJSON = readJSON();
        ArrayList<Exercise> exercises = new ArrayList<>();

        try{

            JSONArray jsonExercises = new JSONArray(contentJSON);

            for(int i = 0; i < jsonExercises.length(); ++i){

                JSONObject jsonObject = (JSONObject) jsonExercises.get(i);

                String exerciseName = jsonObject.get("name").toString();
                String exerciseDescription = jsonObject.get("description").toString();
                String exerciseCategory = jsonObject.get("category").toString();
                ArrayList<String> exercise_muscleGroups = new ArrayList<>();

                JSONArray jsonMuscleGroups = (JSONArray)jsonObject.get("muscle_groups");
                for(int j = 0; j < jsonMuscleGroups.length(); ++j){

                    String muscleGroup = jsonMuscleGroups.get(j).toString();
                    exercise_muscleGroups.add(muscleGroup);
                }

                Exercise exercise = new Exercise(
                        exerciseName,
                        exerciseDescription,
                        exerciseCategory,
                        exercise_muscleGroups
                );

                exercises.add(exercise);
            }

        }catch (JSONException e){
            e.printStackTrace();
            return new ArrayList<>();
        }

        return exercises;
    }

    private void loadToDatabase(ArrayList<Exercise> exercises){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for(Exercise exercise : exercises)
            db.collection("exercises").add(exercise)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                Log.d("Debug", "Added exercise to database successfully");
                            }
                            else{
                                Log.d("Debug", "Error on adding exercise with name: " + exercise.getName());
                            }
                        }
                    });
    }


    public static ExercisesService getInstance(){

        if(singleton == null)
            singleton = new ExercisesService();
        return singleton;
    }

    public void loadExercisesFromJSON() {

        ArrayList<Exercise> exercises = loadJSON();
        loadToDatabase(exercises);
    }
}
