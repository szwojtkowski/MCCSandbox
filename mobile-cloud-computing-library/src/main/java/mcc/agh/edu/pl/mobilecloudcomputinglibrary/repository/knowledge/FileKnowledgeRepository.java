package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

public class FileKnowledgeRepository implements KnowledgeRepository{

    private final String TAG = getClass().getSimpleName();

    private String filePath;
    private KnowledgeDataSet dataSet;
    private Set<String> registeredTasks;


    public FileKnowledgeRepository(String filePath){
        this.registeredTasks = new HashSet<>();
        this.filePath = filePath;
        this.dataSet = new KnowledgeDataSet(registeredTasks);
    }

    private File getFile() throws IOException {
        File myFile = new File(Environment.getExternalStorageDirectory(), filePath);
        if (!myFile.exists()) {
            myFile.mkdirs();
            myFile.createNewFile();
        }
        return myFile;
    }

    @Override
    public KnowledgeDataSet getKnowledgeData() {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(getFile());
            Instances set = loader.getDataSet();
            dataSet.setDataSet(set);
            return dataSet;
        } catch (IOException e) {}
        return new KnowledgeDataSet(registeredTasks);
    }

    @Override
    public void addKnowledgeInstance(KnowledgeInstance instance) {
        try {
            dataSet.addKnowledgeInstance(instance);
            ArffSaver saver = new ArffSaver();
            saver.setFile(getFile());
            Instances instances = dataSet.getDataSet();
            saver.setInstances(instances);
            saver.writeBatch();
            Log.i(TAG, "Added new instance to knowledge repository");
            Log.d(TAG, instances.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearKnowledgeDataSet() {
        try {
            dataSet.getDataSet().clear();
            ArffSaver saver = new ArffSaver();
            saver.setFile(getFile());
            saver.setInstances(dataSet.getDataSet());
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerTask(String name) {
        registeredTasks.add(name);
        KnowledgeDataSet newSet =  new KnowledgeDataSet(registeredTasks);
        Instances newInstances = newSet.getDataSet();
        Instances newDataSet = new InstanceTransformer(dataSet).toNewKnowledgeDataSetFormat(newInstances);
        this.dataSet.setDataSet(newDataSet);
    }

    @Override
    public boolean isRegistered(String name) {
        return registeredTasks.contains(name);
    }
}
