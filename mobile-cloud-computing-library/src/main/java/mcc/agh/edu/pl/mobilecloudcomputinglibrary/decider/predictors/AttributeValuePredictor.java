package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors;

import android.util.Log;

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
            Double res = cls.classifyInstance(instance);
            Log.e("Predictor", "Result: "+res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE;
    }
}
