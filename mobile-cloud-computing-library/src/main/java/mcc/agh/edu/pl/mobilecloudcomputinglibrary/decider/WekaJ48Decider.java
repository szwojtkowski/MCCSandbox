package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

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

public class WekaJ48Decider extends WekaDecider {

    private String TAG = getClass().getSimpleName();

    public WekaJ48Decider(KnowledgeRepository repository, FitnessAlgorithm fitness){
        super(repository, fitness);
        this.classifier = new J48Classifier();
    }

    protected double predictEnvironmentFitness(PredictionInstance predictionInstance, ExecutionEnvironment environment){
        KnowledgeDataSet data = repository.getKnowledgeData();
        InstanceTransformer transformer = new InstanceTransformer(data);
        Instance instance = transformer.toInstance(predictionInstance);
        transformer.addEnvironment(instance, environment);

        Instances trainingSet = data.getDataSet();
        Normalizer normalizer = new Normalizer(trainingSet, instance);
        Instances normalizedTrainingSet = normalizer.normalized();
        Instance normalizedInstance = normalizer.normalizedOne();

        NumericToNominalConverter converter = new NumericToNominalConverter(normalizedTrainingSet, normalizedInstance);
        Instances converted = converter.converted();
        Instance prediction = converter.convertedOne();

        FitnessPredictor predictor = new FitnessPredictor(classifier, fitness);

        return predictor.predictInstanceFitness(prediction, converted);
    }
}
