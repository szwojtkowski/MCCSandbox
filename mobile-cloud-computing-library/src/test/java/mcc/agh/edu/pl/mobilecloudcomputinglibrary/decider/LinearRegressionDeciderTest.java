package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.WeightedArithmeticMean;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.FileKnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;

import static org.junit.Assert.assertEquals;

public class LinearRegressionDeciderTest {

    private LinearRegressionDecider decider;
    //TODO change repository to mock
    private KnowledgeRepository repository;

    @Before
    public void createDecider(){
        List<Double> weights = Arrays.asList(1.0, 5.0);
        FitnessAlgorithm algorithm = new WeightedArithmeticMean(weights);
        this.repository = new FileKnowledgeRepository("./weka/deciderTest.arff");
        this.decider = new LinearRegressionDecider(repository, algorithm);

        KnowledgeInstance instance = new KnowledgeInstance("task", 8, 6, false, ExecutionEnvironment.CLOUD);
        KnowledgeInstance instance2 = new KnowledgeInstance("task", 12, 10, false, ExecutionEnvironment.CLOUD);
        KnowledgeInstance instance3 = new KnowledgeInstance("task", 20, 12, false, ExecutionEnvironment.LOCAL);
        KnowledgeInstance instance4 = new KnowledgeInstance("task", 15, 10, false, ExecutionEnvironment.LOCAL);
        repository.addKnowledgeInstance(instance);
        repository.addKnowledgeInstance(instance2);
        repository.addKnowledgeInstance(instance3);
        repository.addKnowledgeInstance(instance4);
    }

    @After
    public void clearKnowledgeDataSet(){
        repository.clearKnowledgeDataSet();
    }

    @Test
    public void checkDecision() throws Exception {
        PredictionInstance instance = new PredictionInstance("task", false);
        ExecutionEnvironment env = decider.whereExecute(instance);
        assertEquals(ExecutionEnvironment.CLOUD, env);
    }
}