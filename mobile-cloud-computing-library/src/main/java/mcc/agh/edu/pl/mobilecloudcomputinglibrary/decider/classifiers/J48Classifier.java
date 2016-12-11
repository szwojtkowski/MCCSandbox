package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

public class J48Classifier implements PredictionClassifier {

    private Classifier model;

    public J48Classifier(){
        this.model = new J48();
    }

    @Override
    public Classifier getClassifier() {
        return model;
    }
}
