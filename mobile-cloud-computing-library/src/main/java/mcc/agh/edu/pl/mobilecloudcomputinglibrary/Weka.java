package mcc.agh.edu.pl.mobilecloudcomputinglibrary;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;

public class Weka {

    private static final double LEARNING_RATE = 0.5;
    private static final double MOMENTUM = 0.2;

    private XorTrainingSet xor;
    private Classifier model;
    private Instances trainingSet;

    public Classifier getNeuralClassifier() {
        MultilayerPerceptron classifier = new MultilayerPerceptron();
        classifier.setLearningRate(LEARNING_RATE);
        classifier.setMomentum(MOMENTUM);
        return classifier;
    }

    public void init() throws Exception {

        xor = new XorTrainingSet();
        trainingSet = xor.getInstances();

        this.model = getNeuralClassifier();
        model.buildClassifier(trainingSet);

    }

    public XorResult getXorResult(double a, double b) throws Exception {

        Instance testcase = xor.createTestCase(a, b);
        testcase.setDataset(trainingSet);

        double[] distribution = model.distributionForInstance(testcase);

        System.out.println(String.format("0: %s, 1: %s ", distribution[0], distribution[1]));
        return new XorResult(distribution[0], distribution[1]);
    }
}
