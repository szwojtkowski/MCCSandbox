package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.classifiers.NeuralClassifier;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.predictors.FitnessPredictor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;
import weka.core.Instance;

public class WekaNeuralDecider extends WekaDecider {

    private String TAG = getClass().getSimpleName();

    public WekaNeuralDecider(KnowledgeRepository repository, FitnessAlgorithm fitness){
        super(repository, fitness);
        this.classifier = new NeuralClassifier();
    }

    protected double predictEnvironmentFitness(PredictionInstance predictionInstance, ExecutionEnvironment environment){

        KnowledgeDataSet data = repository.getKnowledgeData();
        InstanceTransformer transformer = new InstanceTransformer(data);
        Instance instance = transformer.toInstance(predictionInstance);
        transformer.addEnvironment(instance, environment);

        FitnessPredictor predictor = new FitnessPredictor(classifier, fitness);

        return predictor.predictInstanceFitness(instance, data.getDataSet());
    }
}
