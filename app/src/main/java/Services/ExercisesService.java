package Services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.*;

import Models.Exercise;

public class ExercisesService {

    private static ExercisesService singleton = null;

    private ExercisesService() {}

    private String readJSON(String FileName) {

        try (BufferedReader buffer = new BufferedReader(new FileReader(FileName))) {

            String line = buffer.readLine();
            StringBuilder content = new StringBuilder();

            while (line != null) {
                content.append(line).append("\n");
                line = buffer.readLine();
            }

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private ArrayList<Exercise> loadJSON(String FileName) {

        String contentJSON = readJSON(FileName);

        //Log.d("Debug", contentJSON);

        return new ArrayList<>();
    }

    public static ExercisesService getInstance(){

        if(singleton == null)
            singleton = new ExercisesService();
        return singleton;
    }

    public void loadExercises(String FileName) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        loadJSON(FileName);

//        db.collection("exercises")
//                .add()
//                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                        if(task.isSuccessful()){
//
//                        }
//                        else{
//
//                        }
//                    }
//                })
    }
}
