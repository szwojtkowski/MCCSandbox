package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.FileKnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;

import static org.junit.Assert.assertEquals;

public class RandomDeciderTest {

    private RandomDecider decider;
    private KnowledgeRepository repository;

    @Before
    public void createDecider(){
        this.repository = new FileKnowledgeRepository("./weka/deciderTest.arff");
        this.decider = new RandomDecider(repository);
    }

    @Test
    public void checkIfAllEnvironmentsCanBeChosen() throws Exception {
        PredictionInstance instance = new PredictionInstance("task", false);
        List<ExecutionEnvironment> chosenEnvs = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            chosenEnvs.add(decider.whereExecute(instance));
        }
        assertEquals(true, chosenEnvs.contains(ExecutionEnvironment.CLOUD));
        assertEquals(true, chosenEnvs.contains(ExecutionEnvironment.LOCAL));
    }
}