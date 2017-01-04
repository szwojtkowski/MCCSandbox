package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

public class J48Classifier implements PredictionClassifier {

    private J48 model;

    public J48Classifier(){
        this.model = new J48();
        this.model.setUnpruned(true);
    }

    @Override
    public Classifier getClassifier() {
        return model;
    }
}
