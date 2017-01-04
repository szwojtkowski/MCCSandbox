package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class AttributeRemover {

    private Remove remove;
    private Instances trainingSet;
    private Instance testCase;
    private Instances removed;
    private Instance removedTestCase;

    public AttributeRemover(Instances trainingSet, Instance testCase, String attributeName){
        this.trainingSet = trainingSet;
        this.testCase = testCase;
        this.remove = new Remove();
        remove(attributeName);
    }

    public Instances removed(){
        if(removed != null){
            return removed;
        } else {
            return trainingSet;
        }
    }

    public Instance removedOne(){
        if(removedTestCase != null){
            return removedTestCase;
        } else {
            return testCase;
        }
    }

    private void remove(String attributeName){
        try {
            remove.setAttributeIndices(String.valueOf(trainingSet.attribute(attributeName).index()+1));
            remove.setInputFormat(trainingSet);
            trainingSet.add(testCase);
            removed = Filter.useFilter(trainingSet, remove);
            removedTestCase = removed.lastInstance();
            removed.remove(removedTestCase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
