package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors;

import java.util.Arrays;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.PredictionClassifier;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.Constants;
import weka.core.Instance;
import weka.core.Instances;

public class FitnessPredictor {

    private PredictionClassifier classifier;
    private FitnessAlgorithm algorithm;


    public FitnessPredictor(PredictionClassifier classifier, FitnessAlgorithm algorithm){
        this.classifier = classifier;
        this.algorithm = algorithm;
    }

    public double predictInstanceFitness(Instance instance, Instances dataSet){
        Normalizer normalizer = new Normalizer(dataSet, instance);
        Instances normalizedTrainingSet = normalizer.normalized();
        Instance normalizedInstance = normalizer.normalizedOne();


        double batteryUsage = predictBatteryUsage(normalizedInstance, normalizedTrainingSet);
        double timeUsage = predictTimeUsage(normalizedInstance, normalizedTrainingSet);

        System.out.println(String.format("batteryUsage: %f, time: %f\n", batteryUsage, timeUsage));

        return algorithm.resultFor(Arrays.asList(batteryUsage, timeUsage));
    }

    private double predictBatteryUsage(Instance instance, Instances dataSet){
        AttributeValuePredictor predictor = new AttributeValuePredictor(classifier, dataSet);
        return predictor.predict(instance, Constants.BATTERY_USAGE);
    }

    private double predictTimeUsage(Instance instance, Instances dataSet){
        AttributeValuePredictor predictor = new AttributeValuePredictor(classifier, dataSet);
        return predictor.predict(instance, Constants.TIME_USAGE);
    }
}
