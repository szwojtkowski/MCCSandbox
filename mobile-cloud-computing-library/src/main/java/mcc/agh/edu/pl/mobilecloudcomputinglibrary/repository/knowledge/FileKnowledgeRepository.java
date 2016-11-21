package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.ArffHelper;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;
import weka.core.Instances;

public class FileKnowledgeRepository implements KnowledgeRepository{

    private final String TAG = getClass().getSimpleName();

    private String filePath;
    private KnowledgeDataSet dataSet;
    private Set<String> registeredTasks;
    private ArffHelper arffHelper;


    public FileKnowledgeRepository(String filePath){
        this.registeredTasks = new HashSet<>();
        this.filePath = filePath;
        this.arffHelper = new ArffHelper();
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
        try {
            Instances set = arffHelper.load(filePath);
            dataSet.setDataSet(set);
            return dataSet;
        } catch (IOException e) {}
        return new KnowledgeDataSet(registeredTasks);
    }

    @Override
    public void addKnowledgeInstance(KnowledgeInstance instance) {
        dataSet.addKnowledgeInstance(instance);
        Instances instances = dataSet.getDataSet();
        arffHelper.save(filePath, instances);
        Log.i(TAG, "Added new instance to knowledge repository");
        Log.d(TAG, instances.toString());
    }

    @Override
    public void clearKnowledgeDataSet() {
        arffHelper.clear(filePath, dataSet.getDataSet());
    }

    @Override
    public void registerTask(String name) {
        registeredTasks.add(name);
        KnowledgeDataSet newSet =  new KnowledgeDataSet(registeredTasks);
        Instances newInstances = newSet.getDataSet();
        Instances newDataSet = new InstanceTransformer(dataSet).toNewKnowledgeDataSetFormat(newInstances);
        dataSet.setDataSet(newDataSet);
        arffHelper.save(filePath, newDataSet);
    }

    @Override
    public boolean isRegistered(String name) {
        return registeredTasks.contains(name);
    }
}
