package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

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
    public ExecutionEnvironment whereExecute(PredictionInstance predictionInstance) {
        String taskName = predictionInstance.getTaskName();

        if(!repository.isRegistered(taskName)){
            repository.registerTask(taskName);
        }

        EnvironmentFittingSelector selector = new EnvironmentFittingSelector();

        for(ExecutionEnvironment env: ExecutionEnvironment.values()){
            double fitness = predictEnvironmentFitness(predictionInstance, env);
            selector.addEnvironmentFitness(env, fitness);
        }

        return selector.getBestFittingEnvironment();
    }

    protected abstract double predictEnvironmentFitness(
            PredictionInstance predictionInstance, ExecutionEnvironment environment
    );

}
