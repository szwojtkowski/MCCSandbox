package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import android.util.Log;

import java.util.Arrays;
import java.util.TreeMap;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.NeuralClassifier;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.PredictionClassifier;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.Constants;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class NeuralNetworkDecider implements Decider, Constants {

    private String TAG = getClass().getSimpleName();

    private KnowledgeRepository repository;
    private PredictionClassifier classifier;
    private FitnessAlgorithm fitness;


    public NeuralNetworkDecider(KnowledgeRepository repository, FitnessAlgorithm fitness){
        this.repository = repository;
        this.fitness = fitness;
        this.classifier = new NeuralClassifier();
    }

    @Override
    public ExecutionEnvironment getExecutionEnvironment(PredictionInstance predictionInstance) {
        TreeMap<Double, ExecutionEnvironment> fitnessResults = new TreeMap<>();
        for(ExecutionEnvironment env: ExecutionEnvironment.values()){
            fitnessResults.put(predictEnvironmentFitness(predictionInstance, env), env);
        }
        Log.i(TAG, fitnessResults.toString());
        return fitnessResults.firstEntry().getValue();
    }

    private double predictEnvironmentFitness(PredictionInstance predictionInstance, ExecutionEnvironment environment){
        String taskName = predictionInstance.getTaskName();
        if(!repository.isRegistered(taskName)){
            repository.registerTask(taskName);
        }
        InstanceTransformer transformer = new InstanceTransformer(repository.getKnowledgeData());
        Instance instance = transformer.toInstance(predictionInstance);
        Instances dataSet = repository.getKnowledgeData().getDataSet();
        instance.setDataset(dataSet);
        instance.setValue(dataSet.attribute(EXECUTION_ENVIRONMENT), environment.toString());

        double batteryUsage = predictBatteryUsage(instance, dataSet);
        double timeUsage = predictTimeUsage(instance, dataSet);

        System.out.println(String.format("env: %s, batteryUsage: %f, time: %f\n", environment.toString(), batteryUsage, timeUsage));

        return fitness.resultFor(Arrays.asList(batteryUsage, timeUsage));
    }

    private double predictBatteryUsage(Instance instance, Instances dataSet){
        try {
            Classifier cls = classifier.getClassifier();
            dataSet.setClass(dataSet.attribute(BATTERY_USAGE));
            cls.buildClassifier(dataSet);
            return cls.classifyInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE;
    }

    private double predictTimeUsage(Instance instance, Instances dataSet){
        try {
            Classifier cls = classifier.getClassifier();
            dataSet.setClass(dataSet.attribute(TIME_USAGE));
            cls.buildClassifier(dataSet);
            return cls.classifyInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE;
    }



/*    @Override
    public ExecutionEnvironment getExecutionEnvironment(PredictionInstance instance) {
        Classifier cls = classifier.getClassifier();
        try {
            cls.buildClassifier(repository.getKnowledgeData().getDataSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/


}
