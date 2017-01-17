package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

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
import java.util.HashMap;
import java.util.Map;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Environment.class, Log.class})
public class
FileKnowledgeRepositoryTest {

    private static final String EXTERNAL_STORAGE_DIR_PATH = ".";
    private static final String PATH = "./weka/test.arff";
    private FileKnowledgeRepository repository;


    @Before
    public void createRepository(){
        PowerMockito.mockStatic(Environment.class);
        PowerMockito.mockStatic(Log.class);
        this.repository = new FileKnowledgeRepository(PATH);
        this.repository.registerTask("task");
        when(Environment.getExternalStorageDirectory()).thenReturn(new File(EXTERNAL_STORAGE_DIR_PATH));
    }

    @After
    public void clearKnowledgeDataSet(){
        repository.clearKnowledgeDataSet();
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

    @Test
    public void predictsRegisteredTask() throws Exception {
        KnowledgeInstance instance = new KnowledgeInstance("newTask", 13, 5, true, ExecutionEnvironment.CLOUD);
        PredictionInstance instance2 = new PredictionInstance("newTask2", false);
        repository.registerTask("newTask");
        repository.addKnowledgeInstance(instance);
        repository.registerTask("newTask2");
        InstanceTransformer transformer = new InstanceTransformer(repository.getKnowledgeData());
        transformer.toInstance(instance2);
        assertEquals(1, repository.getKnowledgeData().getDataSet().size());
        assertEquals(true, repository.isRegistered("newTask2"));
    }

    @Test
    public void addTaskWithParams() throws Exception {
        Map<String, String> params = new HashMap<>();
        KnowledgeInstance instance = new KnowledgeInstance("newTask", 13, 5, true, params, ExecutionEnvironment.CLOUD);
        KnowledgeInstance instance2 = new KnowledgeInstance("newTask", 13, 5, true, params, ExecutionEnvironment.CLOUD);

        Map<String, String> params2 = new HashMap<>();
        KnowledgeInstance instance3 = new KnowledgeInstance("newTask2", 18, 12, true, params2, ExecutionEnvironment.CLOUD);
        KnowledgeInstance instance4 = new KnowledgeInstance("newTask2", 19, 7, true, params2, ExecutionEnvironment.CLOUD);

        Map<String, String> params3 = new HashMap<>();
        KnowledgeInstance instance5 = new KnowledgeInstance("newTask3", 22, 3, true, params3, ExecutionEnvironment.CLOUD);
        KnowledgeInstance instance6 = new KnowledgeInstance("newTask3", 8, 12, true, params3, ExecutionEnvironment.CLOUD);

        repository.registerTask("newTask");
        repository.registerTask("newTask2");
        repository.registerTask("newTask3");

        params.put("size", "5");
        repository.addKnowledgeInstance(instance);

        params2.put("length", "14");
        repository.addKnowledgeInstance(instance3);

        params.put("size", "4");
        repository.addKnowledgeInstance(instance2);

        params3.put("other", "12");
        repository.addKnowledgeInstance(instance5);

        params2.put("length", "9");
        params2.put("size", "9");
        repository.addKnowledgeInstance(instance4);


        params3.put("length", "22");
        params3.put("other", "16");
        params3.put("other2", "16");
        params3.put("other3", "18");
        repository.addKnowledgeInstance(instance6);

        Instances data = repository.getKnowledgeData().getDataSet();
        Attribute attr = data.attribute("size");
        assertEquals(6, data.size());
        assertEquals(true, attr != null);
        //System.out.println(data);
    }

}