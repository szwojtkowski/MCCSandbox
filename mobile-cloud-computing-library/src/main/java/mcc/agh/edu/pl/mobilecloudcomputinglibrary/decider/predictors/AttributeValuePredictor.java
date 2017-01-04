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
            try {
                Log.e("Predictor", instance.toString());
                Log.e("Predictor", data.classAttribute().toString());
                //Log.e("Predictor", ((J48) cls).graph());
            }  catch (Exception e) {
                e.printStackTrace();
            }
            int index = (int) cls.classifyInstance(instance);
            Double res = Double.valueOf(data.classAttribute().value(index));
            Log.e("Predictor", "Instance"+ instance);
            Log.e("Predictor", "Result: "+res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE;
    }
}
