package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import java.util.Map;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.J48Classifier;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors.FitnessPredictor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors.Normalizer;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors.NumericToNominalConverter;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.AddValues;
import weka.filters.unsupervised.attribute.Reorder;

public class WekaJ48Decider extends WekaDecider {

    private String TAG = getClass().getSimpleName();

    public WekaJ48Decider(KnowledgeRepository repository, FitnessAlgorithm fitness){
        super(repository, fitness);
        this.classifier = new J48Classifier();
    }

    protected double predictEnvironmentFitness(PredictionInstance predictionInstance, ExecutionEnvironment environment){
        KnowledgeDataSet data = repository.getKnowledgeData();

        Instances trainingSet = data.getDataSet();
        Instances filledData = addMissingAttrValues(trainingSet, predictionInstance);

        data.setDataSet(filledData);
        InstanceTransformer transformer = new InstanceTransformer(data);
        Instance instance = transformer.toInstance(predictionInstance);
        transformer.addEnvironment(instance, environment);

        Normalizer normalizer = new Normalizer(filledData, instance);
        Instances normalizedTrainingSet = normalizer.normalized();
        Instance normalizedInstance = normalizer.normalizedOne();

        NumericToNominalConverter converter = new NumericToNominalConverter(normalizedTrainingSet, normalizedInstance);
        Instances converted = converter.converted();
        Instance prediction = converter.convertedOne();

        FitnessPredictor predictor = new FitnessPredictor(classifier, fitness);

        return predictor.predictInstanceFitness(prediction, converted);
    }

    private Instances addMissingAttrValues(Instances data, PredictionInstance instance){
        Instances instances = data;
        for(Map.Entry<String, String> param:instance.getParams().entrySet()){
            instances = addValues(instances, param.getKey(), param.getValue());
        }
        return instances;
    }

    private Instances moveToLast(Instances data, String attributeName) throws Exception {
        Reorder r = new Reorder();
        String range = "first";
        int index = data.attribute(attributeName).index()+1;

        for (int i = 2; i < data.numAttributes()+1; i++) {
            if (index != i)
                range += "," + i;
        }
        range += "," + index;
        r.setAttributeIndices(range);
        r.setInputFormat(data);
        return Filter.useFilter(data, r);
    }

    private Instances addValues(Instances instances, String attributeName, String value){
        try {
            Instances data = moveToLast(instances, attributeName);
            AddValues addValues = new AddValues();
            addValues.setLabels(value);
            addValues.setInputFormat(data);
            return Filter.useFilter(data, addValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instances;
    }



}
