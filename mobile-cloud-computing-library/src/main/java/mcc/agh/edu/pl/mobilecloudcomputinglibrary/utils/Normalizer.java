package mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class Normalizer {

    private Normalize norm;
    private Instances trainingSet;
    private Instance testCase;
    private Instances normalized;
    private Instance normalizedTestCase;

    public Normalizer(Instances trainingSet, Instance testCase){
        this.trainingSet = trainingSet;
        this.testCase = testCase;
        this.norm = new Normalize();
        normalize();
    }

    public Instances normalized(){
        if(normalized != null){
            return normalized;
        } else {
            return trainingSet;
        }
    }

    public Instance normalizedOne(){
        if(normalizedTestCase != null){
            return normalizedTestCase;
        } else {
            return testCase;
        }
    }

    private void normalize(){
        try {
            norm.setInputFormat(trainingSet);
            trainingSet.add(testCase);
            normalized = Filter.useFilter(trainingSet, norm);
            normalizedTestCase = normalized.lastInstance();
            normalized.remove(normalizedTestCase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
