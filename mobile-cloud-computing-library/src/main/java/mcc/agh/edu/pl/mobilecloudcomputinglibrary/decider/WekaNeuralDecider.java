package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import java.util.Map;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.NeuralClassifier;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors.FitnessPredictor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.AttributeValueAdder;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.Normalizer;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.extractors.Extractor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.extractors.WekaSimpleResultExtractor;
import weka.core.Instance;
import weka.core.Instances;

public class WekaNeuralDecider extends WekaDecider {

    private String TAG = getClass().getSimpleName();

    public WekaNeuralDecider(KnowledgeRepository repository, FitnessAlgorithm fitness){
        super(repository, fitness);
        this.classifier = new NeuralClassifier();
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


        Extractor extractor = new WekaSimpleResultExtractor();
        FitnessPredictor predictor = new FitnessPredictor(classifier, fitness, extractor);

        return predictor.predictInstanceFitness(normalizedInstance, normalizedTrainingSet);
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
