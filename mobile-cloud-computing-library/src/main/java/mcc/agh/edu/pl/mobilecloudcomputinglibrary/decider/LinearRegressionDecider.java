package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

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
import weka.core.Instance;
import weka.core.Instances;

public class LinearRegressionDecider implements Decider, Constants {

    private Classifier classifier;
    private KnowledgeRepository repository;
    private FitnessAlgorithm fitness;
    private InstanceTransformer transformer;

    public LinearRegressionDecider(KnowledgeRepository repository, FitnessAlgorithm fitness){
        this.repository = repository;
        this.fitness = fitness;
        this.transformer = new InstanceTransformer(repository.getKnowledgeData());
        this.classifier = new LinearRegression();
    }

    @Override
    public ExecutionEnvironment whereExecute(PredictionInstance predictionInstance) {
        TreeMap<ExecutionEnvironment, Double> fitnessResults = new TreeMap<>();
        for(ExecutionEnvironment env: ExecutionEnvironment.values()){
            fitnessResults.put(env, predictEnvironmentFitness(predictionInstance, env));
        }
        return fitnessResults.firstKey();
    }

    private double predictEnvironmentFitness(PredictionInstance predictionInstance, ExecutionEnvironment environment){
        Instance instance = transformer.toInstance(predictionInstance);
        Instances dataSet = repository.getKnowledgeData().getDataSet();
        instance.setDataset(dataSet);
        instance.setValue(dataSet.attribute(EXECUTION_ENVIRONMENT), environment.toString());

        double batteryUsage = predictBatteryUsage(instance, dataSet);
        double timeUsage = predictTimeUsage(instance, dataSet);

        //TODO remove println or make logger and log info about prediction results
        //System.out.println(String.format("env: %s, batteryUsage: %f, time: %f\n", environment.toString(), batteryUsage, timeUsage));

        return fitness.resultFor(Arrays.asList(batteryUsage, timeUsage));
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
