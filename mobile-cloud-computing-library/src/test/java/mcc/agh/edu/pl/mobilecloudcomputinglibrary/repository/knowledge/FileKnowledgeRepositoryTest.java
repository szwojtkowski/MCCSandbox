package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import android.os.Environment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import weka.core.Instance;
import weka.core.Instances;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Environment.class)
public class FileKnowledgeRepositoryTest {

    private static final String EXTERNAL_STORAGE_DIR_PATH = ".";
    private static final String PATH = "./weka/test.arff";
    private FileKnowledgeRepository repository;


    @Before
    public void createRepository(){
        this.repository = new FileKnowledgeRepository(PATH);
        this.repository.registerTask("task");
        PowerMockito.mockStatic(Environment.class);
        when(Environment.getExternalStorageDirectory()).thenReturn(new File(EXTERNAL_STORAGE_DIR_PATH));
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

    @Test
    public void registersNewTaskCorrectly() throws Exception {
        KnowledgeInstance instance = new KnowledgeInstance("task", 13, 5, true, ExecutionEnvironment.CLOUD);
        KnowledgeInstance instance2 = new KnowledgeInstance("task", 19, 16, true, ExecutionEnvironment.LOCAL);
        KnowledgeInstance instance3 = new KnowledgeInstance("task2", 19, 16, true, ExecutionEnvironment.LOCAL);
        KnowledgeInstance instance4 = new KnowledgeInstance("task3", 19, 16, false, ExecutionEnvironment.LOCAL);
        repository.addKnowledgeInstance(instance);
        repository.addKnowledgeInstance(instance2);
        repository.registerTask("task2");
        repository.addKnowledgeInstance(instance3);
        repository.registerTask("task3");
        repository.addKnowledgeInstance(instance4);
        assertEquals(4, repository.getKnowledgeData().getDataSet().size());
    }

}