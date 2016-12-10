package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import java.util.Arrays;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.WeightedArithmeticMean;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;

public class SmartDecider implements Decider {

    private final int RANDOM_STEP_SIZE = 10;

    private RandomDecider randomDecider;
    private Decider decider;
    private KnowledgeRepository repository;

    public SmartDecider(KnowledgeRepository repository){
        FitnessAlgorithm algorithm = new WeightedArithmeticMean(Arrays.asList(1.0, 4.0));
        this.randomDecider = new RandomDecider(repository);
        this.decider = new WekaNeuralDecider(repository, algorithm);
        this.repository = repository;
    }

    @Override
    public ExecutionEnvironment getExecutionEnvironment(PredictionInstance instance) {
        if(repository.getKnowledgeData().getDataSet().size() < RANDOM_STEP_SIZE){
            return randomDecider.getExecutionEnvironment(instance);
        } else {
            return decider.getExecutionEnvironment(instance);
        }
    }

}
