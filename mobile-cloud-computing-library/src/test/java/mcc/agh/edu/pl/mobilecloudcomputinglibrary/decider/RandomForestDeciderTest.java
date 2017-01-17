package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import android.os.Environment;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
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
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Environment.class, Log.class})
public class RandomForestDeciderTest {

    private WekaRandomForestDecider decider;
    private KnowledgeRepository repository;
    private static final String EXTERNAL_STORAGE_DIR_PATH = ".";

    @Before
    public void createDecider(){

        PowerMockito.mockStatic(Environment.class);
        PowerMockito.mockStatic(Log.class);
        when(Environment.getExternalStorageDirectory()).thenReturn(new File(EXTERNAL_STORAGE_DIR_PATH));

        List<Double> weights = Arrays.asList(5.0, 1.0);
        FitnessAlgorithm algorithm = new WeightedArithmeticMean(weights);
        this.repository = new FileKnowledgeRepository("./weka/deciderTest.arff");
        this.decider = new WekaRandomForestDecider(repository, algorithm);

        KnowledgeInstance instance = new KnowledgeInstance("task", 8, 6, false, ExecutionEnvironment.CLOUD);
        KnowledgeInstance instance2 = new KnowledgeInstance("task", 12, 10, false, ExecutionEnvironment.CLOUD);
        KnowledgeInstance instance3 = new KnowledgeInstance("task", 20, 12, false, ExecutionEnvironment.LOCAL);
        KnowledgeInstance instance4 = new KnowledgeInstance("task", 15, 10, false, ExecutionEnvironment.LOCAL);
        repository.registerTask("task");
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
        ExecutionEnvironment env = decider.getExecutionEnvironment(instance);
        assertEquals(ExecutionEnvironment.CLOUD, env);
    }

    @Test
    public void checkDecisionChange() throws Exception {
        KnowledgeInstance instance = new KnowledgeInstance("task", 3, 4, false, ExecutionEnvironment.LOCAL);
        KnowledgeInstance instance2 = new KnowledgeInstance("task", 2, 2, false, ExecutionEnvironment.LOCAL);
        repository.addKnowledgeInstance(instance);
        repository.addKnowledgeInstance(instance2);

        PredictionInstance predictionInstance = new PredictionInstance("task", false);
        ExecutionEnvironment env = decider.getExecutionEnvironment(predictionInstance);
        assertEquals(ExecutionEnvironment.LOCAL, env);
    }


}