package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;

import static org.junit.Assert.assertEquals;

public class RandomDeciderTest {

    private RandomDecider decider;

    @Before
    public void createDecider(){
        this.decider = new RandomDecider();
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