package mcc.agh.edu.pl.mobilecloudcomputinglibrary;

import java.util.ArrayList;
import java.util.Arrays;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class XorTrainingSet {

    private static final int ITERATIONS = 500;

    private Attribute a;
    private Attribute b;
    private Attribute result;
    private Instances trainingSet;

    public XorTrainingSet() {
        this.a = new Attribute("a");
        this.b = new Attribute("b");
        this.result = new Attribute("result", Arrays.asList("0", "1"));
        initSet();
    }

    public Instances getInstances(){
        return this.trainingSet;
    }

    private void initSet(){
        ArrayList<Attribute> attributes = new ArrayList<>(Arrays.asList(a, b, result));
        this.trainingSet = new Instances("Rel", attributes, 10);
        trainingSet.setClassIndex(2);
        for(int i = 0; i < ITERATIONS; i++) {
            trainingSet.add(createTrainingExample(1.0, 1.0, "0"));
            trainingSet.add(createTrainingExample(1.0, 0.0, "1"));
            trainingSet.add(createTrainingExample(0.0, 1.0, "1"));
            trainingSet.add(createTrainingExample(0.0, 0.0, "0"));
        }
    }

    public Instance createTrainingExample(double aVal, double bVal, String resultVal){
        Instance example = new DenseInstance(3);
        example.setValue(a, aVal);
        example.setValue(b, bVal);
        example.setValue(result, resultVal);
        return example;
    }

    public Instance createTestCase(double aVal, double bVal){
        Instance testcase = new DenseInstance(3);
        testcase.setValue(a, aVal);
        testcase.setValue(b, bVal);
        return testcase;
    }

}
