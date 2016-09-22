package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import org.junit.Before;
import org.junit.Test;

import weka.core.Instance;
import weka.core.Instances;

import static org.junit.Assert.assertEquals;

public class FileKnowledgeRepositoryTest {

    private static final String PATH = "./weka/data.arff";
    private FileKnowledgeRepository repository;

    @Before
    public void createRepository(){
        this.repository = new FileKnowledgeRepository(PATH);
    }

    @Test
    public void persistsAllKnowledgeInstances() throws Exception {
        KnowledgeInstance instance = new KnowledgeInstance(12, 12, false, false);
        KnowledgeInstance instance2 = new KnowledgeInstance(15, 1, true, false);

        repository.addKnowledgeInstance(instance);
        repository.addKnowledgeInstance(instance2);

        Instances dataSet = repository.getKnowledgeData();
        assertEquals(2, dataSet.size());
    }

    @Test
    public void persistsKnowledgeInstanceCorrectly() throws Exception {
        KnowledgeInstance instance = new KnowledgeInstance(119, 6, false, true);

        repository.addKnowledgeInstance(instance);

        Instances dataSet = repository.getKnowledgeData();

        Instance repoInstance = dataSet.get(0);

        double delta = 0.001;

        assertEquals(1, dataSet.size());
        assertEquals(119, repoInstance.value(0), delta);
        assertEquals(6, repoInstance.value(1), delta);
        assertEquals("false", repoInstance.stringValue(2));
        assertEquals("true", repoInstance.stringValue(3));
    }

}