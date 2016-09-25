package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import org.junit.Before;
import org.junit.Test;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import weka.core.Instance;
import weka.core.Instances;

import static org.junit.Assert.assertEquals;

public class FileKnowledgeRepositoryTest {

    private static final String PATH = "./weka/test.arff";
    private FileKnowledgeRepository repository;

    @Before
    public void createRepository(){
        this.repository = new FileKnowledgeRepository(PATH);
    }

    @Test
    public void persistsAllKnowledgeInstances() throws Exception {
        KnowledgeInstance instance = new KnowledgeInstance("task", 12, 12, false, ExecutionEnvironment.CLOUD);
        KnowledgeInstance instance2 = new KnowledgeInstance("task", 15, 1, true, ExecutionEnvironment.LOCAL);

        repository.addKnowledgeInstance(instance);
        repository.addKnowledgeInstance(instance2);

        Instances dataSet = repository.getKnowledgeData().getDataSet();
        assertEquals(2, dataSet.size());
    }

    @Test
    public void persistsKnowledgeInstanceCorrectly() throws Exception {
        KnowledgeInstance instance = new KnowledgeInstance("task", 119, 6, false, ExecutionEnvironment.CLOUD);

        repository.addKnowledgeInstance(instance);

        Instances dataSet = repository.getKnowledgeData().getDataSet();

        Instance repoInstance = dataSet.get(0);

        double delta = 0.001;

        assertEquals(1, dataSet.size());
        assertEquals("task", repoInstance.stringValue(0));
        assertEquals(119, repoInstance.value(1), delta);
        assertEquals(6, repoInstance.value(2), delta);
        assertEquals("false", repoInstance.stringValue(3));
        assertEquals("cloud", repoInstance.stringValue(4));
    }

}