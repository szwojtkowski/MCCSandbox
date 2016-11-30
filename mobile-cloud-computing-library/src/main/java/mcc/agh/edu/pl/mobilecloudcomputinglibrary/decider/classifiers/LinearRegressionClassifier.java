package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;

public class LinearRegressionClassifier implements PredictionClassifier {

    private Classifier classifier;

    public LinearRegressionClassifier(){
        this.classifier = new LinearRegression();
    }

    public Classifier getClassifier() {
        return classifier;
    }

}
