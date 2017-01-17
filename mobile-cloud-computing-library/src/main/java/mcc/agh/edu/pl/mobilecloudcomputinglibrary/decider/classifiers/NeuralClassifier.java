package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;

public class NeuralClassifier implements PredictionClassifier{

    private static final double LEARNING_RATE = 0.5;
    private static final double MOMENTUM = 0.2;

    private Classifier model;

    public NeuralClassifier(){
        MultilayerPerceptron classifier = new MultilayerPerceptron();
        classifier.setLearningRate(LEARNING_RATE);
        classifier.setMomentum(MOMENTUM);
        this.model = classifier;
    }

    public Classifier getClassifier() {
        return model;
    }


}
