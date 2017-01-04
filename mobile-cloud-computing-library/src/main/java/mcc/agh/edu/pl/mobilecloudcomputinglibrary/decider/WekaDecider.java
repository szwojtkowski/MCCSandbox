package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import android.util.Log;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.PredictionClassifier;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.EnvironmentFittingSelector;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.Constants;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;

public abstract class WekaDecider implements Decider, Constants {

    private String TAG = getClass().getSimpleName();

    protected KnowledgeRepository repository;
    protected FitnessAlgorithm fitness;
    protected PredictionClassifier classifier;


    public WekaDecider(KnowledgeRepository repository, FitnessAlgorithm fitness){
        this.repository = repository;
        this.fitness = fitness;
    }

    @Override
    public ExecutionEnvironment getExecutionEnvironment(PredictionInstance predictionInstance) {
        String taskName = predictionInstance.getTaskName();

        if(!repository.isRegistered(taskName)){
            repository.registerTask(taskName);
        }

        EnvironmentFittingSelector selector = new EnvironmentFittingSelector();

        for(ExecutionEnvironment env: ExecutionEnvironment.values()){
            //Log.e("WekaDecider", env.toString());
            double fitness = predictEnvironmentFitness(predictionInstance, env);
            Log.e("WekaDecider", env.toString() + " fitness: "+ fitness);
            selector.addEnvironmentFitness(env, fitness);
        }

        ExecutionEnvironment selected = selector.getBestFittingEnvironment();
        Log.e("WekaDecider", "SELECTED: "+selected.toString());
        return selector.getBestFittingEnvironment();
    }

    protected abstract double predictEnvironmentFitness(
            PredictionInstance predictionInstance, ExecutionEnvironment environment
    );

}
