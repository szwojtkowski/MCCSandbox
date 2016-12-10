package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import android.util.Log;

import java.util.Arrays;
import java.util.TreeMap;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.Constants;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class LinearRegressionDecider implements Decider, Constants {

    private String TAG = getClass().getSimpleName();

    private Classifier classifier;
    private KnowledgeRepository repository;
    private FitnessAlgorithm fitness;

    public LinearRegressionDecider(KnowledgeRepository repository, FitnessAlgorithm fitness){
        this.repository = repository;
        this.fitness = fitness;
        this.classifier = new LinearRegression();
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
        Instances filteredDataSet = filterEnvironmentDataKnowledge(dataSet, environment);
        instance.setDataset(filteredDataSet);
        instance.setValue(filteredDataSet.attribute(EXECUTION_ENVIRONMENT), environment.toString());

        double batteryUsage = predictBatteryUsage(instance, filteredDataSet);
        double timeUsage = predictTimeUsage(instance, filteredDataSet);

        System.out.println(String.format("env: %s, batteryUsage: %f, time: %f\n", environment.toString(), batteryUsage, timeUsage));

        return fitness.resultFor(Arrays.asList(batteryUsage, timeUsage));
    }

    private Instances filterEnvironmentDataKnowledge(Instances dataSet, ExecutionEnvironment environment){
        try {
            Attribute environmentAttribute = dataSet.attribute(EXECUTION_ENVIRONMENT);
            RemoveWithValues removeFilter = new RemoveWithValues();
            removeFilter.setInvertSelection(true);
            removeFilter.setAttributeIndex(Integer.toString(environmentAttribute.index()+1));
            removeFilter.setNominalIndices(Integer.toString(environmentAttribute.indexOfValue(environment.toString())+1));
            removeFilter.setInputFormat(dataSet);
            return Filter.useFilter(dataSet, removeFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    private double predictBatteryUsage(Instance instance, Instances dataSet){
        try {
            dataSet.setClass(dataSet.attribute(BATTERY_USAGE));
            classifier.buildClassifier(dataSet);
            return classifier.classifyInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE;
    }

    private double predictTimeUsage(Instance instance, Instances dataSet){
        try {
            dataSet.setClass(dataSet.attribute(TIME_USAGE));
            classifier.buildClassifier(dataSet);
            return classifier.classifyInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE;
    }
}
