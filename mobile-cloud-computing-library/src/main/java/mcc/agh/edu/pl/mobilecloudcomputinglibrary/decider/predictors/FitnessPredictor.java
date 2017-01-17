package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors;

import android.util.Log;

import java.util.Arrays;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.PredictionClassifier;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.Constants;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.AttributeRemover;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.extractors.Extractor;
import weka.core.Instance;
import weka.core.Instances;

public class FitnessPredictor {

    private PredictionClassifier classifier;
    private FitnessAlgorithm algorithm;
    private Extractor extractor;


    public FitnessPredictor(PredictionClassifier classifier, FitnessAlgorithm algorithm, Extractor extractor){
        this.classifier = classifier;
        this.algorithm = algorithm;
        this.extractor = extractor;
    }

    public double predictInstanceFitness(Instance instance, Instances dataSet){

        double batteryUsage = predictBatteryUsage(instance, dataSet);
        double timeUsage = predictTimeUsage(instance, dataSet);

        Log.e("Fitness predictor", classifier.getClass().toString());
        System.out.println(String.format("batteryUsage: %f, time: %f\n", batteryUsage, timeUsage));

        return algorithm.resultFor(Arrays.asList(batteryUsage, timeUsage));
    }

    private double predictBatteryUsage(Instance instance, Instances dataSet){
        AttributeRemover remover = new AttributeRemover(dataSet, instance, Constants.TIME_USAGE);
        Instances data = remover.removed();
        AttributeValuePredictor predictor = new AttributeValuePredictor(classifier, data);
        double prediction = predictor.predict(remover.removedOne(), Constants.BATTERY_USAGE);
        return extractor.extractResult(Constants.BATTERY_USAGE, prediction);
    }

    private double predictTimeUsage(Instance instance, Instances dataSet){
        AttributeRemover remover = new AttributeRemover(dataSet, instance, Constants.BATTERY_USAGE);
        Instances data = remover.removed();
        AttributeValuePredictor predictor = new AttributeValuePredictor(classifier, data);
        double prediction = predictor.predict(remover.removedOne(), Constants.TIME_USAGE);
        return extractor.extractResult(Constants.TIME_USAGE, prediction);
    }
}
