package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;

public class RandomForestClassifier implements PredictionClassifier {

    private Classifier model;

    public RandomForestClassifier(){
        this.model = new RandomForest();
    }

    @Override
    public Classifier getClassifier() {
        return model;
    }
}
