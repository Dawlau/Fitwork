package Services;

public class OnLoadService {

    private static OnLoadService singleton = null;

    private OnLoadService() {}

    public static OnLoadService getInstance(){

        if(singleton == null)
            singleton = new OnLoadService();
        return singleton;
    }

    private void OptionalLoads(){

        ExercisesService exercisesService = ExercisesService.getInstance();
        exercisesService.loadExercisesFromJSON();
    }

    public void OnLoad(){

        //OptionalLoads();
    }
}
