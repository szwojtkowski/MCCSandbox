package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class NumericToNominalConverter {

    private NumericToNominal converter;
    private Instances trainingSet;
    private Instance testCase;
    private Instances converted;
    private Instance convertedTestCase;

    public NumericToNominalConverter(Instances trainingSet, Instance testCase){
        this.trainingSet = trainingSet;
        this.testCase = testCase;
        this.converter = new NumericToNominal();
        convert();
    }

    public Instances converted(){
        if(converted != null){
            return converted;
        } else {
            return trainingSet;
        }
    }

    public Instance convertedOne(){
        if(convertedTestCase != null){
            return convertedTestCase;
        } else {
            return testCase;
        }
    }

    private void convert(){
        try {
            String[] options= new String[2];
            options[0]="-R";
            options[1]="2-3";  //range of variables to make numeric
            converter.setInputFormat(trainingSet);
            converter.setOptions(options);
            trainingSet.add(testCase);
            converted = Filter.useFilter(trainingSet, converter);
            convertedTestCase = converted.lastInstance();
            converted.remove(convertedTestCase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
