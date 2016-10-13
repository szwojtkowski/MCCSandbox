package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import java.util.Arrays;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.WeightedArithmeticMean;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;

public class SmartDecider implements Decider {



    private RandomDecider randomDecider;
    private LinearRegressionDecider decider;
    private KnowledgeRepository repository;

    public SmartDecider(KnowledgeRepository repository){
        FitnessAlgorithm algorithm = new WeightedArithmeticMean(Arrays.asList(1.0, 4.0));
        this.randomDecider = new RandomDecider();
        this.decider = new LinearRegressionDecider(repository, algorithm);
        this.repository = repository;
    }

    @Override
    public ExecutionEnvironment whereExecute(PredictionInstance instance) {
        if(repository.getKnowledgeData().getDataSet().size() < 10){
            return randomDecider.whereExecute(instance);
        } else {
            return decider.whereExecute(instance);
        }
    }

}
