package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import java.util.Map;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.J48Classifier;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors.FitnessPredictor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.AttributeValueAdder;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.Normalizer;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.NumericToNominalConverter;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.extractors.Extractor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.extractors.WekaTreeResultExtractor;
import weka.core.Instance;
import weka.core.Instances;

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

        Extractor extractor = new WekaTreeResultExtractor(converted);
        FitnessPredictor predictor = new FitnessPredictor(classifier, fitness, extractor);

        return predictor.predictInstanceFitness(prediction, converted);
    }

    private Instances addMissingAttrValues(Instances data, PredictionInstance instance){
        Instances instances = data;
        for(Map.Entry<String, String> param:instance.getParams().entrySet()){
            AttributeValueAdder add = new AttributeValueAdder(instances, param.getKey(), param.getValue());
            instances = add.valuesAdded();
        }
        return instances;
    }

}
