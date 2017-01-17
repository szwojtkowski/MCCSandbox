package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.PredictionClassifier;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class AttributeValuePredictor {

    private PredictionClassifier predictionClassifier;
    private Instances data;

    public AttributeValuePredictor(PredictionClassifier predictionClassifier, Instances data){
        this.predictionClassifier = predictionClassifier;
        this.data = data;
    }

    public double predict(Instance instance, String attributeName){
        try {
            Classifier cls = predictionClassifier.getClassifier();
            data.setClass(data.attribute(attributeName));
            cls.buildClassifier(data);
            return cls.classifyInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE;
    }


}
